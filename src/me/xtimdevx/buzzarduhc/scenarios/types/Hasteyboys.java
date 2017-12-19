package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by xTimDevx on 5/07/2017.
 */
public class Hasteyboys extends Scenario implements Listener {

    public Hasteyboys() {
        super("Hasteyboys", "All tools get enchanted with efficiency 3.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult().getType() == Material.STONE_PICKAXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.STONE_SPADE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.STONE_AXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        //WOOD
        if (event.getRecipe().getResult().getType() == Material.WOOD_PICKAXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.WOOD_SPADE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.WOOD_AXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        //IRON
        if (event.getRecipe().getResult().getType() == Material.IRON_PICKAXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.IRON_SPADE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.IRON_AXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        //GOLD
        if (event.getRecipe().getResult().getType() == Material.GOLD_PICKAXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.GOLD_SPADE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.GOLD_AXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        //DIAMOND
        if (event.getRecipe().getResult().getType() == Material.DIAMOND_PICKAXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.DIAMOND_SPADE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
        if (event.getRecipe().getResult().getType() == Material.DIAMOND_AXE) {
            event.getInventory().getResult().addEnchantment(Enchantment.DIG_SPEED, 3);
        }
    }
}