package com.jumanjicraft.BungeeChatClient;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeChatListener implements PluginMessageListener {

    static BungeeChatClient plugin;

    public BungeeChatListener(BungeeChatClient plugin) {
        BungeeChatListener.plugin = plugin;
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin,
                "BungeeChat", this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin,
                "BungeeChat");
    }

    public static void TransmitChatMessage(String sender, String message,
                                           String chatChannel, String raw) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(chatChannel);
        out.writeUTF(sender);
        out.writeUTF(ChatColor.translateAlternateColorCodes('&', message));
        out.writeUTF(ChatColor.translateAlternateColorCodes('&', raw));

        Bukkit.getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeChat",
                out.toByteArray());
    }

    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equalsIgnoreCase("BungeeChat")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String chatChannel = in.readUTF();
        String sender = in.readUTF();
        String message = in.readUTF();
        String raw = in.readUTF();
        Channel channel = Herochat.getChannelManager().getChannel(chatChannel);
        if (channel == null) {
            return;
        }
        channel.sendRawMessage(ChatColor.RESET + channel.applyFormat(
                channel.getFormatSupplier().getAnnounceFormat(), "").replace(
                "%2$s", message.replaceAll("(?i)&([a-fklmno0-9])", "\247$1")));
        RemoteChatEvent channelEvent = new RemoteChatEvent(channel, sender, raw);
        Bukkit.getPluginManager().callEvent(channelEvent);
    }

    public static boolean validColor(char ch) {
        return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')
                || (ch >= 'k' && ch <= 'o') || ch == 'r';
    }

    public static String parseMessage(String msg, Chatter sender,
                                      Channel channel) {
        String parsed = "";
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '&' && i < msg.length() - 1) {
                if (!validColor(msg.charAt(i + 1)))
                    parsed += msg.charAt(i);
                else if (sender.canColorMessages(channel,
                        ChatColor.getByChar(msg.charAt(i + 1))) == Chatter.Result.ALLOWED) {
                    parsed = parsed + ChatColor.COLOR_CHAR + msg.charAt(i + 1);
                    i++;
                }
            } else {
                parsed += msg.charAt(i);
            }
        }

        return parsed;
    }

}
