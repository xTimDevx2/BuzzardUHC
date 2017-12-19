package me.xtimdevx.buzzarduhc.gui;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.DateUtils;
import me.xtimdevx.buzzarduhc.utils.NameUtils;
import me.xtimdevx.buzzarduhc.utils.NumberUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 20/07/2017.
 */
public class InventoryGUI implements Listener{


    public static Inventory openInventory(final Player player, final Player target) {
        final Inventory inv = Bukkit.createInventory(target, 54, "§8» §fInventory: §b" + target.getName());

        Main.invsee.put(inv, new BukkitRunnable() {
            @Override
            public void run() {

                inv.setItem(0, target.getInventory().getHelmet());
                inv.setItem(1, target.getInventory().getChestplate());
                inv.setItem(2, target.getInventory().getLeggings());
                inv.setItem(3, target.getInventory().getBoots());

                ItemStack info = new ItemStack(Material.BOOK);
                ItemMeta infoMeta = info.getItemMeta();
                infoMeta.setDisplayName("§8» §b§lINFO §8«");
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§8» ");
                lore.add("§8» §fName: §b" + target.getName());
                lore.add("§8» ");
                int health = (int) target.getHealth();
                lore.add("§8» §fHearts: §b" + (((double) health) / 2) + "♥");
                lore.add("§8» §fPercent: §b" + NumberUtils.makePercent(target.getHealth()) + "%");
                lore.add("§8» §fXP: §b" + target.getLevel());
                lore.add("§8» §f");
                lore.add("§8» §fLocation: X:§b" + target.getLocation().getBlockX() + "§f, Y:§b" + target.getLocation().getBlockY() + "§f, Z:§b" + target.getLocation().getBlockZ() + " §8(§f" + target.getWorld().getEnvironment().name().replaceAll("_", "").toLowerCase().replaceAll("normal", "overworld") + "§8)");
                lore.add("§8» ");
                lore.add("§8» §bPotions §8«");
                lore.add("§8» §f");
                if (target.getActivePotionEffects().size() == 0) {
                    lore.add("§8» §fNone");
                }
                for (PotionEffect effects : target.getActivePotionEffects()) {
                    lore.add("§8» §fPotion: §b" + NameUtils.getPotionName(effects.getType()));
                    lore.add("§8» §fTier: §b" + (effects.getAmplifier() + 1));
                    lore.add("§8» §fDuration: §b" +  DateUtils.ticksToString(effects.getDuration() / 20));
                    lore.add("§8» ");
                }
                infoMeta.setLore(lore);
                info.setItemMeta(infoMeta);
                inv.setItem(5, info);
                lore.clear();

                ItemStack teaminventory = new ItemStack(Material.CHEST);
                ItemMeta teaminventoryMeta = teaminventory.getItemMeta();
                teaminventoryMeta.setDisplayName("§8» §b§lTEAMINVENTORY §8«");
                lore.add("§8» ");
                if (!ScenarioManager.getInstance().getScenario("teaminventory").isEnabled()) {
                    lore.add("§bTeaminventory §fis disabled.");
                }else {
                    lore.add("§fClick here to open his teaminventory.");
                }
                teaminventoryMeta.setLore(lore);
                teaminventory.setItemMeta(teaminventoryMeta);
                inv.setItem(6, teaminventory);
                lore.clear();

                for (int i = 9; i < 18; i++) {
                    ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                    ItemMeta glassMeta = glass.getItemMeta();
                    glassMeta.setDisplayName(" ");
                    glass.setItemMeta(glassMeta);
                    inv.setItem(i, glass);
                }
                int i = 18;

                for (ItemStack item : target.getInventory().getContents()) {
                    inv.setItem(i, item);
                    i++;
                }

                player.updateInventory();

            }
        });
        Main.invsee.get(inv).runTaskTimer(Main.plugin, 1, 1);

        player.openInventory(inv);

        return inv;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();

        if (!Main.invsee.containsKey(inv)) {
            return;
        }

        Main.invsee.get(inv).cancel();
        Main.invsee.remove(inv);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getName().contains("§8» §fInventory: §b")) {
            e.setCancelled(true);
            if(e.getSlot() == 6) {
                if (!ScenarioManager.getInstance().getScenario("teaminventory").isEnabled()) {
                    player.closeInventory();
                    player.sendMessage(PrefixUtils.PREFIX + "§bTeaminventory §fis disabled.");
                }else {
                    player.closeInventory();
                    player.performCommand("ti " + e.getInventory().getTitle().replace("§8» §fInventory: §b", null));
                }
            }
        }
    }
}
