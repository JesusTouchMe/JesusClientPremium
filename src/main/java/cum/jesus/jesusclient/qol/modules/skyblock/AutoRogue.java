package cum.jesus.jesusclient.qol.modules.skyblock;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.settings.BooleanSetting;
import cum.jesus.jesusclient.utils.MilliTimer;
import cum.jesus.jesusclient.utils.SkyblockUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ThreadLocalRandom;

public class AutoRogue extends Module {
    public AutoRogue() {
        super("Auto rogue", "Uses rogue sword automatically", Category.SKYBLOCK);
    }

    private MilliTimer time = new MilliTimer();

    public BooleanSetting legitMode = new BooleanSetting("Legit mode", false);

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!getState() || JesusClient.mc.thePlayer == null || !SkyblockUtils.inDungeon) return; // reminder: add indungeon when done testing
        if (!legitMode.getObject()) {
            if (time.hasTimePassed(30000L)) {
                for (int i = 0; i < 9; i++) {
                    if (JesusClient.mc.thePlayer.inventory.getStackInSlot(i) != null && JesusClient.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains("rogue sword")) {
                        int held = JesusClient.mc.thePlayer.inventory.currentItem;
                        JesusClient.mc.thePlayer.inventory.currentItem = i;
                        SkyblockUtils.updateItemNoEvent();
                        JesusClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(JesusClient.mc.thePlayer.getHeldItem()));
                        JesusClient.Log.info("Used rogue sword (blatant mode) " + time.getTimePassed() + "ms");
                        JesusClient.mc.thePlayer.inventory.currentItem = held;
                        SkyblockUtils.updateItemNoEvent();
                        time.updateTime();
                        break;
                    }
                }
            }
        } else {
            if (time.hasTimePassed((long)ThreadLocalRandom.current().nextInt(27251, 33143 + 1))) {
                for (int i = 0; i < 9; i++)
                    if (JesusClient.mc.thePlayer.inventory.getStackInSlot(i) != null && JesusClient.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains("rogue sword")) {
                        int held = JesusClient.mc.thePlayer.inventory.currentItem;
                        JesusClient.mc.thePlayer.inventory.currentItem = i;
                        SkyblockUtils.updateItem();
                        JesusClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(JesusClient.mc.thePlayer.getHeldItem()));
                        JesusClient.Log.info("Used rogue sword (legit mode) " + time.getTimePassed() + "ms");
                        JesusClient.mc.thePlayer.inventory.currentItem = held;
                        SkyblockUtils.updateItem();
                        time.updateTime();
                        break;
                    }
            }
        }
    }
}
