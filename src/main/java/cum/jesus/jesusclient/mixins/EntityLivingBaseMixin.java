package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.JumpEvent;
import cum.jesus.jesusclient.qol.modules.movement.Jesus;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityLivingBase.class})
public abstract class EntityLivingBaseMixin extends EntityMixin {
    @Shadow
    public float rotationYawHead;

    @Shadow
    private int jumpTicks;

    @Shadow
    protected boolean isJumping;

    @Shadow
    public float jumpMovementFactor;

    @Shadow
    protected int newPosRotationIncrements;

    @Shadow
    public float moveForward;

    @Shadow
    public float moveStrafing;

    @Shadow
    protected float movedDistance;

    @Shadow
    protected int entityAge;

    @Shadow
    protected abstract float getJumpUpwardsMotion();

    @Shadow
    public abstract boolean isPotionActive(int paramInt);

    @Shadow
    public abstract PotionEffect getActivePotionEffect(Potion paramPotion);

    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute paramIAttribute);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract boolean isOnLadder();

    @Shadow
    public abstract boolean isPotionActive(Potion paramPotion);

    @Shadow
    public abstract void setLastAttacker(Entity paramEntity);

    @Shadow
    public abstract float getSwingProgress(float paramFloat);

    @Shadow
    protected abstract void updateFallState(double paramDouble, boolean paramBoolean, Block paramBlock, BlockPos paramBlockPos);

    @Shadow
    protected abstract void resetPotionEffectMetadata();

    @Shadow
    protected abstract void updateAITick();


    public void setJumpTicks(int jumpTicks) {
        this.jumpTicks = jumpTicks;
    }

    public int getJumpTicks() {
        return this.jumpTicks;
    }

    /**
     * @author
     */
    @Overwrite
    protected void jump() {
        JumpEvent jumpEvent = new JumpEvent(getJumpUpwardsMotion());
        MinecraftForge.EVENT_BUS.post(jumpEvent);
        if (jumpEvent.isCanceled())
            return;
        this.motionY = jumpEvent.getMotion();
        if (isPotionActive(Potion.jump))
            this.motionY = ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        if (isSprinting()) {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= (MathHelper.sin(f) * 0.2F);
            this.motionZ += (MathHelper.cos(f) * 0.2F);
        }
        this.isAirBorne = true;
    }

    @Inject(method = {"onLivingUpdate"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;isJumping:Z", ordinal = 1)})
    private void onJumpSection(CallbackInfo callbackInfo) {
        if (Jesus.INSTANCE.getState() && !this.isJumping && !isSneaking() && isInWater())
            updateAITick();
    }
}
