package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * Created by xTimDevx on 25/07/2017.
 */
public class KickSoloCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("kicksolo")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(GameUtils.getTeamMode().equalsIgnoreCase("team")) {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    Team team = online.getScoreboard().getEntryTeam(online.getName());
                    if (team == null) {
                        online.kickPlayer(PrefixUtils.TEAMS + "You are not allowed to be solo this game.");
                        online.setWhitelisted(false);
                        continue;
                    }
                    if (team.getSize() == 1) {
                        online.kickPlayer(PrefixUtils.TEAMS + "You are not allowed to be solo this game.");
                        online.setWhitelisted(false);
                        team.removeEntry(online.getName());
                    }
                }
            }
        }
        return true;
    }
}
