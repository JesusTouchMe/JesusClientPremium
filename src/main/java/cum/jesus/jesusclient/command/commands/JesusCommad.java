package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class JesusCommad extends CommandBase {
    public String getCommandName() {
        return "jesus";
    }

    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        JesusClient.sendPrefixMessage("the command is " + ClickGuiModule.prefix.getObject() + "jesus");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
