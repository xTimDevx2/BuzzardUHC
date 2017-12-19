package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.nio.cs.US_ASCII;

import javax.swing.table.AbstractTableModel;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class HealCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("heal")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.TRIAL) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(State.isState(State.INGAME) && user.getMode() != User.Mode.HOST) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "You §bhealed §fyourself!");
                player.setHealth(player.getMaxHealth());
                player.setSaturation(20);
                player.setFoodLevel(20);
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("all")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.setHealth(20);
                        online.setFoodLevel(20);
                        online.setSaturation(20);
                        online.sendMessage(PrefixUtils.PREFIX + "§bEveryone §fhas been §bhealed§f.");
                    }
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("§cThis player is not online.");
                    return true;
                }
                target.setHealth(20);
                target.setFoodLevel(20);
                target.setSaturation(20);
                target.sendMessage(PrefixUtils.PREFIX + "You have been §bhealed§f.");
                for(Player online : Bukkit.getOnlinePlayers()) {
                    User userO = User.get(online);
                    if(userO.getMode() == User.Mode.HOST || userO.getMode() == User.Mode.SPECTATOR) {
                        online.sendMessage(PrefixUtils.STAFF + "§4" + player.getName() + " §fhealed §4" + target.getName() + "§f!");
                    }
                }
            }
        }
        return true;
    }
}
