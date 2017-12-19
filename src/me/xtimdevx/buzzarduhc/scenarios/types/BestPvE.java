package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.timers.BestPvETimer;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

/**
 * Created by xTimDevx on 14/10/2017.
 */
public class BestPvE extends Scenario implements Listener, CommandExecutor{

    public BestPvE() {
        super("BestPvE", "Everyone starts on a list called the BestPvE list, if you take damage you are removed from the list. The only way of getting back on the list is getting a kill. All players on the list gets 1 extra heart each 10 minutes.");

        Bukkit.getPluginCommand("pve").setExecutor(this);
        Bukkit.getPluginCommand("pvelist").setExecutor(this);
    }

    @Override
    public void onDisable() {
        BestPvETimer.list.clear();
        BestPvETimer.task.cancel();
        BestPvETimer.task = null;
    }

    @Override
    public void onEnable() {
        BestPvETimer.list.clear();
    }

    public Set<String> getList() {
        return BestPvETimer.list;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }
        final Player player = killer;
        if(BestPvETimer.list.contains(player.getName())) {
            return;
        }
        Bukkit.broadcastMessage(PrefixUtils.PREFIX + "§4" + player.getName() + " §fgot a kill and got added back to the §4BestPvE §flist!");

        new BukkitRunnable() {
            public void run() {
                BestPvETimer.list.add(player.getName());
            }
        }.runTaskLater(Main.plugin, 40);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof  Player)) {
            return;
        }
        if(event.isCancelled()) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (!BestPvETimer.list.contains(player.getName())) {
            return;
        }
        if(GameTimer.m < 5) {
            return;
        }
        Bukkit.broadcastMessage(PrefixUtils.PREFIX + "§4" + player.getName() + " took damage and is no longer on the §4BestPvE §flist.");
        BestPvETimer.list.remove(player.getName());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!isEnabled()) {
            sender.sendMessage(PrefixUtils.SCENARIOS + "§eBestPvE §fis not enabled.");
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("pvelist")) {
            sender.sendMessage(PrefixUtils.PREFIX + "Best PvE List:");
            for(String player : BestPvETimer.list) {
                sender.sendMessage("§4" + player);
            }
        }
        if(cmd.getName().equalsIgnoreCase("pve")) {
            if(!sender.hasPermission("uhc.staff")) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length < 2) {
                sender.sendMessage(PrefixUtils.PREFIX + "Help for BestPvE:");
                sender.sendMessage("§8» §f/pve add <player> §8- §f§oAdd a player to the BestPvE list.");
                sender.sendMessage("§8» §f/pve remove <player> §8- §f§oRemove a player from the BestPvE list.");
                return true;
            }
            String player = args[1];
            if(args[0].equalsIgnoreCase("add")) {
                if(BestPvETimer.list.contains(player)) {
                    sender.sendMessage("§cError: " + player + " is already on the BestPvE list.");
                    return true;
                }
                sender.sendMessage(PrefixUtils.PREFIX + "§4" + player + " §fwas added to the §4BestPvE §flist.");
                BestPvETimer.list.add(player);
                return true;
            }

            if(args[0].equalsIgnoreCase("remove")) {
                if(!BestPvETimer.list.contains(player)) {
                    sender.sendMessage("§cError: " + player + " is not on the BestPvE list.");
                    return true;
                }
                sender.sendMessage(PrefixUtils.PREFIX + "§4" + player + " §fwas removed from the §4BestPvE §flist.");
                BestPvETimer.list.remove(player);
                return true;
            }
            sender.sendMessage(PrefixUtils.PREFIX + "Help for BestPvE:");
            sender.sendMessage("§8» §f/pve add <player> §8- §f§oAdd a player to the BestPvE list.");
            sender.sendMessage("§8» §f/pve remove <player> §8- §f§oRemove a player from the BestPvE list.");
        }
        return true;
    }
}
