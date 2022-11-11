package cum.jesus.jesusclient.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.TickEndEvent;
import jline.internal.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.*;
import java.security.PublicKey;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.util.Timer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Utils {
    public static FontRenderer fr = JesusClient.mc.fontRendererObj;
    public static boolean inSkyBlock = false;
    public static boolean inDungeon = false;
    public static boolean forceSkyBlock = false;
    public static boolean forceDungeon = false;

    public static String getColouredBoolean(boolean bool) {
        return bool ? (EnumChatFormatting.GREEN + "Enabled") : (EnumChatFormatting.RED + "Disabled");
    }

    public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return "";
    }

    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    private int ticks = 0;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(forceDungeon || forceSkyBlock) {
            if(forceSkyBlock) inSkyBlock = true;
            if(forceDungeon) inSkyBlock = true; inDungeon = true;
            return;
        }

        if(ticks % 20 == 0) {
            if(JesusClient.mc.thePlayer != null && JesusClient.mc.theWorld != null) {
                ScoreObjective scoreboardObj = JesusClient.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                if(scoreboardObj != null) {
                    inSkyBlock = removeFormatting(scoreboardObj.getDisplayName()).contains("SKYBLOCK");
                }

                inDungeon =
                        inSkyBlock &&
                                ScoreBoardUtils.scoreboardContains("The Catacombs") &&
                                !ScoreBoardUtils.scoreboardContains("Queue") ||
                                ScoreBoardUtils.scoreboardContains("Dungeon Cleared:");
            }
            ticks = 0;
        }
        ticks++;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        forceDungeon = false;
        forceSkyBlock = false;
    }

    public static void throwSlot(int slot) {
        ItemStack curInSlot = JesusClient.mc.thePlayer.inventory.getStackInSlot(slot);
        if (curInSlot != null)
            if (curInSlot.getDisplayName().contains("Snowball")) {
                int ss = curInSlot.stackSize;
                for (int i = 0; i < ss; i++) {
                    JesusClient.mc.thePlayer.inventory.currentItem = slot;
                    JesusClient.mc.playerController.sendUseItem((EntityPlayer)JesusClient.mc.thePlayer, (World)JesusClient.mc.theWorld, JesusClient.mc.thePlayer.inventory.getStackInSlot(slot));
                }
            } else {
                JesusClient.mc.thePlayer.inventory.currentItem = slot;
                JesusClient.mc.playerController.sendUseItem((EntityPlayer)JesusClient.mc.thePlayer, (World)JesusClient.mc.theWorld, JesusClient.mc.thePlayer.inventory.getStackInSlot(slot));
            }
    }

    public static int getAvailableHotbarSlot(String name) {
        for (int i = 0; i < 9; i++) {
            ItemStack is = JesusClient.mc.thePlayer.inventory.getStackInSlot(i);
            if (is == null || is.getDisplayName().contains(name))
                return i;
        }
        return -1;
    }

    public static List<Integer> getAllSlots(int throwSlot, String name) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 9; i < 44; i++) {
            ItemStack is = ((Slot)JesusClient.mc.thePlayer.inventoryContainer.inventorySlots.get(i)).getStack();
            if (is != null && is.getDisplayName().contains(name) && i - 36 != throwSlot)
                ret.add(Integer.valueOf(i));
        }
        return ret;
    }
    private static <T extends Throwable> T sanitizeStackTrace(T throwable) {
        return sanitizeStackTrace(throwable, Utils.class.getName());
    }

    static <T extends Throwable> T sanitizeStackTrace(T throwable, String classNameToDrop) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;
        for (int i = 0; i < size; i++) {
            if (classNameToDrop.equals(stackTrace[i].getClassName()))
                lastIntrinsic = i;
        }
        StackTraceElement[] newStackTrace = Arrays.<StackTraceElement>copyOfRange(stackTrace, lastIntrinsic + 1, size);
        throwable.setStackTrace(newStackTrace);
        return throwable;
    }

    private static void throwParameterIsNullException(String paramName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[3];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        IllegalArgumentException exception = new IllegalArgumentException("Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + paramName);
        throw (IllegalArgumentException)sanitizeStackTrace(exception);
    }

    public static void checkParameterIsNotNull(Object value, String paramName) {
        if (value == null)
            throwParameterIsNullException(paramName);
    }

    public static void checkExpressionValueIsNotNull(Object value, String expression) {
        if (value == null)
            throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(expression + " must not be null"));
    }

    @Nullable
    public static final Block getBlock(@Nullable BlockPos blockPos) {
        JesusClient.mc.theWorld.getBlockState(blockPos);
        return (JesusClient.mc.theWorld != null && JesusClient.mc.theWorld.getBlockState(blockPos) != null) ? JesusClient.mc.theWorld.getBlockState(blockPos).getBlock() : null;
    }

    public static final boolean collideBlock(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Collidable collide) {
        checkParameterIsNotNull(axisAlignedBB, "axisAlignedBB");
        checkParameterIsNotNull(collide, "collide");
        checkExpressionValueIsNotNull(JesusClient.mc.thePlayer, "mc.thePlayer");
        int i = MathHelper.floor_double((JesusClient.mc.thePlayer.getEntityBoundingBox()).minX);
        checkExpressionValueIsNotNull(JesusClient.mc.thePlayer, "mc.thePlayer");
        for (int j = MathHelper.floor_double((JesusClient.mc.thePlayer.getEntityBoundingBox()).maxX) + 1; i < j; i++) {
            checkExpressionValueIsNotNull(JesusClient.mc.thePlayer, "mc.thePlayer");
            int k = MathHelper.floor_double((JesusClient.mc.thePlayer.getEntityBoundingBox()).minZ);
            checkExpressionValueIsNotNull(JesusClient.mc.thePlayer, "mc.thePlayer");
            for (int m = MathHelper.floor_double((JesusClient.mc.thePlayer.getEntityBoundingBox()).maxZ + 1); k < m; k++) {
                Block block = getBlock(new BlockPos(i, axisAlignedBB.minY, k));
                if (!collide.collideBlock(block))
                    return false;
            }
        }
        return true;
    }

    public static boolean isMoving() {
        return (JesusClient.mc.thePlayer.moveForward != 0.0F || JesusClient.mc.thePlayer.moveStrafing != 0.0F);
    }

    public static void bob(double s) {
        double forward = JesusClient.mc.thePlayer.movementInput.moveForward;
        double strafe = JesusClient.mc.thePlayer.movementInput.moveStrafe;
        float yaw = JesusClient.mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            JesusClient.mc.thePlayer.motionX = 0.0D;
            JesusClient.mc.thePlayer.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += ((forward > 0.0D) ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += ((forward > 0.0D) ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }
            double rad = Math.toRadians((yaw + 90.0F));
            double sin = Math.sin(rad);
            double cos = Math.cos(rad);

            JesusClient.mc.thePlayer.motionX = forward * s * cos + strafe * s * sin;
            JesusClient.mc.thePlayer.motionZ = forward * s * sin - strafe * s * cos;
        }
    }

    public static Timer getTimer() {
        return (Timer)ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new String[] { "timer", "field_71428_T" });
    }

    public static void resetTimer() {
        try {
            (getTimer()).timerSpeed = 1.0f;
        } catch (NullPointerException nullPointerException) {}
    }

    public static final void playSound(@NotNull File file, int volume) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (file.exists())
            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                if (clip.getControl(FloatControl.Type.MASTER_GAIN) == null) {
                    clip.getControl(FloatControl.Type.MASTER_GAIN);
                    throw new NullPointerException("null cannot be cast to non-null type javax.sound.sampled.FloatControl");
                }
                FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(volume);
                clip.start();
            } catch (Exception e) {
                Log.info("fail");
            }
    }

    public static ResourceLocation fileToRL(File file) throws IOException { // crash should be fixed
        return JesusClient.mc.getTextureManager().getDynamicTextureLocation("jesusclient", new DynamicTexture(ImageIO.read(file)));
    }

    public static ArrayList<String> getPlayersInLobby() {
        Collection<NetworkPlayerInfo> infoMap = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        return new ArrayList<>();
    }

    public static class API {
        public static JsonObject getPlayerInfo(String uuid, String apiKey) {
            try {
                JsonObject d = (new JsonParser()).parse(new InputStreamReader((new URL(String.format("https://api.hypixel.net/player?uuid=%s&key=%s", new Object[] { uuid, apiKey }))).openStream())).getAsJsonObject();
                if (d.get("player") instanceof com.google.gson.JsonNull)
                    return null;
                return d.getAsJsonObject("player");
            } catch (Exception e) {
                return null;
            }
        }

        public static String getName(JsonObject player) {
            try {
                return player.get("displayname").getAsString();
            } catch (Exception e) {
                return "";
            }
        }

        public static String getRank(JsonObject player) {
            String var = "";
            try {
                switch (player.get("newPackageRank").getAsString()) {
                    case "VIP":
                        var = "§a[VIP] ";
                        break;
                    case "VIP_PLUS":
                        var = "§a[VIP§6+§a] ";
                        break;
                    case "MVP":
                        var = "§b[MVP] ";
                        break;
                    case "MVP_PLUS":
                        var = "§b[MVP§4+§b] ";
                        break;
                }
                if (Objects.equals(player.get("newPackageRank").getAsString(), "MVP_PLUS") && Objects.equals(player.get("monthlyPackageRank").getAsString(), "SUPERSTAR")) {
                    var = "§6[MVP§0++§6] ";
                }
            } catch (Exception e) {
                var = "non";
            }

            return var;
        }

        public static String fullName(JsonObject player) {
            String rank = getRank(player);
            String name = getName(player);
            if (rank == "non") {
                return "§7" + name + ": ";
            } else {
                return rank + name + "§f: ";
            }
        }
    }

    private static final Logger logger =  LogManager.getLogger("JesusClient");
    private static Field fastRenderField;
    static {
        try {
            fastRenderField = GameSettings.class.getDeclaredField("ofFastRender");
            if (!fastRenderField.isAccessible()) {
                fastRenderField.setAccessible(true);
            }
        } catch (NoSuchFieldException e) {}
    }
    public static Logger getLogger() {
        return logger;
    }

    public static void disableFastRender() {
        try {
            if (fastRenderField != null) {
                if (!fastRenderField.isAccessible())
                    fastRenderField.setAccessible(true);
                fastRenderField.setBoolean(JesusClient.mc.gameSettings, false);
            }
        } catch (IllegalAccessException illegalAccessException) {}
    }

    public static void sendEncryption(NetworkManager networkManager, SecretKey secretKey, PublicKey publicKey, S01PacketEncryptionRequest encryptionRequest) {
        networkManager.sendPacket((Packet)new C01PacketEncryptionResponse(secretKey, publicKey, encryptionRequest.getVerifyToken()), p_operationComplete_1_ -> networkManager.enableEncryption(secretKey), new io.netty.util.concurrent.GenericFutureListener[0]);
    }

    public static ItemStack createItem(String itemArguments) {
        try {
            itemArguments = itemArguments.replace('&', '§');
            Item item = new Item();
            String[] args = null;
            int i = 1;
            int j = 0;
            for (int mode = 0; mode <= Math.min(12, itemArguments.length() - 2); mode++) {
                args = itemArguments.substring(mode).split(Pattern.quote(" "));
                ResourceLocation resourcelocation = new ResourceLocation(args[0]);
                item = (Item)Item.itemRegistry.getObject(resourcelocation);
                if (item != null)
                    break;
            }
            if (item == null)
                return null;
            if (((String[])Objects.requireNonNull(args)).length >= 2 && args[1].matches("\\d+"))
                i = Integer.parseInt(args[1]);
            if (args.length >= 3 && args[2].matches("\\d+"))
                j = Integer.parseInt(args[2]);
            ItemStack itemstack = new ItemStack(item, i, j);
            if (args.length >= 4) {
                StringBuilder NBT = new StringBuilder();
                for (int nbtcount = 3; nbtcount < args.length; nbtcount++)
                    NBT.append(" ").append(args[nbtcount]);
                itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT.toString()));
            }
            return itemstack;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getEnchantment(ItemStack itemStack, Enchantment enchantment) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags())
            return 0;
        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); i++) {
            NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);
            if ((tagCompound.hasKey("ench") && tagCompound.getShort("ench") == enchantment.effectId) || (tagCompound.hasKey("id") && tagCompound.getShort("id") == enchantment.effectId))
                return tagCompound.getShort("lvl");
        }
        return 0;
    }

    public static int getEnchantmentCount(ItemStack itemStack) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags())
            return 0;
        int c = 0;
        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); i++) {
            NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);
            if (tagCompound.hasKey("ench") || tagCompound.hasKey("id"))
                c++;
        }
        return c;
    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H&\006\006"}, d2 = {"Lcum/jesus/jesusclient/utils/Utils$Collidable;", "", "collideBlock", "", "block", "Lnet/minecraft/block/Block;", "JesusClient"})
    public static interface Collidable {
        boolean collideBlock(@Nullable Block param1Block);
    }

    public static Point calculateMouseLocation() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0)
            scale = 1000;
        int scaleFactor = 0;
        while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240)
            scaleFactor++;
        return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
    }
}
