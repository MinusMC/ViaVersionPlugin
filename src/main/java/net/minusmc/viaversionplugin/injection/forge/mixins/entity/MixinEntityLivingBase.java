/*
 * ViaVersionPlugin Hacked Client
 * A plugin for MinusBounce that it helps you play on any version to 1.20.6.
 * https://github.com/MinusMC/ViaVersionPlugin
 */
package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minusmc.viaversionplugin.utils.ViaVersionUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    public MixinEntityLivingBase(World world) {
        super(world);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.005D))
    private double ViaVersion_MovementThreshold(double constant) {
        if (ViaVersionUtils.isCurrentVersionNewerThanOrEqualTo(ProtocolVersion.v1_9))
            return 0.003D;
        return 0.005D;
    }
}