package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.NumberUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class DamageEvents implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getWorld().getName().equals("HUB")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Arrow damager = (Arrow) event.getDamager();

        if (!(damager.getShooter() instanceof Player)) {
            return;
        }

        Player killer = (Player) damager.getShooter();
        double distance = killer.getLocation().distance(player.getLocation());

        double health = Math.round(player.getHealth());
        String hp = "" + health;

        killer.sendMessage(PrefixUtils.PREFIX + "§b" + player.getName() + " §fis at §b" + hp.replace(".0", "") + " HP");

        if (distance < 30) {
            return;
        }

        GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + killer.getName() + " §fgot a longshot of §b" + NumberUtils.convertDouble(distance) + " §fblocks!");
    }
}
