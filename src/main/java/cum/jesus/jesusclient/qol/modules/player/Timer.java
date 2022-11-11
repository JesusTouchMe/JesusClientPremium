package cum.jesus.jesusclient.qol.modules.player;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.UpdateEvent;
import cum.jesus.jesusclient.gui.config.clickgui.ClickGui;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Timer extends Module {
    public NumberSetting<Float> multiplier = new NumberSetting<>("Timer multiplier", 1.0f, 0.1f, 5.0f);

    public Timer() {
        super("Timer", "Makes game speed faster (client side)", Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(UpdateEvent e) {
        if (getState() && JesusClient.display != (GuiScreen) ClickGui.INSTANCE) {
            (Utils.getTimer()).timerSpeed = multiplier.getObject();
        } else {
            Utils.resetTimer();
        }
    }
}
