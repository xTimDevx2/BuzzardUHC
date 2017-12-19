package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 20/09/2017.
 */
public class SpawnCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("spawn")) {
            Player player = (Player) sender;
            if(State.isState(State.INGAME) || State.isState(State.SCATTERING)) {
                player.sendMessage("§cError: You can't use this command when a game is running.");
                return true;
            }
            Game.teleport("hub", player);
            player.sendMessage(PrefixUtils.PREFIX + "You teleported to §bspawn§f.");
        }
        return true;
    }
}
