package com.aurilux.hp.handlers;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class HPKeyHandler extends KeyHandler {
	//Keys that can actually be changed in key configs
	public static KeyBinding toggleKey = new KeyBinding("Hotbar Toggle", Keyboard.KEY_LCONTROL);
	
	public static boolean toggleKeyPressed = false;

    public HPKeyHandler() {
        super(new KeyBinding[] {toggleKey}, new boolean[] {false});
    }
    
	@Override
	public String getLabel() {
		return "HudPlus";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (tickEnd && kb.keyCode == toggleKey.keyCode) {
			toggleKeyPressed = !toggleKeyPressed;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
        return EnumSet.allOf(TickType.class);
	}
}