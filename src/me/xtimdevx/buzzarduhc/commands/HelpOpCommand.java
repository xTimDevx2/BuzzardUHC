package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzardpunish.managers.BlockManagers;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.utils.DateUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by xTimDevx on 25/05/2017.
 */
public class HelpOpCommand implements CommandExecutor{

    public static ArrayList<CommandSender> cooldown = new ArrayList<CommandSender>();

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        User user = User.get(player);
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /helpop <message>");
            return true;
        }

        if (cooldown.contains(sender)) {
            sender.sendMessage("§cWarning: HelpOp is under cooldown.");
            return true;
        }

        if(BlockManagers.isBlocked(user)) {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            Date date = new Date();

            if(BlockManagers.getUnblockTime(user) == -1 || BlockManagers.getUnblockTime(user) > date.getTime()) {
                player.sendMessage("§cYou have been helpop-blocked for: §n" + BlockManagers.getBlockedReason(user));
                if (BlockManagers.getUnblockTime(user) < 0) {
                    player.sendMessage("§cYour helpop-block is: §c§npermament§c.");
                } else {
                    player.sendMessage("§cYour helpop-block expires in: §n" + DateUtils.formatDateDiff(BlockManagers.getUnblockTime(user)) + "§c.");
                }
                return true;
            }else {
                BlockManagers.unBlock(user);
            }
        }
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String msg = sb.toString().trim();

        for (Player online : Bukkit.getOnlinePlayers()) {
            User usero = User.get(online);
            if (usero.getMode() != User.Mode.HOST && usero.getMode() != User.Mode.SPECTATOR) {
                continue;
            }
            online.sendMessage(PrefixUtils.STAFF + "The player §4" + sender.getName() + " §frequested some help.");
            online.sendMessage(PrefixUtils.STAFF + "§fHis question was: §4'" + msg + "'§f.");
            online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 1);
        }

        cooldown.add(sender);

        sender.sendMessage(PrefixUtils.PREFIX + "Your helpop was sent, the §bhost §fand §bspectators §fwill review it.");

        new BukkitRunnable() {
            public void run() {
                cooldown.remove(sender);
            }
        }.runTaskLater(Main.plugin, 100);
        return true;
    }
}
