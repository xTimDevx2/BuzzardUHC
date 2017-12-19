package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.State;
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
public class SendCoordsCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can display their location to their.");
            return true;
        }

        if(!State.isState(State.INGAME)) {
            sender.sendMessage("§cError: The game has not started yet.");
            return true;
        }
        Player player = (Player) sender;

        Teams teams = Teams.getInstance();

        Team team = teams.getTeam(player.getName());

        if (team == null) {
            player.sendMessage(PrefixUtils.TEAMS + "You are not on a team, create one using §9/team create§f.");
            return true;
        }

        teams.sendMessage(team, PrefixUtils.TEAMS + "§9" + player.getName() + "'s §floc: §fX:§9" + player.getLocation().getBlockX() + " §fY:§9" + player.getLocation().getBlockY() + " §fZ:§9" + player.getLocation().getBlockZ() + " §8[§9§o" + player.getWorld().getEnvironment().name().replaceAll("_", " ").replaceAll("NORMAL", "Overworld").toLowerCase().replaceAll("normal", "Overworld") + "§8]");
        return true;
    }
}
