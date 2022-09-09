package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.remote.Capes;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractClientPlayer.class, priority = Integer.MAX_VALUE)
public abstract class AbstractClientPlayerMixin extends PlayerMixin {
    @Shadow
    private NetworkPlayerInfo playerInfo;

    @Inject(method = {"getLocationCape"}, at = {@At("RETURN")}, cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir) {
        ResourceLocation person = Capes.getCape(playerInfo.getGameProfile().getId().toString().replace("-", ""));
        if (person != null) cir.setReturnValue(person);
    }
}
