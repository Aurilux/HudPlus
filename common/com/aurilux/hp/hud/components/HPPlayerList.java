package com.aurilux.hp.hud.components;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.aurilux.hp.api.HudComponent;
import com.aurilux.hp.hud.HPHud;

public class HPPlayerList extends HudComponent {

	@Override
	public void render(Minecraft mc, ScaledResolution res, float partialTicks, HPHud hud) {
		super.render(mc, res, partialTicks, hud);
        ScoreObjective scoreobjective = mc.theWorld.getScoreboard().func_96539_a(0);
        NetClientHandler handler = mc.thePlayer.sendQueue;

        if (handler.playerInfoList.size() > 1 || scoreobjective != null) {
            @SuppressWarnings("rawtypes")
			List players = handler.playerInfoList;
            int maxPlayers = handler.currentServerMaxPlayers;
            int rows = maxPlayers;
            int columns = 1;

            for (columns = 1; rows > 20; rows = (maxPlayers + columns - 1) / columns) {
                columns++;
            }

            int columnWidth = 300 / columns;

            if (columnWidth > 150) {
                columnWidth = 150;
            }

            int left = (width - columns * columnWidth) / 2;
            byte border = 10;
            Gui.drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);

            for (int i = 0; i < maxPlayers; i++) {
                int xPos = left + i % columns * columnWidth;
                int yPos = border + i / columns * 9;
                Gui.drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i < players.size()) {
                	FontRenderer fr = hud.fontrenderer;
                    GuiPlayerInfo player = (GuiPlayerInfo)players.get(i);
                    ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(player.name);
                    String displayName = ScorePlayerTeam.formatPlayerName(team, player.name);
                    fr.drawStringWithShadow(displayName, xPos, yPos, 16777215);

                    if (scoreobjective != null) {
                        int endX = xPos + fr.getStringWidth(displayName) + 5;
                        int maxX = xPos + columnWidth - 12 - 5;

                        if (maxX - endX > 5) {
                            Score score = scoreobjective.getScoreboard().func_96529_a(player.name, scoreobjective);
                            String scoreDisplay = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                            fr.drawStringWithShadow(scoreDisplay, maxX - fr.getStringWidth(scoreDisplay), yPos, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    mc.func_110434_K().func_110577_a(Gui.field_110324_m);
                    int pingIndex = 4;
                    int ping = player.responseTime;
                    if (ping < 0) pingIndex = 5;
                    else if (ping < 150) pingIndex = 0;
                    else if (ping < 300) pingIndex = 1;
                    else if (ping < 600) pingIndex = 2;
                    else if (ping < 1000) pingIndex = 3;

                    //hud.zLevel += 100.0F;
                    hud.drawTexturedModalRect(xPos + columnWidth - 12, yPos, 0, 176 + pingIndex * 8, 10, 8);
                    //hud.zLevel -= 100.0F;
                }
            }
        }
	}

	@Override
	public String getProfile() {
		return "playerlist";
	}

	@Override
	public ElementType getType() {
		return null;
	}

	@Override
	public boolean shouldRender(Minecraft mc, EntityClientPlayerMP player) {
		boolean showPlayerList = mc.gameSettings.keyBindPlayerList.pressed && !mc.isIntegratedServerRunning();
		return showPlayerList;
	}
}