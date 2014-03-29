package com.jumanjicraft.BungeeChatClient;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatClient extends JavaPlugin {

	public void onEnable() {
		new BungeeChatListener(this);
		Bukkit.getPluginManager()
				.registerEvents(new BungeeListener(), this);
	}

}
