package me.xtimdevx.buzzarduhc.gui;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
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
 * Created by xTimDevx on 29/04/2017.
 */
public class RatesGUI implements Listener{

    public static Inventory openRates(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "§7Rates:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack applerate = new ItemStack (Material.APPLE);
        ItemMeta applerateMeta = applerate.getItemMeta();
        applerateMeta.setDisplayName("§8» §b§lApple §8«");
        lore.add(" ");
        if(Settings.getData().get("rates.apple") == null) {
            Settings.getData().set("rates.apple", "Vanilla");
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fApple drop rates.");
        lore.add(" ");
        if(Settings.getData().get("rates.apple").equals("Vanilla")) {
            lore.add("§8» §fRate: §a0.5% §8(§f§oVanilla§8)");
        }else{
            lore.add("§8» §fRate: §a" + Main.applerate + "%");
        }
        lore.add(" ");
        lore.add("§7§o(Default: 0.5%)");
        lore.add(" ");
        applerateMeta.setLore(lore);
        applerate.setItemMeta(applerateMeta);
        inv.setItem(10, applerate);
        lore.clear();

        ItemStack flintrate = new ItemStack (Material.FLINT);
        ItemMeta flintrateMeta = flintrate.getItemMeta();
        flintrateMeta.setDisplayName("§8» §b§lFlint §8«");
        lore.add(" ");
        if(Settings.getData().get("rates.flint") == null) {
            Settings.getData().set("rates.flint", "Vanilla");
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fFlint drop rates.");
        lore.add(" ");
        if(Settings.getData().get("rates.flint").equals("Vanilla")) {
            lore.add("§8» §fRate: §a20% §8(§f§oVanilla§8)");
        }else{
            lore.add("§8» §fRate: §a" + Main.flintrate + "%");
        }
        lore.add(" ");
        lore.add("§7§o(Default: 20%)");
        lore.add(" ");
        flintrateMeta.setLore(lore);
        flintrate.setItemMeta(flintrateMeta);
        inv.setItem(12, flintrate);
        lore.clear();

        ItemStack shears = new ItemStack (Material.SHEARS);
        ItemMeta shearsMeta = shears.getItemMeta();
        shearsMeta.setDisplayName("§8» §b§lShears §8«");
        lore.add(" ");
        if(Settings.getData().get("rates.shears") == null) {
            Settings.getData().set("rates.shears", false);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fShears info.");
        lore.add(" ");
        if(Settings.getData().get("rates.shears") == true) {
            lore.add("§8» §fStatus: §aEnabled");
            if(Settings.getData().get("rates.apple").equals("Vanilla")) {
                lore.add("§8» §fRate: §a0.5% §8(§f§oVanilla§8)");
            }else{
                lore.add("§8» §fRate: §a" + Main.applerate + "%");
            }
        }else {
            lore.add("§8» §fStatus: §bDisabled");
        }
        lore.add(" ");
        lore.add("§7§o(Default: Disabled)");
        lore.add("§7§o(Default: 0.5%)");
        lore.add(" ");
        shearsMeta.setLore(lore);
        shears.setItemMeta(shearsMeta);
        inv.setItem(14, shears);
        lore.clear();

        ItemStack tree = new ItemStack (Material.SAPLING);
        ItemMeta treeMeta = tree.getItemMeta();
        treeMeta.setDisplayName("§8» §b§lTrees §8«");
        lore.add(" ");
        if(Settings.getData().get("rates.tree") == null) {
            Settings.getData().set("rates.tree", "oak");
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fTree info.");
        lore.add(" ");
        lore.add("§8» §fCurrent: §b" + (Settings.getData().get("rates.tree").equals("oak") ? "Oak and Darkoak" : "All"));
        lore.add(" ");
        lore.add("§7§o(Default: Oak/DarkOak)");
        lore.add(" ");
        treeMeta.setLore(lore);
        tree.setItemMeta(treeMeta);
        inv.setItem(16, tree);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    public static BukkitRunnable appleTask;
    public static ArrayList<Player> appleList = new ArrayList<>();

    public static BukkitRunnable flintTask;
    public static ArrayList<Player> flintList = new ArrayList<>();

    @EventHandler
    public void onDrop(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        User user = User.get(player);
        if (event.getInventory().getName().equalsIgnoreCase("§7Rates:")) {
            event.setCancelled(true);
            if (event.getSlot() == 10) {
                appleList.add(player);
                player.closeInventory();
                appleTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(appleList.contains(player)) {
                            player.sendMessage(PrefixUtils.PREFIX + "Please type the new §bapple rate §fin §bchat§f!");
                            player.sendMessage("§8» §fType §b'cancel' §fto cancel.");
                            player.sendMessage("§8» §fType §b'vanilla' §fto set the rate to 0.5%.");
                            player.sendTitle("§b§lUHC", "§fPlease the the §bapple rate §fin §bchat§f.");
                        }else {
                            appleTask.cancel();
                        }
                    }
                };
                appleTask.runTaskTimer(Main.plugin, 0, 80);
            }
            if (event.getSlot() == 12) {
                flintList.add(player);
                player.closeInventory();
                flintTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(flintList.contains(player)) {
                            player.sendMessage(PrefixUtils.PREFIX + "Please type the new §bflint rate §fin §bchat§f!");
                            player.sendMessage("§8» §fType §b'cancel' §fto cancel.");
                            player.sendMessage("§8» §fType §b'vanilla' §fto set the rate to 20%.");
                            player.sendTitle("§b§lUHC", "§fPlease the the §bflint rate §fin §bchat§f.");
                        }else {
                            flintTask.cancel();
                        }
                    }
                };
                flintTask.runTaskTimer(Main.plugin, 0, 80);
            }
            if (event.getSlot() == 14) {
                player.closeInventory();
                if(Settings.getData().get("rates.shears") != true) {
                    Settings.getData().set("rates.shears", true);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bSHEARS §fhave been §aENABLED");
                    }
                }else{
                    Settings.getData().set("rates.shears", false);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bSHEARS §fhave been §bDISABLED");
                    }
                }
            }
            if (event.getSlot() == 16) {
                player.closeInventory();
                if(Settings.getData().get("rates.tree").equals("all")) {
                    Settings.getData().set("rates.tree", "oak");
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bApples §fwill now drop from §bOAK §fand §bDARKOAK §ftrees.");
                    }
                }else{
                    Settings.getData().set("rates.tree", "all");
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bApples §fwill now drop from §bALL §ftrees.");
                    }
                }
            }
        }
    }
}
