package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.events.BlockBBEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mixin({Block.class})
public abstract class MixinBlock {
    @Shadow
    @Final
    protected BlockState blockState;

    @Shadow
    public abstract AxisAlignedBB getSelectedBoundingBox(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);

    /**
     * @author
     */
    @Inject(method = {"addCollisionBoxesToList"}, at = {@At("HEAD")})
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity, CallbackInfo ci) {
        AxisAlignedBB axisalignedBB = getSelectedBoundingBox(worldIn, pos, state);
        BlockBBEvent blockBBEvent = new BlockBBEvent(pos, this.blockState.getBlock(), axisalignedBB);
        MinecraftForge.EVENT_BUS.post((Event)blockBBEvent);
        axisalignedBB = blockBBEvent.getBoundingBox();
        if (axisalignedBB != null && mask.intersectsWith(axisalignedBB))
            list.add(axisalignedBB);
    }
}
