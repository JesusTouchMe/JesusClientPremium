package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.NumberSetting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flight extends Module {
    public static Flight INSTANCE = new Flight();

    public NumberSetting<Float> flySpeed = new NumberSetting<>("Flight Speed", 1.0f, 0.1f, 5.0f);

    public Flight() {
        super("Flight", "Makes you fly", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(MotionUpdateEvent.Pre pre) {
        if (getState()) {
            JesusClient.mc.thePlayer.capabilities.isFlying = true;
            if(JesusClient.mc.gameSettings.keyBindJump.isPressed()) {
                JesusClient.mc.thePlayer.motionY += flySpeed.getObject();
            }

            if(JesusClient.mc.gameSettings.keyBindSneak.isPressed()) {
                JesusClient.mc.thePlayer.motionY -= flySpeed.getObject();
            }

            if(JesusClient.mc.gameSettings.keyBindForward.isPressed()) {
                JesusClient.mc.thePlayer.capabilities.setFlySpeed(flySpeed.getObject());
            }
        } else {
            JesusClient.mc.thePlayer.capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (getState()) {
            toggle();

        }
    }
}
