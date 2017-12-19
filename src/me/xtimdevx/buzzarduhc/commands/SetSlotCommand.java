package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by xTimDevx on 24/07/2017.
 */
public class SetSlotCommand implements CommandExecutor{

    public static  int slots;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        User user = User.get(player);
        if(cmd.getName().equalsIgnoreCase("setslot")) {
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.TRIAL) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /setslot <slots>");
                return true;
            }
            if(args.length == 1) {
                try{
                    slots = Integer.parseInt(args[0]);
                }catch (Exception e) {
                    player.sendMessage("§cError: This is not a valid slot number, Please try again.");
                    return true;
                }
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.CONFIG + "Set the rate §cSLOTS §fto §c" + slots + "§f.");
                }
                Settings.getData().set("game.slots", slots);
                Settings.getInstance().saveData();
            }
        }
        return true;
    }
}
