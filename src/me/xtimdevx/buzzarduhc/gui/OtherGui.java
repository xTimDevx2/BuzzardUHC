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

import java.util.ArrayList;

/**
 * Created by xTimDevx on 30/06/2017.
 */
public class OtherGui implements Listener{

    public static Inventory openRates(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "§7Other:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack fireaspect = new ItemStack (Material.BLAZE_POWDER);
        ItemMeta fireaspectMeta = fireaspect.getItemMeta();
        fireaspectMeta.setDisplayName("§8» §b§lFireaspect §8«");
        lore.add(" ");
        if(Settings.getData().get("settings.fireaspect") == null) {
            Settings.getData().set("settings.fireaspect", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fFire aspect enchantment.");
        lore.add(" ");
        lore.add("§8» §f" + (Settings.getData().get("settings.fireaspect").equals(true) ? "§aOn" : "§bOn") + " §8«");
        lore.add("§8» §f" + (Settings.getData().get("settings.fireaspect").equals(false) ? "§aOff" : "§bOff") + " §8«");
        lore.add(" ");
        lore.add("§7§o(Default: On)");
        lore.add(" ");
        fireaspectMeta.setLore(lore);
        fireaspect.setItemMeta(fireaspectMeta);
        inv.setItem(10, fireaspect);
        lore.clear();

        ItemStack flame = new ItemStack (Material.BLAZE_ROD);
        ItemMeta flameMeta = flame.getItemMeta();
        flameMeta.setDisplayName("§8» §b§lFlame §8«");
        lore.add(" ");
        if(Settings.getData().get("settings.flame") == null) {
            Settings.getData().set("settings.flame", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fFlame enchantment.");
        lore.add(" ");
        lore.add("§8» §f" + (Settings.getData().get("settings.flame").equals(true) ? "§aOn" : "§bOn") + " §8«");
        lore.add("§8» §f" + (Settings.getData().get("settings.flame").equals(false) ? "§aOff" : "§bOff") + " §8«");
        lore.add(" ");
        lore.add("§7§o(Default: On)");
        lore.add(" ");
        flameMeta.setLore(lore);
        flame.setItemMeta(flameMeta);
        inv.setItem(12, flame);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onDrop(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        User user = User.get(player);
        if (event.getInventory().getName().equalsIgnoreCase("§7Other:")) {
            event.setCancelled(true);
            if(event.getSlot() == 10) {
                player.closeInventory();
                if(Settings.getData().get("settings.fireaspect") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bFIREASPECT §8: §aENABLED§f!");
                    }
                    Settings.getData().set("settings.fireaspect", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bFIREASPECT §8: §bDISABLED§f!");
                    }
                    Settings.getData().set("settings.fireaspect", false);
                    Settings.getInstance().saveData();
                }
            }
            if(event.getSlot() == 12) {
                player.closeInventory();
                if(Settings.getData().get("settings.flame") != true) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bFLAME §8: §aENABLED§f!");
                    }
                    Settings.getData().set("settings.flame", true);
                    Settings.getInstance().saveData();
                }else {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Option §bFLAME §8: §bDISABLED§f!");
                    }
                    Settings.getData().set("settings.flame", false);
                    Settings.getInstance().saveData();
                }
            }
        }
    }

}
