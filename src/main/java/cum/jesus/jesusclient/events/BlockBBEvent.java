package cum.jesus.jesusclient.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockBBEvent extends Event {
    private final int x;

    private final int y;

    private final int z;

    @NotNull
    private final Block block;

    @Nullable
    private AxisAlignedBB boundingBox;

    @NotNull
    public final Block getBlock() {
        return this.block;
    }

    @Nullable
    public final AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(@Nullable AxisAlignedBB set) {
        this.boundingBox = set;
    }

    public BlockBBEvent(@NotNull BlockPos blockPos, @NotNull Block block, @Nullable AxisAlignedBB boundingBox) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getZ() {
        return this.z;
    }
}
