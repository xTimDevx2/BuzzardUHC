package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import comp.xtimdevx.buzzardhavex.Utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/06/2017.
 */
public class StaffchatCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staffchat")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.OWNER) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length < 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Usage: /staffchat <message>");
                return true;
            }
            if(args.length > 0) {
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                String msg = message.toString().trim();

                    for(Player online : Bukkit.getOnlinePlayers()) {
                        User ouser = User.get(online);
                        if(ouser.getRank() == User.Rank.ADMIN || ouser.getRank() == User.Rank.OWNER || ouser.getRank() == User.Rank.MANAGER || ouser.getRank() == User.Rank.COACH || ouser.getRank() == User.Rank.TRIAL || ouser.getRank() == User.Rank.HOST || ouser.getRank() == User.Rank.BUILDER) {
                            online.sendMessage("§8[§cSTAFF§8-§cCHAT§8]: §7" + player.getName() + ": §f" + msg);
                            online.playSound(online.getLocation(), Sound.ARROW_HIT, 10, 10);
                        }
                    }
                return true;
            }
        }
        return true;
    }
}
