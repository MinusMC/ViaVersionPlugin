package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import net.minusmc.viaversionplugin.fixes.EyeHeightEngine;
import net.minusmc.viaversionplugin.fixes.SwimmingAndCrawlingEngine;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minusmc.viaversionplugin.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    public MixinEntityPlayer(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Shadow
    public abstract boolean isPlayerSleeping();

    /**
     * @author As_pw
     * @reason Eye Height Fix
     */
    @Overwrite
    public float getEyeHeight() {
        final Minecraft mc = MinecraftInstance.mc;
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_13) && SwimmingAndCrawlingEngine.doingEyeRot)
            return SwimmingAndCrawlingEngine.lastEyeHeight + (SwimmingAndCrawlingEngine.eyeHeight - SwimmingAndCrawlingEngine.lastEyeHeight) * mc.timer.renderPartialTicks;
        
        if (this.isPlayerSleeping())
            return 0.2F;
        
        return EyeHeightEngine.lastEyeHeight + (EyeHeightEngine.eyeHeight - EyeHeightEngine.lastEyeHeight) * mc.timer.renderPartialTicks;
    }
}