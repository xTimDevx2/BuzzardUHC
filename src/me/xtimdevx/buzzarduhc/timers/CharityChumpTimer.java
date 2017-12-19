package me.xtimdevx.buzzarduhc.timers;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.types.CharityChump;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 17/09/2017.
 */
public class CharityChumpTimer {

    public static void runCharityChumpTimer(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fNext charity event in §d10 §fminutes.");
                }
            }
        }, Main.tickspeed * 602L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fNext charity event in §d10 §fminutes.");
                }
            }
        }, Main.tickspeed * 1202L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fNext charity event in §d10 §fminutes.");
                }
            }
        }, Main.tickspeed * 1802L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fNext charity event in §d10 §fminutes.");
                }
            }
        }, Main.tickspeed * 2402L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fNext charity event in §d10 §fminutes.");
                }
            }
        }, Main.tickspeed * 3002L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                Player toheal = CharityChump.getLowestPlayer();
                toheal.setHealth(20);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("§dCharityChump §8» §d" + toheal.getName() + " §fwas the lowest at health and got healed because of it.");
                    online.sendMessage("§dCharityChump §8» §fThis was the last charity.");
                }
            }
        }, Main.tickspeed * 3602L);
    }
}
