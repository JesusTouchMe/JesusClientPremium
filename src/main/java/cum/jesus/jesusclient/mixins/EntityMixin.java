package cum.jesus.jesusclient.mixins;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Random;
import java.util.UUID;

@Mixin({Entity.class})
public abstract class EntityMixin {
    @Shadow
    protected UUID entityUniqueID;

    @Shadow
    public double lastTickPosX;

    @Shadow
    protected Random rand;

    @Shadow
    public int fireResistance;

    @Shadow
    public World worldObj;

    @Shadow
    protected boolean inPortal;

    @Shadow
    public float entityCollisionReduction;

    @Shadow
    public float rotationPitch;

    @Shadow
    public int ticksExisted;

    @Shadow
    public boolean noClip;

    @Shadow
    public Entity ridingEntity;

    @Shadow
    public boolean onGround;

    @Shadow
    public float fallDistance;

    @Shadow
    public float rotationYaw;

    @Shadow
    public boolean isAirBorne;

    @Shadow
    public double motionX;

    @Shadow
    public double motionY;

    @Shadow
    public double motionZ;

    @Shadow
    private int fire;

    @Shadow
    public float distanceWalkedModified;

    @Shadow
    public float distanceWalkedOnStepModified;

    @Shadow
    private int nextStepDistance;

    @Shadow
    public double posX;

    @Shadow
    public double posY;

    @Shadow
    public double posZ;

    @Shadow
    public boolean isCollided;

    @Shadow
    public boolean isCollidedHorizontally;

    @Shadow
    public boolean isCollidedVertically;

    @Shadow
    public float stepHeight;

    @Shadow
    protected boolean isInWeb;

    @Shadow
    public abstract boolean isEntityInsideOpaqueBlock();

    @Shadow
    public abstract void extinguish();

    @Shadow
    public abstract void setFire(int paramInt);

    @Shadow
    public abstract boolean isWet();

    @Shadow
    protected abstract void dealFireDamage(int paramInt);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void moveFlying(float paramFloat1, float paramFloat2, float paramFloat3);

    @Shadow
    public abstract UUID getUniqueID();

    @Shadow
    public abstract boolean equals(Object paramObject);

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    public void moveEntity(double x, double y, double z) {}

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    protected abstract boolean getFlag(int paramInt);

    @Shadow
    public abstract void addEntityCrashInfo(CrashReportCategory paramCrashReportCategory);

    @Shadow
    protected abstract void doBlockCollisions();

    @Shadow
    protected abstract void playStepSound(BlockPos paramBlockPos, Block paramBlock);

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB paramAxisAlignedBB);

    @Shadow
    public abstract boolean isSneaking();
}
