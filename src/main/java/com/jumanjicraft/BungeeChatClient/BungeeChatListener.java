package com.jumanjicraft.BungeeChatClient;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeChatListener implements PluginMessageListener {

	static BungeeChatClient plugin;

	public BungeeChatListener(BungeeChatClient plugin) {
		BungeeChatListener.plugin = plugin;
		Bukkit.getMessenger().registerIncomingPluginChannel(plugin,
				"BungeeChat", this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(plugin,
				"BungeeChat");
	}

	public static void TransmitChatMessage(String message, String chatChannel) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(chatChannel);
		out.writeUTF(ChatColor.translateAlternateColorCodes('&', message));
		
		Bukkit.getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeChat",
				out.toByteArray());
	}

	public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
		if (!s.equalsIgnoreCase("BungeeChat")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
		String chatchannel = in.readUTF();
		String message = in.readUTF();
		Channel channel = Herochat.getChannelManager().getChannel(chatchannel);
		if (channel == null) {
			Bukkit.getLogger()
					.warning(
							"Channel "
									+ chatchannel
									+ " doesn't exist, but a message was receieved on it. Your Herochat configs aren't probably the same on each server.");
			return;
		}
		StringBuilder msg = new StringBuilder(channel.applyFormat(
				channel.getFormatSupplier().getAnnounceFormat(), "").replace(
				"%2$s", message.replaceAll("(?i)&([a-fklmno0-9])", "\247$1")));
		channel.sendRawMessage(ChatColor.RESET + msg.toString());
	}

	public static String parseMessage(String msg, Chatter sender, Channel channel) {
		String parsed = "";
		for (int i = 0; i < msg.length() - 1; i++) {
			if (msg.charAt(i) == '&') {
				if (sender.canColorMessages(channel, ChatColor.getByChar(msg.charAt(i+1))) == Chatter.Result.ALLOWED) {
					parsed = parsed + ChatColor.COLOR_CHAR + msg.charAt(i);
				}
				i++;
			} else {
				parsed += msg.charAt(i);
			}
		}
		
		return parsed;
	}

}