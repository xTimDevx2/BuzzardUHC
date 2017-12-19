package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.gui.*;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class ConfigCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("config")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.TRIAL) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.CONFIG + "Config commands:");
                player.sendMessage("§8» §f/config teaming §8- §f§oOpens the teaming menu");
                player.sendMessage("§8» §f/config rates §8- §f§oOpens the rates menu");
                player.sendMessage("§8» §f/config nether §8- §f§oOpens the nether menu");
                player.sendMessage("§8» §f/config other §8- §f§oOpens the other menu");
                player.sendMessage("§8» §f/config potions §8- §f§oOpens the potions menu");

            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("scenarios")) {
                    player.sendMessage("§cThis menu got removed, you have to do /scen enable <scen> (tab completable) now");
                    return true;
                }
                if(args[0].equalsIgnoreCase("teaming")) {
                    TeamGUI.openTeaming(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("rates")) {
                    RatesGUI.openRates(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("nether")) {
                    NetherGUI.openNether(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("other")) {
                    OtherGui.openRates(player);
                    return true;
                }
                if(args[0].equalsIgnoreCase("potions")) {
                    PotionsGUI.openTeaming(player);
                    return true;
                }
                player.sendMessage(PrefixUtils.CONFIG + "Config commands:");
                player.sendMessage("§8» §f/config teaming §8- §f§oOpens the teaming menu");
                player.sendMessage("§8» §f/config rates §8- §f§oOpens the rates menu");
                player.sendMessage("§8» §f/config nether §8- §f§oOpens the nether menu");
                player.sendMessage("§8» §f/config other §8- §f§oOpens the other menu");
                player.sendMessage("§8» §f/config potions §8- §f§oOpens the potions menu");
            }
        }
        return true;
    }
}
