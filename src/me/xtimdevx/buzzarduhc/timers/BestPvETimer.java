package me.xtimdevx.buzzarduhc.timers;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.types.BestPvE;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

/**
 * Created by xTimDevx on 14/10/2017.
 */
public class BestPvETimer {

    public static BukkitRunnable task;
    public static String PREFIX = "§4BestPvE §8» §f";
    public static HashSet<String> list = new HashSet<String>();


    public static void startBestPvE() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (!list.contains(online.getName())) {
                        online.sendMessage(PREFIX + "All players on the §4BestPvE §flist gained §41 §fheart.");
                        continue;
                    }
                    online.sendMessage(PREFIX + "You gained §41 §fheart because of you §4PvE §fskills.");
                    online.setMaxHealth(online.getMaxHealth() + 2);
                    online.setHealth(online.getHealth() + 2);
                }
            }
        };
        task.runTaskTimer(Main.plugin, 12000, 12000);
    }
}
