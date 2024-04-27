package net.minusmc.viaversionplugin

import net.minusmc.viaversionplugin.packets.*

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper
import com.viaversion.viaversion.api.minecraft.Position
import com.viaversion.viaversion.api.type.Type
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9
import com.viaversion.viabackwards.protocol.protocol1_9to1_9_1.Protocol1_9To1_9_1

import de.florianmichael.vialoadingbase.ViaLoadingBase

object PacketHandler {
	fun handlePacketSwapHand(packet: C1APacketSwapHand) {
		if (ViaLoadingBase.getInstance().targetVersion.olderThan(ProtocolVersion.v1_9))
			return

		val swapItemPacket = PacketWrapper.create(ServerboundPackets1_9.PLAYER_DIGGING, ViaVersionPlugin.viaUser)
		swapItemPacket.write(Type.VAR_INT, 6)
		swapItemPacket.write(Type.POSITION1_8, Position(0, 0, 0))
		swapItemPacket.write(Type.BYTE, 0.toByte())

		try {
			swapItemPacket.sendToServer(Protocol1_8To1_9::class.java)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun handlePacketTeleportConfirm(packet: C1BPacketTeleportConfirm) {
		if (ViaLoadingBase.getInstance().targetVersion.olderThan(ProtocolVersion.v1_9))
			return

		val teleportConfirm = PacketWrapper.create(ServerboundPackets1_9.TELEPORT_CONFIRM, ViaVersionPlugin.viaUser)
        teleportConfirm.write(Type.VAR_INT, 1)

        try {
			teleportConfirm.sendToServer(Protocol1_9To1_9_1::class.java, true)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}