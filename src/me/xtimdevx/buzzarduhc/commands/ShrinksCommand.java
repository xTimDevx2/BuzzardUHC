package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.border.CreateBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/07/2017.
 */
public class ShrinksCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("shrinks")) {
            Player player = (Player) sender;
            player.sendMessage(CreateBorder.PREFIX + "Border shrink info:");
            player.sendMessage("§8» §f45 MINUTES §8» §f1000x1000");
            player.sendMessage("§8» §f50 MINUTES §8» §f500x500");
            player.sendMessage("§8» §f55 MINUTES §8» §f250x250");
            player.sendMessage("§8» §f60 MINUTES §8» §f75x75");
            player.sendMessage("§8» §fOriginal §8» §f1500x1500");
            player.sendMessage("§8» §fInterval §8» §f5 minutes");
            player.sendMessage("§8» §fFinal border §8» §f75x75");
        }
        return true;
    }
}
