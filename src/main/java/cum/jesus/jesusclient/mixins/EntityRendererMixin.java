package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.combat.Reach;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EntityRenderer.class})
public class EntityRendererMixin {
    @Redirect(method = {"setupFog"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
    public boolean removeBlindness(EntityLivingBase instance, Potion potionIn) {
        return false;
    }

    private static double reachAmount = JesusClient.config.reachAmount;

    @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal = 2))
    private double distanceTo(Vec3 instance, Vec3 vec) {
        return Reach.distanceTo(instance, vec);
    }
}
