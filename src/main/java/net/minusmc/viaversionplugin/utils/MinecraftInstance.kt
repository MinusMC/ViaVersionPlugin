package net.minusmc.viaversionplugin.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP

open class MinecraftInstance {
    companion object {
        @JvmField
        val mc: Minecraft = Minecraft.getMinecraft()
    }
}