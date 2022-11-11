package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import scala.tools.cmd.CommandLine;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Shows you a list of commands or usage of a specific command", 0, 1, new String[] { "Name of command" }, new String[] { "assist" });
    }

    public void onCall(String[] args) {
        if (args == null) {
            JesusClient.INSTANCE.commandManager.sort();
            JesusClient.sendPrefixMessage("Available commands:");
            for (Command command : JesusClient.INSTANCE.commandManager.getCommandList()) {
                if (command.getName().equalsIgnoreCase("help"))
                    continue;
                JesusClient.sendMessage(JesusClient.COLOR + "d - " + command.getName());
            }
            JesusClient.sendMessage(JesusClient.COLOR + "aRun `" + ClickGuiModule.prefix.getObject() + "help commandname` for more info about a command.");
        } else if (args.length == 2) {
            Command command = JesusClient.INSTANCE.commandManager.getCommandByName(args[1]);
            if (command == null) {
                JesusClient.sendPrefixMessage("Unable to find the command you were looking for.");
                return;
            }

            JesusClient.sendPrefixMessage(command.getName() + " info:");
            if (command.getAliases() != null || (command.getAliases()).length != 0) {
                JesusClient.sendMessage(JesusClient.COLOR + "aCommand Aliases:");
                for (String alias : command.getAliases())
                    JesusClient.sendMessage(JesusClient.COLOR + "a- " + alias.substring(0, 1).toUpperCase() + alias.substring(1));
            }
            if (!command.getHelp().isEmpty()) {
                JesusClient.sendMessage("\n" + JesusClient.COLOR + "aCommand description:");
                for (String helpText : command.getHelp().split("<br>"))
                    JesusClient.sendMessage(JesusClient.COLOR + "a" + helpText);
            }
            if (command.getArgs() != null) {
                JesusClient.sendMessage("\n" + JesusClient.COLOR + "aCommand argument description:");
                JesusClient.sendMessage(JesusClient.COLOR + "aMinimum expected arguments: " + command.getMinArgs() + "\n" + JesusClient.COLOR + "aMaximum expected arguments " + command.getMaxArgs());
                int argIndex = 1;
                for (String argText : command.getArgs()) {
                    for (String line : argText.split("<br>")) {
                            JesusClient.sendMessage(JesusClient.COLOR + "a" + argIndex + ": " + line);
                    }
                    argIndex++;
                }
            }
        }
    }
}
