package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Parkour extends Module {
    public Parkour() {
        super("Parkour", "Jumps on edge of block (why would anyone ever need this)", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(MotionUpdateEvent.Pre pre) {
        if (getState()) {
            if (JesusClient.mc.thePlayer.onGround && !JesusClient.mc.thePlayer.isSneaking() && !JesusClient.mc.gameSettings.keyBindSneak.isPressed() && JesusClient.mc.theWorld.getCollidingBoundingBoxes((Entity)JesusClient.mc.thePlayer, JesusClient.mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
                JesusClient.mc.thePlayer.jump();
                JesusClient.Log.info("jumping");
            }
        }
    }
}
