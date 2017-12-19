package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.events.DeathEvents;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import net.minecraft.server.v1_8_R3.CommandWeather;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 28/06/2017.
 */
public class RespawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("respawn")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if (user.getMode() != User.Mode.SPECTATOR && user.getMode() != User.Mode.HOST) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            player.sendMessage("§cThis command is under maintenance!");
            return true;
        }
        return true;
    }
}
