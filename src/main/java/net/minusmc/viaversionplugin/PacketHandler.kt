package net.minusmc.viaversionplugin

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper
import com.viaversion.viaversion.api.minecraft.Position
import com.viaversion.viaversion.api.type.Type

import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9

object PacketHandler {
	fun handlePacketSwapHand() {
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
}