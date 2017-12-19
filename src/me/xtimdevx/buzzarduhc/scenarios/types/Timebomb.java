package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by xTimDevx on 21/05/2017.
 */
public class Timebomb extends Scenario implements Listener{

    public Timebomb() {
        super("Timebomb", "If someone dies his loot goes in a chest, you have 30 seconds before it explodes.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity().getPlayer();
        if(!player.getWorld().getName().equals("UHC")) {
            return;
        }
        if(!State.isState(State.INGAME)) {
            return;
        }
        event.setKeepInventory(true);
        final Location loc = player.getLocation().add(0, -1, 0);

        loc.getBlock().setType(Material.CHEST);
        loc.getBlock().getState().update(true);
        Chest chest = (Chest) loc.getBlock().getState();

        Location lo = loc.clone().add(0, 0, -1);
        lo.getBlock().setType(Material.CHEST);
        lo.getBlock().getState().update(true);

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                continue;
            }
            chest.getInventory().addItem(item);
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null) {
                continue;
            }
            chest.getInventory().addItem(item);
        }
        if(ScenarioManager.getInstance().getScenario("bloodbooks").isEnabled()) {
            chest.getInventory().addItem(new ItemStack(Material.BOOK));
        }
        ItemStack head = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§8» §b§LGolden Head §8«");
        meta.setLore(Arrays.asList("§8» §fEating this head will heal §b4 §fhearts."));
        head.setItemMeta(meta);
        chest.getInventory().addItem(head);

        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
            public void run() {
                Bukkit.broadcastMessage("§cTimebomb §8» §c"+ player.getName() + "§f's corpse has exploded!");
                loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 10, false, true);
                loc.getWorld().strikeLightning(loc);
            }
        }, 600);

        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
            public void run() {
                loc.getWorld().strikeLightning(loc);
            }
        }, 620);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }
}
