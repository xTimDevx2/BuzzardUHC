package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 19/06/2017.
 */
public class EnchantParanoia extends Scenario implements Listener{

    public EnchantParanoia() {
        super("Enchantparanoia", "Enchants are hidden");
    }

    public static boolean enabled = false;
    public static BukkitRunnable task;

    public void setEnabled(boolean enable) {
        enabled = enable;
        if (enable) {
            task = new BukkitRunnable() {
                public void run() {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        for (ItemStack contents : online.getOpenInventory().getTopInventory().getContents()) {
                            if (contents == null) {
                                continue;
                            }

                            if (!contents.getEnchantments().isEmpty()) {
                                ItemMeta meta = contents.getItemMeta();

                                ArrayList<String> lore = new ArrayList<String>();

                                for (int i = 0; i < contents.getEnchantments().size(); i++) {
                                    lore.add("§7Enchants are hidden. (Enchantparanoia)");
                                }

                                meta.setLore(lore);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                contents.setItemMeta(meta);
                            }
                        }

                        for (ItemStack contents : online.getOpenInventory().getBottomInventory().getContents()) {
                            if (contents == null) {
                                continue;
                            }

                            if (!contents.getEnchantments().isEmpty()) {
                                ItemMeta meta = contents.getItemMeta();

                                ArrayList<String> lore = new ArrayList<String>();

                                for (int i = 0; i < contents.getEnchantments().size(); i++) {
                                    lore.add("§7Enchants are hidden. (Enchantparanoia)");
                                }

                                meta.setLore(lore);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                contents.setItemMeta(meta);
                            }
                        }

                        for (ItemStack contents : online.getInventory().getContents()) {
                            if (contents == null) {
                                continue;
                            }

                            if (!contents.getEnchantments().isEmpty()) {
                                ItemMeta meta = contents.getItemMeta();

                                ArrayList<String> lore = new ArrayList<String>();

                                for (int i = 0; i < contents.getEnchantments().size(); i++) {
                                    lore.add("§7Enchants are hidden. (Enchantparanoia)");
                                }

                                meta.setLore(lore);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                contents.setItemMeta(meta);
                            }
                        }
                    }
                }
            };

            task.runTaskTimer(Main.plugin, 20, 20);
        } else {
            task.cancel();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();

        ArrayList<String> lore = new ArrayList<String>();

        for (int i = 0; i < event.getEnchantsToAdd().size(); i++) {
            lore.add("§7Enchants are hidden. (Enchantparanoia)");
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }


    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

}
