package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by xTimDevx on 18/06/2017.
 */
public class NoFall extends Scenario implements Listener{

    public NoFall() {
        super("Nofall", "Fall damage is disabled.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        event.setCancelled(true);
    }
}
