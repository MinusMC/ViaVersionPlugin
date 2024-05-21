package net.minusmc.viaversionplugin.injection.forge.mixins.packets;

import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(C0FPacketConfirmTransaction.class)
public class MixinC0FPacketConfirmTransaction {

	@Shadow
	private short uid;

	@Shadow
	private boolean accepted;

	@Shadow
	private int windowId;

	/**
     * @author FlorianMichael
     * @reason 1.17+ transaction fix
     */

	@Overwrite
	public void writePacketData(PacketBuffer buf) {
    	if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_17)) {
	        buf.writeInt(this.windowId);
	    } else {
	        buf.writeByte(this.windowId);
	        buf.writeShort(this.uid);
	        buf.writeByte(this.accepted ? 1 : 0);
	    }
	}
}