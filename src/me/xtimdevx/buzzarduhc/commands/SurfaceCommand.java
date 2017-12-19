package me.xtimdevx.buzzarduhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Mod;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import me.xtimdevx.buzzarduhc.utils.ScatterUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 21/05/2017.
 */
public class SurfaceCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(cmd.getName().equalsIgnoreCase("surface")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cError: /surface <player>");
                return true;
            }
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    player.sendMessage("§cThis player is not online.");
                    return true;
                }
                Location loc = target.getLocation();
                double y = LocationUtils.highestTeleportableYAtLocation(loc);
                loc.setY(y + 1);
                target.teleport(loc);
                target.sendMessage(PrefixUtils.PREFIX + "You got teleported to §bsurface§f.");
                player.sendMessage(PrefixUtils.PREFIX + "You teleported §b" + target.getName() + " §fto §bsurface§f.");
            }
        }
        return true;
    }
}
