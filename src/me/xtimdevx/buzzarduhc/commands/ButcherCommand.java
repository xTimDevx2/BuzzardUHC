package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.EntityUtils;
import me.xtimdevx.buzzarduhc.utils.NameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xTimDevx on 5/07/2017.
 */
public class ButcherCommand implements CommandExecutor, TabCompleter{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        User user = User.get(player);
        if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER && user.getRank() != User.Rank.TRIAL) {
            sender.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }
        int amount = 0;

        if (args.length == 0) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity mob : world.getEntities()) {
                    if (EntityUtils.isButcherable(mob.getType())) {
                        mob.remove();
                        amount++;
                    }
                }
            }

            sender.sendMessage(PrefixUtils.STAFF + "Butchered all entities. §8[§4§o" + amount + "§8]");
            return true;
        }

        EntityType type;

        try {
            type = EntityType.valueOf(args[0].toUpperCase());
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + args[0] + " is not a vaild entity type.");
            return true;
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity mob : world.getEntities()) {
                if (mob.getType().equals(type)) {
                    mob.remove();
                    amount++;
                }
            }
        }

        sender.sendMessage(PrefixUtils.STAFF + "Butchered all §4'" + NameUtils.getMobName(type).toLowerCase() + "s'§f. §8[§4§o" + amount + "§8]");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("uhc.butcher")) {
            return null;
        }

        ArrayList<String> toReturn = new ArrayList<String>();

        if (args.length == 1) {
            if (args[0].equals("")) {
                for (EntityType type : EntityType.values()) {
                    toReturn.add(type.name().toLowerCase());
                }
            } else {
                for (EntityType type : EntityType.values()) {
                    if (type.name().toLowerCase().startsWith(args[0].toLowerCase())) {
                        toReturn.add(type.name().toLowerCase());
                    }
                }
            }
        }

        return toReturn;
    }
}
