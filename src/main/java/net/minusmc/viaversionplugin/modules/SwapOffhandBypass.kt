package net.minusmc.viaversionplugin.modules

import net.minusmc.viaversionplugin.ViaVersionPlugin
import net.minusmc.viaversionplugin.features.module.Module
import net.minusmc.viaversionplugin.features.module.ModuleCategory
import net.minusmc.viaversionplugin.features.module.ModuleInfo

import net.minusmc.viaversionplugin.packets.C1APacketSwapHand

@ModuleInfo(name = "SwapOffhandBypass", description = "SwapOffhandBypass", category = ModuleCategory.MISC, onlyEnable = true)
class SwapOffhandBypass: Module() {
	override fun onEnable() {
		mc.netHandler.addToSendQueue(C1APacketSwapHand())
		state = false
	}
}