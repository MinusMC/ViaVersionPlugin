/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors

 */
package net.minusmc.viaversionplugin.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import de.florianmichael.viamcp.gui.AsyncVersionSlider;
import net.minusmc.viaversionplugin.ui.ViaVersionFixButton;
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
        buttonList.add(new AsyncVersionSlider(-1, 5, 40, 98, 20));
        buttonList.add(new ViaVersionFixButton(-2, 5, 65, 110, 20));
    }
}
