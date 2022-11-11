package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Removes fall damage", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(MotionUpdateEvent.Pre pre) {
        if (getState()) {
            if(JesusClient.mc.thePlayer.fallDistance > 2f) {
                JesusClient.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
