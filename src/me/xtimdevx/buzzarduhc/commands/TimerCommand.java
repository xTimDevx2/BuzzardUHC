package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.utils.DateUtils;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xTimDevx on 18/10/2017.
 */
public class TimerCommand implements CommandExecutor, TabCompleter{

    private BukkitRunnable run = null;
    private boolean countdown = true;
    private String message;
    private int ticks;

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("uhc.staff")) {
            sender.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /timer <duration> <message>");
            return true;
        }

        if (args.length == 1 && !args[0].equalsIgnoreCase("cancel")) {
            sender.sendMessage("§cUsage: /timer <duration> <message>");
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("cancel")) {
            if (run != null) {
                run.cancel();
                run = null;
            } else {
            }
            return true;
        }

        if (run != null) {
            return true;
        }

        int millis;

        try {
            millis = Integer.parseInt(args[0]);
        } catch (Exception e) {
            sender.sendMessage("§c" + args[0] + " is not a vaild number.");
            return true;
        }

        if (millis <= 0) {
            countdown = false;
        } else {
            countdown = true;
        }

        StringBuilder msg = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            msg.append(args[i]).append(" ");
        }

        run = new BukkitRunnable() {
            public void run() {
                if (countdown) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        GameUtils.sendAction(online, "§8» §f" + message + " " + DateUtils.ticksToString(ticks) + " §8«");
                    }
                    ticks--;

                    if (ticks < 0) {
                        cancel();
                        run = null;
                    }
                } else {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        GameUtils.sendAction(online, message);
                    }
                }
            }
        };
        run.runTaskTimer(Main.plugin, 0, 20);

        this.message = ChatColor.translateAlternateColorCodes('&', msg.toString().trim());
        this.ticks = millis;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("uhc.timer")) {
            return null;
        }

        ArrayList<String> toReturn = new ArrayList<String>();

        if (args.length == 1) {
            ArrayList<String> types = new ArrayList<String>();
            types.add("cancel");

            if (args[0].equals("")) {
                for (String type : types) {
                    toReturn.add(type);
                }
            } else {
                for (String type : types) {
                    if (type.startsWith(args[0].toLowerCase())) {
                        toReturn.add(type);
                    }
                }
            }
        }

        if (args.length == 2) {
            ArrayList<String> types = new ArrayList<String>();

            if (args[1].equals("")) {
                for (String type : types) {
                    toReturn.add(type);
                }
            } else {
                for (String type : types) {
                    if (type.startsWith(args[1].toLowerCase())) {
                        toReturn.add(type);
                    }
                }
            }
        }

        return toReturn;
    }
}
