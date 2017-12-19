package me.xtimdevx.buzzarduhc.commands;

import com.sun.xml.internal.ws.streaming.PrefixFactoryImpl;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import net.minecraft.server.v1_8_R3.WhiteList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class WhitelistCommand implements CommandExecutor {

    WhiteList whitelist = ((CraftServer)Bukkit.getServer()).getHandle().getWhitelist();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("whitelist")) {
            Player player = (Player) sender;
            User user = User.get(player);
            User.Rank rank = user.getRank();
            User.Mode mode = user.getMode();
            if (rank != User.Rank.OWNER && rank != User.Rank.ADMIN && mode != User.Mode.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER) {
                player.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(PrefixUtils.WHITELIST + "Whitelist Command:");
                player.sendMessage("§8» §f/whitelist on - §oEnable whitelist.");
                player.sendMessage("§8» §f/whitelist off - §oDisable whitelist.");
                player.sendMessage("§8» §f/whitelist add <player> - §oAdd a player to the whitelist.");
                player.sendMessage("§8» §f/whitelist remove <player> - §oRemove a player from the whitelist.");
                player.sendMessage("§8» §f/whitelist all - §oAdd all players to the whitelist.");
                player.sendMessage("§8» §f/whitelist clear - §oClear the whitelist.");
                player.sendMessage("§8» §f/whitelist list - §oSee the whitelisted players.");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    Bukkit.getServer().setWhitelist(true);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "The §6whitelist §fhas been §6enabled§f.");
                    }
                }
                if (args[0].equalsIgnoreCase("off")) {
                    Bukkit.getServer().setWhitelist(false);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "The §6whitelist §fhas been §6disabled§f.");
                    }
                }
                if (args[0].equalsIgnoreCase("all")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "§6Everyone §fhas been §6whitelisted§f.");
                        online.setWhitelisted(true);
                    }
                }
                if (args[0].equalsIgnoreCase("clear")) {
                    whitelist.getValues().clear();
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "The §6whitelist §fhas been §6cleared§f.");
                    }
                }
                if (args[0].equalsIgnoreCase("list")) {
                    if (Bukkit.getWhitelistedPlayers().size() <= 0) {
                        player.sendMessage(PrefixUtils.WHITELIST + "No one has been §6whitelisted§f.");
                        return true;
                    }

                    StringBuilder list = new StringBuilder();
                    int i = 1;

                    for (OfflinePlayer whitelisted : Bukkit.getWhitelistedPlayers()) {
                        if (list.length() > 0) {
                            if (i == Bukkit.getWhitelistedPlayers().size()) {
                                list.append(" §8and§f ");
                            } else {
                                list.append("§8,§f ");
                            }
                        }

                        list.append(whitelisted.getName());
                        i++;
                    }
                    player.sendMessage(PrefixUtils.WHITELIST + "§8[§f" + (i - 1) + "§8]: §f" + list.toString());
                }
                if(args[0].equalsIgnoreCase("add")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "Usage: /whitelist add <player>");
                    }
                }
                if(args[0].equalsIgnoreCase("remove")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "Usage: /whitelist remove <player>");
                    }
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    target.setWhitelisted(true);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "Added §6" + target.getName().toLowerCase() + " §fto the §6whitelist§f.");
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    target.setWhitelisted(false);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.WHITELIST + "Removed §6" + target.getName().toLowerCase() + " §ffrom the §6whitelist§f.");
                    }
                }
            }
        }
        return true;
    }
}
