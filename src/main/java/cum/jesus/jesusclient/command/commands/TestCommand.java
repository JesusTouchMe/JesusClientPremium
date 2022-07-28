package cum.jesus.jesusclient.command.commands;

import com.google.gson.JsonObject;
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.funny.Retardation;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class TestCommand extends Command {
    public static JsonObject response;
    private File a = new File(JesusClient.mc.mcDataDir + "/jesus", "a.wav");

    public TestCommand() {
        super("test", "testing command system", 0, 0, new String[0], new String[] { "testage" });
    }

    public void onCall(String[] args) {
        JesusClient.sendPrefixMessage("command manager works");
        JesusClient.sendPrefixMessage(System.getProperty("user.name"));
        Utils.playSound(a, -40);
    }
}