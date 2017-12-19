package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 21/10/2017.
 */
public class BloodBooks extends Scenario implements Listener{

    public BloodBooks() {
        super("BloodBooks", "You can't gather sugercane and leather, if you kill a player they drop 1 book.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
            event.setCancelled(true);
        }
        if(event.getBlock().getType() == Material.BOOKSHELF) {
            event.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if(entity.getType() == EntityType.COW) {
            for (ItemStack drops : event.getDrops()) {
                if (drops.getType() == Material.LEATHER) {
                    drops.setType(Material.AIR);
                }
            }
        }
        if(entity.getType() == EntityType.RABBIT) {
            for (ItemStack drops : event.getDrops()) {
                if (drops.getType() == Material.RABBIT_HIDE) {
                    drops.setType(Material.AIR);
                }
            }
        }
        if(entity.getType() == EntityType.HORSE) {
            for (ItemStack drops : event.getDrops()) {
                if (drops.getType() == Material.LEATHER) {
                    drops.setType(Material.AIR);
                }
            }
        }
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        Entity entity = event.getEntity();

        event.getDrops().add(new ItemStack(Material.BOOK));
    }
}
