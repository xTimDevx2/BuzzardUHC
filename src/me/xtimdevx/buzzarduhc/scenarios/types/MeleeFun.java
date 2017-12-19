package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by xTimDevx on 29/07/2017.
 */
public class MeleeFun extends Scenario implements Listener {

    public MeleeFun() {
        super("Meleefun", "There is no hit delay.");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        final Player player = (Player) event.getEntity();
        event.setDamage(event.getDamage() * 0.5);

        Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
            public void run() {
                player.setNoDamageTicks(0);
            }
        }, 1L);
    }
}
