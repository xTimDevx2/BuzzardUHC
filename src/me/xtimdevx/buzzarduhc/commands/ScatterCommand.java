package me.xtimdevx.buzzarduhc.commands;

import com.avaje.ebeaninternal.server.lib.sql.Prefix;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import me.xtimdevx.buzzarduhc.utils.ScatterUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class ScatterCommand implements CommandExecutor {

    public static final HashMap<String, Location> scatterLocs = new HashMap<String, Location>();
    public static boolean isReady = true;

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        User user = User.get(p);
        if (user.getMode() != User.Mode.HOST) {
            p.sendMessage(PrefixUtils.NO_HOST_MSG);
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(PrefixUtils.TEAMS + "Usage: /scatter <radius> <teamscatter> <player|*>");
            return true;
        }


        if (Bukkit.getWorld("UHC") == null) {
            sender.sendMessage(PrefixUtils.TEAMS + "A §aUHC §fworld has not been created.");
            return true;
        }

        final boolean teams;
        final int radius;

        try {
            radius = Integer.parseInt(args[0]);
        } catch (Exception e) {
            sender.sendMessage(PrefixUtils.TEAMS + "An invaild radius has been given.");
            return true;
        }

        if (args[1].equalsIgnoreCase("true")) {
            teams = true;
        } else if (args[1].equalsIgnoreCase("false")) {
            teams = false;
        } else {
            sender.sendMessage(PrefixUtils.TEAMS + "§aTeamscatter §fmust be §atrue §for §cfalse§f.");
            return true;
        }

        if (args[2].equalsIgnoreCase("*")) {
            State.setState(State.SCATTERING);
            isReady = false;

            int t = 0;
            int s = 0;

            if (Settings.getData().get("team.mode").equals("Team") || Settings.getData().get("team.mode").equals("random") && Settings.getData().getInt("team.limit") > 1) {
                for (OfflinePlayer whitelisted : Bukkit.getServer().getWhitelistedPlayers()) {
                    if (ScoreBoard.getInstance().board.getEntryTeam(whitelisted.getName()) == null) {
                        Team team = Teams.getInstance().findAvailableTeam();

                        if (team != null) {
                            team.addEntry(whitelisted.getName());
                        }
                    }
                }
            }

            for (Team te : Teams.getInstance().getTeams()) {
                if (te.getSize() > 0) {
                    if (te.getSize() > 1) {
                        t++;
                    } else {
                        s++;
                    }
                }
            }
            for (OfflinePlayer whitelisted : Bukkit.getServer().getWhitelistedPlayers()) {
                if (ScoreBoard.getInstance().board.getEntryTeam(whitelisted.getName()) == null) {
                    s++;
                }
            }

            final int te = t;
            final int so = s;

            new BukkitRunnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    if (teams) {
                        if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                            List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("Dungeoneering"), radius, te + so);
                            int index = 0;

                            for (Team tem : Teams.getInstance().getTeamsWithPlayers()) {
                                for (String player : tem.getEntries()) {
                                    scatterLocs.put(player, loc.get(index));

                                    Bukkit.getOfflinePlayer(player).setWhitelisted(true);
                                }
                                index++;
                            }


                            for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) {
                                if (ScoreBoard.getInstance().board.getEntryTeam(online.getName()) == null) {
                                    scatterLocs.put(online.getName(), loc.get(index));
                                    index++;
                                }
                            }
                        }else {
                            List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, te + so);
                            int index = 0;

                            for (Team tem : Teams.getInstance().getTeamsWithPlayers()) {
                                for (String player : tem.getEntries()) {
                                    scatterLocs.put(player, loc.get(index));

                                    Bukkit.getOfflinePlayer(player).setWhitelisted(true);
                                }
                                index++;
                            }


                            for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) {
                                if (ScoreBoard.getInstance().board.getEntryTeam(online.getName()) == null) {
                                    scatterLocs.put(online.getName(), loc.get(index));
                                    index++;
                                }
                            }
                        }

                    } else {
                        if(ScenarioManager.getInstance().getScenario("dungeoneering").isEnabled()) {
                            List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("Dungeoneering"), radius, Bukkit.getServer().getWhitelistedPlayers().size());
                            int index = 0;

                            for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) {
                                scatterLocs.put(online.getName(), loc.get(index));
                                index++;
                            }
                        }else {
                            List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, Bukkit.getServer().getWhitelistedPlayers().size());
                            int index = 0;

                            for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) {
                                scatterLocs.put(online.getName(), loc.get(index));
                                index++;
                            }
                        }

                    }
                }
            }.runTaskLater(Main.plugin, 30);

            new BukkitRunnable() {
                public void run() {

                    final ArrayList<Location> locs = new ArrayList<Location>(scatterLocs.values());
                    final ArrayList<String> names = new ArrayList<String>(scatterLocs.keySet());

                    new BukkitRunnable() {
                        int i = 0;

                        public void run() {
                            if (i < locs.size()) {
                                if (sender instanceof Player) {
                                    Player player = (Player) sender;
                                    player.teleport(locs.get(i));
                                    if (Settings.getData().get("team.mode").equals("Team") || Settings.getData().get("team.mode").equals("Random")) {
                                        Location loc1 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc2 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc3 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc4 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ() + 1);
                                        Location loc5 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ() + 2);
                                        Location loc6 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc7 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc8 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ() - 1);
                                        Location loc9 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ() - 2);
                                        Location loc10 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 50, locs.get(i).getZ() + 1);
                                        Location loc11 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 50, locs.get(i).getZ() - 1);
                                        Location loc12 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 50, locs.get(i).getZ() - 1);
                                        Location loc13 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 50, locs.get(i).getZ() + 1);
                                        Location loc14 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 50, locs.get(i).getZ() + 2);
                                        Location loc15 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 50, locs.get(i).getZ() - 2);
                                        Location loc16 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 50, locs.get(i).getZ() - 2);
                                        Location loc17 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 50, locs.get(i).getZ() + 2);
                                        Location loc18 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 50, locs.get(i).getZ() + 2);
                                        Location loc19 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 50, locs.get(i).getZ() + 2);
                                        Location loc20 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 50, locs.get(i).getZ() - 2);
                                        Location loc21 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 50, locs.get(i).getZ() - 2);
                                        Location loc22 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 50, locs.get(i).getZ() - 1);
                                        Location loc23 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 50, locs.get(i).getZ() + 1);
                                        Location loc24 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 50, locs.get(i).getZ() + 1);
                                        Location loc25 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 50, locs.get(i).getZ() - 1);
                                        Location loc26 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 51, locs.get(i).getZ() - 2);
                                        Location loc27 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 51, locs.get(i).getZ() - 2);
                                        Location loc28 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 51, locs.get(i).getZ() + 2);
                                        Location loc29 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 51, locs.get(i).getZ() + 2);
                                        Location loc30 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 51, locs.get(i).getZ() + 2);
                                        Location loc31 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 51, locs.get(i).getZ() - 2);
                                        Location loc32 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 51, locs.get(i).getZ() - 2);
                                        Location loc33 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 51, locs.get(i).getZ() - 1);
                                        Location loc34 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 51, locs.get(i).getZ() + 1);
                                        Location loc35 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 51, locs.get(i).getZ() + 1);
                                        Location loc36 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 51, locs.get(i).getZ() - 1);
                                        Location loc37 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 51, locs.get(i).getZ() + 2);
                                        Location loc38 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 51, locs.get(i).getZ() - 0);
                                        Location loc39 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 51, locs.get(i).getZ() + 0);
                                        Location loc40 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 0, locs.get(i).getY() + 51, locs.get(i).getZ() - 2);
                                        Location loc41 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 0, locs.get(i).getY() + 51, locs.get(i).getZ() + 2);
                                        Location loc42 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 52, locs.get(i).getZ() - 2);
                                        Location loc43 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 52, locs.get(i).getZ() - 2);
                                        Location loc44 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 52, locs.get(i).getZ() + 2);
                                        Location loc45 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 52, locs.get(i).getZ() + 2);
                                        Location loc46 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 52, locs.get(i).getZ() + 2);
                                        Location loc47 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 52, locs.get(i).getZ() - 2);
                                        Location loc48 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 52, locs.get(i).getZ() - 2);
                                        Location loc49 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 52, locs.get(i).getZ() - 1);
                                        Location loc50 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 52, locs.get(i).getZ() + 1);
                                        Location loc51 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 52, locs.get(i).getZ() + 1);
                                        Location loc52 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 52, locs.get(i).getZ() - 1);
                                        Location loc53 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 52, locs.get(i).getZ() + 2);
                                        Location loc54 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 52, locs.get(i).getZ() - 0);
                                        Location loc55 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 52, locs.get(i).getZ() + 0);
                                        Location loc56 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 0, locs.get(i).getY() + 52, locs.get(i).getZ() - 2);
                                        Location loc57 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 0, locs.get(i).getY() + 52, locs.get(i).getZ() + 2);
                                        Location loc58 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 53, locs.get(i).getZ() - 2);
                                        Location loc59 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 53, locs.get(i).getZ() - 2);
                                        Location loc60 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 53, locs.get(i).getZ() + 2);
                                        Location loc61 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 53, locs.get(i).getZ() + 2);
                                        Location loc62 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 53, locs.get(i).getZ() + 2);
                                        Location loc63 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 53, locs.get(i).getZ() - 2);
                                        Location loc64 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 53, locs.get(i).getZ() - 2);
                                        Location loc65 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 53, locs.get(i).getZ() - 1);
                                        Location loc66 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 53, locs.get(i).getZ() + 1);
                                        Location loc67 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 53, locs.get(i).getZ() + 1);
                                        Location loc68 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 53, locs.get(i).getZ() - 1);
                                        Location loc69 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 53, locs.get(i).getZ() + 2);
                                        Location loc70 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 2, locs.get(i).getY() + 53, locs.get(i).getZ() - 0);
                                        Location loc71 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 2, locs.get(i).getY() + 53, locs.get(i).getZ() + 0);
                                        Location loc72 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 0, locs.get(i).getY() + 53, locs.get(i).getZ() - 2);
                                        Location loc73 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 0, locs.get(i).getY() + 53, locs.get(i).getZ() + 2);


                                        loc1.getBlock().setType(Material.GLASS);
                                        loc2.getBlock().setType(Material.GLASS);
                                        loc3.getBlock().setType(Material.GLASS);
                                        loc4.getBlock().setType(Material.GLASS);
                                        loc5.getBlock().setType(Material.GLASS);
                                        loc6.getBlock().setType(Material.GLASS);
                                        loc7.getBlock().setType(Material.GLASS);
                                        loc8.getBlock().setType(Material.GLASS);
                                        loc9.getBlock().setType(Material.GLASS);
                                        loc10.getBlock().setType(Material.GLASS);
                                        loc11.getBlock().setType(Material.GLASS);
                                        loc12.getBlock().setType(Material.GLASS);
                                        loc13.getBlock().setType(Material.GLASS);
                                        loc14.getBlock().setType(Material.GLASS);
                                        loc15.getBlock().setType(Material.GLASS);
                                        loc16.getBlock().setType(Material.GLASS);
                                        loc17.getBlock().setType(Material.GLASS);
                                        loc18.getBlock().setType(Material.GLASS);
                                        loc19.getBlock().setType(Material.GLASS);
                                        loc20.getBlock().setType(Material.GLASS);
                                        loc21.getBlock().setType(Material.GLASS);
                                        loc22.getBlock().setType(Material.GLASS);
                                        loc23.getBlock().setType(Material.GLASS);
                                        loc24.getBlock().setType(Material.GLASS);
                                        loc25.getBlock().setType(Material.GLASS);
                                        loc26.getBlock().setType(Material.GLASS);
                                        loc27.getBlock().setType(Material.GLASS);
                                        loc28.getBlock().setType(Material.GLASS);
                                        loc29.getBlock().setType(Material.GLASS);
                                        loc30.getBlock().setType(Material.GLASS);
                                        loc31.getBlock().setType(Material.GLASS);
                                        loc32.getBlock().setType(Material.GLASS);
                                        loc33.getBlock().setType(Material.GLASS);
                                        loc34.getBlock().setType(Material.GLASS);
                                        loc35.getBlock().setType(Material.GLASS);
                                        loc36.getBlock().setType(Material.GLASS);
                                        loc37.getBlock().setType(Material.GLASS);
                                        loc38.getBlock().setType(Material.GLASS);
                                        loc39.getBlock().setType(Material.GLASS);
                                        loc40.getBlock().setType(Material.GLASS);
                                        loc41.getBlock().setType(Material.GLASS);
                                        loc42.getBlock().setType(Material.GLASS);
                                        loc43.getBlock().setType(Material.GLASS);
                                        loc44.getBlock().setType(Material.GLASS);
                                        loc45.getBlock().setType(Material.GLASS);
                                        loc46.getBlock().setType(Material.GLASS);
                                        loc47.getBlock().setType(Material.GLASS);
                                        loc48.getBlock().setType(Material.GLASS);
                                        loc49.getBlock().setType(Material.GLASS);
                                        loc50.getBlock().setType(Material.GLASS);
                                        loc51.getBlock().setType(Material.GLASS);
                                        loc52.getBlock().setType(Material.GLASS);
                                        loc53.getBlock().setType(Material.GLASS);
                                        loc54.getBlock().setType(Material.GLASS);
                                        loc55.getBlock().setType(Material.GLASS);
                                        loc56.getBlock().setType(Material.GLASS);
                                        loc57.getBlock().setType(Material.GLASS);
                                        loc58.getBlock().setType(Material.GLASS);
                                        loc59.getBlock().setType(Material.GLASS);
                                        loc60.getBlock().setType(Material.GLASS);
                                        loc61.getBlock().setType(Material.GLASS);
                                        loc62.getBlock().setType(Material.GLASS);
                                        loc63.getBlock().setType(Material.GLASS);
                                        loc64.getBlock().setType(Material.GLASS);
                                        loc65.getBlock().setType(Material.GLASS);
                                        loc66.getBlock().setType(Material.GLASS);
                                        loc67.getBlock().setType(Material.GLASS);
                                        loc68.getBlock().setType(Material.GLASS);
                                        loc69.getBlock().setType(Material.GLASS);
                                        loc70.getBlock().setType(Material.GLASS);
                                        loc71.getBlock().setType(Material.GLASS);
                                        loc72.getBlock().setType(Material.GLASS);
                                        loc73.getBlock().setType(Material.GLASS);
                                    }
                                    if (Settings.getData().get("team.mode").equals("FFA")) {
                                        Location loc1 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 50, locs.get(i).getZ());
                                        Location loc2 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 51, locs.get(i).getZ());
                                        Location loc3 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 51, locs.get(i).getZ() + 1);
                                        Location loc4 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 51, locs.get(i).getZ());
                                        Location loc5 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 51, locs.get(i).getZ() - 1);
                                        Location loc6 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 52, locs.get(i).getZ() - 1);
                                        Location loc7 = new Location(locs.get(i).getWorld(), locs.get(i).getX() + 1, locs.get(i).getY() + 52, locs.get(i).getZ());
                                        Location loc8 = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() + 52, locs.get(i).getZ() + 1);
                                        Location loc9 = new Location(locs.get(i).getWorld(), locs.get(i).getX() - 1, locs.get(i).getY() + 52, locs.get(i).getZ());
                                        loc1.getBlock().setType(Material.GLASS);
                                        loc2.getBlock().setType(Material.GLASS);
                                        loc3.getBlock().setType(Material.GLASS);
                                        loc4.getBlock().setType(Material.GLASS);
                                        loc5.getBlock().setType(Material.GLASS);
                                        loc6.getBlock().setType(Material.GLASS);
                                        loc7.getBlock().setType(Material.GLASS);
                                        loc8.getBlock().setType(Material.GLASS);
                                        loc9.getBlock().setType(Material.GLASS);
                                    }
                                } else {
                                    locs.get(i).getChunk().load(true);
                                }
                                i++;
                                for(Player online : Bukkit.getOnlinePlayers()) {
                                    GameUtils.sendAction(online, PrefixUtils.SCATTER + "Loaded §a" + i + "§8/§a" + locs.size());
                                }
                            } else {
                                cancel();
                                locs.clear();

                                new BukkitRunnable() {
                                    int i = 0;

                                    public void run() {
                                        if (i < names.size()) {
                                            final Player scatter = Bukkit.getServer().getPlayer(names.get(i));

                                            if (scatter == null) {
                                            } else {
                                                scatter.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
                                                scatter.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1726272000, 6));
                                                scatter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1726272000, 10));
                                                scatter.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1726272000, 2));
                                                for(Player online : Bukkit.getOnlinePlayers()) {
                                                    online.showPlayer(scatter);
                                                }
                                                Location loc = scatterLocs.get(names.get(i));
                                                double y = LocationUtils.highestTeleportableYAtLocation(loc);
                                                loc.setY(y + 1);
                                                scatter.teleport(loc);
                                                scatterLocs.remove(names.get(i));
                                            }
                                            i++;
                                            for(Player online : Bukkit.getOnlinePlayers()) {
                                                GameUtils.sendAction(online, PrefixUtils.SCATTER + "Scattered §a" + i + "§8/§a" + names.size());
                                            }
                                        } else {
                                            isReady = true;
                                            names.clear();
                                            for(Player online : Bukkit.getOnlinePlayers()) {
                                                User userO = User.get(online);
                                                if(userO.getMode() == User.Mode.HOST || userO.getMode() == User.Mode.SPECTATOR) {
                                                    online.sendMessage(PrefixUtils.STAFF + "The scatter is finished. §8[§c§o/uhc next§8]");
                                                }
                                                GameUtils.sendAction(online, PrefixUtils.SCATTER + "The §ascatter §fhas finished");
                                            }
                                            cancel();
                                        }
                                    }
                                }.runTaskTimer(Main.plugin, 40, 3);
                            }
                        }
                    }.runTaskTimer(Main.plugin, 5, 5);
                }
            }.runTaskLater(Main.plugin, 60);
        } else {
            final Player target = Bukkit.getPlayer(args[2]);

            if (target == null) {
                sender.sendMessage("§cError: That player is not online.");
                return true;
            }

            Bukkit.getServer().broadcastMessage(PrefixUtils.SCATTER + "Scattering §a" + target.getName() + "§f.");

            new BukkitRunnable() {
                public void run() {
                    if (teams) {
                        if (target.getScoreboard().getTeam(target.getName()) == null) {
                            List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, 1);
                            scatterLocs.put(target.getName(), loc.get(0));
                            return;
                        }

                        Team tem = target.getScoreboard().getTeam(target.getName());

                        for (String tm : tem.getEntries()) {
                            Player temmate = Bukkit.getServer().getPlayer(tm);

                            if (temmate != null) {
                                scatterLocs.put(target.getName(), temmate.getLocation());
                                break;
                            }
                        }
                    } else {
                        List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, 1);
                        scatterLocs.put(target.getName(), loc.get(0));
                    }
                }
            }.runTaskLater(Main.plugin, 30);

            new BukkitRunnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    if (!target.isOnline()) {
                        Bukkit.getServer().broadcastMessage(PrefixUtils.SCATTER + "§a" + target.getName() + " §fis offline, scheduled.");
                    } else {
                        if (State.isState(State.SCATTERING)) {
                            target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1726272000, 128));
                            target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1726272000, 6));
                            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1726272000, 10));
                            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1726272000, 6));
                            target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1726272000, 2));
                        }

                        target.setWhitelisted(true);
                        target.teleport(scatterLocs.get(target.getName()));
                        Location locje = new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY() - 1, target.getLocation().getZ());
                        locje.getBlock().setType(Material.getMaterial(44));
                        scatterLocs.remove(target.getName());
                    }
                }
            }.runTaskLater(Main.plugin, 60);
        }
        return true;
    }
}
