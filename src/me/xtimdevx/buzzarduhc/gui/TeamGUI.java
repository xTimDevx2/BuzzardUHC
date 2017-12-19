package me.xtimdevx.buzzarduhc.gui;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class TeamGUI implements Listener{

    public static Inventory openTeaming(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§7Teaming:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack teammode = new ItemStack (Material.IRON_SWORD);
        ItemMeta teammodeMeta = teammode.getItemMeta();
        teammodeMeta.setDisplayName("§8» §b§lMode §8«");
        lore.add(" ");
        if(Settings.getData().get("team.mode") == null) {
            Settings.getData().set("team.mode", "FFA");
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fChange the teaming mode.");
        lore.add(" ");
        lore.add("§8» §fCurrent: §b" + GameUtils.getTeamMode());
        lore.add(" ");
        lore.add("§7§o(Default: FFA)");
        lore.add(" ");
        teammodeMeta.setLore(lore);
        teammode.setItemMeta(teammodeMeta);
        inv.setItem(2, teammode);
        lore.clear();

        ItemStack teamlimit = new ItemStack (Material.BOOK);
        ItemMeta teamlimitMeta = teamlimit.getItemMeta();
        teamlimitMeta.setDisplayName("§8» §b§lLimit §8«");
        lore.add(" ");
        if(Settings.getData().get("team.limit") == null) {
            Settings.getData().set("team.limit", 2);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fChange the teaming limit.");
        lore.add(" ");
        lore.add("§8» §fCurrent: §b" + Settings.getData().getInt("team.limit"));
        lore.add(" ");
        lore.add("§7§o(Default: 2)");
        lore.add(" ");
        teamlimitMeta.setLore(lore);
        teamlimit.setItemMeta(teamlimitMeta);
        inv.setItem(4, teamlimit);
        lore.clear();

        ItemStack crossteaming = new ItemStack (Material.IRON_HELMET);
        ItemMeta crossteamingMeta = crossteaming.getItemMeta();
        crossteamingMeta.setDisplayName("§8» §b§lCrossteaming §8«");
        lore.add(" ");
        if(Settings.getData().get("team.limit") == null) {
            Settings.getData().set("team.limit", 2);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fToggle crossteaming.");
        lore.add(" ");
        lore.add("§8» §fStatus: §b" + (Settings.getData().get("team.crossteaming") != true ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        crossteamingMeta.setLore(lore);
        crossteaming.setItemMeta(crossteamingMeta);
        inv.setItem(6, crossteaming);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    public static BukkitRunnable teamTask;
    public static ArrayList<Player> teamList = new ArrayList<>();


    @EventHandler
    public void onDrop(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        User user = User.get(player);
        if (event.getInventory().getName().equalsIgnoreCase("§7Teaming:")) {
            event.setCancelled(true);
            if (event.getSlot() == 2) {
                TeamModeGUI.openTeaming(player);
            }
            if (event.getSlot() == 4) {
                teamList.add(player);
                player.closeInventory();
                teamTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(teamList.contains(player)) {
                            player.sendMessage(PrefixUtils.PREFIX + "Please type the new §bteam limit §fin §bchat§f!");
                            player.sendMessage("§8» §fType §b'cancel' §fto cancel.");
                            player.sendTitle("§b§lUHC", "§fPlease the the §bteam limit §fin §bchat§f.");
                        }else {
                            teamTask.cancel();
                        }
                    }
                };
                teamTask.runTaskTimer(Main.plugin, 0, 80);
            }
            if (event.getSlot() == 6) {
                if(Settings.getData().get("team.crossteaming") != true) {
                    player.closeInventory();
                    Settings.getData().set("team.crossteaming", true);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bCROSSTEAMING §fis now §aENABLED§f.");
                    }
                }else {
                    player.closeInventory();
                    Settings.getData().set("team.crossteaming", false);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bCROSSTEAMING §fis now §bDISABLED§f.");
                    }
                }
            }
        }
        if (event.getInventory().getName().equalsIgnoreCase("§7Teaming mode:")) {
            event.setCancelled(true);
            if (event.getSlot() == 3) {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The team mode is now §bFFA§f.");
                }
                Settings.getData().set("team.mode", "FFA");
                Settings.getInstance().saveData();
                player.closeInventory();
            }
            if (event.getSlot() == 5) {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The team mode is now §bTEAM§f.");
                }
                Settings.getData().set("team.mode", "Team");
                Settings.getInstance().saveData();
                player.closeInventory();
            }
            if (event.getSlot() == 7) {
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(PrefixUtils.PREFIX + "The team mode is now §bRANDOM§f.");
                }
                Settings.getData().set("team.mode", "Random");
                Settings.getInstance().saveData();
                player.closeInventory();
            }
        }
    }
}
