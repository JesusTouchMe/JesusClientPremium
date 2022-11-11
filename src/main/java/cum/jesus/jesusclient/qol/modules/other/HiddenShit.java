package cum.jesus.jesusclient.qol.modules.other;

import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.NumberSetting;

public class HiddenShit extends Module {
    public static HiddenShit INSTANCE = new HiddenShit();

    public static NumberSetting<Integer> aVolume = new NumberSetting<>("a volume", -40, -69, 10);

    public static NumberSetting<Integer> retardMsg = new NumberSetting<>("retard msg", 5000, 1, 10000);

    public HiddenShit() {
        super("Hidden shit", "if you see please report as something is wrong", Category.OTHER, false, true, 0);
    }
}
