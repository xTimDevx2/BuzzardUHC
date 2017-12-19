package me.xtimdevx.buzzarduhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 10/06/2017.
 */
public class SetTickSpeedCommand implements CommandExecutor{
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("settickspeed")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if (args.length == 0) {
                player.sendMessage("§cUsage: /settickspeed <ticks>");
            }
            if (args.length == 1) {
                int ts = 0;
                try {
                    ts = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    player.sendMessage(PrefixUtils.CONFIG + "This is not a valid tick number, Please try again.");
                    return true;
                }
                Main.tickspeed = ts;

                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.CONFIG + "Set the §cTICKSPEED §fto §c" + ts + "§f ticks per second.");
                }
                Settings.getData().set("server.tickspeed", ts);
                Settings.getInstance().saveData();
            }
        }
         return true;
    }
}
