package net.minusmc.viaversionplugin

import net.minusmc.viaversionplugin.packets.*

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper
import com.viaversion.viaversion.api.minecraft.BlockPosition
import com.viaversion.viaversion.api.type.Types
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8
import com.viaversion.viabackwards.protocol.v1_9_1to1_9.Protocol1_9_1To1_9

import de.florianmichael.vialoadingbase.ViaLoadingBase

object PacketHandler {
	fun handlePacketSwapHand(event: C1APacketSwapHand) {
		if (ViaLoadingBase.getInstance().targetVersion.olderThan(ProtocolVersion.v1_9))
			return

		val swapItemPacket = PacketWrapper.create(ServerboundPackets1_9.PLAYER_ACTION, ViaVersionPlugin.viaUser)
		swapItemPacket.write(Types.VAR_INT, 6)
		swapItemPacket.write(Types.BLOCK_POSITION1_8, BlockPosition(0, 0, 0))
		swapItemPacket.write(Types.BYTE, 0.toByte())

		try {
			swapItemPacket.sendToServer(Protocol1_9To1_8::class.java)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun handlePacketTeleportConfirm(event: C1BPacketTeleportConfirm) {
		if (ViaLoadingBase.getInstance().targetVersion.olderThan(ProtocolVersion.v1_9))
			return

		val teleportConfirm = PacketWrapper.create(ServerboundPackets1_9.ACCEPT_TELEPORTATION, ViaVersionPlugin.viaUser)
        teleportConfirm.write(Types.VAR_INT, 1)

        try {
			teleportConfirm.sendToServer(Protocol1_9_1To1_9::class.java, true)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}