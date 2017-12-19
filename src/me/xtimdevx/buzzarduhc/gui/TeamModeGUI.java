package me.xtimdevx.buzzarduhc.gui;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class TeamModeGUI implements Listener{

    public static Inventory openTeaming(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§7Teaming mode:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack ffamode = new ItemStack (Material.WOOD_SWORD);
        ItemMeta ffamodeMeta = ffamode.getItemMeta();
        ffamodeMeta.setDisplayName("§8» §b§lFFA §8«");
        lore.add(" ");
        lore.add("§8» §fChange the teaming mode to FFA.");
        lore.add(" ");
        ffamodeMeta.setLore(lore);
        ffamode.setItemMeta(ffamodeMeta);
        inv.setItem(3, ffamode);
        lore.clear();

        ItemStack teammode = new ItemStack (Material.GOLD_SWORD);
        ItemMeta teammodeMeta = teammode.getItemMeta();
        teammodeMeta.setDisplayName("§8» §b§lTeam §8«");
        lore.add(" ");
        lore.add("§8» §fChange the teaming mode to Teaming.");
        lore.add(" ");
        teammodeMeta.setLore(lore);
        teammode.setItemMeta(teammodeMeta);
        inv.setItem(5, teammode);
        lore.clear();

        ItemStack randommode = new ItemStack (Material.DIAMOND_SWORD);
        ItemMeta randommodeMeta = randommode.getItemMeta();
        randommodeMeta.setDisplayName("§8» §b§lRandom §8«");
        lore.add(" ");
        lore.add("§8» §fChange the teaming mode to Random.");
        lore.add(" ");
        randommodeMeta.setLore(lore);
        randommode.setItemMeta(randommodeMeta);
        inv.setItem(7, randommode);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }
}
