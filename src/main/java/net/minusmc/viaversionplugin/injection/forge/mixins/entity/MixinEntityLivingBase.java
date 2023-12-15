/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import cc.paimonmc.viamcp.ViaMCP;
import net.minecraft.entity.EntityLivingBase;
import net.minusmc.viaversionplugin.injection.forge.mixins.entity.MixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.005D))
    private double ViaVersion_MovementThreshold(double constant) {
        if (ViaMCP.getInstance().getVersion() >= 107)
            return 0.003D;
        return 0.005D;
    }
}