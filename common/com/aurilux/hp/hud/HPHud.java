package com.aurilux.hp.hud;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.ALL;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HELMET;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.PORTAL;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.api.HudRegistry;

public class HPHud extends GuiIngameForge {
	//TODO use reflection helper to get and set private values?
	//PRIVATE VARIABLES
    private Profiler profiler;
    private ScaledResolution res;
  	private RenderItem itemRenderer;
    private RenderGameOverlayEvent eventParent;
    
    //PUBLIC VARIABLES
    public FontRenderer fontrenderer;
    public EntityClientPlayerMP player;

	public HPHud(Minecraft mc) {
		super(mc);
		res = null;
        profiler = mc.mcProfiler;
        itemRenderer = new RenderItem();
        fontrenderer = mc.fontRenderer;
	}
    
    @Override
    public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
        res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        eventParent = new RenderGameOverlayEvent(partialTicks, res, mouseX, mouseY);
        player = mc.thePlayer;
        mc.entityRenderer.setupOverlayRendering();
        
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        if (pre(ALL)) return;
        
        //render the view modifiers
        GL11.glEnable(GL11.GL_BLEND);
        if (Minecraft.isFancyGraphicsEnabled()) renderVignette(player.getBrightness(partialTicks), width, height);
        else GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (!mc.thePlayer.isPotionActive(Potion.confusion)) renderPortal(width, height, partialTicks);
        
        renderHelmet(res, partialTicks, hasScreen, mouseX, mouseY);
        renderSleepFade(width, height);
        
        //render all of the registered components
        for (HudComponent hc : HudRegistry.getComponents()) {
        	ElementType type = hc.getType();
        	String profile = hc.getProfile();
        	
        	if (hc.shouldRender(mc, player)) {
	        	if (pre(type)) continue;
	        	profiler.startSection(profile);
	        	hc.render(mc, res, partialTicks, this);
	        	profiler.endStartSection(profile);
	        	post(type);
        	}
        }
        post(ALL);
    }
    
    //COPIED METHODS FOR ERROR RESOLUTION
    //I don't know why this was made private in the first place
    protected void renderHelmet(ScaledResolution res, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
        if (pre(HELMET)) return;
        ItemStack itemstack = player.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() != null) {
            if (itemstack.itemID == Block.pumpkin.blockID) renderPumpkinBlur(res.getScaledWidth(), res.getScaledHeight());
            else itemstack.getItem().renderHelmetOverlay(itemstack, player, res, partialTicks, hasScreen, mouseX, mouseY);
        }
        post(HELMET);
    }

    protected void renderPortal(int width, int height, float partialTicks)  {
        if (pre(PORTAL)) return;
        float f1 = player.prevTimeInPortal + (player.timeInPortal - player.prevTimeInPortal) * partialTicks;
        if (f1 > 0.0F) func_130015_b(f1, width, height);
        post(PORTAL);
    }
    
    //PUBLIC GETTER AND SETTER METHODS (for otherwise inaccessible protected variables)
    public int getRecordPlayingUpFor() {
    	return recordPlayingUpFor;
    }
    
    public boolean getIsRecordPlaying() {
    	return recordIsPlaying;
    }
    
    public String getRecordPlaying() {
    	return recordPlaying;
    }
    
    public Random getRand() {
    	return rand;
    }
    
    public int getRemainingHighlightTicks() {
    	return remainingHighlightTicks;
    }
    
    public ItemStack getHighlightingItemStack() {
    	return highlightingItemStack;
    }
    
    //PUBLIC HELPER METHODS////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** Helper to use the same texture, but draws it vertically. Args: x, y, u, v, width, height */
  	public void drawTexturedVerticalModalRect(int x, int y, int u, int v, int w, int h) {
  		float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)zLevel, (double)((float)(u + h) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)zLevel, (double)((float)(u + h) * f), (double)((float)(v + w) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + w) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
  	}
  	
  	/** Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height */
  	public void drawTexturedModalRect(int x, int y, int u, int v, int w, int h) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + h) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)zLevel, (double)((float)(u + w) * f), (double)((float)(v + h) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)zLevel, (double)((float)(u + w) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
  	}
  	
  	/** Renders items over (in) slots.*/
  	public void renderInventorySlot(int index, int x, int y, float partialTicks) {
		ItemStack itemstack = player.inventory.mainInventory[index];
			
		if (itemstack != null) {
		    float f1 = (float)itemstack.animationsToGo - partialTicks;
		
		    if (f1 > 0.0F) {
		        GL11.glPushMatrix();
		        float f2 = 1.0F + f1 / 5.0F;
		        GL11.glTranslatef((float)(x + 8), (float)(y + 12), 0.0F);
		        GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
		        GL11.glTranslatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
		    }
		
		    itemRenderer.renderItemAndEffectIntoGUI(fontrenderer, mc.func_110434_K(), itemstack, x, y);
		
		    if (f1 > 0.0F) {
		        GL11.glPopMatrix();
		    }
		
		    itemRenderer.renderItemOverlayIntoGUI(fontrenderer, mc.func_110434_K(), itemstack, x, y);
		}
  	}
  	
	/** Used to bind a specific texture (sprite sheet) for use */
	public void bindTexture(ResourceLocation rl) {
		 if (rl != null) mc.func_110434_K().func_110577_a(rl);
	}
    
    //PROTECTED HELPER METHODS///////////////////////////////////////////////////////////////////////////////////////////////////////
	protected boolean pre(ElementType type) {
        return MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(eventParent, type));
    }
   
	protected void post(ElementType type) {
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(eventParent, type));
    }
}