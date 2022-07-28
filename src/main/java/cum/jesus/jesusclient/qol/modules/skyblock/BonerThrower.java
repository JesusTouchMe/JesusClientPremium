package cum.jesus.jesusclient.qol.modules.skyblock;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.Iterator;

public class BonerThrower extends Module {
    public BonerThrower() {
        super("Boner Throw", JesusClient.config.boner);
    }

    @SubscribeEvent
    public void onInputKey(InputEvent.KeyInputEvent event) {
        if (JesusClient.config.boner && JesusClient.keyBindings[5].isPressed()) {
            int oldSlot = JesusClient.mc.thePlayer.inventory.currentItem;
            if (!JesusClient.config.bonerInvMode) {
                (new Thread(() -> {
                    for (int i = 0; i < 9; i++) {
                        ItemStack a = JesusClient.mc.thePlayer.inventory.getStackInSlot(i);
                        if (a != null && a.getDisplayName().toLowerCase().contains("bonemerang"))
                            Utils.throwSlot(i);
                        try {
                            Thread.sleep(JesusClient.config.bonerDelay);
                        } catch (InterruptedException e) {}
                    }
                })).start();
            } else {

            }
            if (JesusClient.config.bonerMainSlot > 0 && JesusClient.config.bonerMainSlot <= 8) {
                JesusClient.mc.thePlayer.inventory.currentItem = JesusClient.config.bonerMainSlot - 1;
            } else {
                JesusClient.mc.thePlayer.inventory.currentItem = oldSlot;
            }
        }
    }
}
