package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by xTimDevx on 6/10/2017.
 */
public class RtCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("uhc.command.rt")) {
            sender.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /rt <size> [playersnotplaying...]");
            return true;
        }

        int size = 1;

        try {
            size = Integer.parseInt(args[0]);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + args[0] + " is not a vaild teamsize.");
            return true;
        }

        ArrayList<Player> list = new ArrayList<Player>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getScoreboard().getEntryTeam(online.getName()) == null) {
                list.add(online);
            }
        }

        Collections.shuffle(list);

        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                Player target = Bukkit.getServer().getPlayer(args[i]);

                if (target != null) {
                    if (list.contains(target)) {
                        list.remove(target);
                    }
                }
            }
        }

        Teams teams = Teams.getInstance();
        Team team = teams.findAvailableTeam();

        if (team == null) {
            sender.sendMessage(ChatColor.RED + "No more available teams.");
            return true;
        }

        try {
            for (int i = 0; i < size; i++) {
                if (list.size() < i) {
                    sender.sendMessage(ChatColor.RED + "Could not add a player to team " + team.getName() + ".");
                    continue;
                }

                Player p = list.get(i);
                team.addEntry(p.getName());
                p.sendMessage(PrefixUtils.TEAMS + "You were added to team " + team.getName());
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Not enough players for this team.");
        }

        if (team.getSize() > 0) {
            sender.sendMessage(PrefixUtils.TEAMS + "Created a team of §9" + size + " §fusing team §9" + team.getName() + "§f.");

            teams.sendMessage(team, PrefixUtils.TEAMS + "You were added to §9" + team.getName() + "§7.");
            teams.sendMessage(team, PrefixUtils.TEAMS + "Your teammates:");

            for (String entry : team.getEntries()) {
                teams.sendMessage(team, PrefixUtils.TEAMS + "§9" + entry);
            }
        }
        return true;
    }

}
