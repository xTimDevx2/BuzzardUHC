package me.xtimdevx.buzzarduhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by xTimDevx on 17/07/2017.
 */
public class FullbrightCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("fullbright")) {

        }
        return true;
    }
}
