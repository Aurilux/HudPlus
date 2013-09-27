package com.aurilux.hp.hud.components;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPRecordOverlay extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
        
        if (hud.getRecordPlayingUpFor() > 0) {
            float hue = (float)hud.getRecordPlayingUpFor() - partialTicks;
            int opacity = (int)(hue * 256.0F / 20.0F);
            if (opacity > 255) opacity = 255;

            if (opacity > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(width / 2), (float)(height - 48), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int color = (hud.getIsRecordPlaying() ? Color.HSBtoRGB(hue / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF);
                hud.fontrenderer.drawString(hud.getRecordPlaying(), -hud.fontrenderer.getStringWidth(hud.getRecordPlaying()) / 2, -4, color | (opacity << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
	}

	@Override
	public String getProfile() {
		return "recordoverlay";
	}

	@Override
	public ElementType getType() {
		return null;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		return true;
	}
}