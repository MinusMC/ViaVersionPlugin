package net.minusmc.viaversionplugin.injection.forge.mixins.entity;

import de.florianmichael.viamcp.fixes.ActionFixEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    public MixinEntityPlayer(World world) {
        super(world);
    }

    /**
     * @author Aspw
     * @reason Eye Height Fix
     */
    @Overwrite
    public float getEyeHeight() {
        return ActionFixEngine.fixedEyeHeight();
    }
}