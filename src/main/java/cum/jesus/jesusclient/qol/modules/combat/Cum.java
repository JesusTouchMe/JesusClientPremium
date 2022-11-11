package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.BooleanSetting;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import cum.jesus.jesusclient.utils.RenderUtils;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;

public class Cum extends Module {
    private static final ResourceLocation christianity = new ResourceLocation("jesusclient", "boob/boob_1.png");
    private static final float width = 256.0f;
    private static final float height = 256.0f;

    private BooleanSetting cumInvMode = new BooleanSetting("Inv mode", false);
    private NumberSetting<Integer> cumMainSlot = new NumberSetting<>("Main Slot", 0, 0, 8);
    private BooleanSetting cumStash = new BooleanSetting("Pickup Stash", true);

    public Cum() {
        super("Cum", "Throws snowballs very fast", Category.COMBAT, true, false, Keyboard.KEY_NONE);
    }

    public void onEnable() {
        int oldSlot = JesusClient.mc.thePlayer.inventory.currentItem;
        if (!cumInvMode.getObject()) {
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
        if (cumMainSlot.getObject() > 0 && cumMainSlot.getObject() <= 8) {
            JesusClient.mc.thePlayer.inventory.currentItem = cumMainSlot.getObject() - 1;
        } else {
            JesusClient.mc.thePlayer.inventory.currentItem = oldSlot;
        }
        if (cumStash.getObject())
            JesusClient.mc.thePlayer.sendChatMessage("/pickupstash");

        setState(false);
    }
}
