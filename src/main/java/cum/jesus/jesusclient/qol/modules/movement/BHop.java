package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BHop extends Module {
    public BHop() {
        super("B-Hop", JesusClient.config.bHop);
    }

    @SubscribeEvent
    public void onUpdate(MotionUpdateEvent.Pre e) {
        //Log.info(!JesusClient.config.flight + " ," + Utils.isMoving() + " ," + !JesusClient.mc.thePlayer.isInWater() + " ," + JesusClient.config.bHop);
        if (!JesusClient.config.flight && Utils.isMoving() && !JesusClient.mc.thePlayer.isInWater() && JesusClient.config.bHop) {
            double spd = 0.0025D * (double)(JesusClient.config.bHopSpeed);
            if (JesusClient.config.bHopMode == 2)
                spd = 0.0125D * (double)(JesusClient.config.bHopSpeed);
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindJump.getKeyCode(), false);
            JesusClient.mc.thePlayer.noClip = true;
            if (JesusClient.mc.thePlayer.onGround) {
                switch (JesusClient.config.bHopMode) {
                    case 0: // 1 block jumps
                        JesusClient.mc.thePlayer.motionY += 0.45f;
                        break;
                    case 1: // normal jump
                        JesusClient.mc.thePlayer.jump();
                        break;
                    case 2: // short ahh jump
                        JesusClient.mc.thePlayer.motionY += 0.25f;
                        break;
                }
            }
            JesusClient.mc.thePlayer.setSprinting(true);
            double h = (float)(Math.sqrt(JesusClient.mc.thePlayer.motionX * JesusClient.mc.thePlayer.motionX + JesusClient.mc.thePlayer.motionZ * JesusClient.mc.thePlayer.motionZ) + spd);
            Utils.bob(h);
        }
    }
}
