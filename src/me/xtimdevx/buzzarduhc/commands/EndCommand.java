package me.xtimdevx.buzzarduhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import comp.xtimdevx.buzzardhavex.Game;
import comp.xtimdevx.buzzardhavex.User;
import fr.galaxyoyo.spigot.twitterapi.TwitterAPI;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.smartcardio.CommandAPDU;
import java.util.ArrayList;

/**
 * Created by xTimDevx on 21/10/2017.
 */
public class EndCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("end")) {
            if(!sender.hasPermission("uhc.end")) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length < 2 ) {
                sender.sendMessage("§cUsage: /end <totalkills> <winners>");
                return true;
            }

            int kills;

            try {
                kills = Integer.parseInt(args[0]);
            }catch (Exception e) {
                sender.sendMessage("§c" + args[0] + " is not a valid number.");
                return true;
            }

            ArrayList<String> winners = new ArrayList<String>();

            Game.broadcast(PrefixUtils.PREFIX + "The game has ended!");
            Game.broadcast(PrefixUtils.PREFIX + "These are the winners:");
            for(int i = 1; i < args.length; i++) {
                OfflinePlayer winner = Bukkit.getOfflinePlayer(args[i]);

                User user = User.get(winner);

                Game.broadcast("§8» §f" + args[i] + " §8[§b§o" + user.getStat(User.Stat.GAMEKILLS) + " Kills§8]");
                winners.add(args[i]);
            }
            Game.broadcast(PrefixUtils.PREFIX + "Total kills: " + kills);
            State.setState(State.ENDING);
        }
        return true;
    }
}
