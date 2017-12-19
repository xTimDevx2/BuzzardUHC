package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 27/05/2017.
 */
public class Diamondless extends Scenario implements Listener{

    public Diamondless() {
        super("Diamondless", "Diamonds are disabled.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (!event.getBlock().getWorld().equals(Bukkit.getWorld("UHC"))) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        Block b = event.getBlock();
        Location clone = new Location(b.getWorld(),
                b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(),
                b.getLocation().getBlockZ() + 0.5D);
        if (b.getType() == Material.DIAMOND_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(1);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().add(new ItemStack(Material.DIAMOND));
    }
}
