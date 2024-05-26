package net.minusmc.viaversionplugin.injection.forge.mixins.render;

import net.minusmc.viaversionplugin.fixes.EyeHeightEngine;
import net.minusmc.viaversionplugin.fixes.SwimmingAndCrawlingEngine;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import net.minusmc.viaversionplugin.utils.MinecraftInstance;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public abstract class MixinModelBiped {

    @Shadow
    public ModelRenderer bipedRightArm;

    @Shadow
    public ModelRenderer bipedLeftArm;

    @Shadow
    public boolean isSneak;

    @Shadow
    public ModelRenderer bipedHead;

    @Shadow
    public ModelRenderer bipedHeadwear;

    @Inject(method = "setRotationAngles", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F"))
    private void revertSwordAnimation(float p_setRotationAngles1, float p_setRotationAngles2, float p_setRotationAngles3, float p_setRotationAngles4, float p_setRotationAngles5, float p_setRotationAngles6, Entity p_setRotationAngles7, CallbackInfo callbackInfo) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && SwimmingAndCrawlingEngine.canSwimmingOrCrawling() && p_setRotationAngles7 instanceof EntityPlayer && p_setRotationAngles7.equals(MinecraftInstance.mc.thePlayer)) {
            GlStateManager.rotate(45.0F, 1F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, -0.7F);

            float swing = MinecraftInstance.mc.thePlayer.limbSwing / 3;

            this.bipedHead.rotateAngleX = -0.95f;
            this.bipedHeadwear.rotateAngleX = -0.95f;
            this.bipedLeftArm.rotateAngleX = swing;
            this.bipedRightArm.rotateAngleX = swing;
            this.bipedLeftArm.rotateAngleY = swing;
            this.bipedRightArm.rotateAngleY = -swing;
            this.isSneak = false;
        }
    }
}