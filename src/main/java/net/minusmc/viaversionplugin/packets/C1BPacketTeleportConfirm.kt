package net.minusmc.viaversionplugin.packets

import net.minecraft.network.Packet
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.INetHandlerPlayServer

class C1BPacketTeleportConfirm(var teleportId: Int): Packet<INetHandlerPlayServer> {
	override fun processPacket(handler: INetHandlerPlayServer) {}

    override fun readPacketData(buf: PacketBuffer) {
        teleportId = buf.readVarIntFromBuffer()
    }

    override fun writePacketData(buf: PacketBuffer) {
        buf.writeVarIntToBuffer(teleportId)
    }
}