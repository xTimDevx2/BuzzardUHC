package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 26/05/2017.
 */
public class UHCDebugCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("uhcdebug")) {
            Player player = (Player) sender;
            User user = User.get(player);
            User.Rank rank = user.getRank();
            if(rank != User.Rank.OWNER) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "UHCDebug commands:");
                player.sendMessage("§8» §f/uhcdebug addall - §oAdd everyone to the arraylist.");
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("addall")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        UHCCommand.ingame.put(online.getName(),  null);
                        online.sendMessage(PrefixUtils.STAFF + "Everyone was added to the arraylist §bingame§f.");
                    }
                }
                if(args[0].equalsIgnoreCase("enableboard")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        ScoreBoard board = ScoreBoard.getInstance();
                        board.setScore("§8» §cPlayers", UHCCommand.ingame.size());
                    }
                }
            }
        }
        return true;
    }
}
