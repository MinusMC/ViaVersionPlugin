package net.minusmc.viaversionplugin.packets

import net.minecraft.network.Packet
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.INetHandlerPlayServer

class C1APacketSwapHand: Packet<INetHandlerPlayServer> {
	override fun processPacket(handler: INetHandlerPlayServer) {}

    override fun readPacketData(buf: PacketBuffer) {}

    override fun writePacketData(buf: PacketBuffer) {}
}