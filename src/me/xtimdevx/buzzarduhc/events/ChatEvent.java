package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.gui.RatesGUI;
import me.xtimdevx.buzzarduhc.gui.RestartGUI;
import me.xtimdevx.buzzarduhc.gui.TeamGUI;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class ChatEvent implements Listener{

    public static  int aRate;
    public static  int fRate;
    public static  int tLimit;

    @EventHandler
    public static void onChat(AsyncPlayerChatEvent event) {
        Player player = (Player) event.getPlayer();
        if(RatesGUI.appleList.contains(player)) {
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                event.setCancelled(true);
                RatesGUI.appleList.remove(player);
                RatesGUI.appleTask.cancel();
                player.sendMessage(PrefixUtils.PREFIX + "Canceled!");
                return;
            }
            if(event.getMessage().equalsIgnoreCase("vanilla")) {
                event.setCancelled(true);
                RatesGUI.appleList.remove(player);
                RatesGUI.appleTask.cancel();
                Settings.getData().set("rates.apple", "Vanilla");
                Settings.getInstance().saveData();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.CONFIG + "Set the rate §cAPPLE §fto §cVanilla§f.");
                }
                return;
            }
            event.setCancelled(true);
            try{
                aRate = Integer.parseInt(event.getMessage());
            }catch (Exception e) {
                player.sendMessage(PrefixUtils.CONFIG + "This is not a valid rate number, Please try again.");
                player.sendMessage("§8» §fIf you want to cancel type §c'cancel'§f!");
                return;
            }
            Main.applerate = aRate;

            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(PrefixUtils.CONFIG + "Set the rate §cAPPLE §fto §c" + aRate + "§f%.");
            }
            Settings.getData().set("rates.apple", aRate);
            Settings.getInstance().saveData();
            RatesGUI.appleList.remove(player);
            RatesGUI.appleTask.cancel();
        }

        if(RatesGUI.flintList.contains(player)) {
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                event.setCancelled(true);
                RatesGUI.flintList.remove(player);
                RatesGUI.flintTask.cancel();
                player.sendMessage(PrefixUtils.CONFIG + "Canceled!");
                return;
            }
            if(event.getMessage().equalsIgnoreCase("vanilla")) {
                event.setCancelled(true);
                RatesGUI.flintList.remove(player);
                RatesGUI.flintTask.cancel();
                Settings.getData().set("rates.flint", "Vanilla");
                Settings.getInstance().saveData();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.CONFIG + "Set the rate §cFLINT §fto §cVanilla§f.");
                }
                return;
            }
            event.setCancelled(true);
            try{
                fRate = Integer.parseInt(event.getMessage());
            }catch (Exception e) {
                player.sendMessage(PrefixUtils.CONFIG + "This is not a valid rate number, Please try again.");
                player.sendMessage("§8» §fIf you want to cancel type §c'cancel'§f!");
                return;
            }
            Main.flintrate = fRate;

            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(PrefixUtils.CONFIG + "Set the rate §cFLINT §fto §c" + fRate + "§f%.");
            }
            Settings.getData().set("rates.flint", fRate);
            Settings.getInstance().saveData();
            RatesGUI.flintList.remove(player);
            RatesGUI.flintTask.cancel();
        }

        if(TeamGUI.teamList.contains(player)) {
            if(event.getMessage().equalsIgnoreCase("cancel")) {
                event.setCancelled(true);
                TeamGUI.teamList.remove(player);
                TeamGUI.teamTask.cancel();
                player.sendMessage(PrefixUtils.CONFIG + "Canceled!");
                return;
            }
            event.setCancelled(true);
            try{
                tLimit = Integer.parseInt(event.getMessage());
            }catch (Exception e) {
                player.sendMessage(PrefixUtils.CONFIG + "This is not a valid limit number, Please try again.");
                player.sendMessage("§8» §fIf you want to cancel type §c'cancel'§f!");
                return;
            }
            Main.teamlimit = tLimit;

            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(PrefixUtils.CONFIG + "Set the teaming §cLIMIT §fto §c" + tLimit + "§f.");
            }
            Settings.getData().set("team.limit", tLimit);
            Settings.getInstance().saveData();
            TeamGUI.teamList.remove(player);
            TeamGUI.teamTask.cancel();
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        String command = message.split(" ")[0].substring(1);

        if (command.equalsIgnoreCase("me") ) {
            player.sendMessage("§cD I S A B L E D");
            event.setCancelled(true);
            return;
        }
        if(command.equalsIgnoreCase("restart") || command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("reload") || command.equalsIgnoreCase("rl")) {
            if(player.isOp()) {
                RestartGUI.openRestart(player);
                event.setCancelled(true);
            }else {
                event.setCancelled(true);
            }
        }
        if(command.equalsIgnoreCase("butcher")) {
            if(player.isOp()) {
                event.setCancelled(true);
                player.performCommand("scyleuhc:butcher");
            }
        }
        if(command.equalsIgnoreCase("tps")) {
            if(player.isOp()) {
                event.setCancelled(true);
                player.performCommand("lag");
            }
        }
        if(command.equalsIgnoreCase("/calc")) {
            event.setCancelled(true);
            player.sendMessage(PrefixUtils.PREFIX + "This command has been §cdisabled §fdue to safety measures!");
        }
        if (command.startsWith("bukkit:") || command.startsWith("minecraft:")) {
            if (player.hasPermission("uhc.admin")) {
                return;
            }

            player.sendMessage(PrefixUtils.NO_PERM_MSG);
            event.setCancelled(true);
            return;
        }
    }

}
