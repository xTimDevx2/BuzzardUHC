package me.xtimdevx.buzzarduhc.gui;

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
import sun.io.ByteToCharKOI8_R;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 18/06/2017.
 */
public class RestartGUI implements Listener{

    public static Inventory openRestart(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "§7Restart:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack safe = new ItemStack (Material.WOOL, 1, (short) 5);
        ItemMeta safeMeta = safe.getItemMeta();
        safeMeta.setDisplayName("§aSafe restart");
        lore.add(" ");
        lore.add("§8» §fNo world reset.");
        lore.add("§8» §fUHC settings are saved.");
        lore.add(" ");
        safeMeta.setLore(lore);
        safe.setItemMeta(safeMeta);
        inv.setItem(2, safe);
        lore.clear();

        ItemStack unsafe = new ItemStack (Material.WOOL, 1, (short) 14);
        ItemMeta unsafeMeta = unsafe.getItemMeta();
        unsafeMeta.setDisplayName("§bUnsafe restart");
        lore.add(" ");
        lore.add("§8» §fAll the UHC worlds reset.");
        lore.add("§8» §fSome off the UHC settings reset.");
        lore.add(" ");
        unsafeMeta.setLore(lore);
        unsafe.setItemMeta(unsafeMeta);
        inv.setItem(6, unsafe);
        lore.clear();
        
        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equalsIgnoreCase("§7Restart:")) {
            e.setCancelled(true);
            if(e.getSlot() == 2) {
                Settings.getData().set("server.saferestart", true);
                Settings.getInstance().saveData();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Restarting the server... §8(§aSafe mode§8)");
                        }
                    }
                }, 20L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.kickPlayer(PrefixUtils.PREFIX + "Restarting the server! §8(§aSafe mode§8)");
                        }
                    }
                }, 40L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getServer().shutdown();
                    }
                }, 60L);
            }
            if(e.getSlot() == 6) {
                Settings.getData().set("server.saferestart", false);
                Settings.getInstance().saveData();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.sendMessage(PrefixUtils.PREFIX + "Restarting the server... §8(§bUnsafe mode§8)");
                        }
                    }
                }, 20L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(Player online : Bukkit.getOnlinePlayers()) {
                            online.kickPlayer(PrefixUtils.PREFIX + "Restarting the server! §8(§bUnsafe mode§8)");
                        }
                    }
                }, 40L);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getServer().shutdown();
                    }
                }, 60L);
            }
        }
    }
}
