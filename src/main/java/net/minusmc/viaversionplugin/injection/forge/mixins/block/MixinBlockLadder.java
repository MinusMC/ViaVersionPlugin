/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.minusmc.viaversionplugin.injection.forge.mixins.block;

import cc.paimonmc.viamcp.ViaMCP;
import net.minecraft.block.BlockLadder;
import net.minusmc.viaversionplugin.injection.forge.mixins.block.MixinBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockLadder.class)
public abstract class MixinBlockLadder extends MixinBlock {

    @ModifyConstant(method = "setBlockBoundsBasedOnState", constant = @Constant(floatValue = 0.125F))
    private float ViaVersion_LadderBB(float constant) {
        if (ViaMCP.getInstance().getVersion() >= 107)
            return 0.1875F;
        return 0.125F;
    }
}