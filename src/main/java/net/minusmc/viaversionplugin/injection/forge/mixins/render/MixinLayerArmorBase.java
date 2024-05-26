package net.minusmc.viaversionplugin.injection.forge.mixins.render;

import net.minusmc.viaversionplugin.fixes.SwimmingAndCrawlingEngine;
import net.minusmc.viaversionplugin.utils.MinecraftInstance;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase {
    @Inject(method = "doRenderLayer", at = @At("HEAD"), cancellable = true)
    public void doRenderLayer(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final CallbackInfo ci) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && SwimmingAndCrawlingEngine.canSwimmingOrCrawling() && entitylivingbaseIn == MinecraftInstance.mc.thePlayer)
            ci.cancel();
    }
}