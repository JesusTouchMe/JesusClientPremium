package cum.jesus.jesusclient.mixins;


import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.events.MoveEvent;
import cum.jesus.jesusclient.events.PlayerUpdateEvent;
import cum.jesus.jesusclient.events.UpdateEvent;
import cum.jesus.jesusclient.qol.modules.combat.KillAura;
import cum.jesus.jesusclient.utils.MovementUtils;
import cum.jesus.jesusclient.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {EntityPlayerSP.class}, priority = 1)
public abstract class PlayerSPMixin extends AbstractClientPlayerMixin {
    @Shadow
    public MovementInput movementInput;

    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    @Shadow
    public float timeInPortal;

    @Shadow
    public float prevRenderArmPitch;

    @Shadow
    public float prevRenderArmYaw;

    @Shadow
    public float renderArmPitch;

    @Shadow
    public float renderArmYaw;

    @Shadow
    private boolean serverSprintState;

    @Shadow
    private float lastReportedPitch;

    @Shadow
    private double lastReportedPosX;

    @Shadow
    private double lastReportedPosY;

    @Shadow
    private double lastReportedPosZ;

    @Shadow
    private float lastReportedYaw;

    @Shadow
    private int positionUpdateTicks;

    @Shadow
    private boolean serverSneakState;

    @Shadow
    public abstract void setSprinting(boolean paramBoolean);

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public abstract void onCriticalHit(Entity paramEntity);

    @Shadow
    public abstract void onEnchantmentCritical(Entity paramEntity);

    //@Shadow
    //public abstract void addStat(StatBase paramStatBase, int paramInt);

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Shadow
    public abstract void playSound(String paramString, float paramFloat1, float paramFloat2);

    /**
     * @author
     */
    @Overwrite
    public void onUpdateWalkingPlayer() {
        MotionUpdateEvent.Pre pre = new MotionUpdateEvent.Pre(this.posX, (getEntityBoundingBox()).minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround, isSprinting(), isSneaking());
        if (MinecraftForge.EVENT_BUS.post((Event)pre))
            return;
        boolean flag = ((MotionUpdateEvent)pre).sprinting;
        if (flag != this.serverSprintState) {
            if (flag) {
                this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)(Object)this, C0BPacketEntityAction.Action.START_SPRINTING));
            } else {
                this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)(Object)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = flag;
        }
        boolean flag1 = ((MotionUpdateEvent)pre).sneaking;
        if (flag1 != this.serverSneakState) {
            if (flag1) {
                this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)(Object)this, C0BPacketEntityAction.Action.START_SNEAKING));
            } else {
                this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)(Object)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = flag1;
        }
        if (isCurrentViewEntity()) {
            double d0 = ((MotionUpdateEvent)pre).x - this.lastReportedPosX;
            double d1 = ((MotionUpdateEvent)pre).y - this.lastReportedPosY;
            double d2 = ((MotionUpdateEvent)pre).z - this.lastReportedPosZ;
            double d3 = (((MotionUpdateEvent)pre).yaw - this.lastReportedYaw);
            double d4 = (((MotionUpdateEvent)pre).pitch - this.lastReportedPitch);
            boolean flag2 = (d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20);
            boolean flag3 = (d3 != 0.0D || d4 != 0.0D);
            if (this.ridingEntity == null) {
                if (flag2 && flag3) {
                    this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((MotionUpdateEvent)pre).x, ((MotionUpdateEvent)pre).y, ((MotionUpdateEvent)pre).z, ((MotionUpdateEvent)pre).yaw, ((MotionUpdateEvent)pre).pitch, ((MotionUpdateEvent)pre).onGround));
                } else if (flag2) {
                    this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((MotionUpdateEvent)pre).x, ((MotionUpdateEvent)pre).y, ((MotionUpdateEvent)pre).z, ((MotionUpdateEvent)pre).onGround));
                } else if (flag3) {
                    this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(((MotionUpdateEvent)pre).yaw, ((MotionUpdateEvent)pre).pitch, ((MotionUpdateEvent)pre).onGround));
                } else {
                    this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(((MotionUpdateEvent)pre).onGround));
                }
            } else {
                this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, ((MotionUpdateEvent)pre).yaw, ((MotionUpdateEvent)pre).pitch, ((MotionUpdateEvent)pre).onGround));
                flag2 = false;
            }
            this.positionUpdateTicks++;
            if (flag2) {
                this.lastReportedPosX = ((MotionUpdateEvent)pre).x;
                this.lastReportedPosY = ((MotionUpdateEvent)pre).y;
                this.lastReportedPosZ = ((MotionUpdateEvent)pre).z;
                this.positionUpdateTicks = 0;
            }
            RotationUtils.lastReportedPitch = this.lastReportedPitch;
            if (flag3) {
                this.lastReportedYaw = ((MotionUpdateEvent)pre).yaw;
                this.lastReportedPitch = ((MotionUpdateEvent)pre).pitch;
            }
        }
        MinecraftForge.EVENT_BUS.post((Event)new MotionUpdateEvent.Post((MotionUpdateEvent)pre));
    }

    public void jump() {
        this.motionY = getJumpUpwardsMotion();
        if (isPotionActive(Potion.jump.id))
            this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        if (isSprinting()) {
            float f = ((JesusClient.config.toggleSprint && JesusClient.config.omniSprint) ? MovementUtils.getYaw() : ((JesusClient.config.killAura && KillAura.target != null && false) ? RotationUtils.getAngles((Entity)KillAura.target)[0] : this.rotationYaw)) * 0.017453292F;
            this.motionX -= (MathHelper.sin(f) * 0.2F);
            this.motionZ += (MathHelper.cos(f) * 0.2F);
        }
        this.isAirBorne = true;
        ForgeHooks.onLivingJump((EntityLivingBase)(Object)this);
        //addStat(StatList.jumpStat);
        if (isSprinting()) {
            addExhaustion(0.8F);
        } else {
            addExhaustion(0.2F);
        }
    }

    public void moveFlying(float strafe, float forward, float friction) {
        float f = strafe * strafe + forward * forward;
        if (f >= 1.0E-4F) {
            f = MathHelper.sqrt_float(f);
            if (f < 1.0F)
                f = 1.0F;
            f = friction / f;
            strafe *= f;
            forward *= f;
            float yaw = (JesusClient.config.killAura && KillAura.target != null && false) ? RotationUtils.getAngles((Entity)KillAura.target)[0] : this.rotationYaw;
            float f1 = MathHelper.sin(yaw * 3.1415927F / 180.0F);
            float f2 = MathHelper.cos(yaw * 3.1415927F / 180.0F);
            this.motionX += (strafe * f2 - forward * f1);
            this.motionZ += (forward * f2 + strafe * f1);
        }
    }

    @Inject(method = {"pushOutOfBlocks"}, at = {@At("HEAD")}, cancellable = true)
    public void pushOutOfBlocks(double d2, double f, double blockpos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Boolean.valueOf(false));
    }

    @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isUsingItem()Z"))
    public boolean isUsingItem(EntityPlayerSP instance) {
        if (false && instance.isUsingItem())
            return false;
        return instance.isUsingItem();
    }

    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Inject(method = {"onLivingUpdate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onLivingUpdate()V")}, cancellable = true)
    public void onLivingUpdate(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new UpdateEvent());
        if (JesusClient.config.omniSprint && JesusClient.config.toggleSprint) {
            if (!MovementUtils.isMoving() || isSneaking() || (getFoodStats().getFoodLevel() <= 6.0F && !this.capabilities.allowFlying)) {
                if (isSprinting())
                    setSprinting(false);
                return;
            }
            if (!isSprinting())
                setSprinting(true);
        }
        if (MinecraftForge.EVENT_BUS.post((Event)new PlayerUpdateEvent()))
            ci.cancel();

    }

    public void moveEntity(double x, double y, double z) {
        MoveEvent event = new MoveEvent(x, y, z);
        if (MinecraftForge.EVENT_BUS.post((Event)event))
            return;
        x = event.x;
        y = event.y;
        z = event.z;
        super.moveEntity(x, y, z);
    }

    public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
        if (ForgeHooks.onPlayerAttackTarget((EntityPlayer)(Object)this, targetEntity) &&
                targetEntity.canAttackWithItem() && !targetEntity.hitByEntity((Entity)(Object)this)) {
            float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int i = 0;
            float f1 = 0.0F;
            if (targetEntity instanceof EntityLivingBase) {
                f1 = EnchantmentHelper.getModifierForCreature(getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
            } else {
                f1 = EnchantmentHelper.getModifierForCreature(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            }
            i += EnchantmentHelper.getRespiration((EntityLivingBase)(Object)this);
            if (isSprinting())
                i++;
            if (f > 0.0F || f1 > 0.0F) {
                boolean flag = (this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase);
                if (flag && f > 0.0F)
                    f *= 1.5F;
                f += f1;
                boolean flag1 = false;
                int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)(Object)this);
                if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
                    flag1 = true;
                    targetEntity.setFire(1);
                }
                double d0 = targetEntity.motionX;
                double d1 = targetEntity.motionY;
                double d2 = targetEntity.motionZ;
                boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)(Object)this), f);
                if (flag2) {
                    EntityLivingBase entityLivingBase = null;
                    if (i > 0) {
                        targetEntity.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
                        if (JesusClient.config.toggleSprint && JesusClient.config.keepSprint) {
                            if (isSprinting()) {
                                JesusClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                                JesusClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)JesusClient.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                            }
                        } else {
                            this.motionX *= 0.6D;
                            this.motionZ *= 0.6D;
                            setSprinting(false);
                        }
                    }
                    if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                        ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(targetEntity));
                        targetEntity.velocityChanged = false;
                        targetEntity.motionX = d0;
                        targetEntity.motionY = d1;
                        targetEntity.motionZ = d2;
                    }
                    if (flag)
                        onCriticalHit(targetEntity);
                    if (f1 > 0.0F)
                        onEnchantmentCritical(targetEntity);
                    if (f >= 18.0F)
                        //addStat((StatBase)AchievementList.overkill);
                    setLastAttacker(targetEntity);
                    if (targetEntity instanceof EntityLivingBase)
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)(Object)this);
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)(Object)this, targetEntity);
                    ItemStack itemstack = getCurrentEquippedItem();
                    Entity entity = targetEntity;
                    if (targetEntity instanceof EntityDragonPart) {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;
                        if (ientitymultipart instanceof EntityLivingBase)
                            entityLivingBase = (EntityLivingBase)ientitymultipart;
                    }
                    if (itemstack != null && entityLivingBase instanceof EntityLivingBase) {
                        itemstack.hitEntity(entityLivingBase, (EntityPlayer)(Object)this);
                        if (itemstack.stackSize <= 0)
                            destroyCurrentEquippedItem();
                    }
                    if (targetEntity instanceof EntityLivingBase) {
                        //addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
                        if (j > 0)
                            targetEntity.setFire(j * 4);
                    }
                    addExhaustion(0.3F);
                } else if (flag1) {
                    targetEntity.extinguish();
                }
            }
        }
    }
}