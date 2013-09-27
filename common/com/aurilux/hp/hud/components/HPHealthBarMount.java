package com.aurilux.hp.hud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPHealthBarMount extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(ICONS);
		
        EntityLivingBase mount = (EntityLivingBase) hud.player.ridingEntity;
        int health = MathHelper.ceiling_float_int(mount.func_110143_aJ());
        
        int top = height - hudBaseline;
        int left = halfWidth + hudHalfWidth;
        int rowCount = MathHelper.ceiling_float_int(health / 10);

        for (int i = 0; i < rowCount; i++) {
            int rowDiff = (i * 20);
            int hearts = Math.min((health + 1 - rowDiff) / 2, 10);
            
        	for (int j = 0; j < hearts; j++) {
                int x = left - (j * 8) - iconSize;
                int y = top - (i * levelOffset);
                int icon = 52;
                int background = icon;
    	    	//determines how the current icon will be displayed (full, partial, empty)
                int wholeHeart = (j * 2 + 1);
                hud.drawTexturedModalRect(x, y, background, 9, iconSize, iconSize);

                if (wholeHeart < health - rowDiff)
                	hud.drawTexturedModalRect(x, y, icon + (iconSize * 4), 9, iconSize, iconSize);
                else if (wholeHeart == health - rowDiff)
                	hud.drawTexturedModalRect(x, y, icon + (iconSize * 5), 9, iconSize, iconSize);
        		
        	}
        }

	}

	@Override
	public String getProfile() {
		return "healthmount";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean notCreativeMode = mc.playerController.shouldDrawHUD();
		boolean entityIsLiving = player.ridingEntity instanceof EntityLivingBase;
		return notCreativeMode && entityIsLiving;
	}
}