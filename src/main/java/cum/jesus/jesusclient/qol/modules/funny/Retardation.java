package cum.jesus.jesusclient.qol.modules.funny;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class Retardation extends Module {
    private File a = new File(JesusClient.mc.mcDataDir + "/jesus/sounds", "a.wav");
    private File boom = new File(JesusClient.mc.mcDataDir + "/jesus/sounds", "vineboom.wav");

    public Retardation() {
        super("Retardation", JesusClient.config.retard);
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;
        if (Math.random() < 0.00001) {
            Utils.playSound(a, JesusClient.config.aVolume);
        }
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if (Keyboard.isKeyDown(Keyboard.KEY_H) && JesusClient.config.boomAllowed) {
            Utils.playSound(boom, JesusClient.config.boomVolume);
        }
    }
}
