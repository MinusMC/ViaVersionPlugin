/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors

 */
package net.minusmc.viaversionplugin.injection.forge.mixins.client;

import de.florianmichael.viamcp.ViaMCP;
import de.florianmichael.viamcp.fixes.AttackOrder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public MovingObjectPosition objectMouseOver;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void startVia(GameConfiguration p_i45547_1_, CallbackInfo ci) {
        ViaMCP.create();

        ViaMCP.INSTANCE.initAsyncSlider(5, 40, 110, 20);
    }

    @Redirect(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingItem()V"))
    private void fixAttackOrder_VanillaSwing(EntityPlayerSP player) {
        AttackOrder.sendConditionalSwing(this.objectMouseOver);
    }


    @Redirect(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    public void fixAttackOrder_VanillaAttack(PlayerControllerMP controller, EntityPlayer player, Entity e) {
        AttackOrder.sendFixedAttack(this.thePlayer, this.objectMouseOver.entityHit);
    }
}
