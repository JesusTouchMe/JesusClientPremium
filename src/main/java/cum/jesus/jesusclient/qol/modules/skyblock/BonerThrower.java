package cum.jesus.jesusclient.qol.modules.skyblock;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.BooleanSetting;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class BonerThrower extends Module {
    public BooleanSetting invMode = new BooleanSetting("Inv mode", false);

    public NumberSetting<Integer> delay = new NumberSetting<>("Delay", 60, 0, 1000);

    public NumberSetting<Integer> mainSlot = new NumberSetting<>("Main slot", 0, 0, 8);

    public BonerThrower() {
        super("Boner thrower", "Throws bones", Category.SKYBLOCK);
    }

    public void onEnable() {
        int oldSlot = JesusClient.mc.thePlayer.inventory.currentItem;
        if (!invMode.getObject()) {
            (new Thread(() -> {
                for (int i = 0; i < 9; i++) {
                    ItemStack a = JesusClient.mc.thePlayer.inventory.getStackInSlot(i);
                    if (a != null && a.getDisplayName().toLowerCase().contains("bonemerang"))
                        Utils.throwSlot(i);
                    try {
                        Thread.sleep(delay.getObject());
                    } catch (InterruptedException e) {}
                }
            })).start();
        } else {

        }
        if (mainSlot.getObject() > 0 && mainSlot.getObject() <= 8) {
            JesusClient.mc.thePlayer.inventory.currentItem = mainSlot.getObject() - 1;
        } else {
            JesusClient.mc.thePlayer.inventory.currentItem = oldSlot;
        }

        setState(false);
    }
}
