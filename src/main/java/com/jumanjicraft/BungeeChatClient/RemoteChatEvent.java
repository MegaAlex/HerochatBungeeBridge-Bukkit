package com.jumanjicraft.BungeeChatClient;

import com.dthielke.herochat.Channel;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoteChatEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Channel channel;
    private final String sender;
    private final String message;

    public RemoteChatEvent(Channel channel, String sender, String message) {
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }


}
