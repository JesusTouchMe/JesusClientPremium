package cum.jesus.jesusclient.qol.modules.render;

import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.StringSetting;

public class NickHider extends Module {
    public static NickHider INSTANCE = new NickHider();

    public StringSetting name = new StringSetting("Name", "Jesus");

    public NickHider() {
        super("Nick hider", "Changes your name (client side)", Category.RENDER);
    }
}
