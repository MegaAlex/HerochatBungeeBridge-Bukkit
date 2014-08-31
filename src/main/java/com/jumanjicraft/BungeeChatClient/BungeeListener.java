package com.jumanjicraft.BungeeChatClient;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BungeeListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChannelChat(ChannelChatEvent event) {
        if (event.getResult() == Chatter.Result.ALLOWED) {
            if (!event.getFormat().equalsIgnoreCase(
                    Herochat.getChannelManager().getConversationFormat())) {
                String msg = BungeeChatListener.parseMessage(
                        event.getMessage(), event.getSender(),
                        event.getChannel());
                BungeeChatListener.TransmitChatMessage(
                        event.getSender().getName(),
                        ChatColor.RESET
                                + BungeeChatClient.vaultChat
                                .getPlayerPrefix(event.getSender()
                                        .getPlayer())
                                + event.getSender().getPlayer()
                                .getDisplayName()
                                + BungeeChatClient.vaultChat
                                .getPlayerSuffix(event.getSender()
                                        .getPlayer())
                                + event.getChannel().getColor()
                                + ": "
                                + msg, event.getChannel()
                                .getName(), msg);
            }
        }
    }

}
