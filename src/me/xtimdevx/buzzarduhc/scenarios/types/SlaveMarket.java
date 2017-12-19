package me.xtimdevx.buzzarduhc.scenarios.types;

import com.sun.org.apache.regexp.internal.RE;
import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 20/10/2017.
 */
public class SlaveMarket extends Scenario implements Listener, CommandExecutor {

    private boolean bidProgressing = false;
    private int bidTime = 0, biggestBid = -1;
    private String bidWinner = null;
    private ArrayList<String> traders = new ArrayList<String>();

    public SlaveMarket() {
        super("SlaveMarket", "8 slave owners are chosen and they get 30 diamonds to bid on players as they choose. Any spare diamonds will be kept by the slaveowner.");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    public String prefix() {
        return "§aSlavemarket §8» §f";
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String lable, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("slavereset")) {
            if (!ScenarioManager.getInstance().getScenario("slavemarket").isEnabled()) {
                sender.sendMessage("§cSlave market is not enabled.");
                return true;
            }
            if (!sender.hasPermission("uhc.staff")) {
                sender.sendMessage("§cYou can't use that command.");
                return true;
            }
            bidProgressing = false;
            bidWinner = null;
            traders.clear();
            biggestBid = -1;
            bidTime = 0;

            for (Team teams : Teams.getInstance().getTeamsWithPlayers()) {
                for (String entry : teams.getEntries()) {
                    teams.removeEntry(entry);
                }
            }
            Game.broadcast(prefix() + "§aSlaveMarket §fhas been §areset§f.");
        }
        if (cmd.getName().equalsIgnoreCase("slaveowner")) {
            if (!sender.hasPermission("uhc.staff")) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if (!ScenarioManager.getInstance().getScenario("slavemarket").isEnabled()) {
                sender.sendMessage("§cSlave market is not enabled.");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§cUsage: /slaveowner <add|remove|list> [player] [amountofdias]");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length >= 2) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "That player is not online.");
                        return true;
                    }

                    traders.add(target.getName());
                    Team t = Teams.getInstance().findAvailableTeam();

                    if (t == null) {
                        sender.sendMessage(ChatColor.RED + "Could not find any open teams.");
                        return true;
                    }

                    t.addEntry(target.getName());
                    Game.broadcast(prefix() + ChatColor.GREEN + target.getName() + " §fis now a slave owner.");

                    if (args.length >= 3) {
                        int i;

                        try {
                            i = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.RED + "Invaild number.");
                            return true;
                        }

                        target.getInventory().addItem(new ItemStack(Material.DIAMOND, i));
                    } else {
                        target.getInventory().addItem(new ItemStack(Material.DIAMOND, 30));
                    }
                } else {
                    sender.sendMessage("§cUsage: /slaveowner <add|remove|list> [player] [amountofdias]");
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length >= 2) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "That player is not online.");
                        return true;
                    }

                    traders.remove(args[1]);
                    Team t = target.getScoreboard().getEntryTeam(target.getName());

                    if (t != null) {
                        t.removeEntry(target.getName());
                    }

                    Game.broadcast(prefix() + ChatColor.GREEN + args[1] + " §fis no longer a slave owner!");
                    target.getInventory().clear();
                } else {
                    sender.sendMessage("§cUsage: /slaveowner <add|remove|list> [player] [amountofdias]");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                StringBuilder s = new StringBuilder();

                for (String l : traders) {
                    if (s.length() > 0) {
                        s.append("§8, §a");
                    }

                    s.append(ChatColor.GREEN + l);
                }

                sender.sendMessage(prefix() + "Current slaves: " + s.toString().trim());
            } else {
                sender.sendMessage("§cUsage: /slaveowner <add|remove|list> [player] [amountofdias]");
            }
        }
        if (cmd.getName().equalsIgnoreCase("startbid")) {
            if (!ScenarioManager.getInstance().getScenario("slavemarket").isEnabled()) {
                sender.sendMessage("§cSlave market is not enabled.");
                return true;
            }
            if (!sender.hasPermission("uhc.staff")) {
                sender.sendMessage(PrefixUtils.NO_PERM_MSG);
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /startbid <player> <time>");
                return true;
            }
            Game.broadcast(prefix() + "The bidding of player §a" + args[0] + " §fis about to start.");

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.playSound(online.getLocation(), Sound.FIREWORK_LAUNCH, 1, 0);
            }
            Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    Game.broadcast(prefix() + "Bidding starts in §a3§f.");

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 1);
                    }
                }
            }, 20);
            Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    Game.broadcast(prefix() + "Bidding starts in §a2§f.");

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 1);
                    }
                }
            }, 40);
            Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    Game.broadcast(prefix() + "Bidding starts in §a1§f.");

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 1);
                    }
                }
            }, 60);
            Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    int i;
                    try {
                        i = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage("§cInvalid number.");
                        return;
                    }
                    Game.broadcast(prefix() + "The bidding of player §a" + args[0] + "§f has started.");
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 0);
                    }

                    bidProgressing = true;
                    bidTime = i;
                    new BukkitRunnable() {
                        public void run() {
                            bidTime--;
                            if (bidTime == 0) {
                                cancel();
                                bidProgressing = false;
                                if (bidWinner != null) {
                                    Game.broadcast(prefix() + "None of the slave traders bid on §a" + args[0] + "§f.");
                                    return;
                                }
                                bidWinner = sender.getName();
                                Player target = Bukkit.getPlayer(bidWinner);
                                if (target == null) {
                                    Game.broadcast(prefix() + "Bid winner is offline...");
                                    return;
                                }
                                Game.broadcast(prefix() + ChatColor.GREEN + bidWinner + "§f has won the bidding on §a" + args[0] + "§f for §a" + biggestBid + "§f diamonds.");

                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    online.playSound(online.getLocation(), Sound.FIREWORK_TWINKLE, 1, 1);
                                }
                                for (ItemStack item : target.getInventory().getContents()) {
                                    if (item != null && item.getType() == Material.DIAMOND) {
                                        if (item.getAmount() > biggestBid) {
                                            item.setAmount(item.getAmount() - biggestBid);
                                        } else {
                                            target.getInventory().remove(item);
                                        }
                                        break;
                                    }
                                }

                                biggestBid = -1;
                                Team t = target.getScoreboard().getEntryTeam(target.getName());

                                if (t == null) {
                                    sender.sendMessage(ChatColor.RED + "Could not join team.");
                                } else {
                                    t.addEntry(args[0]);
                                }
                            }
                            if (bidTime < 6) {
                                Game.broadcast(prefix() + "Bidding ends in §a" + bidTime + "§f.");
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    online.playSound(online.getLocation(), Sound.NOTE_PLING, 1, 1);
                                }
                            }
                        }
                    }.runTaskTimer(Main.plugin, 0, 20);
                }
            }, 80);
        }
        if(cmd.getName().equalsIgnoreCase("bid")) {
            if(!(sender instanceof  Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can bid on players.");
                return true;
            }
            if (!ScenarioManager.getInstance().getScenario("slavemarket").isEnabled()) {
                sender.sendMessage("§cSlave market is not enabled.");
                return true;
            }
            Player player = (Player) sender;
            if(traders.contains(player.getName())) {
                player.sendMessage(prefix() + "You are not a slave owner.");
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /bid <amount>");
                return true;
            }
            int i;
            try {
                i = Integer.parseInt(args[0]);
            }catch (Exception e) {
                player.sendMessage("§cInvalid nunmber.");
                return true;
            }
            if(i < 0 ){
                player.sendMessage(ChatColor.RED + "Bids cannot be negative.");
                return true;
            }
            if(bidProgressing) {
                player.sendMessage(ChatColor.RED + "There are no slaves being bid on right now.");
                return true;
            }
            if (!GameUtils.hasEnough(player, Material.DIAMOND, i)) {
                player.sendMessage(prefix() + "You can't bid more diamonds than you have.");
                return true;
            }
            if(i < biggestBid) {
                player.sendMessage(prefix() + "Bids must be greater than the previous bid.");
                return true;
            }
            biggestBid = i;
            bidWinner = player.getName();
            if (bidTime < 3) {
                bidTime = bidTime + 3;
            }
            Game.broadcast(prefix() + "§a" + player.getName() + "§f bid §a" + i + "§f diamond(s).");
        }
        return true;
    }
}
