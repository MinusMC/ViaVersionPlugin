package net.minusmc.viaversionplugin.injection.forge.mixins.block;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockLilyPad.class)
public abstract class MixinBlockLilyPad extends BlockBush {
    @Overwrite
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_9))
            return new AxisAlignedBB((double)pos.getX() + 0.0625D, (double)pos.getY() + 0.0D, (double)pos.getZ() + 0.0625D, (double)pos.getX() + 0.9375D, (double)pos.getY() + 0.09375D, (double)pos.getZ() + 0.9375D);
        return new AxisAlignedBB((double)pos.getX() + 0.0D, (double)pos.getY() + 0.0D, (double)pos.getZ() + 0.0D, (double)pos.getX() + 1.0D, (double)pos.getY() + 0.015625D, (double)pos.getZ() + 1.0D);
    }
}