package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPExperienceBar extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
        FontRenderer fr = mc.fontRenderer;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int cap = hud.player.xpBarCap();
        int left = width / 2 - hudHalfWidth;

        if (cap > 0) {
            short barWidth = 182;
            int filled = (int)(hud.player.experience * (float)(barWidth + 1));
            int top = height - 32 + 3;
            hud.drawTexturedModalRect(left, top, 0, 64, barWidth, 5);

            if (filled > 0) {
                hud.drawTexturedModalRect(left, top, 0, 69, filled, 5);
            }
        }

        if (hud.player.experienceLevel > 0) {
            boolean flag1 = false;
            int color = flag1 ? 16777215 : 8453920;
            String text = "" + hud.player.experienceLevel;
            int x = (width - fr.getStringWidth(text)) / 2;
            int y = height - 31 - 4;
            fr.drawString(text, x + 1, y, 0);
            fr.drawString(text, x - 1, y, 0);
            fr.drawString(text, x, y + 1, 0);
            fr.drawString(text, x, y - 1, 0);
            fr.drawString(text, x, y, color);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public String getProfile() {
		return "experiencebar";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.EXPERIENCE;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
		return notCreativeMode;
	}
}