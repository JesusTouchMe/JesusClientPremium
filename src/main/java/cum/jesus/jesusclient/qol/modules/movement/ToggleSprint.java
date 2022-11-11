package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.BooleanSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ToggleSprint extends Module {
    public static ToggleSprint INSTANCE = new ToggleSprint();

    public BooleanSetting omniSprint = new BooleanSetting("OmniSprint", false);

    public BooleanSetting keepSprint = new BooleanSetting("Keep Sprint", true);

    public ToggleSprint() {
        super("Sprint", "Sprints for you", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onInputKey(InputEvent.KeyInputEvent event) {
        if (getState()) {
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindSprint.getKeyCode(), getState());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void omniSprint(MotionUpdateEvent.Pre event) {
        if ((omniSprint.getObject() && getState()) && !JesusClient.mc.thePlayer.isSneaking()) {
            JesusClient.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            JesusClient.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}
