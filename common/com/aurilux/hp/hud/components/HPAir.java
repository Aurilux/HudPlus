package com.aurilux.hp.hud.components;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPAir extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
	    
		//a full air bar returns 300, but only 10 (max) bubbles are drawn
        int air = hud.player.getAir();
        //These two following variables are used to determine whether to draw a popped bubble or not.
        int full = MathHelper.ceiling_double_int((double)(air - 2) / 30.0D);
        int partial = MathHelper.ceiling_double_int((double)air / 30.0D) - full;

	    int top = height - (hudBaseline + levelOffset);
		int left = halfWidth + hudHalfWidth;
	    
		//draws all of the air bubbles
        for (int i = 0; i < full + partial; i++) {
        	int x = left - (i * 8) - iconSize;
        	int y = top;
        	int icon = 16;
        	if (i < full) icon += iconSize;
            hud.drawTexturedModalRect(x, y, icon, 18, iconSize, iconSize);
        }
	}

	@Override
	public String getProfile() {
		return "air";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.AIR;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
		boolean isUnderwater = player.isInsideOfMaterial(Material.water);
		return isUnderwater && notCreativeMode;
	}
}