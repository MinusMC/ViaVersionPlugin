package net.minusmc.viaversionplugin.injection.forge.mixins.render;

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
}