package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.gui.InfoGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class GameinfoCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("gameinfo")) {
            Player player = (Player) sender;
            InfoGUI.openInfo(player);
        }
        return true;
    }
}
