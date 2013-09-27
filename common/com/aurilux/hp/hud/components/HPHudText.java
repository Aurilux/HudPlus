package com.aurilux.hp.hud.components;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

import cpw.mods.fml.common.FMLCommonHandler;

public class HPHudText extends HudComponent {
	private static final String MC_VERSION = (new CallableMinecraftVersion(null)).minecraftVersion();

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
        ArrayList<String> left = new ArrayList<String>();
        ArrayList<String> right = new ArrayList<String>();
        GL11.glPushMatrix();
        left.add("Minecraft " + MC_VERSION + " (" + mc.debug + ")");
        left.add(mc.debugInfoRenders());
        left.add(mc.getEntityDebug());
        left.add(mc.debugInfoEntities());
        left.add(mc.getWorldProviderName());
        left.add(null); //Spacer

        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        long used = total - free;

        right.add("Used memory: " + used * 100L / max + "% (" + used / 1024L / 1024L + "MB) of " + max / 1024L / 1024L + "MB");
        right.add("Allocated memory: " + total * 100L / max + "% (" + total / 1024L / 1024L + "MB)");

        int x = MathHelper.floor_double(mc.thePlayer.posX);
        int y = MathHelper.floor_double(mc.thePlayer.posY);
        int z = MathHelper.floor_double(mc.thePlayer.posZ);
        float yaw = mc.thePlayer.rotationYaw;
        int heading = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        left.add(String.format("x: %.5f (%d) // c: %d (%d)", mc.thePlayer.posX, x, x >> 4, x & 15));
        left.add(String.format("y: %.3f (feet pos, %.3f eyes pos)", mc.thePlayer.boundingBox.minY, mc.thePlayer.posY));
        left.add(String.format("z: %.5f (%d) // c: %d (%d)", mc.thePlayer.posZ, z, z >> 4, z & 15));
        left.add(String.format("f: %d (%s) / %f", heading, Direction.directions[heading], MathHelper.wrapAngleTo180_float(yaw)));

        if (mc.theWorld != null && mc.theWorld.blockExists(x, y, z)) {
            Chunk chunk = mc.theWorld.getChunkFromBlockCoords(x, z);
            left.add(String.format("lc: %d b: %s bl: %d sl: %d rl: %d",
              chunk.getTopFilledSegment() + 15,
              chunk.getBiomeGenForWorldCoords(x & 15, z & 15, mc.theWorld.getWorldChunkManager()).biomeName,
              chunk.getSavedLightValue(EnumSkyBlock.Block, x & 15, y, z & 15),
              chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15),
              chunk.getBlockLightValue(x & 15, y, z & 15, 0)));
        }
        else {
            left.add(null);
        }

        left.add(String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), mc.thePlayer.onGround, mc.theWorld.getHeightValue(x, z)));
        right.add(null);
        for (String s : FMLCommonHandler.instance().getBrandings().subList(1, FMLCommonHandler.instance().getBrandings().size())) {
            right.add(s);
        }
        GL11.glPopMatrix();
	}

	@Override
	public String getProfile() {
		return "debug";
	}

	@Override
	public ElementType getType() {
		return RenderGameOverlayEvent.ElementType.TEXT;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean showDebugInfo = mc.gameSettings.showDebugInfo;
		return showDebugInfo;
	}
}