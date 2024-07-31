package net.minusmc.viaversionplugin.fixes

import net.minusmc.minusbounce.event.EventTarget
import net.minusmc.minusbounce.event.Listenable
import net.minusmc.minusbounce.event.SentPacketEvent
import net.minusmc.viaversionplugin.utils.ViaVersionUtils
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import net.minecraft.network.play.client.*


object PacketFixes: Listenable {

	@EventTarget
	fun onPacket(event: SentPacketEvent) {
		val packet = event.packet

		if (packet is C08PacketPlayerBlockPlacement)
			if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_11)) {
				packet.facingX /= 16f
				packet.facingY /= 16f
				packet.facingZ /= 16f
			}

		if (packet is C03PacketPlayer && !packet.moving && !packet.rotating) {
			if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_9))
				event.isCancelled = true
		} 
	}

	override fun handleEvents() = true
}