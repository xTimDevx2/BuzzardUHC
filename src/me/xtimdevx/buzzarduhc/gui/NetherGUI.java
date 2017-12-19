package me.xtimdevx.buzzarduhc.gui;

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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 27/07/2017.
 */
public class NetherGUI implements Listener{

    public static Inventory openNether(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§7Nether:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack nether = new ItemStack (Material.OBSIDIAN);
        ItemMeta netherMeta = nether.getItemMeta();
        netherMeta.setDisplayName("§8» §b§lNether §8«");
        lore.add(" ");
        if(Settings.getData().get("world.nether") == null) {
            Settings.getData().set("world.nether", true);
            Settings.getInstance().saveData();
        }
        lore.add("§8» §fStatus: " + (Settings.getData().get("world.nether") == false ? "§bDisabled" : "§aEnabled"));
        lore.add(" ");
        lore.add("§7§o(Default: Enabled)");
        lore.add(" ");
        netherMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        netherMeta.setLore(lore);
        nether.setItemMeta(netherMeta);
        inv.setItem(4, nether);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equalsIgnoreCase("§7Nether:")) {
            e.setCancelled(true);
            if(e.getSlot() == 4) {
                if(Settings.getData().get("world.nether") != true) {
                    Settings.getData().set("world.nether", true);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bNETHER §fis now §aenabled§f.");
                    }
                    player.closeInventory();
                }else {
                    Settings.getData().set("world.nether", false);
                    Settings.getInstance().saveData();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "§bNETHER §fis now §bdisabled§f.");
                    }
                    player.closeInventory();
                }
            }
        }
    }
}
