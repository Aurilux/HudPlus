package com.aurilux.hp.handlers;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.aurilux.hp.lib.HP_Ref;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

public class HPUpdateHandler implements IConnectionHandler {
    private final String VERSION_XML_FILE = "https://raw.github.com/Aurilux/HudPlus/master/version.xml";
    private final String MESSAGE_PREFACE = "[§6HudPlus] A new version of HudPlus is available.";
    
    private String updateThread = null;

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		if (isUpdateAvailable()) {
			netHandler.getPlayer().sendChatToPlayer(ChatMessageComponent.func_111066_d(MESSAGE_PREFACE + " Please go to " + updateThread + " to download the update."));
		}
	}
	
	private boolean isUpdateAvailable() {
		try {
			InputStream versionFile = new URL(VERSION_XML_FILE).openStream();
			Properties versionProperties = new Properties();
			versionProperties.loadFromXML(versionFile);
			String currentVersion = versionProperties.getProperty(Loader.instance().getMCVersionString());
			updateThread = versionProperties.getProperty("thread");
			return (currentVersion != null && updateThread != null && !currentVersion.equals(HP_Ref.MOD_VERSION));
		}
		catch (Exception e) {
			return false;
		}
	}
	
	//UNUSED INHERITED METHODS//////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
	}
}