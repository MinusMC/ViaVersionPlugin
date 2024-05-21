package de.florianmichael.viamcp.fixes;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

public class EyeHeightFixEngine {

    public static float eyeHeight = 0f;
    public static float lastEyeHeight = 0f;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void fixEyeHeight() {
        float endHeight;
        float delta;

        if (ViaVersionUtils.isCurrentVersionOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
            endHeight = 1.54f;
            delta = 0.154f;
        } else if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_14)) {
            endHeight = 1.32f;
            delta = 0.132f;
        } else {
            endHeight = 1.47f;
            delta = 0.147f;
        }

        if (mc.thePlayer != null && mc.thePlayer.isSneaking()) {
            eyeHeight = animate(endHeight, eyeHeight, 4 * delta);
        } else if (eyeHeight < 1.62f) {
            eyeHeight = animate(1.62f, eyeHeight, 4 * delta);
        }
    }

    public static float animate(float target, float current, float pSpeed) {
        if (target == pSpeed) {
            return current;
        }

        float speed = MathHelper.clamp_float(pSpeed, 0f, 1f);

        float dif = Math.max(target, current) - Math.min(target, current);
        float factor = Math.max(dif * speed, 0.01f);

        return target > current ? Math.min(current + factor, target) : Math.max(current - factor, target);
    }

    public static void fixSwordAnimation(ModelRenderer bipedLeftArm, ModelRenderer bipedRightArm, ModelRenderer bipedHead, ModelRenderer bipedHeadwear, boolean isSneak, Entity p_setRotationAngles7) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && ActionFixEngine.canPlayerSwimmingOrCrawling() && p_setRotationAngles7 instanceof EntityPlayer && p_setRotationAngles7.equals(mc.thePlayer)) {
            GlStateManager.rotate(45.0F, 1F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, -0.7F);

            float swing = mc.thePlayer.limbSwing / 3;

            bipedHead.rotateAngleX = -0.95f;
            bipedHeadwear.rotateAngleX = -0.95f;
            bipedLeftArm.rotateAngleX = swing;
            bipedRightArm.rotateAngleX = swing;
            bipedLeftArm.rotateAngleY = swing;
            bipedRightArm.rotateAngleY = -swing;
            isSneak = false;
        }
    }
}
