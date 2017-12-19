package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 24/06/2017.
 */
public class FreezeCommand implements CommandExecutor{

    public static boolean Serverfreeze = false;
    public static boolean Mobfreeze = false;
    public static boolean Damagefreeze = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("freeze")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(State.isState(State.INGAME)) {
                if(user.getMode() != User.Mode.SPECTATOR && user.getMode() != User.Mode.HOST) {
                    player.sendMessage(PrefixUtils.NO_HOST_MSG);
                    return true;
                }
            }else {
                if(user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN) {
                    player.sendMessage(PrefixUtils.NO_PERM_MSG);
                    return true;
                }
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Freeze command:");
                player.sendMessage("§8» §f/freeze all");
                player.sendMessage("§8» §f/freeze <player>");
                player.sendMessage("§8» §f/freeze mobs");
                player.sendMessage("§8» §f/freeze damage");
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("all")) {
                    if(Serverfreeze != true) {

                    }else {

                    }
                }
            }
        }
        return true;
    }
}
