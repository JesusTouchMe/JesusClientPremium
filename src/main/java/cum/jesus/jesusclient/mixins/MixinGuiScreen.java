package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.commands.BanCommand;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String msg, boolean addToChat, CallbackInfo ci) {
        if (msg.startsWith(ClickGuiModule.prefix.getObject())) {
            String c = msg.substring(1);
            if (!c.isEmpty()) {
                String cm = c.toLowerCase();
                boolean hasArgs = c.contains(" ");
                String[] args = hasArgs ? c.split(" ") : null;

                JesusClient.Log.info("Ran command " + cm);

                JesusClient.INSTANCE.commandManager.executeCommand(cm.split(" ")[0], args);
                JesusClient.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
            }
            ci.cancel();
        }

        if (BanCommand.isDoingThing && msg.toLowerCase().startsWith("cancel")) {
            BanCommand.isDoingThing = false;
            ci.cancel();
        }
    }
}
