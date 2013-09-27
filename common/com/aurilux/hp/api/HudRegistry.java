package com.aurilux.hp.api;

import java.util.ArrayList;

public class HudRegistry {
	private static ArrayList<HudComponent> components = new ArrayList<HudComponent>();
	
	public static void registerComponent(HudComponent hc) {
		if (!components.contains(hc)) {
			components.add(hc);
		}
	}
	
	public static ArrayList<HudComponent> getComponents() {
		return components;
	}
}