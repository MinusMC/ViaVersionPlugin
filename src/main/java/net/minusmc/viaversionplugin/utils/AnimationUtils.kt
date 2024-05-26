package net.minusmc.viaversionplugin.utils

import kotlin.math.*

object AnimationUtils {

    fun animate(target: Float, current: Float, speed: Float): Float {
        if (current == target) return current

        val clampedSpeed = speed.coerceIn(0.0f, 1.0f)
        val difference = abs(target - current)
        val adjustment = (difference * clampedSpeed).coerceAtLeast(0.01f)

        return if (target > current) {
            (current + adjustment).coerceAtMost(target)
        } else {
            (current - adjustment).coerceAtLeast(target)
        }
    }
    
}
