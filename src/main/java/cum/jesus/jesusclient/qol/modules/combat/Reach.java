package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import net.minecraft.util.Vec3;
import cum.jesus.jesusclient.JesusClient;

public class Reach extends Module {
    public static Reach INSTANCE = new Reach();
    public static NumberSetting<Double> reachAmount = new NumberSetting<>("Reach amount", 3.0D, 2.0D, 4.5D);

    public Reach() {
        super("Reach", "Hits entities from further away", Category.COMBAT);
    }
}
