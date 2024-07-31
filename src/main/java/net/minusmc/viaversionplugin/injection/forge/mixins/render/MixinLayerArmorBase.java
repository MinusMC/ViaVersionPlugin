package net.minusmc.viaversionplugin.injection.forge.mixins.render;

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

}