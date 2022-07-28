package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

public class Cum extends Module {
    private static final ResourceLocation christianity = new ResourceLocation("jesusclient", "boob/boob_1.png");
    private static final float width = 256.0f;
    private static final float height = 256.0f;
    private static float x = (JesusClient.mc.displayWidth / 2) - (width / 2);
    private static float y = (JesusClient.mc.displayHeight / 2) - (height / 2);
    private static long t = System.currentTimeMillis();
    private static long end = t+1000;

    private boolean isCumming;
    private long lastKeyPress = 0;

    public Cum() {
        super("Cum", JesusClient.config.cumEnabled);
    }

    @SubscribeEvent
    public void onInputKey(InputEvent.KeyInputEvent event) {
        if (JesusClient.config.cumEnabled && JesusClient.keyBindings[1].isPressed()) {
            isCumming = true;
            lastKeyPress = System.currentTimeMillis();
            int oldSlot = JesusClient.mc.thePlayer.inventory.currentItem;
            if (!JesusClient.config.cumInvMode) {
                for (int i = 0; i < 9; i++) {
                    ItemStack a = JesusClient.mc.thePlayer.inventory.getStackInSlot(i);
                    if (a != null && a.getDisplayName().toLowerCase().contains("snowball"))
                        Utils.throwSlot(i);
                }
            } else {
                int ballSlot = Utils.getAvailableHotbarSlot("Snowball");
                if (ballSlot == -1 || Utils.getAllSlots(ballSlot, "Snowball").size() == 0) {
                    return;
                }
                Utils.throwSlot(ballSlot);
                for (Iterator<Integer> iterator = Utils.getAllSlots(ballSlot, "Snowball").iterator(); iterator.hasNext(); ) {
                    int slotNum = ((Integer)iterator.next()).intValue();
                    ItemStack curInSlot = JesusClient.mc.thePlayer.inventory.getStackInSlot(ballSlot);
                    if (curInSlot == null)
                        JesusClient.mc.playerController.windowClick(JesusClient.mc.thePlayer.inventoryContainer.windowId, slotNum, ballSlot, 2, (EntityPlayer)JesusClient.mc.thePlayer);
                    Utils.throwSlot(ballSlot);
                }
            }
            if (JesusClient.config.cumMainSlot > 0 && JesusClient.config.cumMainSlot <= 8) {
                JesusClient.mc.thePlayer.inventory.currentItem = JesusClient.config.cumMainSlot - 1;
            } else {
                JesusClient.mc.thePlayer.inventory.currentItem = oldSlot;
            }
            if (JesusClient.config.cumStash)
                JesusClient.mc.thePlayer.sendChatMessage("/pickupstash");
        }
    }

    @SubscribeEvent
    public void t(TickEvent.ClientTickEvent t) {
        if (isCumming && System.currentTimeMillis() - lastKeyPress > 500)
            isCumming = false;
    }

    @SubscribeEvent
    public void boobies(TickEvent.RenderTickEvent event) {
        if (JesusClient.config.boob && isCumming) {
            Utils.drawImage(christianity, x, y, width, height, 100);
        }
    }
}
