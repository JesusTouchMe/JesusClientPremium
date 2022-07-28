package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ToggleSprint extends Module {
    public ToggleSprint() {
        super("Sprint", JesusClient.config.toggleSprint);
    }

    @SubscribeEvent
    public void onInputKey(InputEvent.KeyInputEvent event) {
        if (JesusClient.config.toggleSprint) {
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindSprint.getKeyCode(), JesusClient.config.toggleSprint);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void omniSprint(MotionUpdateEvent.Pre event) {
        if ((JesusClient.config.omniSprint && JesusClient.config.toggleSprint) && !JesusClient.mc.thePlayer.isSneaking()) {
            JesusClient.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            JesusClient.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}
