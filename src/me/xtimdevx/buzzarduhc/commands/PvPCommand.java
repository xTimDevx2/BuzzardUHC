package me.xtimdevx.buzzarduhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.BlockUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

/**
 * Created by xTimDevx on 31/05/2017.
 */
public class PvPCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("pvp")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getMode() != User.Mode.SPECTATOR && user.getMode() != User.Mode.HOST) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /pvp enable");
                player.sendMessage("§cUsage /pvp disable");
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("enable")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bPvP §fhas been §aenabled§f, be careful!");
                    }
                    Bukkit.getWorld("UHC").setPVP(true);
                }
                if(args[0].equalsIgnoreCase("disable")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bPvP §fhas been §cdisabled§f.");
                    }
                    Bukkit.getWorld("UHC").setPVP(false);
                }
            }
        }
        return true;
    }
}
