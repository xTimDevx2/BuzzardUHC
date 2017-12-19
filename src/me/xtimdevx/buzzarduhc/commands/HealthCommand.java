package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class HealthCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("health")) {
            Player player = (Player) sender;
            if(args.length == 0) {
                double health = Math.round(player.getHealth());
                String hp = "" + health;
                player.sendMessage(PrefixUtils.PREFIX + "You §8» §f" + hp.replace(".0", "")+ " HP");
                return true;
            }
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("§cThis player is not online.");
                    return true;
                }
                double health = Math.round(target.getHealth());
                String hp = "" + health;
                player.sendMessage(PrefixUtils.PREFIX + target.getName() + " §8» §f" + hp.replace(".0", "") + " HP");
            }
        }
        return true;
    }
}
