package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPCrosshairs extends HudComponent {
	private int crosshairSize = 14;

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
        hud.drawTexturedModalRect(halfWidth - (crosshairSize / 2), halfHeight - (crosshairSize / 2), 0, 0, crosshairSize, crosshairSize);
        GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public String getProfile() {
		return "crosshairs";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.CROSSHAIRS;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		// Regardless of the gamemode, the crosshairs should always be rendered
		return true;
	}
}