package me.xtimdevx.buzzarduhc.events;

import org.bukkit.event.HandlerList;

/**
 * Created by xTimDevx on 25/10/2017.
 */
public class GameStartEvent extends UHCEvent{

    public GameStartEvent() {}

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHadlerList() {
        return handlers;
    }
}
