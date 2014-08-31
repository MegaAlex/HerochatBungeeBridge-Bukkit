package com.jumanjicraft.BungeeChatClient;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatClient extends JavaPlugin {

    public static Chat vaultChat = null;

    public void onEnable() {
        new BungeeChatListener(this);
        Bukkit.getPluginManager().registerEvents(new BungeeListener(), this);
        if (!setupChat()) {
            Bukkit.getLogger()
                    .severe(String
                            .format("[%s] - Disabled due to no Vault dependency found!",
                                    getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer()
                .getServicesManager().getRegistration(
                        net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            vaultChat = chatProvider.getProvider();
        }

        return (vaultChat != null);
    }

}
