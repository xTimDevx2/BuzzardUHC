package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.border.CreateBorder;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 29/05/2017.
 */
public class ShrinkCommand implements CommandExecutor{
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("shrink")) {
            final Player player = (Player) sender;
            User user = User.get(player);
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /shrink <size>");
                return true;
            }
            if(args.length == 1) {
                final int bordersize;
                try {
                    bordersize = Integer.parseInt(args[0]);
                }catch (Exception e) {
                    player.sendMessage(PrefixUtils.BORDER + "This is not a valid border size.");
                    return true;
                }
                World UHC = Bukkit.getWorld("UHC");
                final WorldBorder UHCBorder = UHC.getWorldBorder();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        CreateBorder.createBorder(bordersize, player);
                    }
                }, 20L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +" §fin §75 §fseconds.");
                        }
                    }
                }, 40L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +" §fin §74 §fseconds.");
                        }
                    }
                }, 60L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +" §fin §73 §fseconds.");
                        }
                    }
                }, 80L);Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +" §fin §72 §fseconds.");
                        }
                    }
                }, 100L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +" §fin §71 §fseconds.");
                        }
                    }
                }, 120L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.BORDER + "Border is shrinking to §7" +  bordersize + "§fx§7" + bordersize +"§f.");
                        }
                        player.performCommand("wb UHC set " + (bordersize)  + " 0 0");
                    }
                }, 140L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                        }
                    }
                }, 160L);

            }
        }
        return true;
    }
}
