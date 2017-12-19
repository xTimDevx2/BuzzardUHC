package me.xtimdevx.buzzarduhc.events;

import comp.xtimdevx.buzzardhavex.Game;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.commands.LatespawnCommand;
import me.xtimdevx.buzzarduhc.commands.UHCCommand;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.scenarios.types.Fireless;
import me.xtimdevx.buzzarduhc.scenarios.types.SuperHeroes;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.NameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import me.xtimdevx.buzzarduhc.utils.TabUtils;
import net.md_5.bungee.api.chat.*;
import org.apache.logging.log4j.core.helpers.NameUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xTimDevx on 26/05/2017.
 */
public class JoinEvents implements Listener {

    public static Map<String, String> logtimer = new HashMap<>();
    public static BukkitRunnable latespawntask;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        User user = User.get(player);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                TabUtils.setTabList(player);
            }
        }, 0L, 1L);
        if (State.isState(State.CLOSED)) {
            player.setFoodLevel(20);
            player.setSaturation(20);
            if (user.getRank() != User.Rank.PLAYER && user.getRank() != User.Rank.WINNER) {
                event.setJoinMessage("§a§l✔ §8» " + NameUtils.getRankColor(user.getRank()) + "" + player.getName());
            } else {
                event.setJoinMessage("§a§l✔ §8» §f" + player.getName());
            }
        }
        if (State.isState(State.ENDING)) {
            if (user.getRank() != User.Rank.PLAYER && user.getRank() != User.Rank.WINNER) {
                event.setJoinMessage("§a§l✔ §8» " + NameUtils.getRankColor(user.getRank()) + "" + player.getName());
            } else {
                event.setJoinMessage("§a§l✔ §8» §f" + player.getName());
            }
        }
        if (State.isState(State.LOBBY)) {
            player.setFoodLevel(20);
            player.setSaturation(20);
            if (user.getRank() != User.Rank.PLAYER && user.getRank() != User.Rank.WINNER) {
                event.setJoinMessage("§a§l✔ §8» " + NameUtils.getRankColor(user.getRank()) + "" + player.getName() + " §8(§f" + Bukkit.getOnlinePlayers().size() + "§8/§f" + Bukkit.getMaxPlayers() + "§8)");
            } else {
                event.setJoinMessage("§a§l✔ §8» §f" + player.getName() + " §8(§f" + Bukkit.getOnlinePlayers().size() + "§8/§f" + Bukkit.getMaxPlayers() + "§8)");
            }
            Game.teleport("hub", player);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setLevel(0);
            player.setExp(0);
            player.setMaxHealth(20);
            user.resetEffects();
            GameUtils.broadcastGameinfo(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                for (Achievement a : Achievement.values()) {
                    if (online.hasAchievement(a)) {
                        online.removeAchievement(a);
                    }
                }
                online.hidePlayer(player);
                player.hidePlayer(online);
            }
        }
        if (State.isState(State.PREGAME)) {
            event.setJoinMessage(null);
        }
        if (State.isState(State.SCATTERING)) {
            event.setJoinMessage(null);
        }
        if (State.isState(State.INGAME)) {
            if (user.getRank() != User.Rank.PLAYER && user.getRank() != User.Rank.WINNER) {
                event.setJoinMessage("§a§l✔ §8» " + NameUtils.getRankColor(user.getRank()) + "" + player.getName());
                if (!UHCCommand.ingame.containsKey(player.getName())) {
                    user.setMode(User.Mode.SPECTATOR);
                    player.teleport(new Location(Bukkit.getWorld("UHC"), 0, 100, 0));
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(PrefixUtils.PREFIX + "You late-joined this game! You where automaticly set as a spec.");
                    ComponentBuilder builder = new ComponentBuilder("");
                    if (GameTimer.m < 20) {
                        builder.append("§8» §b§oClick here to request a latespawn.");
                        builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/latespawn " + player.getName()));
                        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§f§oClick here to request a latespawn")}));
                    } else {
                        builder.append("§8» §c§o§mClick here to request a latespawn.");
                        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§cYou are too late.")}));
                    }
                    player.spigot().sendMessage(builder.create());
                }
            } else {
                event.setJoinMessage("§a§l✔ §8» §f" + player.getName());
            }
            ScoreBoard board = ScoreBoard.getInstance();
            if (logtimer.containsKey(player.getName())) {
                logtimer.remove(player.getName());
                logtask.cancel();
                UHCCommand.ingame.put(player.getName(), null);
            }
            if (LatespawnCommand.latespawn.containsKey(player.getName())) {
                LatespawnCommand.latespawn.remove(player.getName());
                UHCCommand.ingame.put(player.getName(), null);
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                Player host = Bukkit.getPlayer(Settings.getData().get("game.host").toString());
                host.performCommand("scatter 1000 false " + player.getName());
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
                if (ScenarioManager.getInstance().getScenario("fireless").isEnabled()) {
                    Fireless.enableFireless(player);
                }
                if (ScenarioManager.getInstance().getScenario("superheroes").isEnabled()) {
                    SuperHeroes.giveHeroesEffect(player);
                }
            }
            board.setScore("§8» §b§oPlayers", UHCCommand.ingame.size());
        }
    }

    public static BukkitRunnable logtask;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final User user = User.get(player);
        if (State.isState(State.CLOSED)) {
            event.setQuitMessage("§c§l✖ §8» §7" + player.getName());
        }
        if (State.isState(State.LOBBY)) {
            event.setQuitMessage("§c§l✖ §8» §7" + player.getName() + " §8(§f" + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§f80§8)");
        }
        if (State.isState(State.PREGAME)) {
            event.setQuitMessage(null);
        }
        if (State.isState(State.SCATTERING)) {
            event.setQuitMessage(null);
        }
        if (State.isState(State.INGAME)) {
            ScoreBoard board = ScoreBoard.getInstance();
            event.setQuitMessage("§c§l✖ §8» §7" + player.getName());
            if (UHCCommand.ingame.containsKey(player.getName())) {
                UHCCommand.ingame.remove(player.getName());
                logtimer.put(player.getName(), null);
                logtask = new BukkitRunnable() {
                    public void run() {
                        if (logtimer.containsKey(player.getName())) {
                            player.setWhitelisted(false);
                            user.setStat(User.Stat.GAMEKILLS, 0);
                            logtimer.remove(player.getName());
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage("§c§l✖ §8» §c" + player.getName() + " §fdidn't join back the game in time.");
                            }
                        }
                    }
                };
                logtask.runTaskLater(Main.plugin, 6000);
            }
            board.setScore("§8» §b§oPlayers", UHCCommand.ingame.size());
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.get(player);
        if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            if(user.getRank() != User.Rank.PLAYER && user.getRank() != User.Rank.WINNER) {
                player.setWhitelisted(true);
                event.allow();
            }
        }
    }
}
