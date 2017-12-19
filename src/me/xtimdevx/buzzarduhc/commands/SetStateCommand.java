package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 26/05/2017.
 */
public class SetStateCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("setstate")) {
            Player player = (Player) sender;
            User user = User.get(player);
            User.Rank rank = user.getRank();
            if(rank != User.Rank.OWNER && rank != User.Rank.ADMIN) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /setstate <state>");
                return true;
            }
            if(args.length == 1) {
                try {
                    State.setState(State.valueOf(args[0]));
                    player.sendMessage(PrefixUtils.CONFIG + "§fThe state is now: §c" + args[0].toUpperCase() + "§f.");
                }catch (Exception e) {
                    player.sendMessage("§cError: This is not a valid state: (CLOSED, LOBBY, PREGAME, SCATTERING, INGAME)");
                    return true;
                }
            }
        }
        return true;
    }
}
