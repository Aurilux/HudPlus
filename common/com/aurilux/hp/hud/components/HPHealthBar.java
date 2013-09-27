package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPHealthBar extends HudComponent {
	private int maxHealth = 20;

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);

        int left = halfWidth - hudHalfWidth;
        int top = height - hudBaseline;
        
        //Determines if the hearts should be drawn with the highlighted background
        boolean highlight = hud.player.hurtResistantTime / 3 % 2 == 1;

        if (hud.player.hurtResistantTime < 10) {
            highlight = false;
        }

        int health = MathHelper.ceiling_float_int(hud.player.func_110143_aJ());
        int absorb = MathHelper.ceiling_float_int(hud.player.func_110139_bj());
        int healthLast = MathHelper.ceiling_float_int(hud.player.prevHealth);

        hud.getRand().setSeed((long)(hud.getUpdateCounter() * 312871));
        
        int icon = 16;
        int background = 16;
        if (highlight) background += iconSize; //25 total
        // Determines which icon set to use. Different icons are used for hardcore mode.
        int iconSet = 9 * (mc.theWorld.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
        
        if (hud.player.isPotionActive(Potion.poison))      icon += (iconSize * 4); //52 total
        else if (hud.player.isPotionActive(Potion.wither)) icon += (iconSize * 8); //88 total
        
        boolean wounded = health <= 4;
        boolean regen = hud.player.isPotionActive(Potion.regeneration);
        
        for (int i = 0; i < maxHealth/2; i++) {
            int x = left + (i * 8);
            int y = top;
	    	//determines how the current icon will be displayed (full, partial, empty)
            int wholeHeart = (i * 2) + 1;
            
            //draws the appropriate backgound texture
            hud.drawTexturedModalRect(x, y, background, iconSet, iconSize, iconSize);
            
            if (wounded) y += hud.getRand().nextInt(2);
            if (regen && hud.getUpdateCounter() % 25 == i) y -= 2;

            if (highlight)  {
                if (wholeHeart < healthLast)
                	hud.drawTexturedModalRect(x, y, icon + (iconSize * 6), iconSet, iconSize, iconSize);
                else if (wholeHeart == healthLast)
                	hud.drawTexturedModalRect(x, y, icon + (iconSize * 7), iconSet, iconSize, iconSize);
            }
            
            //draws the normal red hearts
            if (wholeHeart < health)
            	hud.drawTexturedModalRect(x, y, icon + (iconSize * 4), iconSet, iconSize, iconSize);
            else if (wholeHeart == health)
            	hud.drawTexturedModalRect(x, y, icon + (iconSize * 5), iconSet, iconSize, iconSize);
            
            //draws the absorb (yellow) hearts which overlay the normal hearts
            if (wholeHeart < absorb)
            	hud.drawTexturedModalRect(x, y, icon + (iconSize * 16), iconSet, iconSize, iconSize);
            else if (wholeHeart == absorb)
            	hud.drawTexturedModalRect(x, y, icon + (iconSize * 17), iconSet, iconSize, iconSize);
        }
	}

	@Override
	public String getProfile() {
		return "health";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.HEALTH;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
		return notCreativeMode;
	}
}