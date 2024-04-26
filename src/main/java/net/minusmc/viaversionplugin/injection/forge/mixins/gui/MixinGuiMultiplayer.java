/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors

 */
package net.minusmc.viaversionplugin.injection.forge.mixins.gui;

import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minusmc.viaversionplugin.ui.ViaVersionFixButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {
    @Inject(method = "createButtons", at = @At("HEAD"))
    public void createButtons(CallbackInfo ci) {
        buttonList.add(ViaMCP.INSTANCE.getAsyncVersionSlider());
        buttonList.add(new ViaVersionFixButton(-2, 5, 65, 110, 20));
    }
}
