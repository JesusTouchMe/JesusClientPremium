package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flight extends Module {
    public Flight() {
        super("FlyHack", JesusClient.config.flight);
    }

    @SubscribeEvent
    public void onUpdate(MotionUpdateEvent.Pre pre) {
        if (JesusClient.config.flight) {
            JesusClient.mc.thePlayer.capabilities.isFlying = true;
            if(JesusClient.mc.gameSettings.keyBindJump.isPressed()) {
                JesusClient.mc.thePlayer.motionY += JesusClient.config.flySpeed;
            }

            if(JesusClient.mc.gameSettings.keyBindSneak.isPressed()) {
                JesusClient.mc.thePlayer.motionY -= JesusClient.config.flySpeed;
            }

            if(JesusClient.mc.gameSettings.keyBindForward.isPressed()) {
                JesusClient.mc.thePlayer.capabilities.setFlySpeed(JesusClient.config.flySpeed);
            }
        } else {
            JesusClient.mc.thePlayer.capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (JesusClient.config.flight) {
            JesusClient.config.flight = !JesusClient.config.flight;

        }
    }
}
