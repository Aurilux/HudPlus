package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPJumpBar extends HudComponent {
	
	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float charge = hud.player.func_110319_bJ();
        final int barWidth = 182;
        int x = halfWidth - (barWidth / 2);
        int filled = (int)(charge * (float)(barWidth + 1));
        int top = height - 32 + 3;
        
        //draws the jumpbar background, aka empty jumpbar
        hud.drawTexturedModalRect(x, top, 0, 84, barWidth, 5);
        
        //draws the jumpbar foreground, aka filled jumpbar
        if (filled > 0) {
            hud.drawTexturedModalRect(x, top, 0, 89, filled, 5);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public String getProfile() {
		return "jumpbar";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.JUMPBAR;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean isRidingHorse = player.func_110317_t();
		return isRidingHorse;
	}
}