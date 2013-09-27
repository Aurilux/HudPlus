package com.aurilux.hp.handlers;

import java.util.EnumSet;

import com.aurilux.hp.hud.HPHud;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class HPTickHandler implements ITickHandler {

	//private variable to ensure I only change the in-game gui once (no need to do it after the first)
    private boolean ticked = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//this does nothing
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = Minecraft.getMinecraft();
		if (!ticked && mc.ingameGUI != null) {
	        mc.ingameGUI = new HPHud(mc);
	        ticked = true;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "Hotbar Plus GUI";
	}
}