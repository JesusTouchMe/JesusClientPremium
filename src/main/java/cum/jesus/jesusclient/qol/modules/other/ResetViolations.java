package cum.jesus.jesusclient.qol.modules.other;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResetViolations extends Module {
    private int jumps = 0;

    private NumberSetting<Integer> jumpAmount = new NumberSetting<>("Jump amount", 10, 1, 50);

    public ResetViolations() {
        super("Reset violations", "Resets violations on hypixel by jumping a few times", Category.OTHER);
    }

    public void onEnable() {
        if (jumps <= jumpAmount.getObject()) {
            if (JesusClient.mc.thePlayer.onGround) {
                jumps += 1;
                JesusClient.mc.thePlayer.motionY = 0.11;
            }
        } else {
            setState(false);
        }
    }

    public void onDisable() {
        jumps = jumpAmount.getObject();
    }
}
