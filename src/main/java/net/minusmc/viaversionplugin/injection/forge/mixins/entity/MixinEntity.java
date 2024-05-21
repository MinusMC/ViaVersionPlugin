/*
 * ViaVersionPlugin Hacked Client
 * A plugin for MinusBounce that it helps you play on any version to 1.20.6.
 * https://github.com/MinusMC/ViaVersionPlugin
 */
package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import net.minecraft.entity.Entity;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method = "getCollisionBorderSize", at = @At("HEAD"), cancellable = true)
    private void getCollisionBorderSize(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_9)) {
            callbackInfoReturnable.setReturnValue(0.0F);
        }
    }
}