package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class GlobalmuteCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("globalmute")) {
            Player p = (Player) sender;
            User user = User.get(p);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.TRIAL) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }

            if (GameUtils.isMuted()) {
                GameUtils.setMuted(false);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The §bchat §fhas been §bunmuted§f.");
                }
                return true;
            }

            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(PrefixUtils.PREFIX + "The §bchat §fhas been §bmuted§f.");
            }
            GameUtils.setMuted(true);
        }
        return true;
    }
}
