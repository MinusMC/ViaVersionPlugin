package net.minusmc.viaversionplugin.fixes

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import net.minusmc.minusbounce.event.*
import net.minusmc.viaversionplugin.utils.MinecraftInstance
import net.minusmc.viaversionplugin.utils.ViaVersionUtils
import net.minusmc.viaversionplugin.utils.AnimationUtils
import net.minecraft.block.material.Material
import net.minecraft.client.settings.GameSettings
import net.minecraft.entity.item.EntityBoat
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraft.world.World

object SwimmingAndCrawlingEngine : MinecraftInstance(), Listenable {

    private var isSwimmingOrCrawling = false

    @JvmField
    var doingEyeRot = false

    @JvmField
    var eyeHeight = 0f

    @JvmField
    var lastEyeHeight = 0f

    private val isPlayerUnderWater: Boolean 
        get() = {
            val world = mc.thePlayer.entityWorld
            val eyeBlock = mc.thePlayer.posY + mc.thePlayer.eyeHeight - 0.25
            val blockPos = BlockPos(mc.thePlayer.posX, eyeBlock, mc.thePlayer.posZ)

            return world.getBlockState(blockPos).block.material == Material.water && mc.thePlayer.ridingEntity !is EntityBoat
        }

    private val isSwimming:
        get() = !mc.thePlayer.noClip && mc.thePlayer.isInWater && mc.thePlayer.isSprinting

    @JvmStatic
    fun canSwimmingOrCrawling(): Boolean {
        val box: AxisAlignedBB = mc.thePlayer.entityBoundingBox
        val crawl = AxisAlignedBB(box.minX, box.minY + 0.9, box.minZ, box.minX + 0.6, box.minY + 1.5, box.minZ + 0.6)

        return !mc.thePlayer.noClip && (isSwimmingOrCrawling && mc.thePlayer.isSprinting && mc.thePlayer.isInWater || isSwimmingOrCrawling && mc.theWorld.getCollisionBoxes(crawl).isNotEmpty())
    }

    @EventTarget
    fun onPushOut(event: PushOutEvent) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && (canSwimmingOrCrawling() || mc.thePlayer.isSneaking))
            event.cancelEvent()
    }

    @EventTarget
    fun onPreMotion(event: PreMotionEvent) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13)) {
            lastEyeHeight = eyeHeight

            eyeHeight = if (canSwimmingOrCrawling()) {
                AnimationUtils.animate(0.45f, eyeHeight, 0.34f)
            } else if (eyeHeight < 1.62f) {
                AnimationUtils.animate(1.62f, eyeHeight, 0.34f)
            } else eyeHeight

            if (eyeHeight >= 1.62f && doingEyeRot) {
                doingEyeRot = false
            }
        }
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && isSwimming) {
            mc.thePlayer.motionX = mc.thePlayer.motionX.coerceIn(-0.39, 0.39)
            mc.thePlayer.motionY = mc.thePlayer.motionY.coerceIn(-0.39, 0.39)
            mc.thePlayer.motionZ = mc.thePlayer.motionZ.coerceIn(-0.39, 0.39)

            val lookVecY = mc.thePlayer.lookVec.yCoord

            if (lookVecY <= 0.0 || mc.thePlayer.worldObj.getBlockState(BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 0.36, mc.thePlayer.posZ)).block.material == Material.water)
                mc.thePlayer.motionY += (lookVecY - mc.thePlayer.motionY) * 0.025

            mc.thePlayer.motionY += 0.018

            if (canSwimmingOrCrawling())
                mc.thePlayer.motionX *= 1.09f
                mc.thePlayer.motionZ *= 1.09f
        }

        val sneakLength = when {
            ViaVersionUtils.isCurrentVersionOlderThanOrEqualTo(ProtocolVersion.v1_8) -> 1.8f
            ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_14) -> 1.5f
            else -> 1.65f
        }

        val d0 = mc.thePlayer.width / 2.0
        val box = mc.thePlayer.entityBoundingBox
        val setThrough = AxisAlignedBB(mc.thePlayer.posX - d0, box.minY, mc.thePlayer.posZ - d0, mc.thePlayer.posX + d0, box.minY + mc.thePlayer.height, mc.thePlayer.posZ + d0)
        val sneak = AxisAlignedBB(box.minX, box.minY + 0.9, box.minZ, box.minX + 0.6, box.minY + 1.8, box.minZ + 0.6)
        val crawl = AxisAlignedBB(box.minX, box.minY + 0.9, box.minZ, box.minX + 0.6, box.minY + 1.5, box.minZ + 0.6)

        val (newHeight, newWidth) = when {
            ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && isSwimmingOrCrawling && isPlayerUnderWater && mc.thePlayer.rotationPitch >= 0.0 -> {
                isSwimmingOrCrawling = true
                0.6f to 0.6f
            }
            ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && (isSwimming && isPlayerUnderWater || mc.theWorld.getCollisionBoxes(crawl).isNotEmpty()) -> {
                isSwimmingOrCrawling = true
                0.6f to 0.6f
            }
            mc.thePlayer.isSneaking && !isPlayerUnderWater -> {
                sneakLength to 0.6f
            }
            else -> {
                if (isSwimmingOrCrawling) 
                    isSwimmingOrCrawling = false
                1.8f to 0.6f
            }
        }

        mc.thePlayer.setEntityBoundingBox(setThrough)

        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_9) && mc.thePlayer.onGround && !mc.thePlayer.isSneaking && !isPlayerUnderWater &&
            (mc.thePlayer.height == sneakLength || mc.thePlayer.height == 0.6f) && mc.theWorld.getCollisionBoxes(sneak).isNotEmpty()
        ) {
            mc.gameSettings.keyBindSneak.pressed = true
        } else if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) && mc.theWorld.getCollisionBoxes(sneak).isEmpty()) {
            mc.gameSettings.keyBindSneak.pressed = false
        }

        mc.thePlayer.height = newHeight
        mc.thePlayer.width = newWidth
    }

    override fun handleEvents() = true
}