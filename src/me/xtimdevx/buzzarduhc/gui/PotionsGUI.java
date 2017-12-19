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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 15/06/2017.
 */
public class PotionsGUI implements Listener{
    public static Inventory openTeaming(Player player) {
        Inventory inv = Bukkit.createInventory(player, 36, "§7Potions:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack strenghtenabled = new ItemStack (Material.POTION, 1, (short) 8201);
        ItemMeta strenghtenabledMeta = strenghtenabled.getItemMeta();
        strenghtenabledMeta.setDisplayName("§8» §b§lEnabled §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.strenght.enabled") == null) {
            Settings.getData().set("potions.strenght.enabled", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.strenght.enabled") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        strenghtenabledMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        strenghtenabledMeta.setLore(lore);
        strenghtenabled.setItemMeta(strenghtenabledMeta);
        inv.setItem(11, strenghtenabled);
        lore.clear();

        ItemStack strenghttier = new ItemStack (Material.POTION, 1, (short) 8233);
        ItemMeta strenghttierMeta = strenghttier.getItemMeta();
        strenghttierMeta.setDisplayName("§8» §b§lTier II §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.strenght.tier2") == null) {
            Settings.getData().set("potions.strenght.tier2", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.strenght.tier2") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        strenghttierMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        strenghttierMeta.setLore(lore);
        strenghttier.setItemMeta(strenghttierMeta);
        inv.setItem(13, strenghttier);
        lore.clear();

        ItemStack strenghtsplash = new ItemStack (Material.POTION, 1, (short) 16393);
        ItemMeta strenghtsplashMeta = strenghtsplash.getItemMeta();
        strenghtsplashMeta.setDisplayName("§8» §b§lSplash §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.strenght.splash") == null) {
            Settings.getData().set("potions.strenght.splash", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.strenght.splash") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        strenghtsplashMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        strenghtsplashMeta.setLore(lore);
        strenghtsplash.setItemMeta(strenghtsplashMeta);
        inv.setItem(15, strenghtsplash);
        lore.clear();

        ItemStack speedenabled = new ItemStack (Material.POTION, 1, (short) 8194);
        ItemMeta speedenabledMeta = speedenabled.getItemMeta();
        speedenabledMeta.setDisplayName("§8» §b§lEnabled §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.speed.enabled") == null) {
            Settings.getData().set("potions.speed.enabled", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.speed.enabled") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        speedenabledMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        speedenabledMeta.setLore(lore);
        speedenabled.setItemMeta(speedenabledMeta);
        inv.setItem(20, speedenabled);
        lore.clear();

        ItemStack speedtier = new ItemStack (Material.POTION, 1, (short) 8226);
        ItemMeta speedtierMeta = speedtier.getItemMeta();
        speedtierMeta.setDisplayName("§8» §b§lTier II §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.speed.tier2") == null) {
            Settings.getData().set("potions.speed.tier2", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.speed.tier2") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        speedtierMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        speedtierMeta.setLore(lore);
        speedtier.setItemMeta(speedtierMeta);
        inv.setItem(22, speedtier);
        lore.clear();

        ItemStack speedsplash = new ItemStack (Material.POTION, 1, (short) 16386);
        ItemMeta speedsplashMeta = speedsplash.getItemMeta();
        speedsplashMeta.setDisplayName("§8» §b§lSplash §8«");
        lore.add(" ");
        if(Settings.getData().get("potions.speed.splash") == null) {
            Settings.getData().set("potions.speed.splash", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("potions.speed.splash") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        speedsplashMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        speedsplashMeta.setLore(lore);
        speedsplash.setItemMeta(speedsplashMeta);
        inv.setItem(24, speedsplash);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onDrop(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        User user = User.get(player);
        if (event.getInventory().getName().equalsIgnoreCase("§7Potions:")) {
            event.setCancelled(true);
            if(event.getSlot() == 11) {
                player.closeInventory();
                if(Settings.getData().get("potions.strenght.enabled") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §aENABLED§f!");
                    }
                    Settings.getData().set("potions.strenght.enabled", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §bDISABLED§f!");
                    }
                    Settings.getData().set("potions.strenght.enabled", false);
                    Settings.getInstance().saveData();
                }
            }
            if(event.getSlot() == 13) {
                player.closeInventory();
                if(Settings.getData().get("potions.strenght.tier2") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §bTIER2 §8: §aENABLED");
                    }
                    Settings.getData().set("potions.strenght.tier2", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §bTIER2 §8: §bDISABLED§f!");
                    }
                    Settings.getData().set("potions.strenght.tier2", false);
                    Settings.getInstance().saveData();
                }
            }
            if(event.getSlot() == 15) {
                player.closeInventory();
                if(Settings.getData().get("potions.strenght.splash") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §bSPLASH §8: §aENABLED");
                    }
                    Settings.getData().set("potions.strenght.splash", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bSTRENGHT §8: §bSPLASH §8: §bDISABLED§f!");
                    }
                    Settings.getData().set("potions.strenght.splash", false);
                    Settings.getInstance().saveData();
                }
            }
        }
    }
}
