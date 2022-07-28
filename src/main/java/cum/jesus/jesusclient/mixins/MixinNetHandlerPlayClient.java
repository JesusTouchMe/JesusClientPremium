package cum.jesus.jesusclient.mixins;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.PacketEvent;
import cum.jesus.jesusclient.qol.modules.combat.Velo;
// norotate l8r
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        if(MinecraftForge.EVENT_BUS.post(new PacketEvent.SendEvent(packet))) callbackInfo.cancel();
    }

    /*@Inject(method = "handlePlayerPosLook", at = @At("HEAD"))
    public void handlePlayerPosLookPre(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
        NoRotate.handlePlayerPosLookPre();
    }

    @Inject(method = "handlePlayerPosLook", at = @At("RETURN"))
    public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
        NoRotate.handlePlayerPosLook(packetIn);
    }*/

    @Inject(method = "handleExplosion", at = @At("RETURN"))
    private void handleExplosion(S27PacketExplosion packet, CallbackInfo ci) {
        Velo.handleExplosion(packet);
    }

    @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
    public void handleEntityVelocity(S12PacketEntityVelocity packetIn, CallbackInfo ci) {
        if(Velo.handleEntityVelocity(packetIn)) ci.cancel();
    }

}