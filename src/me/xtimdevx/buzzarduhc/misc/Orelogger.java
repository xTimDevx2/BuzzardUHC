package me.xtimdevx.buzzarduhc.misc;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by xTimDevx on 5/06/2017.
 */
public class Orelogger implements Listener {

    public static HashMap<String, Integer> totalDiamonds = new HashMap<String, Integer>();
    public static HashMap<String, Integer> totalGold = new HashMap<String, Integer>();
    public static HashSet<Location> locs = new HashSet<Location>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.GOLD_ORE) {
            if (locs.contains(block.getLocation())) {
                return;
            }

            Location loc = block.getLocation();
            Player player = event.getPlayer();
            int amount = 0;

            for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++) {
                for (int y = loc.getBlockY() - 2; y <= loc.getBlockY() + 2; y++) {
                    for (int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++) {
                        if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.GOLD_ORE) {
                            locs.add(loc.getWorld().getBlockAt(x, y, z).getLocation());
                            amount++;
                        }
                    }
                }
            }

            if (totalGold.containsKey(player.getName())) {
                totalGold.put(player.getName(), totalGold.get(player.getName()) + amount);
            } else {
                totalGold.put(player.getName(), amount);
            }

            for (Player online : Bukkit.getOnlinePlayers()) {
                User usero = User.get(online);
                if (!ScenarioManager.getInstance().getScenario("diamondless").isEnabled()) {
                    if (usero.getMode() == User.Mode.HOST || usero.getMode() == User.Mode.SPECTATOR) {
                        online.sendMessage(PrefixUtils.STAFF + "§f" + player.getName() + " §8» §6GOLD §8[§c§o" + totalGold.get(player.getName()) + "§8]");
                    }
                }
            }
            return;
        }

        if (block.getType() == Material.DIAMOND_ORE) {
            if (locs.contains(block.getLocation())) {
                return;
            }

            Location loc = block.getLocation();
            Player player = event.getPlayer();
            int amount = 0;

            for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++) {
                for (int y = loc.getBlockY() - 2; y <= loc.getBlockY() + 2; y++) {
                    for (int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++) {
                        if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.DIAMOND_ORE) {
                            locs.add(loc.getWorld().getBlockAt(x, y, z).getLocation());
                            amount++;
                        }
                    }
                }
            }

            if (totalDiamonds.containsKey(player.getName())) {
                totalDiamonds.put(player.getName(), totalDiamonds.get(player.getName()) + amount);
            } else {
                totalDiamonds.put(player.getName(), amount);
            }

            for (Player online : Bukkit.getOnlinePlayers()) {
                User usero = User.get(online);
                if (usero.getMode() == User.Mode.HOST || usero.getMode() == User.Mode.SPECTATOR) {
                    online.sendMessage(PrefixUtils.STAFF + "§f" + player.getName() + " §8» §bDIAMOND §8[§c§o" + totalDiamonds.get(player.getName()) + "§8]");
                }
            }
        }
    }
}
