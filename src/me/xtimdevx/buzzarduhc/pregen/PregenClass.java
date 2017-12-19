package me.xtimdevx.buzzarduhc.pregen;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Created by xTimDevx on 25/08/2017.
 */
public class PregenClass implements CommandExecutor{

    Thread thread;

    public void cancel()
    {
        running = Boolean.valueOf(false);
    }

    public boolean onCommand(final CommandSender sender, Command command, String label, final String[] args)
    {
        if ((!sender.hasPermission("pregenerator.use")) && (!sender.isOp()))
        {
            sender.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }
        if (args.length < 5)
        {
            if ((running.booleanValue()) && (args.length > 0) && ((args[0].equalsIgnoreCase("stop")) || (args[0].equalsIgnoreCase("cancel"))))
            {
                if (this.thread != null) {
                    cancel();
                }
                log(ChatColor.DARK_RED + "Task cancelled succesfull. Progress saved.");
                return true;
            }
            sender.sendMessage(PrefixUtils.PREFIX + "Usage: /pregen <World> <StartX> <StartZ> <EndX> <EndZ>");
            sender.sendMessage(PrefixUtils.PREFIX + "Usage: /pregen stop/cancel");
            return true;
        }
        this.thread = new Thread()
        {
            public void run()
            {
                World world = Bukkit.getWorld(args[0]);
                if (world == null)
                {
                    sender.sendMessage(ChatColor.DARK_RED + "World not found: " + ChatColor.RED + args[0]);
                    return;
                }
                Integer startx = null;
                Integer startz = null;
                Integer endx = null;
                Integer endz = null;
                try
                {
                    startx = Integer.valueOf(args[1]);
                    startz = Integer.valueOf(args[2]);
                    endx = Integer.valueOf(args[3]);
                    endz = Integer.valueOf(args[4]);
                }
                catch (Exception ex)
                {
                    sender.sendMessage(ChatColor.DARK_RED + "Coordinates not correct.");
                    return;
                }
                if ((startx == null) || (startz == null) || (endx == null) || (endz == null))
                {
                    sender.sendMessage(ChatColor.DARK_RED + "Coordinates not correct.");
                    return;
                }
                if (PregenClass.isRunning())
                {
                    sender.sendMessage(ChatColor.DARK_RED + "PreGenerator is already generating land. Wait for this task before starting a new one.");
                    return;
                }
                PregenClass.running = Boolean.valueOf(true);
                Long start = Long.valueOf(System.currentTimeMillis());
                Bukkit.broadcastMessage("§bPregen §8» §fGenerator started generating land!");
                try
                {
                    world.save();
                }
                catch (Exception localException1) {}
                Cuboid region = new Cuboid(Cuboid.plugin, world, startx.intValue(), 3, startz.intValue(), endx.intValue(), 3, endz.intValue());
                region.generateChunks();
                Long duration = Long.valueOf(System.currentTimeMillis() - start.longValue());
                Long seconds = Long.valueOf(duration.longValue() / 1000L);
                Long minutes = Long.valueOf(seconds.longValue() / 60L);
                Long hours = Long.valueOf(minutes.longValue() / 60L);
                Bukkit.broadcastMessage("§bPregen §8» §fGenerating done! §8(§b" + duration + "ms, =" + seconds + "s, =" + minutes + "m, =" + hours + "h.§8)");
                Bukkit.broadcastMessage("§bPregen §8» §fPlease use §b/finishpregen §fto make the server ready to host.");
                PregenClass.this.running = Boolean.valueOf(false);
            }
        };
        this.thread.start();

        return true;
    }

    static Boolean running = Boolean.valueOf(false);

    public static boolean isRunning()
    {
        return running.booleanValue();
    }

    public static void log(String str)
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + "PG" + ChatColor.BLUE + "] " + ChatColor.YELLOW + str);
    }

    static Integer max = Integer.valueOf(-1);
}
