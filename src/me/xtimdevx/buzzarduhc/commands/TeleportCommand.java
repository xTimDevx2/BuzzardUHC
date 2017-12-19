package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by xTimDevx on 5/06/2017.
 */
public class TeleportCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        User user = User.get(p);
        if(cmd.getName().equalsIgnoreCase("teleport")) {
            if(!State.isState(State.INGAME)) {
                if (user.getRank() == User.Rank.PLAYER || user.getRank() == User.Rank.FAMOUS) {
                    p.sendMessage(PrefixUtils.NO_PERM_MSG);
                    return true;
                }
            }else {
                if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                    p.sendMessage(PrefixUtils.NO_HOST_MSG);
                    return true;
                }
            }
            if(args.length == 0) {
                p.sendMessage(PrefixUtils.PREFIX + "Teleport usage:");
                p.sendMessage(PrefixUtils.PREFIX + "/teleport <player>");
                p.sendMessage(PrefixUtils.PREFIX + "/teleport <player> <target>");
                p.sendMessage(PrefixUtils.PREFIX + "/teleport <x> <y> <z>");
                p.sendMessage(PrefixUtils.PREFIX + "/teleport map");
                p.sendMessage(PrefixUtils.PREFIX + "/teleport random");
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("map")) {
                    p.sendMessage(PrefixUtils.PREFIX + "You teleported to the uhc §bMAP§f.");
                    p.teleport(new Location(Bukkit.getWorld("UHC"),0, 100,0));
                    return true;
                }
                if(args[0].equalsIgnoreCase("random")) {
                    Random r = new Random();
                    ArrayList<Player> players = new ArrayList<Player>();
                    for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                        if(online == p) {
                        }
                        else
                            players.add(online);
                    }
                    int index = r.nextInt(players.size());
                    Player loc = (Player) players.get(index);
                    p.teleport(loc);
                    p.sendMessage(PrefixUtils.PREFIX + "Teleporting to §b" + loc.getPlayer().getName().toUpperCase() + "§f. §8[§b§oRandom§8]");
                    if (loc.getPlayer().getScoreboard().getEntryTeam(loc.getPlayer().getName()) != null) {
                        StringBuilder list = new StringBuilder("");
                        int i = 1;
                        Team team = loc.getPlayer().getScoreboard().getEntryTeam(loc.getPlayer().getName());
                        for (String entry : TeamCommand.savedTeams.get(team.getName())) {
                            if (list.length() > 0) {
                                if (i == TeamCommand.savedTeams.get(team.getName()).size()) {
                                    list.append(" §fand §b");
                                } else {
                                    list.append("§8, §b");
                                }
                            }

                            @SuppressWarnings("deprecation")
                            OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry);

                            if (teammates.isOnline()) {
                                list.append(ChatColor.GREEN + teammates.getName());
                            } else {
                                list.append(ChatColor.RED + teammates.getName());
                            }
                            i++;
                        }

                        p.sendMessage("§8» §f" + list.toString().trim());
                    }else{

                    }
                    return true;

                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    p.sendMessage(PrefixUtils.PREFIX + args[0] + " is not online.");
                    return true;
                }
                p.teleport(target);
                p.sendMessage(PrefixUtils.PREFIX + "You teleported to §b" + target.getName().toUpperCase() + "§f.");
            }
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                Player target2 = Bukkit.getPlayer(args[1]);
                if(target == null || target2 == null) {
                    p.sendMessage(PrefixUtils.PREFIX + "Your target is not online.");
                    return true;
                }
                target.teleport(target2);
                target.sendMessage(PrefixUtils.PREFIX + "You got teleported to §b" + target2.getName().toUpperCase() + "§f.");
                p.sendMessage(PrefixUtils.PREFIX + "You teleported §b" + target.getName().toUpperCase() + " §fto §b" + target2.getName().toUpperCase() + "§f.");
            }
            if(args.length == 3) {
                double x;
                try {
                    x = Double.parseDouble(args[1]);
                } catch(Exception e) {
                    p.sendMessage(PrefixUtils.PREFIX + "This is not a valid x value.");
                    return true;
                }
                double y;
                try {
                    y = Double.parseDouble(args[2]);
                } catch(Exception e) {
                    p.sendMessage(PrefixUtils.PREFIX + "This is not a valid y value.");
                    return true;
                }
                double z;
                try {
                    z = Double.parseDouble(args[3]);
                } catch(Exception e) {
                    p.sendMessage(PrefixUtils.PREFIX + "This is not a valid z value.");
                    return true;
                }
                p.teleport(new Location(p.getWorld(), x, y, z));
                p.sendMessage(PrefixUtils.PREFIX + "You teleported to X:§b" + x + " §fY:§b" + y + " §fZ:§b" + z);
            }
        }
        return true;
    }
}
