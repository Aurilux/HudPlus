package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.ForgeHooks;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPArmorValue extends HudComponent {
	
	private int maxArmor = 20;

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
		int left = halfWidth - hudHalfWidth;
	    int top = height - (hudBaseline + levelOffset);
	
	    int level = ForgeHooks.getTotalArmorValue(hud.player);
	    for (int i = 0; i < maxArmor/2; i++) {
	        int x = left + (i * 8);
	    	int y = top;
	    	int icon = 16;
	    	//determines how the current icon will be displayed (full, partial, empty)
	    	int wholeArmor = (i * 2) + 1;
	    	
	        if (wholeArmor < level) icon += (iconSize * 2); //34 total
	        else if (wholeArmor == level) icon += iconSize; //25 total
            hud.drawTexturedModalRect(x, y, icon, 9, iconSize, iconSize);
	    }
	}

	@Override
	public String getProfile() {
		return "armor";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.ARMOR;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
		boolean wearingArmor = ForgeHooks.getTotalArmorValue(player) > 0;
		return wearingArmor && notCreativeMode;
	}
}