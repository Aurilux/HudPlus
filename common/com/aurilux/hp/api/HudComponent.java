package com.aurilux.hp.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.aurilux.hp.hud.HPHud;

public abstract class HudComponent {
	//TEXTURE LOCATIONS
	protected static final ResourceLocation VIGNETTE     = new ResourceLocation("textures/misc/vignette.png");
    protected static final ResourceLocation WIDGETS      = new ResourceLocation("textures/gui/widgets.png");
    protected static final ResourceLocation PUMPKIN_BLUR = new ResourceLocation("textures/misc/pumpkinblur.png");
    protected static final ResourceLocation ICONS        = new ResourceLocation("textures/gui/icons.png");
    
    //PROTECTED FINAL VARIABLES
    /** Used by components above the hotbar to determine the y-coord for rendering. */
	protected final int hudBaseline = 39;
	/** Used by air and armor to render above the health and food bars respectively. */
	protected final int levelOffset = 10;
	/** The width and height of the icons used by armor, food, health, air, etc. */
	protected final int iconSize = 9;
	/** Half the width of the hotbar. Used by most other components to determine drawing origins. */
	protected final int hudHalfWidth = 91;
	
	//PROTECTED, FREQUENTLY USED VARIABLES
	protected int width;
	protected int height;
	protected int halfWidth;
	protected int halfHeight;
	
	/** Renders this HUD Component.*/
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		width = res.getScaledWidth();
		height = res.getScaledHeight();
		halfWidth = width / 2;
		halfHeight = height / 2;
	}
	
	/** Gets the profiler tag for this component used by the Minecraft profiler.*/
	public abstract String getProfile();
	
	/** Gets the RenderGameOverlayEvent.ElementType that this component uses for MinecraftForge event firing.*/
	public abstract ElementType getType();
	
	/** Determines if this component should render. For example, the food, health, armor, exp bar,
	 * and air won't render if the player is in creative mode.*/
	public abstract boolean shouldRender(Minecraft mc, EntityClientPlayerMP player);
}