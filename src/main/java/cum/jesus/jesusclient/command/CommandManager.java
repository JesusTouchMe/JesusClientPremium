package cum.jesus.jesusclient.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.commands.*;

public class CommandManager {
    public List<Command> commandList;
    public List<Command> sortedCommandList;

    public CommandManager() {
        this.commandList = new ArrayList<>();
        this.sortedCommandList = new ArrayList<>();

        // add commands
        addCommand((Command)new TestCommand());
        addCommand((Command)new VClipCommand());
        addCommand((Command)new HelpCommand());
        addCommand((Command)new SettingsCommand());
        addCommand((Command)new BanCommand());
        //addCommand((Command)new DiscordThing());
        addCommand((Command)new RatCommand());
        addCommand((Command)new BoomToggleCommand());
    }

    public void addCommand(Command c) {
        this.commandList.add(c);
    }

    public List<Command> getCommandList() {
        return this.commandList;
    }

    public Command getCommandByName(String name) {
        for (Command command : this.commandList) {
            if (command.getName().equalsIgnoreCase(name))
                return command;
            for (String alias : command.getAliases()) {
                if (alias.equalsIgnoreCase(name))
                    return command;
            }
        }
        return null;
    }

    public void noSuchCommand(String name) {
        JesusClient.sendPrefixMessage("Couldn't find the command you were using. Type `-help` for a list of commands.");
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = JesusClient.commandManager.getCommandByName(commandName);
        if (command == null) {
            noSuchCommand(commandName);
            return;
        }
        command.onCall(args);
    }

    public void sort() {
        this.sortedCommandList.sort(Comparator.comparing(Command::getName));
    }
}
