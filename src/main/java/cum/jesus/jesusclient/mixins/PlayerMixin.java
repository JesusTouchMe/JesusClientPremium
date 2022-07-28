package cum.jesus.jesusclient.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityPlayer.class})
public abstract class PlayerMixin extends EntityLivingBaseMixin {
    @Shadow
    public PlayerCapabilities capabilities;

    @Shadow
    private int itemInUseCount;

    @Shadow
    public InventoryPlayer inventory;

    @Shadow
    protected float speedInAir;

    @Shadow
    public float experience;

    @Shadow
    public int experienceLevel;

    @Shadow
    public int experienceTotal;

    @Shadow
    public float eyeHeight;

    private boolean wasSprinting;

    @Shadow
    public abstract void addStat(StatBase paramStatBase);

    @Shadow
    public abstract void addExhaustion(float paramFloat);

    @Shadow
    public abstract FoodStats getFoodStats();

    @Shadow
    public abstract void attackTargetEntityWithCurrentItem(Entity paramEntity);

    @Shadow
    public abstract ItemStack getHeldItem();

    @Shadow
    public abstract ItemStack getCurrentEquippedItem();

    @Shadow
    public abstract void destroyCurrentEquippedItem();

    @Shadow
    protected void updateEntityActionState() {}

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract ItemStack getItemInUse();

    @Shadow
    protected abstract String getSwimSound();

    @Shadow
    protected abstract boolean canTriggerWalking();

    @Shadow
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }
}
