package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.gui.InventoryGUI;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 14/06/2017.
 */
public class InvseeCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("invsee")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(State.isState(State.INGAME)) {
                if (user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                    player.sendMessage(PrefixUtils.NO_HOST_MSG);
                    return true;
                }
            }else {
                if(user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER) {
                    player.sendMessage(PrefixUtils.NO_PERM_MSG);
                    return true;
                }
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Usage: /invsee <player>");
                return true;
            }
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage(PrefixUtils.PREFIX + "This player is not online.");
                    return true;
                }
                InventoryGUI.openInventory(player, target);
                player.sendMessage(PrefixUtils.STAFF + "§fOpening the inventory of §4" + target.getName());
            }
        }
        return true;
    }
}
