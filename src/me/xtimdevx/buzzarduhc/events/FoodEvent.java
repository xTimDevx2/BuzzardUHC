package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.State;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by xTimDevx on 11/10/2017.
 */
public class FoodEvent implements Listener{

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if(!State.isState(State.INGAME)) {
            event.setCancelled(true);
        }
    }
}
