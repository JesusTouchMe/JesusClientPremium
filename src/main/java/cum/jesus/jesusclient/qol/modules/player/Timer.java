package cum.jesus.jesusclient.qol.modules.player;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.UpdateEvent;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Timer extends Module {
    public Timer() {
        super("Timer", JesusClient.config.timer);
    }

    @SubscribeEvent
    public void onTick(UpdateEvent e) {
        if (JesusClient.config.timer && JesusClient.display != (GuiScreen)JesusClient.config.gui()) {
            (Utils.getTimer()).timerSpeed = (float)JesusClient.config.timerMult;
        } else {
            Utils.resetTimer();
        }
    }
}
