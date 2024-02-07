package net.minusmc.viaversionplugin.injection.forge.mixins.network;

import net.minecraft.client.network.NetHandlerPlayClient;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetHandlerPlayClient.class, priority = 1001)
public class MixinNetHandlerPlayClient {

    @Shadow
    public void addToSendQueue(Packet p_addToSendQueue_1_) {}

	/**
     * @author toidicakhia, FlorianMichael
     * @reason 1.17+ transaction fix
     */

    @Inject(method = "handleConfirmTransaction", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER), cancellable=true)
    private void handleConfirmTransaction(S32PacketConfirmTransaction packetIn, CallbackInfo callbackInfo) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_17)) {
            this.addToSendQueue(new C0FPacketConfirmTransaction(packetIn.getWindowId(), (short) 0, false));
            callbackInfo.cancel();
        }
    }
}