package com.jumanjicraft.BungeeChatClient;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;

public class BungeeListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChannelChat(ChannelChatEvent event) {
		if (event.getResult() == Chatter.Result.ALLOWED) {
			if (!event.getFormat().equalsIgnoreCase(
					Herochat.getChannelManager().getConversationFormat())) {
				BungeeChatListener.TransmitChatMessage(
						ChatColor.RESET
								+ event.getSender().getPlayer()
										.getDisplayName()
								+ event.getChannel().getColor() + ": "
								+ event.getMessage(), event.getChannel()
								.getName());
			}
		}
	}

}