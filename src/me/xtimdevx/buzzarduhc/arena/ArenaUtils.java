package me.xtimdevx.buzzarduhc.arena;

import me.xtimdevx.buzzarduhc.Settings;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xTimDevx on 13/10/2017.
 */
public class ArenaUtils {

    public static void giveKit(Player player) {
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack sword = new ItemStack (Material.DIAMOND_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
        sword.setItemMeta(swordMeta);

        ItemStack helmet = new ItemStack (Material.DIAMOND_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        helmetMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        helmet.setItemMeta(helmetMeta);

        ItemStack leggings = new ItemStack (Material.DIAMOND_LEGGINGS);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        leggingsMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        leggings.setItemMeta(leggingsMeta);

        ItemStack chestplate = new ItemStack (Material.DIAMOND_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, false);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack boots = new ItemStack (Material.DIAMOND_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, false);
        boots.setItemMeta(bootsMeta);

        ItemStack goldenapple = new ItemStack (Material.GOLDEN_APPLE, 2);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemStack wbucket = new ItemStack(Material.WATER_BUCKET);
        ItemStack lbucket = new ItemStack(Material.LAVA_BUCKET);
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack cobble = new ItemStack(Material.COBBLESTONE, 64);
        ItemStack wood = new ItemStack(Material.WOOD, 64);

        ItemStack head = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§8» §b§LGolden Head §8«");
        meta.setLore(Arrays.asList("§8» §fEating this head will heal §b4 §fhearts."));
        head.setItemMeta(meta);

        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, rod);
        player.getInventory().setItem(2, wood);
        player.getInventory().setItem(3, cobble);
        player.getInventory().setItem(4, wbucket);
        player.getInventory().setItem(5, lbucket);
        player.getInventory().setItem(6, goldenapple);
        player.getInventory().setItem(7, head);
        player.getInventory().setItem(8, steak);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        lore.clear();
    }
}
