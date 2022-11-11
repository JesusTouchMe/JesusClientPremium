package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class SettingsCommand extends Command {
    public SettingsCommand() {
        super("jesus", "Opens the config GUI", 0, 0, new String[0], new String[] { "j", "settings" });
    }

    Module gui = JesusClient.INSTANCE.moduleManager.getModule(ClickGuiModule.class);

    public void onCall(String[] args) {
        gui.setState(true);
    }
}
