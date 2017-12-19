package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.Game;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.border.CreateBorder;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.timers.PreTimer;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class UHCCommand implements CommandExecutor {

    public static Map<String, String> ingame = new HashMap<>();

    public static boolean explain = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("finishpregen")) {
            final Player player = (Player) sender;
            User user = User.get(player);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            player.sendMessage("§cYou don't need to use this command anymore.");
        }
        if(cmd.getName().equalsIgnoreCase("uhc")) {
            final Player player = (Player) sender;
            User user = User.get(player);
            if (user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER && user.getRank() != User.Rank.HOST && user.getRank() != User.Rank.COACH && user.getRank() != User.Rank.MANAGER&& user.getRank() != User.Rank.TRIAL) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "UHC Commands:");
                player.sendMessage("§8» §f/uhc - §oDisplays this menu.");
                player.sendMessage("§8» §f/uhc next - §oGo to the next state.");
                player.sendMessage("§8» §f/uhc host - §oGo into host mode.");
                player.sendMessage("§8» §f/uhc host <player> - §oSet someone else into host mode.");
                player.sendMessage("§8» §f/uhc spec - §oGo into spec mode.");
                player.sendMessage("§8» §f/uhc spec <player> - §oSet someone else into spec mode.");
                player.sendMessage("§8» §f/uhc player - §oGo into player mode.");
                player.sendMessage("§8» §f/uhc player <player> - §oSet someone else into player mode.");
                player.sendMessage("§8» §f/uhc world - §oCreate the UHC world.");
                player.sendMessage("§8» §f/uhc pregen - §oPregen the UHC world.");
                player.sendMessage("§8» §f/uhc end - §oEnd the UHC.");
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("pregenborder")) {
                    CreateBorder.generateFirstBorder(1500, player);
                }
                if(args[0].equalsIgnoreCase("end")) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for(Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "Ending the UHC!");
                            }
                            Settings.getData().set("server.saferestart", false);
                            player.performCommand("wb UHC set 1500 0 0");
                            player.performCommand("team clear");
                            me.xtimdevx.buzzardworld.managers.WorldManager.getInstance().deleteWorld(Bukkit.getWorld("UHC"));
                            player.sendMessage("§fDeleted world §bUHC§f.");
                            me.xtimdevx.buzzardworld.managers.WorldManager.getInstance().deleteWorld(Bukkit.getWorld("UHC_nether"));
                            player.sendMessage("§fDeleted world §bUHC_nether§f.");
                        }
                    }, 0L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for(Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "Stopping the server!");
                            }
                        }
                    }, 40L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.getServer().shutdown();
                        }
                    }, 60L);
                }
                if(args[0].equalsIgnoreCase("pregen")) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for(Player online : Bukkit.getOnlinePlayers()) {
                                player.getInventory().clear();
                                player.getInventory().setArmorContents(null);
                                Game.teleport("hub");
                            }
                        }
                    }, 0L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for(Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "§fStarted the §bpregen §fprocess!");
                            }
                        }
                    }, 0L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                player.teleport(new Location(Bukkit.getWorld("Dungeoneering"), 0, 100, 0));
                            }else {
                                player.teleport(new Location(Bukkit.getWorld("UHC"), 0, 100, 0));
                            }
                        }
                    }, 20L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.setGameMode(GameMode.CREATIVE);
                        }
                    }, 40L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.performCommand("/replacenear 100 log,log2,leaves,leaves2,flower,175:4,38,175:1 0");
                        }
                    }, 60L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            World UHC = Bukkit.getWorld("UHC");
                            WorldBorder border = UHC.getWorldBorder();
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                player.performCommand("wb Dungeoneering set 1500 0 0");
                            }else {
                                player.performCommand("wb UHC set 1500 0 0");
                            }
                            CreateBorder.generateFirstBorder(1500, player);
                        }
                    }, 120L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                Bukkit.getWorld("Dungeoneering").setPVP(false);
                            }else {
                                Bukkit.getWorld("UHC").setPVP(false);
                                Bukkit.getWorld("UHC_nether").setPVP(false);
                            }
                        }
                    }, 140L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                World UHC = Bukkit.getWorld("Dungeoneering");
                                UHC.setGameRuleValue("naturalRegeneration", "false");
                            }else {
                                World UHC = Bukkit.getWorld("UHC");
                                World UHCNETHER = Bukkit.getWorld("UHC_nether");
                                UHC.setGameRuleValue("naturalRegeneration", "false");
                                UHCNETHER.setGameRuleValue("naturalRegeneration", "false");
                            }
                        }
                    }, 160L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                World Dungeoneering = Bukkit.getWorld("Dungeoneering");
                                player.performCommand("wb Dungeoneering fill 1000");
                                player.performCommand("wb fill confirm");
                            }else {
                                World UHC = Bukkit.getWorld("UHC");
                                player.performCommand("wb UHC fill 1000");
                                player.performCommand("wb fill confirm");
                            }
                        }
                    }, 180L);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                                Bukkit.getWorld("Dungeoneering").setMonsterSpawnLimit(15);
                                Bukkit.getWorld("Dungeoneering").setAnimalSpawnLimit(15);
                            }else {
                                Bukkit.getWorld("UHC").setMonsterSpawnLimit(15);
                                Bukkit.getWorld("UHC").setAnimalSpawnLimit(15);
                            }
                                player.sendMessage("§8» §fNow wait until the map is a 100% generated! §8«");
                        }
                    }, 200L);
                }
                if(args[0].equalsIgnoreCase("resetboard")) {
                    player.performCommand("scoreboard objectives remove kills");
                    ScoreBoard.getInstance().setup();
                }
                if(args[0].equalsIgnoreCase("next")) {
                    if(State.isState(State.CLOSED)) {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Opened the server, everyone can join now!");
                            Game.teleport("hub", online);
                            online.hidePlayer(online);
                        }
                        GameUtils.broadcastGameinfo();
                        Bukkit.setWhitelist(false);
                        player.sendMessage(PrefixUtils.STAFF + "Next state 'Pregame': /uhc next §8[§C§oWait 5 minutes first§8]");
                        State.setState(State.LOBBY);
                        if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                            Bukkit.getWorld("Dungeoneering").setPVP(false);
                            Bukkit.getWorld("Dungeoneering").setAnimalSpawnLimit(15);
                        }else {
                            Bukkit.getWorld("UHC").setPVP(false);
                            Bukkit.getWorld("UHC").setAnimalSpawnLimit(15);
                        }
                        return true;
                    }
                    if(State.isState(State.LOBBY)) {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Setting up the server, this will only take a few seconds!");
                            online.sendMessage(PrefixUtils.PREFIX + "Please don't relog until you recieved your started food.");
                            online.setWhitelisted(true);
                            online.getInventory().clear();
                            online.getInventory().setArmorContents(null);
                            online.setGameMode(GameMode.SURVIVAL);
                            online.setMaxHealth(20);
                        }
                        Bukkit.setWhitelist(true);
                        player.sendMessage(PrefixUtils.STAFF + "Next state 'Scattering': §8[§b§o/uhc next§8]");
                        State.setState(State.PREGAME);
                        return true;
                    }
                    if(State.isState(State.PREGAME)) {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Started scattering everyone!");
                        }
                        if(GameUtils.getTeamMode().equalsIgnoreCase("ffa")) {
                            player.performCommand("scatter 1500 false *");
                        }
                        if(GameUtils.getTeamMode().equalsIgnoreCase("team")) {
                            player.performCommand("scatter 1500 true *");
                        }
                        player.sendMessage(PrefixUtils.STAFF + "Next state 'Ingame': /uhc next §8[§b§oWait until scatter is finished§8]");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.chat("Hello guys, Welcome in this " + GameUtils.getTeamMode().replace("Team", "Chosen Team" + Main.teamlimit) + ", " + GameUtils.getEnabledScenarios().replace("§b", "§f").replace("§8", "§f") + " UHC!");
                            }
                        }, 20L);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.chat("The server is scattering you, so i will explain some basic rules.");
                            }
                        }, 80L);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                if(GameUtils.getTeamMode().equalsIgnoreCase("FFA")) {
                                    player.chat("This game is free for all.");
                                    player.chat("Teaming will result in a 2 weeks ban!");
                                }else {
                                    player.chat("This game is chosen team of " + Main.teamlimit + ".");
                                    player.chat("This means you can team with max " + (Main.teamlimit - 1) + " other person(s)");
                                }
                            }
                        }, 140L);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.chat("Everyone will get a heal at 5 minutes in the game, no more heals will be given after that.");
                            }
                        }, 200L);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.chat("Use /helpop if you have a question, dont forget to thank your host.");
                            }
                        }, 260L);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.chat("But the most important thing is, Good luck and have fun!");
                            }
                        }, 320L);
                        return true;
                    }
                    if(State.isState(State.SCATTERING)) {
                        PreTimer.runTimer(player);
                        return true;
                    }
                }
                if(args[0].equalsIgnoreCase("host")) {
                    if (user.getMode() == User.Mode.HOST) {
                        Settings.getData().set("game.host", "none");
                        Settings.getInstance().saveData();
                        ScoreBoard.getInstance().setup();
                        player.sendMessage(PrefixUtils.PREFIX + "You are no longer the host.");
                        player.sendMessage("§fYou are now in the mode: §bPLAYER§f.");
                        try{
                            user.setMode(User.Mode.PLAYER);
                        }catch (Exception e) {
                            Game.printError("Something went wrong while trying to set a mode.");
                        }
                        return true;
                    }
                    Settings.getData().set("game.host", player.getName());
                    Settings.getInstance().saveData();
                    ScoreBoard.getInstance().setup();
                    GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + player.getName() + " §fis now in the mode §bhost§f.");
                    player.sendMessage("§fYou are now in the mode: §bHOST§f.");
                    try{
                        user.setMode(User.Mode.HOST);
                    }catch (Exception e) {
                        Game.printError("Something went wrong while trying to set a mode.");
                    }
                }
                if(args[0].equalsIgnoreCase("spec")) {
                    if (user.getMode() == User.Mode.SPECTATOR) {
                        player.sendMessage(PrefixUtils.PREFIX + "You are no longer a spectator.");
                        player.sendMessage("§fYou are now in the mode: §bPLAYER§f.");
                        try{
                            user.setMode(User.Mode.PLAYER);
                        }catch (Exception e) {
                            Game.printError("Something went wrong while trying to set a mode.");
                        }
                        return true;
                    }
                    GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + player.getName() + " §fis now in the mode §bspectator§f.");
                    player.sendMessage("§fYou are now in the mode: §bSPECTATOR§f.");
                    try{
                        user.setMode(User.Mode.SPECTATOR);
                    }catch (Exception e) {
                        Game.printError("Something went wrong while trying to set a mode.");
                    }
                }
            }
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("host")) {
                    Player target = (Player) Bukkit.getPlayer(args[1]);
                    User user1 = User.get(target);
                    if(target == null) {
                        Settings.getData().set("game.host", args[1]);
                        Settings.getInstance().saveData();
                        ScoreBoard.getInstance().setup();
                        GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + args[1] + " §fis now in the mode §bhost§f. §8(§f§oOffline§8)");
                        user1.setMode(User.Mode.HOST);
                        return true;
                    }
                    Settings.getData().set("game.host", target.getName());
                    Settings.getInstance().saveData();
                    ScoreBoard.getInstance().setup();
                    GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + target.getName() + " §fis now in the mode §bhost§f.");
                    target.sendMessage("§fYou are now in the mode: §bHOST§f.");
                    user1.setMode(User.Mode.HOST);
                }
                if(args[0].equalsIgnoreCase("spec")) {
                    Player target = (Player) Bukkit.getPlayer(args[1]);
                    User user1 = User.get(target);
                    if(target == null) {
                        GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + args[1] + " §fis now in the mode §bspectator§f. §8(§f§oOffline§8)");
                        user1.setMode(User.Mode.SPECTATOR);
                        return true;
                    }
                    GameUtils.broadcast(PrefixUtils.PREFIX + "§b" + target.getName() + " §fis now in the mode §bspectator§f.");
                    target.sendMessage("§fYou are now in the mode: §bSPECTATOR§f.");
                    user1.setMode(User.Mode.SPECTATOR);
                }
            }
        }
        return true;
    }
}
