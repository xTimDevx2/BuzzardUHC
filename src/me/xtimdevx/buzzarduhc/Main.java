package me.xtimdevx.buzzarduhc;

import com.google.common.collect.Maps;
import me.xtimdevx.buzzarduhc.arena.ArenaCommand;
import me.xtimdevx.buzzarduhc.combatlog.Combatlogger;
import me.xtimdevx.buzzarduhc.commands.*;
import me.xtimdevx.buzzarduhc.events.*;
import me.xtimdevx.buzzarduhc.gui.*;
import me.xtimdevx.buzzarduhc.misc.Orelogger;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.pregen.PregenClass;
import me.xtimdevx.buzzarduhc.scenarios.*;
import me.xtimdevx.buzzarduhc.scenarios.types.*;
import me.xtimdevx.buzzarduhc.utils.NumberUtils;
import me.xtimdevx.buzzardworld.managers.WorldManager;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class Main extends JavaPlugin {

    public static int applerate;
    public static int flintrate;
    public static int teamlimit;
    public static int tickspeed;
    public static Recipe headRecipe;
    public static HashMap<Material, ItemStack> toReplace = new HashMap<Material, ItemStack>();
    public static Map<Team, Inventory> teamInvs = Maps.newHashMap();
    public static HashMap<Inventory, BukkitRunnable> invsee = new HashMap<Inventory, BukkitRunnable>();

    public static Plugin plugin;

    public static int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners();
        ScenarioManager.getInstance().setup();
        addRecipes();
        Settings.getInstance().setup(plugin);
        Teams.getInstance().setup();
        State.setState(State.CLOSED);
        if (!ScenarioManager.getInstance().getScenario("craftclean").isEnabled()) {
            CraftClean.addCraftCleanRecipes();
        }
        Bukkit.getWorld("gi").spawn
        applerate = Settings.getData().getInt("rates.apple");
        flintrate = Settings.getData().getInt("rates.flint");
        teamlimit = Settings.getData().getInt("team.limit");
        tickspeed = Settings.getData().getInt("server.tickspeed");
        Bukkit.getWorld("HUB").setPVP(false);
        setupWorlds();
        recoverData();
        new BukkitRunnable() {
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    String percentString = NumberUtils.makePercent(online.getHealth());

                    int percent = Integer.parseInt(percentString.substring(2));
                    Scoreboard sb = ScoreBoard.getInstance().board;

                    Objective bellowName = sb.getObjective("nameHealth");
                    Objective tabList = sb.getObjective("tabHealth");

                    if (tabList != null) {
                        Score score = tabList.getScore(online.getName());
                        score.setScore(percent);
                    }

                    if (bellowName != null) {
                        Score score = bellowName.getScore(online.getName());
                        score.setScore(percent);
                    }
                }
            }
        }.runTaskTimer(this, 1, 1);
        ScoreBoard.getInstance().setup();
    }


    public void registerCommands() {
        getCommand("uhc").setExecutor(new UHCCommand());
        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("scatter").setExecutor(new ScatterCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("surface").setExecutor(new SurfaceCommand());
        getCommand("whitelist").setExecutor(new WhitelistCommand());
        getCommand("sendcoords").setExecutor(new SendCoordsCommand());
        getCommand("pm").setExecutor(new PMCommand());
        getCommand("helpop").setExecutor(new HelpOpCommand());
        getCommand("setstate").setExecutor(new SetStateCommand());
        getCommand("uhcdebug").setExecutor(new UHCDebugCommand());
        getCommand("gameinfo").setExecutor(new GameinfoCommand());
        getCommand("timeleft").setExecutor(new TimeleftCommand());
        getCommand("health").setExecutor(new HealthCommand());
        getCommand("globalmute").setExecutor(new GlobalmuteCommand());
        getCommand("scenario").setExecutor(new ScenarioCommand());
        getCommand("shrink").setExecutor(new ShrinkCommand());
        getCommand("pvp").setExecutor(new PvPCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("settickspeed").setExecutor(new SetTickSpeedCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("biomelist").setExecutor(new BiomeParanoia());
        getCommand("switcherores").setExecutor(new SwitcherOres());
        getCommand("latespawn").setExecutor(new LatespawnCommand());
        getCommand("staffchat").setExecutor(new StaffchatCommand());
        getCommand("butcher").setExecutor(new ButcherCommand());
        getCommand("respawn").setExecutor(new RespawnCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("teaminventory").setExecutor(new Teaminventory());
        getCommand("backpack").setExecutor(new BackPacks());
        getCommand("postdiamond").setExecutor(new PostdiamondCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("weakestlink").setExecutor(new WeakestLink());
        getCommand("molehelp").setExecutor(new Moles());
        getCommand("mcc").setExecutor(new Moles());
        getCommand("mcl").setExecutor(new Moles());
        getCommand("mcp").setExecutor(new Moles());
        getCommand("kicksolo").setExecutor(new KickSoloCommand());
        getCommand("shrinks").setExecutor(new ShrinksCommand());
        getCommand("setslot").setExecutor(new SetSlotCommand());
        getCommand("pregen").setExecutor(new PregenClass());
        getCommand("finishpregen").setExecutor(new UHCCommand());
        getCommand("maintenance").setExecutor(new MaintenanceCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("lag").setExecutor(new LagCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("rt").setExecutor(new RtCommand());
        getCommand("postgame").setExecutor(new PostgameCommand());
        getCommand("playerinfo").setExecutor(new PlayerinfoCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("slavereset").setExecutor(new SlavemarketCommand());
        getCommand("slaveowner").setExecutor(new SlavemarketCommand());
        getCommand("startbid").setExecutor(new SlavemarketCommand());
        getCommand("bid").setExecutor(new SlavemarketCommand());
        getCommand("end").setExecutor(new EndCommand());
        getCommand("combatlog").setExecutor(new Combatlogger());
        getCommand("mlg").setExecutor(new MLGCommand());
        getCommand("createdungeon").setExecutor(new Dungeoneering());
    }

    public void onDisable() {
        saveData();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new TeamGUI(), this);
        Bukkit.getPluginManager().registerEvents(new RatesGUI(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ConsumeEvents(), this);
        Bukkit.getPluginManager().registerEvents(new Orelogger(), this);
        Bukkit.getPluginManager().registerEvents(new InfoGUI(), this);
        Bukkit.getPluginManager().registerEvents(new WorldGUI(), this);
        Bukkit.getPluginManager().registerEvents(new PortalEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PotionsGUI(), this);
        Bukkit.getPluginManager().registerEvents(new PotionEvents(), this);
        Bukkit.getPluginManager().registerEvents(new RestartGUI(), this);
        Bukkit.getPluginManager().registerEvents(new OtherGui(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryGUI(), this);
        Bukkit.getPluginManager().registerEvents(new NetherGUI(), this);
        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new FoodEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Combatlogger(), this);
    }

    public static void addRecipes() {
        ItemStack head = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§8» §b§LGolden Head §8«");
        meta.setLore(Arrays.asList("§8» §fEating this head will heal §b4 §fhearts."));
        head.setItemMeta(meta);

        MaterialData data = new MaterialData(Material.SKULL_ITEM, (byte) 3);

        ShapedRecipe goldenhead = new ShapedRecipe(head).shape("@@@", "@*@", "@@@").setIngredient('@', Material.GOLD_INGOT).setIngredient('*', data);

        Bukkit.getServer().addRecipe(goldenhead);

        headRecipe = goldenhead;
    }

    public void setupWorlds() {
        if(Settings.getData().get("server.saferestart") == true) {
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                WorldManager.getInstance().deleteWorld(Bukkit.getWorld("UHC"));
                WorldManager.getInstance().deleteWorld(Bukkit.getWorld("UHC_nether"));
                WorldManager.getInstance().deleteWorld(Bukkit.getWorld("UHC_arena"));
            }
        }, 60L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                long normalseed = Long.valueOf(getRandom(0, 500000000));
                long netherseed = Long.valueOf(getRandom(0, 500000000));
                long arenaseed = Long.valueOf(getRandom(0, 500000000));
                if(Bukkit.getWorld("UHC_nether") == null) {
                    WorldManager.getInstance().createWorld("UHC_nether", 1000, netherseed, World.Environment.NETHER, WorldType.NORMAL);
                }
                if(Bukkit.getWorld("UHC") == null) {
                    WorldManager.getInstance().createWorld("UHC", 2000, normalseed, World.Environment.NORMAL, WorldType.NORMAL);
                }
                if(Bukkit.getWorld("UHC_arena") == null) {
                    WorldManager.getInstance().createWorld("UHC_arena", 100, normalseed, World.Environment.NORMAL, WorldType.NORMAL);
                    Bukkit.getWorld("UHC_arena").setGameRuleValue("doMobSpawning", "false");
                }
            }
        }, 100L);
    }
    public static double getTps() {
        double tps = MinecraftServer.getServer().recentTps[0];
        String converted = NumberUtils.convertDouble(tps);

        return Double.parseDouble(converted);
    }

    public static Map<Team, Inventory> getTeamInvs() {
        return teamInvs;
    }

    public void saveData() {
        List<String> scenarios = new ArrayList<String>();
        Settings settings = Settings.getInstance();

        for (Scenario scen : ScenarioManager.getInstance().getEnabledScenarios()) {
            scenarios.add(scen.getName());
        }

        settings.getData().set("scenarios", scenarios);


        settings.saveData();
    }

    /**
     * Recover all the data from the data files.
     */
    public void recoverData() {
        Settings settings = Settings.getInstance();

        try {
            for (String scen : settings.getData().getStringList("scenarios")) {
                ScenarioManager.getInstance().getScenario(scen).setEnabled(true);
            }
        } catch (Exception e) {
            getLogger().warning("Could not recover scenario data.");
        }
    }
}
