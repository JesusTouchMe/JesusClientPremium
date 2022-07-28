package cum.jesus.jesusclient.qol.modules.keybindshit;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.other.ResetViolations;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyBindShit {
    @SubscribeEvent
    public void onInputKey(InputEvent.KeyInputEvent event) {
        if (JesusClient.keyBindings[0].isPressed()) {
            JesusClient.config.killAura = !JesusClient.config.killAura;
            JesusClient.sendPrefixMessage(Utils.getColouredBoolean(JesusClient.config.killAura) + "\u00A77 KillAura");
        }
        if (JesusClient.keyBindings[2].isPressed()) {
            JesusClient.config.flight = !JesusClient.config.flight;
            JesusClient.sendPrefixMessage(Utils.getColouredBoolean(JesusClient.config.flight) + "\u00A77 Fly Hack");
        }
        if (JesusClient.keyBindings[3].isPressed()) {
            JesusClient.config.bHop = !JesusClient.config.bHop;
            JesusClient.sendPrefixMessage(Utils.getColouredBoolean(JesusClient.config.bHop) + "\u00A77 BHop");
        }
        if (JesusClient.keyBindings[4].isPressed()) {
            if (!JesusClient.violateChild) {
                JesusClient.violateChild = true;
                ResetViolations.jumps = 0;
            } else {
                JesusClient.violateChild = false;
                ResetViolations.jumps = 10;
            }
        }
    }
}
