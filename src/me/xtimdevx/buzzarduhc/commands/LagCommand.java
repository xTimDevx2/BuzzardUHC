package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.utils.DateUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by xTimDevx on 13/09/2017.
 */
public class LagCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();

        double tps = Main.getTps();
        long maxRAM = 4096;

        long ramUsage = (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        long startTime = runtime.getStartTime();

        int entities = 0;
        int chunks = 0;

        ChatColor color;
        String status;

        if (tps == 20.0) {
            status = "Perfect";
            color = ChatColor.GREEN;
        } else if (tps >= 17 && tps <= 23) {
            status = "All Good";
            color = ChatColor.DARK_GREEN;
        } else if (tps >= 14 && tps <= 26) {
            status = "Small Hiccup";
            color = ChatColor.GOLD;
        } else {
            status = "Struggling";
            color = ChatColor.RED;
        }

        for (World world : Bukkit.getWorlds()) {
            chunks += world.getLoadedChunks().length;
            entities += world.getEntities().size();
        }

        entities -= Bukkit.getOnlinePlayers().size();

        sender.sendMessage(PrefixUtils.PREFIX + "Server performance:");
        sender.sendMessage("§8» §fCurrent TPS: " + color + tps + " §8[§6§o" + status + "§8)");
        sender.sendMessage("§8» §fUptime: §b" + DateUtils.formatDateDiff(startTime));
        sender.sendMessage("§8» §fRAM Usage: §b" + ramUsage + " MB");
        sender.sendMessage("§8» §fMax Memory: §b" + maxRAM + " MB");
        sender.sendMessage("§8» §fEntities: §b" + entities);
        sender.sendMessage("§8» §fLoaded chunks: §b" + chunks);
        return true;
    }
}
