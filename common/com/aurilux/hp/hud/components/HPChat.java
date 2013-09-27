package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPChat extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(height - 48), 0.0F);
        hud.getChatGUI().drawChat(hud.getUpdateCounter());
        GL11.glPopMatrix();
	}

	@Override
	public String getProfile() {
		return "chat";
	}

	@Override
	public ElementType getType() {
		return null;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		// Regardless of the gamemode, the chat should always be rendered
		return true;
	}
}