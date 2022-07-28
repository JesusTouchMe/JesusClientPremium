package cum.jesus.jesusclient.qol.modules.funny;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.*;

public class Retardation extends Module {
    private File a = new File(JesusClient.mc.mcDataDir + "/jesus", "a.wav");

    public Retardation() {
        super("Retardation", JesusClient.config.retard);
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;
        if (Math.random() < 0.00001) {
            Utils.playSound(a, 2);
        }
    }
}
