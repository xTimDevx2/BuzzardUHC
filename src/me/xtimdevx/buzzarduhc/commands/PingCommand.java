package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 10/09/2017.
 */
public class PingCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ping")) {
            Player player = (Player) sender;
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Ping: §b" + GameUtils.getPing(player));
            }
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("§8» §cThis player is not online.");
                    return true;
                }
                player.sendMessage(PrefixUtils.PREFIX + target.getName() + "'s Ping: " + GameUtils.getPing(target));
            }
        }
        return true;
    }
}
