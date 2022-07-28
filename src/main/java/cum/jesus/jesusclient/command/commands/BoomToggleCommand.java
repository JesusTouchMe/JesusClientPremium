package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.utils.Utils;

public class BoomToggleCommand extends Command {
    public BoomToggleCommand() {
        super("togglevineboom", "Toggles the vine boom from Retardation module (don't bother if you don't use it)", 0, 0, new String[0]);
    }

    public void onCall(String[] args) {
        JesusClient.config.boomAllowed = !JesusClient.config.boomAllowed;
        JesusClient.sendPrefixMessage(Utils.getColouredBoolean(JesusClient.config.boomAllowed) + " Vine Boom");
    }
}
