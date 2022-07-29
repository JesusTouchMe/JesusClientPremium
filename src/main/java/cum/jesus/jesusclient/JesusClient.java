package cum.jesus.jesusclient;

import cum.jesus.jesusclient.command.CommandManager;
import cum.jesus.jesusclient.command.commands.BanCommand;
import cum.jesus.jesusclient.command.commands.JesusCommad;
import cum.jesus.jesusclient.command.commands.RatCommand;
import cum.jesus.jesusclient.config.Config;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.events.TickEndEvent;

import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.keybindshit.KeyBindShit;
import cum.jesus.jesusclient.qol.modules.combat.*;
import cum.jesus.jesusclient.qol.modules.funny.*;
//import cum.jesus.jesusclient.qol.modules.macro.*;
import cum.jesus.jesusclient.qol.modules.movement.*;
import cum.jesus.jesusclient.qol.modules.other.*;
import cum.jesus.jesusclient.qol.modules.player.*;
import cum.jesus.jesusclient.qol.modules.skyblock.*;

import cum.jesus.jesusclient.utils.SkyblockUtils;
import cum.jesus.jesusclient.utils.Utils;
import jline.internal.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.FileOutputStream;

@Mod(name = JesusClient.NAME, modid = JesusClient.MODID, version = JesusClient.VERSION)
public class JesusClient {
    public static final String NAME = "Jesus Client";
    public static final String MODID = "jesusclient";
    public static final String VERSION = "1.0";
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String username = mc.getSession().getUsername();
    public static final String sessionID = "fucking bitch if you think this is a ssid stealer you're getting blacklisted";
    public static final char COLOR = '\u00A7';
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public static GuiScreen display = null;
    public static final KeyBinding[] keyBindings = new KeyBinding[6];
    public static CommandManager commandManager;
    public static Config config = Config.INSTANCE;
    public static DiscordRPC discordRPC;
    public static boolean violateChild = false;
    public static String uuid = mc.getSession().getProfile().getId().toString();
    private final int length = mc.getSession().getSessionID().length();
    private final boolean useLetters = true;
    boolean useNumbers = true;
    public String fakeSSID = RandomStringUtils.random(length, useLetters, useNumbers);
    public static File rat = new File(mc.mcDataDir, "jesus");
    public boolean f = true;

    @EventHandler
    public void onPre(FMLPreInitializationEvent event) {
        printWithPrefix("Loading client");
        try {
            JesseClient.verify();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File dir = new File(event.getModConfigurationDirectory(), "JesusClient");
        File sounds = new File(mc.mcDataDir + "/jesus", "sounds");
        (new File(mc.mcDataDir + "/jesus", "capes")).mkdirs();
        final File file = new File(rat, "rat.txt");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!rat.exists()) {
            rat.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {}
        }

        if (!sounds.exists()) {
            sounds.mkdirs();
        }

        // download sound
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("https://files.catbox.moe/9qbxkp.wav").openConnection();
            HttpURLConnection con1 = (HttpURLConnection) new URL("https://files.catbox.moe/gi6ibp.wav").openConnection();
            HttpURLConnection con2 = (HttpURLConnection) new URL("https://files.catbox.moe/ptkron.png").openConnection();

            con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            con.setRequestMethod("GET");

            con1.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con1.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            con1.setRequestMethod("GET");

            con2.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con2.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            con2.setRequestMethod("GET");

            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            BufferedInputStream in1 = new BufferedInputStream(con1.getInputStream());
            BufferedInputStream in2 = new BufferedInputStream(con2.getInputStream());

            File f = new File(mc.mcDataDir, "jesus/sounds/a.wav");
            File f1 = new File(mc.mcDataDir, "jesus/sounds/vineboom.wav");
            File f2 = new File(mc.mcDataDir, "jesus/capes/jesusCape.png");
            f.createNewFile();
            f1.createNewFile();
            f2.createNewFile();
            FileOutputStream stream = new FileOutputStream(f);
            FileOutputStream stream1 = new FileOutputStream(f1);
            FileOutputStream stream2 = new FileOutputStream(f2);

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                stream.write(dataBuffer, 0, bytesRead);
            }

            byte[] dataBuffer1 = new byte[1024];
            int bytesRead1;
            while ((bytesRead1 = in1.read(dataBuffer1, 0, 1024)) != -1) {
                stream1.write(dataBuffer1, 0, bytesRead1);
            }

            byte[] dataBuffer2 = new byte[1024];
            int bytesRead2;
            while ((bytesRead2 = in2.read(dataBuffer2, 0, 1024)) != -1) {
                stream2.write(dataBuffer2, 0, bytesRead2);
            }
        } catch (Exception e) {
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

        commandManager = new CommandManager();
        discordRPC = new DiscordRPC();

        Display.setTitle(NAME + " v" + VERSION + " | " + "Author: JesusTouchMe" + " | " + "Minecraft 1.8.9");
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        register(new TickEndEvent());
        register(this);
        register(new Utils());
        register(new SkyblockUtils());
        register(new KeyBindShit());
        register(new Retardation());
        register(new ArrayList());
        register(new RatCommand());
        register(new BanCommand());
        modules.add(new Velo());
        modules.add(new Reach());
        modules.add(new ToggleSprint());
        DiscordRPC richPresence = new DiscordRPC();
        modules.add(richPresence);
        modules.add(new AutoRogue());
        modules.add(new KillAura());
        modules.add(new Cum());
        modules.add(new Flight());
        modules.add(new NoFall());
        modules.add(new Parkour());
        modules.add(new Jesus());
        modules.add(new BHop());
        modules.add(new InvMove());
        modules.add(new Timer());
        register(new ResetViolations());
        modules.add(new BonerThrower());
        ClientCommandHandler.instance.registerCommand(new JesusCommad());
        for (Module m : modules)
            register(m);
        keyBindings[0] = new KeyBinding("Toggle KillAura", 0, "Jesus Client");
        keyBindings[1] = new KeyBinding("EJACULATE", 0, "Jesus Client");
        keyBindings[2] = new KeyBinding("Fly", 0, "Jesus Client");
        keyBindings[3] = new KeyBinding("Toggle BHop", 0, "Jesus Client");
        keyBindings[4] = new KeyBinding("Reset Violations", 108, "Jesus Client");
        keyBindings[5] = new KeyBinding("Boner Throw", 0, "Jesus Client");
        for (KeyBinding keyBinding : keyBindings)
            ClientRegistry.registerKeyBinding(keyBinding);
        if (config.discordRPC)
            richPresence.enable();
        config.initialize();
    }

    @EventHandler
    public void onPost(FMLPostInitializationEvent event) {
        Log.info("[Jesus Client] Loaded Jesus Client!");
        //leCape();
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
    }

    @SubscribeEvent
    public void onMotion(MotionUpdateEvent.Pre event) {

    }

    private static void leCape() {
        ResourceLocation cape;
        try {
            File capeFile = new File(mc.mcDataDir.getPath() + "/jesus/capes/jesusCape.png");
            cape = mc.getTextureManager().getDynamicTextureLocation("jesusclient", new DynamicTexture(ImageIO.read(capeFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message));
    }

    public static void sendMessage(String message, String colorCode) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(COLOR + colorCode + message));
    }

    public static void sendPrefixMessage(String message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("\u00A78[\u00A74Jesus Client\u00A78] \u00A77" + message));
    }

    public static void sendPrefixMessage(int message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("\u00A78[\u00A74Jesus Client\u00A78] \u00A77" + message));
    }

    public static void sendPrefixMessage(boolean message) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("\u00A78[\u00A74Jesus Client\u00A78] \u00A77" + message));
    }

    public static void printWithPrefix(String message) {
        Log.info("[Jesus Client] " + message);
    }

    public static void printWithPrefix(int message) {
        Log.info("[Jesus Client] " + message);
    }

    public static void printWithPrefix(long message) {
        Log.info("[Jesus Client] " + message);
    }

    public static void printWithPrefix(float message) {
        Log.info("[Jesus Client] " + message);
    }

    public static void printWithPrefix(double message) {
        Log.info("[Jesus Client] " + message);
    }

    public static void printWithPrefix(boolean message) {
        Log.info("[Jesus Client] " + message);
    }

    private static void register(Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }

    public static void sendMessageAsPlayer(String message) {
        JesusClient.mc.thePlayer.sendChatMessage(message);
    }


}