package cum.jesus.jesusclient.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public class MinecraftMixin {
    @Inject(method = {"getRenderViewEntity"}, at = {@At("HEAD")})
    public void getRenderViewEntity(CallbackInfoReturnable<Entity> cir) {

    }
}
