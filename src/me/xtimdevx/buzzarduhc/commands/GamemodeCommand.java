package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xTimDevx on 20/07/2017.
 */
public class GamemodeCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        User user = User.get(p);
        if(cmd.getName().equalsIgnoreCase("gamemode")) {
            if(user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.HOST) {
                p.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                p.sendMessage("§cUsage: /gamemode <mode> <player>");
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    p.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bCREATIVE§f.");
                    p.setGameMode(GameMode.CREATIVE);
                }
                if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    p.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bSURVIVAL§f.");
                    p.setGameMode(GameMode.SURVIVAL);
                }
                if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    p.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bSPECTATOR§f.");
                    p.setGameMode(GameMode.SPECTATOR);
                }
                if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    p.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bADVENTURE§f.");
                    p.setGameMode(GameMode.ADVENTURE);
                }
            }
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(PrefixUtils.PREFIX + "This player is not online.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    target.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bCREATIVE§f.");
                    target.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(PrefixUtils.PREFIX + "You set the gamemode from §b" + target.getName() + " §fto §bCREATIVE§f.");
                }
                if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    target.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bSURVIVAL§f.");
                    target.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(PrefixUtils.PREFIX + "You set the gamemode from §b" + target.getName() + " §fto §bSURVIVAL§f.");
                }
                if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    target.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bSPECTATOR§f.");
                    target.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(PrefixUtils.PREFIX + "You set the gamemode from §b" + target.getName() + " §fto §bSPECTATOR§f.");
                }
                if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    target.sendMessage(PrefixUtils.PREFIX + "Your gamemode was set to §bADVENTURE§f.");
                    target.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(PrefixUtils.PREFIX + "You set the gamemode from §b" + target.getName() + " §fto §bADVENTURE§f.");
                }
            }
        }
        return true;
    }
}
