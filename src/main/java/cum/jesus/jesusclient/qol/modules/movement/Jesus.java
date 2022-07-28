package cum.jesus.jesusclient.qol.modules.movement;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.*;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Jesus extends Module {
    private boolean nextTick;

    public Jesus() {
        super("Jesus", JesusClient.config.jesusHack);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (JesusClient.config.jesusHack) {
            if (Utils.collideBlock(mc.thePlayer.getEntityBoundingBox(), block -> block instanceof net.minecraft.block.BlockLiquid) && mc.thePlayer.isInsideOfMaterial(Material.air) && !mc.thePlayer.isSneaking()) {
                mc.thePlayer.motionY = 0.08D;
            }
        }
    }

    @SubscribeEvent
    public void onBlockBB(BlockBBEvent event) {
        if (mc.thePlayer == null || mc.thePlayer.getEntityBoundingBox() == null)
            return;
        if (event.getBlock() instanceof net.minecraft.block.BlockLiquid && !Utils.collideBlock(mc.thePlayer.getEntityBoundingBox(), block -> block instanceof net.minecraft.block.BlockLiquid) && !mc.thePlayer.isSneaking()) {
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), (event.getX() + 1), (event.getY() + 1), (event.getZ() + 1)));
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (mc.thePlayer == null)
            return;
        //C03PacketPlayer packetPlayer = (C03PacketPlayer)event.getPacket();
        if (event.getPacket() instanceof C03PacketPlayer && Utils.collideBlock(new AxisAlignedBB((mc.thePlayer.getEntityBoundingBox()).maxX, (mc.thePlayer.getEntityBoundingBox()).maxY, (mc.thePlayer.getEntityBoundingBox()).maxZ, (mc.thePlayer.getEntityBoundingBox()).minX, (mc.thePlayer.getEntityBoundingBox()).minY - 0.01D, (mc.thePlayer.getEntityBoundingBox()).minZ), block -> block instanceof BlockLiquid)) {
            this.nextTick = !this.nextTick;
            if (this.nextTick)
                //packetPlayer.y -= 0.001D;
                mc.thePlayer.posY -= 0.001D;
        }
    }

    @SubscribeEvent
    public void onJump(JumpEvent event) {}
}
