package me.xtimdevx.buzzarduhc.timers;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.types.WeakestLink;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/07/2017.
 */
public class WeakestlinkTimer {

    public static void runWeakestTimer(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fNext victim will fall in §310 §fminutes.");
                }
            }
        }, Main.tickspeed * 602L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fNext victim will fall in §310 §fminutes.");
                }
            }
        }, Main.tickspeed * 1202L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fNext victim will fall in §310 §fminutes.");
                }
            }
        }, Main.tickspeed * 1802L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fNext victim will fall in §310 §fminutes.");
                }
            }
        }, Main.tickspeed * 2402L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fLast victim will fall in §310 §fminutes.");
                }
            }
        }, Main.tickspeed * 3002L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player tokill = WeakestLink.getLowestPlayer();
                tokill.damage(0);
                tokill.setHealth(0);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§3Weakestlink §8» §3" + tokill.getName() + " §fwas the lowest at health and died because of it.");
                    online.sendMessage("§3Weakestlink §8» §fThis was the last victim..");
                }
            }
        }, Main.tickspeed * 3602L);
    }
}
