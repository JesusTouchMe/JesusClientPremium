package cum.jesus.jesusclient.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.funny.Retardation;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.json.JSONString;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static cum.jesus.jesusclient.JesusClient.COLOR;
import static cum.jesus.jesusclient.JesusClient.uuid;

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
        JesusClient.sendPrefixMessage(JesusClient.mc.thePlayer.getGameProfile().getId().toString().replace("-", ""));
        JesusClient.sendPrefixMessage("command manager works");
        //Utils.playSound(a, -40);

        JesusClient.Log.trace("test");
        JesusClient.Log.debug("test");
        JesusClient.Log.info("test");
        JesusClient.Log.warn("test");
        JesusClient.Log.error("test");

        JesusClient.sendMessage(JesusClient.people[new Random().nextInt(JesusClient.people.length)].replace('&',COLOR) + COLOR + "7" + ": " + Retardation.i(JesusClient.obfMessages[new Random().nextInt(JesusClient.obfMessages.length)]));

        List<EntityPlayer> playerList = JesusClient.mc.theWorld.playerEntities;

        String[] uuidList = new String[playerList.size()];
        Iterator<EntityPlayer> pl = playerList.iterator();
        int c = 0;
        while (pl.hasNext()) { // TODO: filter bots
            EntityPlayer uuid = pl.next();
            uuidList[c] = uuid.getUniqueID().toString();
            c++;
        }

        for (String uuid : uuidList) {
            JesusClient.sendMessage(Utils.API.fullName(Utils.API.getPlayerInfo(uuid, JesusClient.jesusClient.get("key").getAsString())) + "test");
        }

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