package net.minusmc.viaversionplugin.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton

class ViaVersionFixButton(id: Int, x: Int, y: Int, width: Int, height: Int): GuiButton(id, x, y, width, height, "Via Version Fix: OFF") {
    companion object {
        var state = false
    }

    override fun drawButton(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        displayString = "Via Version Fix: ${if (state) "ON" else "OFF"}"
        super.drawButton(mc, mouseX, mouseY)
    }

    override fun mousePressed(mc: Minecraft?, mouseX: Int, mouseY: Int): Boolean {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            state = !state
            return true
        }
        return false
    }
}