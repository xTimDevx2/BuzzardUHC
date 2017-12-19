package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class TimeleftCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("timeleft")) {
            Player p = (Player) sender;
            if(!State.isState(State.INGAME)) {
                p.sendMessage(PrefixUtils.PREFIX + "Timers:");
                p.sendMessage("§8» §fIngame: §b00§8:§b00§8:§b00");
                p.sendMessage("§8» §fFinalheal: §b05§8:§b00");
                p.sendMessage("§8» §fPvP: §b20§8:§b00");
                p.sendMessage("§8» §fBorder 1: §b45§8:§b00");
                p.sendMessage("§8» §fBorder 2: §b50§8:§b00");
                p.sendMessage("§8» §fBorder 3: §b55§8:§b00");
            }else{
                p.sendMessage("§8» §fIngame: §b" + (GameTimer.h < 9 ? "0" + GameTimer.h : GameTimer.h) + "§8:§b" + (GameTimer.m < 9 ? "0" + GameTimer.m : GameTimer.m) + "§8:§b" + (GameTimer.s < 9 ? "0" + GameTimer.s : GameTimer.s));
                if(GameTimer.m > 4) {
                    p.sendMessage("§8» §fFinalheal: §bFinished");
                }else{
                    p.sendMessage("§8» §fFinalheal: §b00§8:§b" + (30 - GameTimer.s));
                }
                if(GameTimer.pvpm < 0) {
                    p.sendMessage("§8» §fPvP: §bFinished");
                }else{
                    p.sendMessage("§8» §fPvP: §b" + GameTimer.pvpm + "§8:§b" + GameTimer.pvps);
                }
                if(GameTimer.border1m < 0) {
                    p.sendMessage("§8» §fBorder 1: §bFinished");
                }else{
                    p.sendMessage("§8» §fBorder 1: §b" + GameTimer.border1m + "§8:§b" + GameTimer.border1s);
                }
                if(GameTimer.border2m < 0) {
                    p.sendMessage("§8» §fBorder 2: §bFinished");
                }else{
                    p.sendMessage("§8» §fBorder 2: §b" + GameTimer.border2m + "§8:§b" + GameTimer.border2s);
                }
                if(GameTimer.border3m < 0) {
                    p.sendMessage("§8» §fBorder 3: §bFinished");
                }else{
                    p.sendMessage("§8» §fBorder 3: §b" + GameTimer.border3m + "§8:§b" + GameTimer.border3s);
                }
            }
        }
        return true;
    }
}
