package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPBuffs extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		// TODO render all current buffs
	}

	@Override
	public String getProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		// TODO Auto-generated method stub
		return false;
	}
}