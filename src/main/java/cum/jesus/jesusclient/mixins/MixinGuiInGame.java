package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.gui.config.accessible.AccessibleGui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiInGame {
    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    public void renderScoreboard(ScoreObjective p_renderScoreboard_1_, ScaledResolution p_renderScoreboard_2_, CallbackInfo ci) {
        if (AccessibleGui.isMenuActive) {
            ci.cancel();
        }
    }
}
