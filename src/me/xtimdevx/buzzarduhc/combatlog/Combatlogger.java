package me.xtimdevx.buzzarduhc.combatlog;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xTimDevx on 26/10/2017.
 */
public class Combatlogger implements Listener, CommandExecutor{

    private static Combatlogger instance = new Combatlogger();

    public static Combatlogger getInstance() {
        return instance;
    }

    private Map<String, String> combatlog = new HashMap<String, String>();
    private BukkitRunnable task;

    private String prefix() {
        return "§4Combatlog §8» §f";
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();

        if(combatlog.containsKey(player.getName()) && combatlog.containsKey(damager.getName())) {
            return;
        }

        if(!combatlog.containsKey(player.getName()) && !combatlog.containsKey(damager.getName())) {
            setCombat(player);
            setCombat(damager);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    removeCombat(player);
                    removeCombat(damager);
                }
            }, 1200);
        }
        if(combatlog.containsKey(player.getName()) && !combatlog.containsKey(damager.getName())) {
            setCombat(damager);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    removeCombat(damager);
                }
            }, 1200);
        }
        if(!combatlog.containsKey(player.getName()) && combatlog.containsKey(damager.getName())) {
            setCombat(player);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    removeCombat(player);
                }
            }, 1200);
        }
        return;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(combatlog.containsKey(player.getName())) {
            killCombat(player);
        }
    }

    public void setCombat(Player player) {
        combatlog.put(player.getName(), null);
        player.sendMessage(prefix() + "You are now in §4combat§f, do not log out!");
    }

    public void removeCombat(Player player) {
        combatlog.remove(player.getName());
        player.sendMessage(prefix() + "You are no longer in §4combat§f.");
    }

    public void killCombat(Player player) {
        player.setHealth(0);
        Game.broadcast(prefix() + "§4" + player.getName() + " §flogged out in §4combat§f, he got killed§f.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("combatlog")) {
            Player player = (Player) sender;
            if(combatlog.containsKey(player.getName())) {
                player.sendMessage(prefix() + "§cYou are currently in combat!");
            }else {
                player.sendMessage(prefix() + "You are not in combat");
            }
        }
        return true;
    }
}
