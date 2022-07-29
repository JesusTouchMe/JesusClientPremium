package cum.jesus.jesusclient.command.commands;

import com.google.gson.JsonObject;
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.funny.Retardation;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestCommand extends Command {
    private File ballsPng = new File(JesusClient.mc.mcDataDir + "/jesus/balls", "Balls.png");
    private File ballsSound = new File(JesusClient.mc.mcDataDir + "/jesus/balls", "Balls.wav");

    private static final float width = 480.0f;
    private static final float height = 288.0f;
    private static float x = (JesusClient.mc.displayWidth / 2) - (width / 2);
    private static float y = (JesusClient.mc.displayHeight / 2) - (height / 2);

    private static boolean doing = false;
    private static long time = 0;

    public TestCommand() {
        super("test", "testing command system", 0, 0, new String[0], new String[] { "testage" });
    }

    public void onCall(String[] args) {
        JesusClient.sendPrefixMessage("command manager works");
        //Utils.playSound(a, -40);

        doing = true;
        time = System.currentTimeMillis();
        Utils.playSound(ballsSound, -20);
    }

    @SubscribeEvent
    public void t(TickEvent.ClientTickEvent t) {
        if (System.currentTimeMillis() - time > JesusClient.config.balls)
            doing = false;
    }

    @SubscribeEvent
    public void renderPoo(TickEvent.RenderTickEvent t) {
        if (doing) {
            try {
                Utils.drawImage(JesusClient.mc.getTextureManager().getDynamicTextureLocation("jesusclient", new DynamicTexture(ImageIO.read(ballsPng))), x, y, width, height, 100);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}