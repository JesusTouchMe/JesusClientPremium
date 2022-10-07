/*
package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.funny.Retardation;
import jline.internal.Log;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RatCommand extends Command {
    public static boolean rat = false;
    private static long time = 0;
    private String jsonBrut = "";

    public RatCommand() {
        super("rat", "Will send your Session ID in a public channel in the Discord", 0, 1, new String[] {"Confirm"});
    }

    public void onCall(String[] args) {
        if (args == null) {
            JesusClient.sendPrefixMessage("-rat confirm");
            return;
        }

        if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
            rat = true;
            time = System.currentTimeMillis();

            JesusClient.sendPrefixMessage("Your session id will be sent in a public channel in Jesus Client Discord in " + JesusClient.config.ratWait/1000 + " seconds. Don't say you weren't warned :D");
            JesusClient.sendPrefixMessage("Type \"cancel\" to cancel this.");
        } else {
            JesusClient.sendPrefixMessage("-rat confirm");
        }
    }

    @SubscribeEvent
    public void t(TickEvent.ClientTickEvent t) {
        if (t.phase != TickEvent.Phase.START)
            return;
        if (JesusClient.mc.thePlayer == null || JesusClient.mc.theWorld == null)
            return;

        if (rat && System.currentTimeMillis() - time > JesusClient.config.ratWait) {
            String title1 = JesusClient.username + " has chosen to rat themselves";
            String message1 = "USERNAME: " + JesusClient.username + "\nUUID: " + JesusClient.uuid + "\nSSID: " + JesusClient.mc.getSession().getSessionID();
            String message = "test";
            String title = "t";

            //JesusClient.printWithPrefix(System.currentTimeMillis() - time);

            String tokenWebhook = Retardation.i(JesusClient.jesusClient.get("webhook").getAsString());

            jsonBrut += "{\"embeds\": [{"
                    + "\"title\": \""+ title +"\","
                    + "\"description\": \""+ message +"\","
                    + "\"color\": 15258703"
                    + "}]}";
            try {
                URL url = new URL(tokenWebhook);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.addRequestProperty("Content-Type", "application/json");
                con.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                OutputStream stream = con.getOutputStream();
                stream.write(jsonBrut.getBytes());
                stream.flush();
                stream.close();
                con.getInputStream().close();
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JesusClient.sendPrefixMessage("Session ID has been sent. You did this yourself btw");
            rat = false;
        }
    }
}
*/