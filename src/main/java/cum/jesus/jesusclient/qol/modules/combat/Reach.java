package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraft.util.Vec3;
import cum.jesus.jesusclient.JesusClient;

public class Reach extends Module {
    private static double reachAmount = JesusClient.config.reachAmount;

    public Reach() {
        super("Reach", JesusClient.config.reach);
    }

    public static double distanceTo(Vec3 instance, Vec3 vec) {
        return (JesusClient.config.reach && instance.distanceTo(vec) + (0.0D) <= reachAmount ? 2.9000000953674316D : (instance
                .distanceTo(vec) + (0.0D)));
    }
}
