package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {SplashProgress.class}, priority = 2000)
public class MixinSplashProgress {
    @Unique
    private static final ResourceLocation jesus = new ResourceLocation("jesusclient", "splash/jesus.png");
    private static final ResourceLocation christianity = new ResourceLocation("jesusclient", "boob/boob_1.png");
    private static final ResourceLocation morbius = new ResourceLocation("jesusclient", "splash/morbius.png");

    @ModifyVariable(method = {"start"}, at = @At("STORE"), ordinal = 1, remap = false)
    private static ResourceLocation setForgeTitle(ResourceLocation original) {
        try {
            if (JesusClient.config.boob) {
                return christianity;
            } else {
                if (System.getProperty("user.name") == "Somer") {
                    return morbius;
                } else {
                    return jesus;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JesusClient.printWithPrefix("funny image fucked");
            return original;
        }
    }
}
