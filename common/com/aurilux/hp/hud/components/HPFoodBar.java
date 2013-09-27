package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPFoodBar extends HudComponent {
	//TODO include a way to display saturation (recoloured haunches which overlay the usual ones)
	private int maxFood = 20;

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
        int left = halfWidth + hudHalfWidth;
        int top = height - hudBaseline;

        FoodStats stats = mc.thePlayer.getFoodStats();
        int level = stats.getFoodLevel();
        
        for (int i = 0; i < maxFood/2; i++)  {
            int x = left - (i * 8) - iconSize;
            int y = top;
            int icon = 16;
            byte backgound = 0;
	    	//determines how the current icon will be displayed (full, partial, empty)
            int wholeFood = (i * 2) + 1;

            if (stats.getSaturationLevel() <= 0.0F && hud.getUpdateCounter() % (level * 3 + 1) == 0) {
                y = top + (hud.getRand().nextInt(3) - 1);
            }

            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                icon += 36;
                backgound = 13;
            }
            //draws the haunch outline (background)
            hud.drawTexturedModalRect(x, y, 16 + (backgound * iconSize), 27, iconSize, iconSize);

            //draws the actual food haunch
            if (wholeFood < level)
        		hud.drawTexturedModalRect(x, y, icon + (iconSize * 4), 27, iconSize, iconSize);
            else if (wholeFood == level)
        		hud.drawTexturedModalRect(x, y, icon + (iconSize * 5), 27, iconSize, iconSize);
            //else; draw nothing. no haunch is drawn but the background is.
        }
	}

	@Override
	public String getProfile() {
		return "food";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.FOOD;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
        boolean notRidingEntity = mc.thePlayer.ridingEntity == null;
		return notCreativeMode && notRidingEntity;
	}
}