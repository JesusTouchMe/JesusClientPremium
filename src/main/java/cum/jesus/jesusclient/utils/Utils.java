package cum.jesus.jesusclient.utils;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.TickEndEvent;
import jline.internal.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.event.world.WorldEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
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

    public static void drawImage(ResourceLocation location, float x, float y, float width, float height, float opacity) {
        if (location != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(location);
            GlStateManager.color(1, 1, 1, opacity);
            Utils.drawTexturedRect(x, y, width, height, GL11.GL_LINEAR);
        }
    }

    public static void drawTexturedRect(float x, float y, float width, float height, int filter) {
        drawTexturedRect(x, y, width, height, 0f, 1f, 0f , 1f, filter);
    }

    public static void drawTexturedRect(float x, float y, float width, float height, float uMin, float uMax, float vMin, float vMax, int filter) {
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedRectNoBlend(x, y, width, height, uMin, uMax, vMin, vMax, filter);
    }

    public static void drawTexturedRectNoBlend(float x, float y, float width, float height, float uMin, float uMax, float vMin, float vMax, int filter) {
        GlStateManager.enableTexture2D();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer
                .pos(x, y+height, 0.0D)
                .tex(uMin, vMax).endVertex();
        worldrenderer
                .pos(x+width, y+height, 0.0D)
                .tex(uMax, vMax).endVertex();
        worldrenderer
                .pos(x+width, y, 0.0D)
                .tex(uMax, vMin).endVertex();
        worldrenderer
                .pos(x, y, 0.0D)
                .tex(uMin, vMin).endVertex();
        tessellator.draw();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
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

    public static ResourceLocation fileToRL(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {}
        
        return JesusClient.mc.getRenderManager().renderEngine.getDynamicTextureLocation(file.getName(), new DynamicTexture(img));
    }

    @Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H&\006\006"}, d2 = {"Lcum/jesus/jesusclient/utils/Utils$Collidable;", "", "collideBlock", "", "block", "Lnet/minecraft/block/Block;", "JesusClient"})
    public static interface Collidable {
        boolean collideBlock(@Nullable Block param1Block);
    }
}
