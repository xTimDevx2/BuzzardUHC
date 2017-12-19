package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xTimDevx on 29/05/2017.
 */
public class ScenarioCommand implements CommandExecutor, TabCompleter{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ScenarioManager manager = ScenarioManager.getInstance();
        Player player = (Player) sender;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("enable")) {
                if (!player.hasPermission("uhc.command.scenario")) {
                    player.sendMessage("§cYou don't have the permissions to use this command. (uhc.command.scenario)");
                    return true;
                }
                if (args.length == 1) {
                    player.sendMessage("§cUsage: /scenario enable <scenario>");
                    return true;
                }
                Scenario scen = manager.getScenario(args[1]);
                if (scen == null) {
                    player.sendMessage("§c" + args[1] + " is not a scenario.");
                    return true;
                }
                if (scen.isEnabled()) {
                    player.sendMessage("§c" + scen.getName() + " is already enabled.");
                    return true;
                }
                Bukkit.broadcastMessage(PrefixUtils.SCENARIOS + "§e" + scen.getName() + " §fhas been §aenabled§f.");
                scen.setEnabled(true);
                return true;
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (!player.hasPermission("uhc.command.scenario")) {
                    player.sendMessage("§cYou don't have the permissions to use this command. (uhc.command.scenario)");
                    return true;
                }
                if (args.length == 1) {
                    player.sendMessage("§cUsage: /scenario disable <scenario>");
                    return true;
                }
                Scenario scen = manager.getScenario(args[1]);
                if (scen == null) {
                    player.sendMessage("§c" + args[1] + " is not a scenario.");
                }
                if (!scen.isEnabled()) {
                    player.sendMessage("§c" + scen.getName() + " is already disabled.");
                    return true;
                }
                Bukkit.broadcastMessage(PrefixUtils.SCENARIOS + "§e" + scen.getName() + " §fhas been §cdisabled§f.");
                scen.setEnabled(false);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                StringBuilder list = new StringBuilder("");
                int i = 1;

                for (Scenario scen : manager.getScenarios()) {
                    if (list.length() > 0) {
                        if (i == manager.getScenarios().size()) {
                            list.append("§8 and §7");
                        } else {
                            list.append("§8, §7");
                        }
                    }
                    list.append((scen.isEnabled() ? "§a" : "§c") + scen.getName());
                    i++;
                }
                player.sendMessage(PrefixUtils.SCENARIOS + "List of all scenarios: §8(§e" + manager.getScenarios().size() + "§8)");
                player.sendMessage("§8» §7" + list.toString().trim());
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (args.length == 1) {
                    sender.sendMessage("§cUsage: /scenario info <scenario>");
                    return true;
                }
                Scenario scen = manager.getScenario(args[1]);
                if (scen == null) {
                    player.sendMessage("§c" + args[1] + " is not a scenario.");
                    return true;
                }
                player.sendMessage(PrefixUtils.SCENARIOS + "Information about §e" + scen.getName() + "§8:");
                player.sendMessage("§8» §f§o" + scen.getDescription());
                return true;
            }
        }
        int size = manager.getEnabledScenarios().size();

        player.sendMessage(PrefixUtils.SCENARIOS + "Currently enabled scenarios §8- §8[§e§o" + (size == 0 ? 1 : size) + "§8]");
        if (size == 0) {
            player.sendMessage("§8» §eVanilla+ §8-> §f§oA normal UHC with a few changes.");
            return true;
        }
        for (Scenario scen : manager.getEnabledScenarios()) {
            sender.sendMessage("§8» §e" + scen.getName() + " §8-> §f§o" + scen.getDescription());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ScenarioManager manager = ScenarioManager.getInstance();
        ArrayList<String> toReturn = new ArrayList<String>();
        if(args.length == 1) {
            ArrayList<String> types = new ArrayList<String>();
            types.add("list");
            types.add("info");
            if(sender.hasPermission("uhc.scenario")) {
                types.add("enable");
                types.add("disable");
            }
            if(args[0].equalsIgnoreCase("")) {
                for(String type : types) {
                    toReturn.add(type);
                }
            }else {
                for (String type : types) {
                    if (type.startsWith(args[0].toLowerCase())) {
                        toReturn.add(type);
                    }
                }
            }
        }
        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("info")) {
                if (args[1].equalsIgnoreCase("")) {
                    for(Scenario scen : manager.getScenarios()) {
                        toReturn.add(scen.getName());
                    }
                }else {
                    for(Scenario scen : manager.getScenarios()) {
                        if(scen.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            toReturn.add(scen.getName());
                        }
                    }
                }
            }
            if(!sender.hasPermission("uhc.scenario")) {
                return null;
            }
            if(args[0].equalsIgnoreCase("enable")) {
                if(args[1].equals("")) {
                    for(Scenario scen : manager.getDisabledScenarios()) {
                        toReturn.add(scen.getName());
                    }
                }else {
                    for(Scenario scen : manager.getEnabledScenarios()) {
                        if(scen.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            toReturn.add(scen.getName());
                        }
                    }
                }
            }
        }
        return toReturn;
    }
}
