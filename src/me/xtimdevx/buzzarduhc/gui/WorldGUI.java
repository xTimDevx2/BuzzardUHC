package me.xtimdevx.buzzarduhc.gui;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by xTimDevx on 10/06/2017.
 */
public class WorldGUI implements Listener{

    public static Inventory openWorld(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§7World:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack nether = new ItemStack (Material.NETHER_STALK);
        ItemMeta netherMeta = nether.getItemMeta();
        netherMeta.setDisplayName("§8» §b§lNether §8«");
        lore.add(" ");
        if(Settings.getData().get("world.nether") == null) {
            Settings.getData().set("world.nether", false);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fEnable / Disable the nether world.");
        lore.add(" ");
        lore.add("§8» §fStatus: §b" + (Settings.getData().get("world.nether").equals(false) ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Disabled)");
        lore.add(" ");
        netherMeta.setLore(lore);
        nether.setItemMeta(netherMeta);
        inv.setItem(1, nether);
        lore.clear();

        ItemStack potions = new ItemStack (Material.POTION, 1, (short) 16453);
        ItemMeta potionsMeta = potions.getItemMeta();
        potionsMeta.setDisplayName("§8» §b§lPotions §8«");
        lore.add(" ");
        lore.add("§8» §fPotion settings.");
        lore.add(" ");
        lore.add("§8» §bStrenght §8«");
        lore.add("      §8» §fEnabled: §aYes");
        lore.add("      §8» §fTier II: §aEnabled");
        lore.add("      §8» §fSplash: §aEnabled");
        lore.add("§8» §bSpeed §8«");
        lore.add("      §8» §fEnabled: §aYes");
        lore.add("      §8» §fTier II: §aEnabled");
        lore.add("      §8» §fSplash: §aEnabled");
        lore.add(" ");
        potionsMeta.setLore(lore);
        potions.setItemMeta(potionsMeta);
        inv.setItem(3, potions);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onDrop(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        User user = User.get(player);
        if (event.getInventory().getName().equalsIgnoreCase("§7World:")) {
            event.setCancelled(true);
            if (event.getSlot() == 1) {
                if(Settings.getData().get("world.nether") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bNETHER §fhas been §aENABLED");
                    }
                    Settings.getData().set("world.nether", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bNETHER §fhas been §bDISABLED");
                    }
                    Settings.getData().set("world.nether", false);
                    Settings.getInstance().saveData();
                }
                openWorld(player);
            }
            if(event.getSlot() == 3) {
                PotionsGUI.openTeaming(player);
            }
            if(event.getSlot() == 8) {
                player.closeInventory();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Creating the world §bUHC§f.");
                        }
                        player.performCommand("mv create UHC normal -g TerrainControl");
                    }
                }, 20L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Finished creating the world §bUHC§f.");
                        }
                    }
                }, 40L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if(Settings.getData().get("world.nether") == true) {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "Creating the world §bUHC_nether§f. §8(§bNether§8)");
                            }
                            player.performCommand("mv create UHC_nether nether");
                        }else {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "World creation finished!");
                                online.sendMessage(PrefixUtils.PREFIX + "Created worlds: ");
                                online.sendMessage("§8» §f- UHC");
                            }
                        }
                    }
                }, 60L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if(Settings.getData().get("world.nether") == true) {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                online.sendMessage(PrefixUtils.PREFIX + "World creation finished!");
                                online.sendMessage(PrefixUtils.PREFIX + "Created worlds: ");
                                online.sendMessage("§8» §f- UHC");
                                online.sendMessage("§8» §f- UHC_nether");
                            }
                        }
                    }
                }, 80L);
            }
        }
    }
}
