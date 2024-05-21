package net.minusmc.viaversionplugin.injection.forge.mixins.packets;

import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(S32PacketConfirmTransaction.class)
public class MixinS32PacketConfirmTransaction {

	@Shadow
	private short actionNumber;

	@Shadow
	private boolean field_148893_c;

	@Shadow
	private int windowId;

	/**
     * @author FlorianMichael
     * @reason 1.17+ transaction fix
     */

	@Overwrite
	public void readPacketData(PacketBuffer buf) {
	    if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_17)) {
	        this.windowId = buf.readInt();
	    } else {
	        this.windowId = buf.readUnsignedByte();
	        this.actionNumber = buf.readShort();
	        this.field_148893_c = buf.readBoolean();
	    }
	}
}