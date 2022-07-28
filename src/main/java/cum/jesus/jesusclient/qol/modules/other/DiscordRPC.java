package cum.jesus.jesusclient.qol.modules.other;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Module;
import net.minecraft.client.Minecraft;

import java.io.InputStreamReader;
import java.time.OffsetDateTime;

public class DiscordRPC extends Module {
    public static IPCClient ipcClient = new IPCClient(984197260223070228L);
    private static boolean hasConnected;
    private static RichPresence richPresence;

    public DiscordRPC() {
        super("Discord RPC", JesusClient.config.discordRPC);
    }

    public void enable() {
        if (!hasConnected) {
            setupIPC();
        } else {
            try {
                ipcClient.sendRichPresence(richPresence);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setupIPC() {
        if (Minecraft.isRunningOnMac)
            return;
        try {
            ipcClient.setListener(new IPCListener() {
                public void onReady(IPCClient client) {
                    RichPresence.Builder builder = new RichPresence.Builder();
                    builder.setState("Jesus Client best legit ong")
                            .setLargeImage("jesus_rpc", "https://discord.gg/Y9kmQ9b9Fn")
                            .setStartTimestamp(OffsetDateTime.now());
                    client.sendRichPresence(builder.build());
                    hasConnected = true;
                }
            });
            ipcClient.connect(new com.jagrosh.discordipc.entities.DiscordBuild[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
