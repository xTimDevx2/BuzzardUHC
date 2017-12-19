package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 30/08/2017.
 */
public class BaldChicken extends Scenario implements Listener {

    public BaldChicken() {
        super("Baldchicken", "If you kill a skeleton it drops 3 / 5 feathers.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Chicken) {
            for (ItemStack drop : event.getDrops()) {
                if (drop.getType() == Material.FEATHER) {
                    drop.setType(Material.AIR);
                }
            }
            return;
        }

        if (entity instanceof Skeleton) {
            for (ItemStack drop : event.getDrops()) {
                if (drop.getType() == Material.ARROW) {
                    drop.setType(Material.AIR);
                }
            }
        }
    }
}
