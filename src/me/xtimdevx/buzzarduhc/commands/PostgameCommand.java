package me.xtimdevx.buzzarduhc.commands;

import fr.galaxyoyo.spigot.twitterapi.TwitterAPI;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 8/10/2017.
 */
public class PostgameCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("postgame")) {
            if(args.length < 3) {
                player.sendMessage("§cUsage: /postgame twitter <matchpost> <timeUTC>");
                player.sendMessage("§cUsage: /postgame discord <matchpost> <timeUTC>");
                return true;
            }else {
                if(args[0].equalsIgnoreCase("twitter")) {
                    player.sendMessage(PrefixUtils.PREFIX + "You succesfully sent the game tweet with the following info:");
                    player.sendMessage("§8» §fTeamsize: §b" + (GameUtils.getTeamMode().equalsIgnoreCase("team") ? "cTo" + me.xtimdevx.buzzarduhc.Main.teamlimit : "FFA"));
                    player.sendMessage("§8» §fScenarios: §b" + GameUtils.getEnabledScenarios().replace("§f", "§b"));
                    player.sendMessage("§8» §fPost: §b" + args[1]);
                    player.sendMessage("§8» §fTime: §b" + args[2] + " UTC");
                    try {
                        TwitterAPI.instance().tweet("[SCYLE]: \n» " + (GameUtils.getTeamMode().equalsIgnoreCase("team") ? "cTo" + me.xtimdevx.buzzarduhc.Main.teamlimit : "FFA")
                                + " - " + GameUtils.getEnabledScenarios().replace(",", " -").replace("§8", "").replace("§f", "") + "\n» IP: 178.32.56.136:25583\n" +
                                " \n» Time: " + args[2] + " UTC\n \n» Post: " + args[1]);
                        player.sendMessage(PrefixUtils.PREFIX + "You succesfully posted the tweet.");
                    }catch (Exception e) {
                        player.sendMessage("§cError: Something went wrong while tweeting.");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
