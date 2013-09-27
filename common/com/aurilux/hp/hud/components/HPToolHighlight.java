package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPToolHighlight extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);

        if (hud.getRemainingHighlightTicks() > 0 && hud.getHighlightingItemStack() != null) {
            String name = hud.getHighlightingItemStack().getDisplayName();

            int opacity = (int)((float)hud.getRemainingHighlightTicks() * 256.0F / 10.0F);
            if (opacity > 255) opacity = 255;

            if (opacity > 0) {
                int y = height - 59;
                if (!mc.playerController.shouldDrawHUD()) y += 14;

                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                FontRenderer font = hud.getHighlightingItemStack().getItem().getFontRenderer(hud.getHighlightingItemStack());
                if (font != null) {
                    int x = (width - font.getStringWidth(name)) / 2;
                    font.drawStringWithShadow(name, x, y, 0xFFFFFF| (opacity << 24));
                }
                else  {
                    int x = (width - hud.fontrenderer.getStringWidth(name)) / 2;
                    hud.fontrenderer.drawStringWithShadow(name, x, y, 0xFFFFFF | (opacity << 24));
                }
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
	}

	@Override
	public String getProfile() {
		return "toolhighlight";
	}

	@Override
	public ElementType getType() {
		return null;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean tooltipsEnabled = mc.gameSettings.heldItemTooltips;
		return tooltipsEnabled;
	}
}