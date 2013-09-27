package com.aurilux.hp.hud.components;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.handlers.HPKeyHandler;
import com.aurilux.hp.hud.HPHud;
import com.aurilux.hp.lib.HP_Ref;

import cpw.mods.fml.common.network.PacketDispatcher;

public class HPHotbar extends HudComponent {
	private final int slotWidth = 20;
	private final int hotbarHeight = 22;
	
	private InventoryPlayer inv;
	private float lastTick = 0;

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
		hud.bindTexture(WIDGETS);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        inv = hud.player.inventory;
        int left = halfWidth - hudHalfWidth;
        
        boolean toggle = HPKeyHandler.toggleKeyPressed;
        
        //draws the default (vanilla) hotbar
        hud.drawTexturedModalRect(left, height - hotbarHeight, 0, 0, 182, hotbarHeight);
        
        //There is no need to draw the vanilla hotbar item selection highlight if we are drawing the extended hotbar
        if (toggle) drawExtendedHotbar(mc, partialTicks, hud);
        else hud.drawTexturedModalRect(left - 1 + inv.currentItem * slotWidth, height - hotbarHeight - 1, 0, hotbarHeight, 24, hotbarHeight);
        
        //draws the items on the default hotbar
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 0; i < 9; ++i) {
            int x = halfWidth - 90 + i * slotWidth + 2;
            int y = height - 16 - 3;
            hud.renderInventorySlot(i, x, y, partialTicks);
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        //GL11.glEnable(GL11.GL_BLEND);
	}

	@Override
	public String getProfile() {
		return "hotbar";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.HOTBAR;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		// Regardless of the gamemode, the hotbar should always be rendered
		return true;
	}
	
	//Draws the extended hotbar
	protected void drawExtendedHotbar(Minecraft mc, float partialTicks, HPHud hud) {
		int selection1 = 0;
		int selection2 = 0;
    	//draws the vertical, extended hotbar
    	hud.drawTexturedVerticalModalRect(width / 2 - 91 + inv.currentItem * slotWidth, height - 62, 0, 0, hotbarHeight, 62);
        
        //draws the items for the extended hotbar
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 0; i < 2; ++i) {
            int x = halfWidth - 90 + 2 + inv.currentItem * slotWidth;
            int y = height - 16 - 23 - (i * slotWidth);
            int itemIndex = inv.currentItem + (27 - (i * 9));
            hud.renderInventorySlot(itemIndex, x, y, partialTicks);
            
            if    (i == 0)  selection1 = itemIndex;
            else /*i == 1*/ selection2 = itemIndex;
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        //GL11.glEnable(GL11.GL_BLEND);
        
        //This if statement ensures 'lastTick' doesn't get to obscenely high numbers if the player has the extended
        //hotbar open for long periods
        if (lastTick < 5) {
        	lastTick += partialTicks;
        }
        else if (lastTick >= 5) {
            if      (Keyboard.isKeyDown(Keyboard.KEY_V)) sendPacket(selection1);
            else if (Keyboard.isKeyDown(Keyboard.KEY_B)) sendPacket(selection2);
        }
	}
	
	protected void sendPacket(int selection) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
                outputStream.writeInt(selection);
                outputStream.writeInt(inv.currentItem);
        }
        catch (Exception ex) {
                ex.printStackTrace();
        }
       
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = HP_Ref.MOD_ID;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        PacketDispatcher.sendPacketToServer(packet);
        lastTick = 0;
    }
}