package cum.jesus.jesusclient.qol.modules.player;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.UpdateEvent;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.movement.ToggleSprint;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    public InvMove() {
        super("InvMove", "Moves while in inventory. Will most likely get you banend", Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e) {
        if (getState() && JesusClient.mc.currentScreen != null) {
            if (JesusClient.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)
                return;
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindForward.getKeyCode()));
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindBack.getKeyCode()));
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindRight.getKeyCode()));
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindLeft.getKeyCode()));
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindJump.getKeyCode()));
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindSprint.getKeyCode(), Keyboard.isKeyDown(JesusClient.mc.gameSettings.keyBindSprint.getKeyCode()));
            if (Keyboard.isKeyDown(208) && JesusClient.mc.thePlayer.rotationPitch > -90.0f) {
                EntityPlayerSP var1 = JesusClient.mc.thePlayer;
                var1.rotationPitch += 6.0f;
            }
            if (Keyboard.isKeyDown(200) && JesusClient.mc.thePlayer.rotationPitch > -90.0f) {
                EntityPlayerSP var1 = JesusClient.mc.thePlayer;
                var1.rotationPitch -= 6.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                EntityPlayerSP var1 = JesusClient.mc.thePlayer;
                var1.rotationYaw += 6.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                EntityPlayerSP var1 = JesusClient.mc.thePlayer;
                var1.rotationYaw -= 6.0f;
            }

            if (ToggleSprint.INSTANCE.getState()) {
                KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindSprint.getKeyCode(), ToggleSprint.INSTANCE.getState());
            }
        }
    }
}
