package cum.jesus.jesusclient.qol.modules.render;

import cum.jesus.jesusclient.gui.config.clickgui.ClickGui;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.BooleanSetting;
import cum.jesus.jesusclient.qol.settings.StringSetting;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {
    public static StringSetting prefix = new StringSetting("Command Prefix", "-");

    public static BooleanSetting hideNotifs = new BooleanSetting("Hide Notifications", false);

    public ClickGuiModule() {
        super("ClickGUI", "Config GUI", Category.RENDER, true, false, Keyboard.KEY_F4);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(ClickGui.INSTANCE);
        setStateNoNotif(false);
    }

    @Override
    public boolean shouldNotify() {
        return false;
    }
}
