package me.xtimdevx.buzzarduhc.events;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.event.Event;

/**
 * Created by xTimDevx on 25/10/2017.
 */
public abstract class UHCEvent extends Event{

    public GameUtils getGame() {
        return GameUtils.getInstance();
    }
}
