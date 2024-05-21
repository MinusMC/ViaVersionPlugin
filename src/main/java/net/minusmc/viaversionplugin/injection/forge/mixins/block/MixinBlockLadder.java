/*
 * ViaVersionPlugin Hacked Client
 * A plugin for MinusBounce that it helps you play on any version to 1.20.6.
 * https://github.com/MinusMC/ViaVersionPlugin
 */
package net.minusmc.viaversionplugin.injection.forge.mixins.block;

import net.minecraft.block.BlockLadder;
import net.minusmc.viaversionplugin.ui.ViaVersionFixButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BlockLadder.class, priority = 1001)
public abstract class MixinBlockLadder extends MixinBlock {

    @ModifyConstant(method = "setBlockBoundsBasedOnState", constant = @Constant(floatValue = 0.125F))
    private float ViaVersion_LadderBB(float constant) {
        if (ViaVersionFixButton.Companion.getState())
            return 0.1875F;
        return 0.125F;
    }
}