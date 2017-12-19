package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.misc.Orelogger;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

/**
 * Created by xTimDevx on 13/10/2017.
 */
public class PlayerinfoCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("playerinfo")) {
            if(!player.hasPermission("uhc.staff")) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /playerinfo <player>");
                return true;
            }
            if(args.length == 1) {
                OfflinePlayer target = (Player) Bukkit.getOfflinePlayer(args[0]);
                if(!UHCCommand.ingame.containsKey(target.getName())) {
                    player.sendMessage("§c" + args[0] + " is not playing this game.");
                    return true;
                }
                User user = User.get(target);
                player.sendMessage(PrefixUtils.STAFF + "User info: §c" + target.getName());
                player.sendMessage("§8» §fDiamonds mined: §c" + Orelogger.totalDiamonds.get(target.getName()));
                player.sendMessage("§8» §fGold mined: §c" + Orelogger.totalGold.get(target.getName()));
                player.sendMessage("§8» §fKills: §c" + user.getStat(User.Stat.GAMEKILLS));
                if(!GameUtils.getTeamMode().equalsIgnoreCase("ffa")) {
                    player.sendMessage("§8» §fTeam: §c" + (ScoreBoard.getInstance().board.getEntryTeam(target.getName()) == null ? "Solo" : ScoreBoard.getInstance().board.getEntryTeam(target.getName()).getName()));
                }
                player.sendMessage("§8» §fOnline: §a" + (target.isOnline() ? "True" : "§cFalse"));
            }
        }
        return true;
    }
}
