package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

/**
 * Created by xTimDevx on 16/07/2017.
 */
public class CraftClean extends Scenario{

    public CraftClean() {
        super("Craftclean", "Surround an unsmelted ore or unsmelted food with 8 coal to smelt it.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    public static Recipe ironRecipe;
    public static Recipe goldRecipe;
    public static Recipe beefRecipe;
    public static Recipe porkRecipe;
    public static Recipe muttonRecipe;
    public static Recipe chickenRecipe;
    public static Recipe rabbitRecipe;

    public static void addCraftCleanRecipes() {
        ItemStack iron = new ItemStack(Material.IRON_INGOT, 8);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 8);
        ItemStack beef = new ItemStack(Material.COOKED_BEEF, 8);
        ItemStack pork = new ItemStack(Material.GRILLED_PORK, 8);
        ItemStack mutton = new ItemStack(Material.COOKED_MUTTON, 8);
        ItemStack chicken = new ItemStack(Material.COOKED_CHICKEN, 8);
        ItemStack rabbit = new ItemStack(Material.COOKED_RABBIT, 8);
        @SuppressWarnings("deprecation")
        MaterialData coal = new MaterialData(Material.COAL);

        ShapedRecipe ironS = new ShapedRecipe(iron).shape("@@@", "@*@", "@@@").setIngredient('@', Material.IRON_ORE).setIngredient('*', coal);
        ShapedRecipe goldS = new ShapedRecipe(gold).shape("@@@", "@*@", "@@@").setIngredient('@', Material.GOLD_ORE).setIngredient('*', coal);
        ShapedRecipe beefS = new ShapedRecipe(beef).shape("@@@", "@*@", "@@@").setIngredient('@', Material.RAW_BEEF).setIngredient('*', coal);
        ShapedRecipe porkS = new ShapedRecipe(pork).shape("@@@", "@*@", "@@@").setIngredient('@', Material.PORK).setIngredient('*', coal);
        ShapedRecipe muttonS = new ShapedRecipe(mutton).shape("@@@", "@*@", "@@@").setIngredient('@', Material.MUTTON).setIngredient('*', coal);
        ShapedRecipe chickenS = new ShapedRecipe(chicken).shape("@@@", "@*@", "@@@").setIngredient('@', Material.RAW_CHICKEN).setIngredient('*', coal);
        ShapedRecipe rabbitS = new ShapedRecipe(rabbit).shape("@@@", "@*@", "@@@").setIngredient('@', Material.RABBIT).setIngredient('*', coal);

        Bukkit.getServer().addRecipe(ironS);
        Bukkit.getServer().addRecipe(goldS);
        Bukkit.getServer().addRecipe(beefS);
        Bukkit.getServer().addRecipe(porkS);
        Bukkit.getServer().addRecipe(muttonS);
        Bukkit.getServer().addRecipe(chickenS);
        Bukkit.getServer().addRecipe(rabbitS);


        ironRecipe = ironS;
        goldRecipe = goldS;
        beefRecipe = beefS;
        porkRecipe = porkS;
        muttonRecipe = muttonS;
        chickenRecipe = chickenS;
        rabbitRecipe = rabbitS;
    }
}
