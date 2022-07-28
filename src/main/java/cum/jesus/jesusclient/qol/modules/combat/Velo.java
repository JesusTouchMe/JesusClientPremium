package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velo extends Module {
    public Velo() {
        super("AntiKB", JesusClient.config.antiKB);
    }

    public static void handleExplosion(S27PacketExplosion packet) {
        if (isEnabled()) {
            JesusClient.mc.thePlayer.motionX -= packet.func_149149_c();
            JesusClient.mc.thePlayer.motionY -= packet.func_149144_d();
            JesusClient.mc.thePlayer.motionZ -= packet.func_149147_e();
        }
    }

    public static boolean handleEntityVelocity(S12PacketEntityVelocity packet) {
        if(isEnabled()) return JesusClient.mc.theWorld.getEntityByID(packet.getEntityID()) == JesusClient.mc.thePlayer;
        return false;
    }

    private static boolean isEnabled() {
        if (!JesusClient.config.antiKB) return false;
        if (JesusClient.mc.thePlayer.isInLava()) return false;

        if (JesusClient.mc.thePlayer.getHeldItem() != null) {
            String itemId = Utils.getSkyBlockID(JesusClient.mc.thePlayer.getHeldItem());
            return !itemId.equals("JERRY_STAFF") && !itemId.equals("BONZO_STAFF") && !itemId.equals("STARRED_BONZO_STAFF");
        }

        return true;
    }
}
