package de.florianmichael.viamcp.fixes;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import kotlin.math.*;

public class ActionFixEngine {

    public static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean isSwimmingOrCrawling = false;

    public static boolean doingEyeRot = false;
    public static float eyeHeight = 0f;
    public static float lastEyeHeight = 0f;

    public static boolean isPlayerUnderWater() {
        double playerYPos = mc.thePlayer.posY + mc.thePlayer.eyeHeight - 0.25;
        BlockPos blockPos = new BlockPos(mc.thePlayer.posX, playerYPos, mc.thePlayer.posZ);

        return mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.water && !(mc.thePlayer.ridingEntity instanceof EntityBoat);
    }

    public static boolean isPlayerSwimming() {
        return !mc.thePlayer.noClip && mc.thePlayer.isInWater() && mc.thePlayer.isSprinting();
    }

    public static boolean canPlayerSwimmingOrCrawling() {
        AxisAlignedBB playerBox = mc.thePlayer.getEntityBoundingBox();
        AxisAlignedBB crawlBox = new AxisAlignedBB(
                playerBox.minX, playerBox.minY + 0.9, playerBox.minZ,
                playerBox.minX + 0.6, playerBox.minY + 1.5, playerBox.minZ + 0.6);

        return !mc.thePlayer.noClip && (isSwimmingOrCrawling && mc.thePlayer.isSprinting() && mc.thePlayer.isInWater() || isSwimmingOrCrawling && !mc.theWorld.getCollisionBoxes(crawl).isEmpty());
    }

    public static void fixAnimationEye() {
        if (!ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13)) {
            return;
        }

        lastEyeHeight = eyeHeight;

        if (canPlayerSwimmingOrCrawling()) {
            eyeHeight = EyeHeightFixEngine.animate(0.45f, eyeHeight, 0.34f);
            doingEyeRot = true;
        } else if (eyeHeight < 1.62) {
            eyeHeight = EyeHeightFixEngine.animate(1.62f, eyeHeight, 0.34f);
        }

        if (eyeHeight >= 1.62 && doingEyeRot) {
            doingEyeRot = false;
        }
    }

    public static void fixSneakAndSwimmingOrCrawling() {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && isPlayerSwimming()) {
            mc.thePlayer.motionX = MathHelper.clamp_double(mc.thePlayer.motionX, -0.39, 0.39);
            mc.thePlayer.motionY = MathHelper.clamp_double(mc.thePlayer.motionY, -0.39, 0.39);
            mc.thePlayer.motionZ = MathHelper.clamp_double(mc.thePlayer.motionZ, -0.39, 0.39);

            double lookVecY = mc.thePlayer.getLookVec().yCoord;

            if (lookVecY <= 0.0 || mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 0.36, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water) {
                mc.thePlayer.motionY += (lookVecY - mc.thePlayer.motionY) * 0.025;
            }

            mc.thePlayer.motionY += 0.018;

            if (canPlayerSwimmingOrCrawling()) {
                mc.thePlayer.motionX *= 1.09F;
                mc.thePlayer.motionZ *= 1.09F;
            }
        }

        float sneakLength = 1.65f;
        if (ViaVersionUtils.isCurrentVersionOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
            sneakLength = 1.8f;
        } else if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_14)) {
            sneakLength = 1.5f;
        }

        double rangeSize = mc.thePlayer.width / 2.0;
        AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox();
        AxisAlignedBB setThrough = new AxisAlignedBB(mc.thePlayer.posX - rangeSize, box.minY, mc.thePlayer.posZ - rangeSize,
                mc.thePlayer.posX + rangeSize, box.minY + mc.thePlayer.height, mc.thePlayer.posZ + rangeSize);
        AxisAlignedBB sneak = new AxisAlignedBB(box.minX, box.minY + 0.9, box.minZ, box.minX + 0.6, box.minY + 1.8, box.minZ + 0.6);
        AxisAlignedBB crawl = new AxisAlignedBB(box.minX, box.minY + 0.9, box.minZ, box.minX + 0.6, box.minY + 1.5, box.minZ + 0.6);

        float playerHeight = 0f;
        float playerWidth = 0.6f;

        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && isSwimmingOrCrawling && isPlayerUnderWater() && mc.thePlayer.rotationPitch >= 0.0) {
            playerHeight = 0.6f;
            isSwimmingOrCrawling = true;
        } else if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && (isPlayerSwimming() && isPlayerUnderWater() || !mc.theWorld.getCollisionBoxes(crawl).isEmpty())) {
            playerHeight = 0.6f;
            isSwimmingOrCrawling = true;
        } else if (mc.thePlayer.isSneaking() && !isPlayerUnderWater()) {
            playerHeight = sneakLength;
        } else {
            if (isSwimmingOrCrawling) {
                isSwimmingOrCrawling = false;
            }
            playerHeight = 1.8f;
        }

        mc.thePlayer.setEntityBoundingBox(setThrough);

        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_9) &&
                mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && !isPlayerUnderWater() &&
                (mc.thePlayer.height == sneakLength || mc.thePlayer.height == 0.6F) &&
                !mc.theWorld.getCollisionBoxes(sneak).isEmpty()) {
            mc.gameSettings.keyBindSneak.pressed = true;
        } else if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) &&
                mc.theWorld.getCollisionBoxes(sneak).isEmpty()) {
            mc.gameSettings.keyBindSneak.pressed = false;
        }

        mc.thePlayer.height = playerHeight;
        mc.thePlayer.width = playerWidth;
    }

    public static float fixedEyeHeight() {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && doingEyeRot)
            return lastEyeHeight + (eyeHeight - lastEyeHeight) * mc.timer.renderPartialTicks;

        if (mc.thePlayer.isPlayerSleeping())
            return 0.2F;
        
        return EyeHeightFixEngine.lastEyeHeight + (EyeHeightFixEngine.eyeHeight - EyeHeightFixEngine.lastEyeHeight) * mc.timer.renderPartialTicks;
    }
}
