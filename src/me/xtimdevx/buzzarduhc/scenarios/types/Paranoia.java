package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TravelAgent;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import sun.org.mozilla.javascript.internal.ast.Block;

/**
 * Created by xTimDevx on 18/07/2017.
 */
public class Paranoia extends Scenario implements Listener{

    public Paranoia() {
        super("Paranoia", "Every time you complete a certain activity, your name or co-ordinates are broadcast in chat. These activities include: Mining Gold/Diamond, going to the nether, crafting a brewing stand or golden apple, and dying...");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(event.getBlock().getType() == Material.GOLD_ORE) {
            if(ScenarioManager.getInstance().getScenario("Cutclean").isEnabled()) {
                org.bukkit.block.Block b = event.getBlock();
                Location clone = new Location(b.getWorld(),
                        b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(),
                        b.getLocation().getBlockZ() + 0.5D);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getState().update();
                ((ExperienceOrb) event.getBlock().getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                event.getBlock().getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
            }
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage("§6Paranoia §8» §6" + player.getName() + " §fmined §6gold §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
            }
        }
        if(event.getBlock().getType() == Material.DIAMOND_ORE) {
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage("§6Paranoia §8» §6" + player.getName() + " §fmined §6diamond §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNether(PlayerPortalEvent event) {
        TravelAgent agent = event.getPortalTravelAgent();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        if(GameTimer.m > 40 || Settings.getData().get("world.nether").equals(false)) {
            return;
        }
        if(from.getWorld() != Bukkit.getWorld("UHC")) {
            return;
        }
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage("§6Paranoia §8» §6" + player.getName() + " §fentered the §6nether §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getRecipe().getResult();
        if(item.getType() == Material.BREWING_STAND_ITEM) {
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage("§6Paranoia §8» §6" + player.getName() + " §fcrafted a §6brewingstand §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
            }
        }
        if(item.getType() == Material.GOLDEN_APPLE) {
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage("§6Paranoia §8» §6" + player.getName() + " §fcrafted a §6golden apple §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage("§6Paranoia §8» §6" + player.getName() + "§fjust §6died §fat X:§6" + player.getLocation().getBlockX() + " §fY:§6" + player.getLocation().getBlockY() + " §fZ:§6" + player.getLocation().getBlockZ() + "§f.");
        }
    }
}

