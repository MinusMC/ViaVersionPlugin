package net.minusmc.viaversionplugin

import net.minusmc.minusbounce.MinusBounce
import net.minusmc.minusbounce.plugin.Plugin
import net.minusmc.minusbounce.plugin.PluginAPIVersion
import net.minusmc.viaversionplugin.modules.SwapOffhandBypass
import net.minusmc.viaversionplugin.packets.*

import net.minecraft.network.Packet

import com.viaversion.viaversion.api.connection.UserConnection

object ViaVersionPlugin: Plugin("ViaVersionPlugin", version = "0.2", minApiVersion = PluginAPIVersion.VER_01) {
	lateinit var viaUser: UserConnection

	override fun registerModules() {
		MinusBounce.moduleManager.registerModule(SwapOffhandBypass())
	}

	fun handlePacket(packet: Packet<*>): Boolean {
		when (packet) {
			is C1APacketSwapHand -> PacketHandler.handlePacketSwapHand(packet)
			is C1BPacketTeleportConfirm -> PacketHandler.handlePacketTeleportConfirm(packet)
			else -> return false
		}

		return true
	}
}