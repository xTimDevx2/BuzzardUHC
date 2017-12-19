package me.xtimdevx.buzzarduhc.timers;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class PreTimer {

    public static void runTimer(final Player sender) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                GameTimer.startings--;
            }
        },0, Main.tickspeed * 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b30 §fseconds.");
                }
            }
        }, Main.tickspeed * 0L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b20 §fseconds.");
                }
            }
        }, Main.tickspeed * 10L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "Releasing everyone in §b3§f.");
                    online.playSound(online.getLocation(), Sound.ITEM_PICKUP, 20, 20);
                }
            }
        }, Main.tickspeed * 17L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "Releasing everyone in §b2§f.");
                    online.playSound(online.getLocation(), Sound.ITEM_PICKUP, 20, 20);
                }
            }
        }, Main.tickspeed * 18L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "Releasing everyone in §b1§f.");
                    online.playSound(online.getLocation(), Sound.ITEM_PICKUP, 20, 20);
                }
            }
        }, Main.tickspeed * 19L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b10 §fseconds.");
                    online.sendMessage(PrefixUtils.PREFIX + "Everyone has been released.");

                    Location playerPos = online.getLocation();
                    int r = 10;
                    for(int x = (r * -1); x <= r; x++) {
                        for(int y = (r * -1); y <= r; y++) {
                            for(int z = (r * -1); z <= r; z++) {
                                Block b = playerPos.getWorld().getBlockAt(playerPos.getBlockX() + x, playerPos.getBlockY() + y, playerPos.getBlockZ() + z);
                                if(b.getType() == Material.GLASS)
                                    b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }, Main.tickspeed * 20L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b5 §fseconds.");
                }
            }
        }, Main.tickspeed * 25L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b4 §fseconds.");
                }
            }
        }, Main.tickspeed * 26L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b3 §fseconds.");
                    online.sendTitle("§b3", "");
                }
            }
        }, Main.tickspeed * 27L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b2 §fseconds.");
                    online.sendTitle("§e2", "");
                }
            }
        }, Main.tickspeed * 28L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is starting in §b1 §fseconds.");
                    online.sendTitle("§a1", "");
                }
            }
        }, Main.tickspeed * 29L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                GameTimer.runTimer(sender);}
        }, Main.tickspeed * 30L);
    }
}
