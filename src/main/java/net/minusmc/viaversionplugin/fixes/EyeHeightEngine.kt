package net.minusmc.viaversionplugin.fixes

import net.minusmc.minusbounce.event.EventTarget
import net.minusmc.minusbounce.event.Listenable
import net.minusmc.minusbounce.event.PreMotionEvent
import net.minusmc.viaversionplugin.utils.MinecraftInstance
import net.minusmc.viaversionplugin.utils.ViaVersionUtils
import net.minusmc.viaversionplugin.utils.AnimationUtils
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion

object EyeHeightEngine : MinecraftInstance(), Listenable {

    @JvmField
    var eyeHeight = 0.0f

    @JvmField
    var lastEyeHeight = 0.0f

    @EventTarget
    fun onPreMotion(event: PreMotionEvent) {
        mc.leftClickCounter = 0

        val endHeight = when {
            ViaVersionUtils.isCurrentVersionOlderThanOrEqualTo(ProtocolVersion.v1_8) -> 1.54f
            ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_14) -> 1.32f
            else -> 1.47f
        }

        val delta = when {
            ViaVersionUtils.isCurrentVersionOlderThanOrEqualTo(ProtocolVersion.v1_8) -> 0.616f
            ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_14) -> 0.528f
            else -> 0.588f
        }

        lastEyeHeight = eyeHeight

        eyeHeight = if (mc.thePlayer.isSneaking) {
            AnimationUtils.animate(endHeight, eyeHeight, delta)
        } else if (eyeHeight < 1.62f) {
            AnimationUtils.animate(1.62f, eyeHeight, delta)
        } else eyeHeight
    }

    override fun handleEvents() = true
}
