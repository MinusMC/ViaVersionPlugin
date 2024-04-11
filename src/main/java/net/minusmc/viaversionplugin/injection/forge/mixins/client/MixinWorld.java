/*
 * MinusBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/MinusMC/MinusBounce
 */
package net.minusmc.minusbounce.injection.forge.mixins.world;

import de.florianmichael.viamcp.fixes.FixedSoundEngine;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public abstract class MixinWorld {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
        return FixedSoundEngine.destroyBlock((World) (Object) this, pos, dropBlock);
    }
}
