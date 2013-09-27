package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPBossHealth extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
        --BossStatus.statusBarLength;
        
        FontRenderer fr = hud.fontrenderer;
        short barWidth = 182;
        int currentBossHealth = (int)(BossStatus.healthScale * (float)(barWidth + 1));
        int x = halfWidth - (barWidth / 2);
        //draws the background of the boss health bar
        hud.drawTexturedModalRect(x, 12, 0, 74, barWidth, 5);
        
        //draws the boss's current health
        if (currentBossHealth > 0) {
            hud.drawTexturedModalRect(x, 12, 0, 79, currentBossHealth, 5);
        }

        String bossname = BossStatus.bossName;
        fr.drawStringWithShadow(bossname, halfWidth - (fr.getStringWidth(bossname) / 2), 2, 16777215);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public String getProfile() {
		return "bosshealth";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.BOSSHEALTH;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean bossPresent = BossStatus.bossName != null && BossStatus.statusBarLength > 0;
		return bossPresent;
	}
}