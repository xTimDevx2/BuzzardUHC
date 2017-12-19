package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 26/08/2017.
 */
public class MaintenanceCommand implements CommandExecutor{


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("maintenance")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getRank() != User.Rank.OWNER) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Maintenance Command:");
                player.sendMessage("§8» §f/maintenance <reason>");
                player.sendMessage("§8» §f/maintenance end");
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("end")) {
                    if(Settings.getData().get("maintenance.enabled") != true) {
                        player.sendMessage("§9Maintenance §8» §fMaintenance is not enabled.");
                        return true;
                    }
                    Settings.getData().set("maintenance.enabled", false);
                    Settings.getInstance().saveData();
                    Bukkit.broadcastMessage("§9Maintenance §8» §fTurned §9Off §fMaintenance mode.");
                    return true;
                }
                player.sendMessage("§cUsage: /maintenance <reason>");
                return true;
            }
            if(args.length > 1) {
                StringBuilder sb = new StringBuilder("");

                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }

                String msg = sb.toString().trim();

                Settings.getData().set("maintenance.enabled", true);
                Settings.getData().set("maintenance.reason", msg);
                Settings.getInstance().saveData();

                Bukkit.broadcastMessage("§9Maintenance §8» §fTurned §9On §fMaintenance mode.");
                Bukkit.broadcastMessage("§8» §fReason: " + msg);
            }
        }
        return true;
    }

}
