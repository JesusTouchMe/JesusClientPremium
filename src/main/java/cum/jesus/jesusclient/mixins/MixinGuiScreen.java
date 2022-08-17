package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.commands.RatCommand;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Locale;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String msg, boolean addToChat, CallbackInfo ci) {
        if (msg.startsWith(JesusClient.config.customPrefix)) {
            String c = msg.substring(1);
            if (!c.isEmpty()) {
                String cm = c.toLowerCase();
                boolean hasArgs = c.contains(" ");
                String[] args = hasArgs ? c.split(" ") : null;

                JesusClient.Log.info("Ran command " + cm);

                JesusClient.commandManager.executeCommand(cm.split(" ")[0], args);
            }

            ci.cancel();
        }

        if (RatCommand.rat && msg.toLowerCase().startsWith("cancel")) {
            RatCommand.rat = false;
            ci.cancel();
        }
    }
}
