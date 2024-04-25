package net.minusmc.viaversionplugin.modules


import net.minusmc.minusbounce.features.module.Module
import net.minusmc.minusbounce.features.module.ModuleCategory
import net.minusmc.minusbounce.features.module.ModuleInfo

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9
import com.viaversion.viaversion.api.minecraft.Position

import java.util.Timer
import kotlin.concurrent.schedule

@ModuleInfo(name = "SwapOffhandBypass", description = "SwapOffhandBypass", category = ModuleCategory.MISC)
class SwapOffhandBypass: Module() {

	override fun onEnable() {
		setOffhand() 

		Timer().schedule(200L) { 
		    setOffhand()
		}

		state = false
	}

	private fun setOffhand() {
		val swapItemPacket = PacketWrapper.create(ServerboundPackets1_9.PLAYER_DIGGING, null)
		swapItemPacket.write(Type.VAR_INT, 6)
		swapItemPacket.write(Type.POSITION1_8, Position(0, 0, 0))
		swapItemPacket.write(Type.BYTE, 0.toByte())


		try {
			swapItemPacket.sendToServer(Protocol1_8To1_9.javaClass)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

}