package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by xTimDevx on 21/10/2017.
 */
public class Birds extends Scenario implements Listener{

    public Birds() {
        super("Birds", "All players can fly!");
    }

    @Override
    public void onDisable() {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.setAllowFlight(false);
            online.setFlying(false);
        }
    }

    @Override
    public void onEnable() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();
        player.setAllowFlight(true);
    }
}
