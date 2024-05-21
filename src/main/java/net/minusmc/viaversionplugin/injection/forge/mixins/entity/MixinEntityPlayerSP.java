package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import de.florianmichael.viamcp.fixes.ActionFixEngine;
import de.florianmichael.viamcp.fixes.EyeHeightFixEngine;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class, priority = 1001)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP(World world, GameProfile playerProfile) {
        super(world, playerProfile);
    }

    @Shadow
    public abstract boolean isSneaking();

    /**
     * @author Aspw
     * @reason 1.13+ swimming & crawling fixes
     */

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    private void onUpdateWalkingPlayer(CallbackInfo ci) {
        EyeHeightFixEngine.fixEyeHeight();
        ActionFixEngine.fixAnimationEye();

        EyeHeightFixEngine.fixEyeHeight();
        ActionFixEngine.fixAnimationEye();
    }

    /**
     * @author Aspw
     * @reason 1.13+ swimming & crawling fixes
     */
    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void onLivingUpdate(CallbackInfo ci) {
        ActionFixEngine.fixSneakAndSwimmingOrCrawling();
    }


    /**
     * @author Aspw
     * @reason 1.13+ swimming & crawling fixes
     */
    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void pushOutEvent(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this.noClip) {
            callbackInfoReturnable.setReturnValue(false);
            return;
        }

        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && 
            (ActionFixEngine.isPlayerUnderWater() || this.isSneaking()))
            callbackInfoReturnable.setReturnValue(false);
    }
}