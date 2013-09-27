package com.aurilux.hp;

import com.aurilux.hp.handlers.HPKeyHandler;
import com.aurilux.hp.handlers.HPPacketHandler;
import com.aurilux.hp.handlers.HPTickHandler;
import com.aurilux.hp.lib.HP_Ref;
import com.aurilux.hp.proxy.CommonHPProxy;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = HP_Ref.MOD_ID, name = HP_Ref.MOD_NAME, version = HP_Ref.VERSION)
@NetworkMod(channels = {HP_Ref.MOD_ID}, clientSideRequired = true, serverSideRequired = false, packetHandler = HPPacketHandler.class)

public class HudPlus_ModBase {
	
	@Instance(HP_Ref.MOD_ID)
    public static HudPlus_ModBase instance;

    @SidedProxy(clientSide = "com.aurilux.hp.proxy.ClientHPProxy", serverSide = "com.aurilux.hp.proxy.CommonHPProxy")
    public static CommonHPProxy proxy;
	
    //Init blocks and items, logger, config and localizations, and sounds
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	}
	
	//Register handlers, event listeners, tile entities, and recipes
	@EventHandler
	public void init(FMLInitializationEvent e) {
        KeyBindingRegistry.registerKeyBinding(new HPKeyHandler());
        TickRegistry.registerTickHandler(new HPTickHandler(), Side.CLIENT);
        
        proxy.registerComponents();
	}
	
	//Load addon modules
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
}