package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 19/07/2017.
 */
public class PostdiamondCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("postdiamond")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(player.getLocation().getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(0, 0, 1).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(1, 0, 0).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(1, 0, 1).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(0, -1, 0).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(1, -1, 0).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(0, -1, 1).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            if(player.getLocation().add(1, -1, 1).getBlock().getType() == Material.AIR) {
                player.sendMessage("§cError: Your ore vein cannot be vissible!");
                return true;
            }
            player.getLocation().getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(0, 0,1).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(1, 0,0).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(1, 0,1).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(0, -1,0).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(0, -1,1).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(1, -1,0).getBlock().setType(Material.DIAMOND_ORE);
            player.getLocation().add(1, -1,1).getBlock().setType(Material.DIAMOND_ORE);
            player.sendMessage(PrefixUtils.STAFF + "You placed a §4diamond §fvein.");
            for(Player online : Bukkit.getOnlinePlayers()) {
                User user1 = User.get(online);
                if (user1.getMode() == User.Mode.HOST || user1.getMode() == User.Mode.SPECTATOR) {
                    online.sendMessage(PrefixUtils.STAFF + "§4" + player.getName() + " §fplaced a diamond vein.");
                }
            }
        }
        return true;
    }
}
