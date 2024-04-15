package net.minusmc.viaversionplugin.injection.forge.mixins.item;

import de.florianmichael.viamcp.fixes.FixedSoundEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemBlock.class)
public class MixinItemBlock extends Item {
    /**
     * @author FlorianMichael
     * @reason Fix item using sound engine
     */
    @Overwrite
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        return FixedSoundEngine.onItemUse((ItemBlock) (Object) this, stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }
}