package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * Created by xTimDevx on 25/05/2017.
 */
public class PMCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use teamchat.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cUsage: /pm <message>");
            return true;
        }
        Teams teams = Teams.getInstance();

        Team team = teams.getTeam(player);

        if (team == null) {
            player.sendMessage(ChatColor.RED + "You are not on a team.");
            return true;
        }

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        String msg = message.toString().trim();

        teams.sendMessage(team, PrefixUtils.TEAMS + "§7" + player.getName() + ":  §f" + msg);
        return true;
    }
}
