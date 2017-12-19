package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class TripleOres extends Scenario implements Listener{

    public TripleOres() {
        super("Tripleores", "All ores are tripled.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.getBlock().getWorld().equals(Bukkit.getWorld("UHC"))) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        Block b = e.getBlock();
        Location clone = new Location(b.getWorld(),
                b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(),
                b.getLocation().getBlockZ() + 0.5D);
        if (b.getType() == Material.IRON_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.IRON_INGOT, 3));
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
        } else if (b.getType() == Material.GOLD_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT, 3));
        }else if (b.getType() == Material.COAL_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.COAL, 3));
        }else if (b.getType() == Material.DIAMOND_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.DIAMOND, 3));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        Entity en = e.getEntity();
        if (en.getType() == EntityType.CHICKEN) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_CHICKEN) {
                    drops.setType(Material.COOKED_CHICKEN);
                    drops.setAmount(3);
                }
            }
        }
        if (en.getType() == EntityType.COW) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_BEEF) {
                    drops.setType(Material.COOKED_BEEF);
                    drops.setAmount(3);
                }
            }
        }
        if (en.getType() == EntityType.SHEEP) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.MUTTON) {
                    drops.setType(Material.COOKED_MUTTON);
                    drops.setAmount(3);
                }
                if (drops.getType() == Material.WOOL) {
                    drops.setType(Material.AIR);
                }
            }
        }
        if (en.getType() == EntityType.PIG) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.PORK) {
                    drops.setType(Material.GRILLED_PORK);
                    drops.setAmount(3);
                }
            }
        }
        if (en.getType() == EntityType.CHICKEN) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_CHICKEN) {
                    drops.setType(Material.COOKED_CHICKEN);
                    drops.setAmount(3);
                }
            }
        }
        if (en.getType() == EntityType.RABBIT) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RABBIT) {
                    drops.setType(Material.COOKED_RABBIT);
                    drops.setAmount(3);
                }
            }
        }
    }

}
