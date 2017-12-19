package me.xtimdevx.buzzarduhc.timers;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.border.CreateBorder;
import me.xtimdevx.buzzarduhc.commands.UHCCommand;
import me.xtimdevx.buzzarduhc.events.GameStartEvent;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.scenarios.types.*;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class GameTimer {

    public static int h = 0;
    public static int m = 0;
    public static int s = 0;

    public static int pvpm = 19;
    public static int pvps = 60;

    public static int startings = 30;

    public static int border1m = 44;
    public static void runTimer(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        s++;
                        if(s == 60) {
                            s = 0;
                            m++;
                        }
                        if(m == 60) {
                            m = 0;
                            h++;
                        }
                        if(h == 60) {
                            h++;
                        }
                        pvps--;
                        if(pvps < 0) {
                            pvps= 59;
                            pvpm--;
                        }
                        if(pvpm == 0) {
                            pvpm = 0;
                        }
                        border1s--;
                        if(border1s < 0) {
                            border1s= 59;
                            border1m--;
                        }
                        if(border1m == 0) {
                            border1m = 0;
                        }
                        border2s--;
                        if(border2s < 0) {
                            border2s= 59;
                            border2m--;
                        }
                        if(border2m == 0) {
                            border2m = 0;
                        }
                        border3s--;
                        if(border3s < 0) {
                            border3s= 59;
                            border3m--;
                        }
                        if(border3m == 0) {
                            border3m = 0;
                        }
                    }
                },0, Main.tickspeed * 1L);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    for(int i=0; i < 100; i ++) {
                        online.sendMessage(" ");
                    }
                    User userO = User.get(online);
                    if(userO.getMode() == User.Mode.HOST || userO.getMode() == User.Mode.SPECTATOR) {
                        online.setGameMode(GameMode.SPECTATOR);
                    }else{
                        online.setGameMode(GameMode.SURVIVAL);
                        UHCCommand.ingame.put(online.getName(), null);
                    }
                }
                GameUtils.removeEffects();
                State.setState(State.INGAME);
                if (ScenarioManager.getInstance().getScenario("weakestlink").isEnabled()) {
                    WeakestlinkTimer.runWeakestTimer(player);
                }
                if (ScenarioManager.getInstance().getScenario("charitychump").isEnabled()) {
                    CharityChumpTimer.runCharityChumpTimer(player);
                }
                if(ScenarioManager.getInstance().getScenario("periodofresistance").isEnabled()) {
                    PeriodOfResistance.onStart();
                }
                if(ScenarioManager.getInstance().getScenario("bestpve").isEnabled()) {
                    BestPvETimer.startBestPvE();
                }
                if(ScenarioManager.getInstance().getScenario("halloween").isEnabled()) {
                    Halloween.startTimer();
                }
             }
        }, 0L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                ScoreBoard board = ScoreBoard.getInstance();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage("  ");
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b0 §fminutes in.");
                    if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                        Bukkit.getWorld("Dungeoneering").setMonsterSpawnLimit(0);
                    }else {
                        Bukkit.getWorld("UHC").setMonsterSpawnLimit(0);
                    }
                    online.sendMessage(PrefixUtils.PREFIX + "Everyone will be healed in §b5 §fminutes.");
                    board.setScore("§8» §b§oPlayers", UHCCommand.ingame.size());
                }
                player.performCommand("timer 299 Time until &cfinal heal &8»&c");
            }
        }, Main.tickspeed * 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    User userO = User.get(online);
                    online.setHealth(20);
                    online.setFoodLevel(20);
                    online.setSaturation(20);
                    online.setFireTicks(0);
                    if (ScenarioManager.getInstance().getScenario("superheroes").isEnabled()) {
                        SuperHeroes.giveHeroesEffect(online);
                    }
                    if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                        Bukkit.getWorld("Dungeoneering").setGameRuleValue("naturalRegeneration", "false");
                    }else {
                        Bukkit.getWorld("UHC").setGameRuleValue("naturalRegeneration", "false");
                    }
                    if (ScenarioManager.getInstance().getScenario("fireless").isEnabled()) {
                        Fireless.enableFireless();
                    }
                }
            }
        }, Main.tickspeed * 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                GameUtils.broadcastGameinfo();
            }
        }, Main.tickspeed * 5L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    if (ScenarioManager.getInstance().getScenario("superheroes").isEnabled()) {
                        online.setHealth(online.getMaxHealth());
                    }else {
                        online.setHealth(20);
                    }
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b5 §fminutes in.");
                    online.sendMessage(PrefixUtils.PREFIX + "Everyone has been healed, no more heals will be given.");

                }
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    Bukkit.getWorld("Dungeoneering").setAnimalSpawnLimit(15);
                    Bukkit.getWorld("Dungeoneering").setMonsterSpawnLimit(15);
                }else {
                    Bukkit.getWorld("UHC").setAnimalSpawnLimit(15);
                    Bukkit.getWorld("UHC").setMonsterSpawnLimit(15);
                }
                player.performCommand("timer cancel");
                player.performCommand("timer 900 Time until &cPvP &8»&c");
            }
        }, Main.tickspeed * 300L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b10 §fminutes in.");
                }
            }
        }, Main.tickspeed * 600L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b15 §fminutes in.");
                }
            }
        }, Main.tickspeed * 900L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b20 §fminutes in.");
                    online.sendMessage(PrefixUtils.PREFIX + "PvP has been enabled, be careful!");
                }
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    Bukkit.getWorld("Dungeoneering").setPVP(true);
                }else {
                    Bukkit.getWorld("UHC").setPVP(true);
                    Bukkit.getWorld("UHC_nether").setPVP(true);
                }
                if (ScenarioManager.getInstance().getScenario("moles").isEnabled()) {
                    Moles.setMoles();
                }
                player.performCommand("timer cancel");
                player.performCommand("timer 1500 Time until &c1000x1000 &fborder shrink &8»&c");
            }
        }, Main.tickspeed * 1200L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b25 §fminutes in.");
                }
            }
        }, Main.tickspeed * 1500L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b30 §fminutes in.");
                }
            }
        }, Main.tickspeed * 1800L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b35 §fminutes in.");
                }
            }
        }, Main.tickspeed * 2100L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b40 §fminutes in.");
                    if(Settings.getData().get("world.nether") == true) {
                        online.sendMessage("§8[§4NETHER§8]: §fTeleporting everyone out off nether!");
                        if(online.getWorld().getName().equalsIgnoreCase("UHC_nether")) {
                            Location loc = new Location(Bukkit.getWorld("UHC"), online.getLocation().getBlockX(), online.getLocation().getBlockY(), online.getLocation().getBlockZ());
                            loc.setY(LocationUtils.highestTeleportableYAtLocation(loc));
                            online.teleport(loc);
                        }
                    }
                }
            }
        }, Main.tickspeed * 2400L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §75 §fminutes.");
                }
            }
        }, Main.tickspeed * 2402L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §74 §fminutes.");
                }
            }
        }, Main.tickspeed * 2460L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §73 §fminutes.");
                }
            }
        }, Main.tickspeed * 2510L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §72 §fminutes.");
                }
            }
        }, Main.tickspeed * 2570L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §71 §fminute.");
                }
            }
        }, Main.tickspeed * 2630L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §730 §fseconds.");
                }
            }
        }, Main.tickspeed * 2660L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §710 §fseconds.");
                }
            }
        }, Main.tickspeed * 2690L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                CreateBorder.createBorder(1000, player);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §75 §fseconds.");
                }
            }
        }, Main.tickspeed * 2695L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §74 §fseconds.");
                }
            }
        }, Main.tickspeed * 2696L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §73 §fseconds.");
                }
            }
        }, Main.tickspeed * 2697L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §72 §fseconds.");
                }
            }
        }, Main.tickspeed * 2698L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §71000§fx§71000§f in §71 §fsecond.");
                }
            }
        }, Main.tickspeed * 2699L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {

            public void run() {
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    player.performCommand("wb Dungeoneering set 1000 0 0");
                }else {
                    player.performCommand("wb UHC set 1000 0 0");
                }

                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b45 §fminutes in.");
                    online.sendMessage(PrefixUtils.BORDER + "The border has shrunk to §71000§fx§71000§f.");
                }
                player.performCommand("timer cancel");
                player.performCommand("timer 300 Time until &c500x500 &fborder shrink &8»&c");
            }
        }, Main.tickspeed * 2700L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §75 §fminutes.");
                }
            }
        }, Main.tickspeed * 2702L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §74 §fminutes.");
                }
            }
        }, Main.tickspeed * 2760L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §73 §fminutes.");
                }
            }
        }, Main.tickspeed * 2820L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §72 §fminutes.");
                }
            }
        }, Main.tickspeed * 2880L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §71 §fminute.");
                }
            }
        }, Main.tickspeed * 2940L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §730 §fseconds.");
                }
            }
        }, Main.tickspeed * 2970L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §710 §fseconds.");
                }
            }
        }, Main.tickspeed * 2990L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                CreateBorder.createBorder(500, player);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §75 §fseconds.");
                }
            }
        }, Main.tickspeed * 2995L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §74 §fseconds.");
                }
            }
        }, Main.tickspeed * 2996L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §73 §fseconds.");
                }
            }
        }, Main.tickspeed * 2997L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §72 §fseconds.");
                }
            }
        }, Main.tickspeed * 2998L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7500§fx§7500§f in §71 §fsecond.");
                }
            }
        }, Main.tickspeed * 2999L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    player.performCommand("wb Dungeoneering set 500 0 0");
                }else {
                    player.performCommand("wb UHC set 500 0 0");
                }
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b50 §fminutes in.");
                    online.sendMessage(PrefixUtils.BORDER + "The border has shrunk to §7500§fx§7500§f.");
                }
                player.performCommand("timer cancel");
                player.performCommand("timer 300 Time until &c250x250 &fborder shrink &8»&c");
            }
        }, Main.tickspeed * 3000L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §75 §fminutes.");
                }
            }
        }, Main.tickspeed * 3002L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §74 §fminutes.");
                }
            }
        }, Main.tickspeed * 3060L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §73 §fminutes.");
                }
            }
        }, Main.tickspeed * 3120L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §72 §fminutes.");
                }
            }
        }, Main.tickspeed * 3180L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §71 §fminute.");
                }
            }
        }, Main.tickspeed * 3240L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §730 §fseconds.");
                }
            }
        }, Main.tickspeed * 3270L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §710 §fseconds.");
                }
            }
        }, Main.tickspeed * 3290L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                CreateBorder.createBorder(250, player);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §75 §fseconds.");
                }
            }
        }, Main.tickspeed * 3295L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §74 §fseconds.");
                }
            }
        }, Main.tickspeed * 3296L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §73 §fseconds.");
                }
            }
        }, Main.tickspeed * 3297L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §72 §fseconds.");
                }
            }
        }, Main.tickspeed * 3298L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §7250§fx§7250§f in §71 §fsecond.");
                }
            }
        }, Main.tickspeed * 3299L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    player.performCommand("wb Dungeoneering set 250 0 0");
                }else {
                    player.performCommand("wb UHC set 250 0 0");
                }
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b55 §fminutes in.");
                    online.sendMessage(PrefixUtils.BORDER + "The border has shrunk to §7250§fx§7250§f.");
                }
                player.performCommand("timer cancel");
                player.performCommand("timer 300 Time until &c75x75 &fborder shrink &8»&c");
            }
        }, Main.tickspeed * 3300L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §75 §fminutes.");
                }
            }
        }, Main.tickspeed * 3302L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §74 §fminutes.");
                }
            }
        }, Main.tickspeed * 3360L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §73 §fminutes.");
                }
            }
        }, Main.tickspeed * 3420L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §72 §fminutes.");
                }
            }
        }, Main.tickspeed * 3480L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §71 §fminute.");
                }
            }
        }, Main.tickspeed * 3540L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §730 §fseconds.");
                }
            }
        }, Main.tickspeed * 3570L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §710 §fseconds.");
                }
            }
        }, Main.tickspeed * 3590L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                CreateBorder.createBorder(75, player);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §75 §fseconds.");
                }
            }
        }, Main.tickspeed * 3595L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §74 §fseconds.");
                }
            }
        }, Main.tickspeed * 3596L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §73 §fseconds.");
                }
            }
        }, Main.tickspeed * 3597L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §72 §fseconds.");
                }
            }
        }, Main.tickspeed * 3598L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.BORDER + "The border will shrink to §775§fx§775§f in §71 §fsecond.");
                }
            }
        }, Main.tickspeed * 3599L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                    player.performCommand("wb Dungeoneering set 75 0 0");
                }else {
                    player.performCommand("wb UHC set 75 0 0");
                }                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The game is now §b60 §fminutes in.");
                    online.sendMessage(PrefixUtils.BORDER + "The border has shrunk to §775§fx§775§f.");
                    online.sendMessage(PrefixUtils.PREFIX + "Meetup has started, no more skybasing!");
                }
                player.performCommand("timer cancel");
            }
        }, Main.tickspeed * 3600L);
    }

    public static int border1s = 60;
    public static int border2m = 49;

    public static int border2s = 60;
    public static int border3m = 54;
    public static int border3s = 60;
}
