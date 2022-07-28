package cum.jesus.jesusclient.qol.modules.other;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResetViolations {
    public static int jumps = 0;

    @SubscribeEvent
    public void onMotion(MotionUpdateEvent.Pre e) {
        if (JesusClient.violateChild) {
            if (done(jumps, 10.0D) <= 0) {
                if (notZero(JesusClient.mc.thePlayer.onGround ? 1 : 0)) {
                    jumps += 1;
                    JesusClient.mc.thePlayer.motionY = 0.11;
                }
            } else {
                JesusClient.violateChild = false;
            }
        }
    }

    private static int done(double d, double d2) {
        return d == d2 ? 0 : (d < d2 ? -1 : 1);
    }

    private static boolean notZero(int n) {
        return n != 0;
    }
}
