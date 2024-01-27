/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors

 */
package net.minusmc.viaversionplugin.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import de.florianmichael.viamcp.gui.AsyncVersionSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.ArrayList;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {
    @Inject(method = "createButtons", at = @At("HEAD"))
    public void createButtons(CallbackInfo ci) {
        // if client is LiquidBounce, replace this
        // buttonList.add(new AsyncVersionSlider(-1, this.width - 213, 8, 98, 20));
        buttonList.add(new AsyncVersionSlider(-1, this.width - 115, 8, 98, 20));
    }
}
