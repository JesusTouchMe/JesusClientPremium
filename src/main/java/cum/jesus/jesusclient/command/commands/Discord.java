package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.utils.Utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Discord extends Command {
    public Discord() {
        super("discord", "Will take you to the Jesus Client Discord server.", 0, 0, new String[0]);
    }

    public void onCall(String[] args) {
        try {
            JesusClient.sendPrefixMessage("Taking you to the Discord...");
            java.awt.Desktop.getDesktop().browse(new URI("https://discord.gg/tjpg7mHjn2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
