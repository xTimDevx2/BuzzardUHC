package me.xtimdevx.buzzarduhc.events;

import com.avaje.ebeaninternal.server.lib.sql.Prefix;
import com.google.common.collect.Maps;
import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.combatlog.Combatlogger;
import me.xtimdevx.buzzarduhc.commands.UHCCommand;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xTimDevx on 21/05/2017.
 */
public class DeathEvents implements Listener {

    public static Map<Player , ItemStack[]> Ditems = Maps.newHashMap();
    public static Map<Player, ItemStack[]> Darmor = Maps.newHashMap();
    public static Map<Player , Location> Dlocation = Maps.newHashMap();

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!ScenarioManager.getInstance().getScenario("teaminventory").isEnabled()) {
            return;
        }
        Team team = ScoreBoard.getInstance().board.getPlayerTeam(player);

        if (team == null) {
            return;
        }

        if (team.getSize() == 1 && Main.getTeamInvs().containsKey(team)) {
            Inventory inv = Main.getTeamInvs().get(team);

            for (ItemStack item : inv.getContents()) {
                if (item == null) {
                    continue;
                }

                if (item.getType() == Material.AIR) {
                    return;
                }

                event.getDrops().add(item);
            }

            Main.getTeamInvs().remove(team);
        }

        team.removePlayer(player);
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();

        ScoreBoard board = ScoreBoard.getInstance();

        player.setWhitelisted(false);

        player.getWorld().strikeLightningEffect(player.getLocation());

        ItemStack[] content = event.getEntity().getInventory().getContents();
        ItemStack[] acontent = event.getEntity().getInventory().getArmorContents();
        Location location = event.getEntity().getLocation();
        Ditems.put(event.getEntity(), content);
        Darmor.put(event.getEntity(), acontent);
        Dlocation.put(event.getEntity(), location);

        if (player.getWorld().getName().equals("UHC")) {
            new BukkitRunnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    if (ScenarioManager.getInstance().getScenario("timebomb").isEnabled()) {
                        return;
                    }
                    player.getLocation().getBlock().setType(Material.SPRUCE_FENCE);
                    player.getLocation().add(0, 1, 0).getBlock().setType(Material.SKULL);

                    Skull skull;

                    try {
                        skull = (Skull) player.getLocation().add(0, 1, 0).getBlock().getState();
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("Could not place player skull.");
                        return;
                    }

                    skull.setSkullType(SkullType.PLAYER);
                    skull.setOwner(player.getName());
                    skull.setRotation(BlockFace.EAST_NORTH_EAST);
                    skull.update();

                    Block b = player.getLocation().add(0, 1, 0).getBlock();
                    b.setData((byte) 0x1, true);
                }
            }.runTaskLater(Main.plugin, 1);
        }
    }
    @EventHandler
    public void onPlayerDeaths(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if(!State.isState(State.INGAME)) {
            return;
        }
        if(!UHCCommand.ingame.containsKey(player.getName())) {
            return;
        }
        Combatlogger.getInstance().removeCombat(player);
        final ScoreBoard board = ScoreBoard.getInstance();
        User user = User.get(player);
        user.increaseStat(User.Stat.DEATHS);
        UHCCommand.ingame.remove(player.getName());
        if(killer instanceof Player) {
            Player k = (Player) killer;
            User kuser = User.get(k);
            User puser = User.get(player);
            kuser.increaseStat(User.Stat.GAMEKILLS);
            event.setDeathMessage("§8[§bDEATHS§8]: §f" + event.getDeathMessage().replace(player.getName(), "§b" + player.getName() + "§8[§b§o" + puser.getStat(User.Stat.GAMEKILLS) + "§8]§f").replace(k.getName(), "§b" + k.getName() + "§8[§b§o" + kuser.getStat(User.Stat.GAMEKILLS) + "§8]§f"));
            puser.setStat(User.Stat.GAMEKILLS, 0);
            if(board.board.getEntryTeam(killer.getName()) != null) {
                board.resetScore("§8» " + board.board.getEntryTeam(player.getName()).getPrefix() + player.getName());
                board.setScore("§8» " + board.board.getEntryTeam(killer.getName()).getPrefix() + killer.getName(), board.getScore("§8» " + board.board.getEntryTeam(killer.getName()).getPrefix() + killer.getName()) + 1);
            }else {
                board.resetScore("§8» §f" + player.getName());
                board.setScore("§8» §f" + killer.getName(), board.getScore("§8» §f" + killer.getName()) + 1);
            }
        }else {
            User puser = User.get(player);
            event.setDeathMessage("§8[§bDEATHS§8]: §f" + event.getDeathMessage().replace(player.getName(), "§b" + player.getName() + "§8[§b§o" + puser.getStat(User.Stat.GAMEKILLS) + "§8]§f"));
            puser.setStat(User.Stat.GAMEKILLS, 0);
            board.setScore("§8» §b§oPvE", board.getScore("§8» §b§oPvE") + 1);
            board.resetScore("§8» " + board.board.getEntryTeam(player.getName()).getPrefix() + player.getName());
            board.resetScore("§8» §f" + player.getName());
        }
        board.setScore("§8» §b§oPlayers", UHCCommand.ingame.size());
        if(!GameUtils.getTeamMode().equalsIgnoreCase("team")) {
            if(UHCCommand.ingame.size() == 1) {
                Set<String> names = UHCCommand.ingame.keySet();
                for (String name : names) {
                    final Player winner = Bukkit.getPlayer(UHCCommand.ingame.get(name));
                    User uwinner = User.get(winner);
                    Bukkit.broadcastMessage("§c§lUHC §8» §c" + winner.getName() + " §fwon the UHC!");
                    uwinner.setRank(User.Rank.WINNER, null);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final User user = User.get(player);
        ScoreBoard board = ScoreBoard.getInstance();

        if (!State.isState(State.INGAME)) {
            return;
        }

        double x0 = -545.5;
        double y0 = 15;
        double z0 = -554.5;
        org.bukkit.Location loc0 = new org.bukkit.Location(Bukkit.getWorld("HUB"), x0, y0, z0);
        loc0.setYaw(90F);
        loc0.setPitch(0F);
        event.setRespawnLocation(loc0);
        board.resetScore(player.getName());
        player.setMaxHealth(20);

        player.sendMessage(PrefixUtils.PREFIX + "Thanks for playing our game, this game was hosted by §b" + Settings.getData().get("game.host") + "§f.");
        player.sendMessage(PrefixUtils.PREFIX + "Follow us on twitter to know when our next games are: §b@ScyleNetwork§f.");

        if (!player.hasPermission("uhc.spectate")) {
            player.sendMessage(PrefixUtils.PREFIX + "You have §b30 §fseconds before you get kicked.");
            player.sendMessage(PrefixUtils.PREFIX + "Please do not spam, rage, spoil or be a bad sportsman.");
            new BukkitRunnable() {
                public void run() {
                    user.setStat(User.Stat.GAMEKILLS, 0);
                    player.kickPlayer(PrefixUtils.PREFIX + "Thanks for playing on Scyle Network.");
                }
            }.runTaskLater(Main.plugin, 600);
            return;
        }

        player.sendMessage(PrefixUtils.PREFIX + "You will be put into spectator mode in §b10 §fseconds.");
        player.sendMessage(PrefixUtils.PREFIX + "Please do not spam, rage, spoil or be a bad sportsman.");

        new BukkitRunnable() {
            public void run() {
                player.teleport(new Location(Bukkit.getWorld("UHC"), 0, 100, 0));
                user.setStat(User.Stat.GAMEKILLS, 0);
            }
        }.runTaskLater(Main.plugin, 200);

        new BukkitRunnable() {
            public void run() {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }.runTaskLater(Main.plugin, 220);
    }
}
