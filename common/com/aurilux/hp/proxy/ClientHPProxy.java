package com.aurilux.hp.proxy;

import com.aurilux.hp.api.HudRegistry;
import com.aurilux.hp.hud.components.HPAir;
import com.aurilux.hp.hud.components.HPArmorValue;
import com.aurilux.hp.hud.components.HPBossHealth;
import com.aurilux.hp.hud.components.HPBuffs;
import com.aurilux.hp.hud.components.HPChat;
import com.aurilux.hp.hud.components.HPCrosshairs;
import com.aurilux.hp.hud.components.HPExperienceBar;
import com.aurilux.hp.hud.components.HPFoodBar;
import com.aurilux.hp.hud.components.HPHealthBar;
import com.aurilux.hp.hud.components.HPHealthBarMount;
import com.aurilux.hp.hud.components.HPHotbar;
import com.aurilux.hp.hud.components.HPHudText;
import com.aurilux.hp.hud.components.HPJumpBar;
import com.aurilux.hp.hud.components.HPPlayerList;
import com.aurilux.hp.hud.components.HPRecordOverlay;
import com.aurilux.hp.hud.components.HPToolHighlight;

public class ClientHPProxy extends CommonHPProxy {
	
	@Override
	public void registerComponents() {
		HudRegistry.registerComponent(new HPAir());
		//HudRegistry.registerComponent(new GPArmorDurability());
		HudRegistry.registerComponent(new HPArmorValue());
		HudRegistry.registerComponent(new HPBossHealth());
		HudRegistry.registerComponent(new HPBuffs());
		HudRegistry.registerComponent(new HPChat());
		HudRegistry.registerComponent(new HPCrosshairs());
		HudRegistry.registerComponent(new HPExperienceBar());
		HudRegistry.registerComponent(new HPFoodBar());
		HudRegistry.registerComponent(new HPHealthBar());
		HudRegistry.registerComponent(new HPHealthBarMount());
		HudRegistry.registerComponent(new HPHotbar());
		HudRegistry.registerComponent(new HPHudText());
		HudRegistry.registerComponent(new HPJumpBar());
		HudRegistry.registerComponent(new HPPlayerList());
		HudRegistry.registerComponent(new HPRecordOverlay());
		HudRegistry.registerComponent(new HPToolHighlight());
	}
}