package me.xtimdevx.buzzarduhc.gui;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class InfoGUI implements Listener{

    public static Inventory openInfo(Player player) {
        Inventory inv = Bukkit.createInventory(player, 45, "§7Game info:");
        ArrayList<String> lore = new ArrayList<String>();

        ItemStack glass1 = new ItemStack (Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemStack glass2 = new ItemStack (Material.STAINED_GLASS_PANE, 1, (short) 14);
        inv.setItem(0, glass1);
        inv.setItem(1, glass1);
        inv.setItem(2, glass1);
        inv.setItem(3, glass1);
        inv.setItem(4, glass1);
        inv.setItem(5, glass1);
        inv.setItem(6, glass1);
        inv.setItem(7, glass1);
        inv.setItem(8, glass1);
        inv.setItem(9, glass1);
        inv.setItem(17, glass1);
        inv.setItem(18, glass1);
        inv.setItem(26, glass1);
        inv.setItem(27, glass1);
        inv.setItem(35, glass1);
        inv.setItem(36, glass1);
        inv.setItem(37, glass1);
        inv.setItem(38, glass1);
        inv.setItem(39, glass1);
        inv.setItem(40, glass1);
        inv.setItem(41, glass1);
        inv.setItem(42, glass1);
        inv.setItem(43, glass1);
        inv.setItem(44, glass1);

        inv.setItem(11, glass2);
        inv.setItem(13, glass2);
        inv.setItem(15, glass2);
        inv.setItem(19, glass2);
        inv.setItem(20, glass2);
        inv.setItem(21, glass2);
        inv.setItem(23, glass2);
        inv.setItem(24, glass2);
        inv.setItem(25, glass2);
        inv.setItem(29, glass2);
        inv.setItem(31, glass2);
        inv.setItem(33, glass2);

        ItemStack basic = new ItemStack (Material.BOOK);
        ItemMeta basicMeta = basic.getItemMeta();
        basicMeta.setDisplayName("§8» §b§lBASIC §8«");
        lore.add(" ");
        lore.add("§8» §fHost: §b" + Settings.getData().get("game.host"));
        lore.add(" ");
        lore.add("§8» §fTwitter: §b@ScyleNetwork");
        if(ScenarioManager.getInstance().getEnabledScenarios().size() == 0) {
            lore.add("§8» §fScenario's: §bVanilla+");
        }else{
            lore.add("§8» §fScenario's: §b" + GameUtils.getEnabledScenarios());
        }
        lore.add(" ");
        basicMeta.setLore(lore);
        basic.setItemMeta(basicMeta);
        inv.setItem(10, basic);
        lore.clear();

        ItemStack border = new ItemStack (Material.BARRIER);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName("§8» §b§lBORDER §8«");
        if(Bukkit.getWorld("UHC") == null) {
            lore.add(" ");
            lore.add("§8» §fNo §bUHC §fworld created.");
            lore.add(" ");
        }else{
            lore.add(" ");
            lore.add("§8» §fCurrent: §b" + Settings.getData().get("border.size"));
            lore.add(" ");
            lore.add("§8» §fFirst shrink: §b500x500§f. §8(§b§o45 minutes§8)");
            lore.add("§8» §fSecond shrink: §b250x250§f. §8(§b§o50 minutes§8)");
            lore.add("§8» §fThird shrink: §b75x75§f. §8(§b§o55 minutes§8)");
            lore.add(" ");
        }
        borderMeta.setLore(lore);
        border.setItemMeta(borderMeta);
        inv.setItem(12, border);
        lore.clear();

        @SuppressWarnings("deprecation")
        ItemStack timer = new ItemStack (Material.getMaterial(347));
        ItemMeta timerMeta = timer.getItemMeta();
        timerMeta.setDisplayName("§8» §b§lTIMER §8«");
        if(!State.isState(State.INGAME)) {
            lore.add(" ");
            lore.add("§8» §fIngame: §b00§8:§b00§8:§b00");
            lore.add(" ");
            lore.add("§8» §fFinalheal: §b00§8:§b30");
            lore.add("§8» §fPvP: §b20§8:§b00");
            lore.add("§8» §fBorder 1: §b45§8:§b00");
            lore.add("§8» §fBorder 2: §b50§8:§b00");
            lore.add("§8» §fBorder 3: §b55§8:§b00");
        }else{
            lore.add(" ");
            lore.add("§8» §fIngame: §b" + (GameTimer.h < 9 ? "0" + GameTimer.h : GameTimer.h) + "§8:§b" + (GameTimer.m < 9 ? "0" + GameTimer.m : GameTimer.m) + "§8:§b" + (GameTimer.s < 9 ? "0" + GameTimer.s : GameTimer.s));
            lore.add(" ");
            if(GameTimer.s > 30 || GameTimer.m > 1) {
                lore.add("§8» §fFinalheal: §bFinished");
            }else{
                lore.add("§8» §fFinalheal: §b00§8:§b" + (30 - GameTimer.s));
            }
            if(GameTimer.pvpm < 0) {
                lore.add("§8» §fPvP: §bFinished");
            }else{
                lore.add("§8» §fPvP: §b" + GameTimer.pvpm + "§8:§b" + GameTimer.pvps);
            }
            if(GameTimer.border1m < 0) {
                lore.add("§8» §fBorder 1: §bFinished");
            }else{
                lore.add("§8» §fBorder 1: §b" + GameTimer.border1m + "§8:§b" + GameTimer.border1s);
            }
            if(GameTimer.border2m < 0) {
                lore.add("§8» §fBorder 2: §bFinished");
            }else{
                lore.add("§8» §fBorder 2: §b" + GameTimer.border2m + "§8:§b" + GameTimer.border2s);
            }
            if(GameTimer.border3m < 0) {
                lore.add("§8» §fBorder 3: §bFinished");
            }else{
                lore.add("§8» §fBorder 3: §b" + GameTimer.border3m + "§8:§b" + GameTimer.border3s);
            }
            lore.add(" ");
            lore.add("§8» §fClick on the clock to refresh the timer.");
            lore.add(" ");
        }
        timerMeta.setLore(lore);
        timer.setItemMeta(timerMeta);
        inv.setItem(14, timer);
        lore.clear();

        ItemStack scen = new ItemStack (Material.GOLD_INGOT);
        ItemMeta scenMeta = scen.getItemMeta();
        scenMeta.setDisplayName("§8» §b§lSCENARIO §8«");
        lore.add(" ");
        lore.add("§8» §fEnabled scenario's");
        if(ScenarioManager.getInstance().getEnabledScenarios().size() == 0) {
            lore.add("§8» - §bVanilla+");
        }else{
            for(Scenario scenarios : ScenarioManager.getInstance().getScenarios()) {
                if(scenarios.isEnabled()) {
                    lore.add("§8» - §b" + scenarios.getName());
                }
            }
        }
        lore.add(" ");
        scenMeta.setLore(lore);
        scen.setItemMeta(scenMeta);
        inv.setItem(16, scen);
        lore.clear();

        ItemStack extra = new ItemStack (Material.BLAZE_POWDER);
        ItemMeta extraMeta = extra.getItemMeta();
        extraMeta.setDisplayName("§8» §b§lEXTRA §8«");
        lore.add(" ");
        lore.add("§8» §fFireaspect: " + (Settings.getData().get("settings.fireaspect").equals(true) ? "§aEnabled" : "§cDisabled"));
        lore.add("§8» §fFlame: " + (Settings.getData().get("settings.flame").equals(true) ? "§aEnabled" : "§cDisabled"));
        lore.add("§8» §fNether: " + (Settings.getData().get("world.nether").equals(true) ? "§aEnabled" : "§cDisabled"));
        lore.add(" ");
        extraMeta.setLore(lore);
        extra.setItemMeta(extraMeta);
        inv.setItem(28, extra);
        lore.clear();


        ItemStack sword = new ItemStack (Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName("§8» §b§lTEAMING §8«");
        lore.add(" ");
        lore.add("§8» §fMode: §b" + GameUtils.getTeamMode());
        lore.add(" ");
        if(!GameUtils.getTeamMode().equals("FFA")) {
            lore.add("§8» §fLimit: §b" + Main.teamlimit);
            lore.add("§8» §fCrossteaming: " + (Settings.getData().get("team.crossteaming") == true ? "§aAllowed" : "§cNot allowed"));
        }else{
            lore.add("§8» §fTeaming will result in a §b1 §fweek ban.");
        }
        lore.add(" ");
        swordMeta.setLore(lore);
        sword.setItemMeta(swordMeta);
        inv.setItem(30, sword);
        lore.clear();

        ItemStack mining = new ItemStack (Material.DIAMOND_PICKAXE);
        ItemMeta miningMeta = mining.getItemMeta();
        miningMeta.setDisplayName("§8» §b§lMINING §8«");
        lore.add(" ");
        lore.add("§8» §fStripmining is §cnot §fallowed. §8(§b§oUnder y32§8)");
        lore.add("§8» §fPokeholing is §cnot §fallowed.");
        lore.add("§8» §fBlastmining is allowed.");
        lore.add("§8» §fRollercoasting is allowed. §8(§b§oFrom y32 -> y5§8)");
        lore.add("§8» §fStaircasing is allowed. §8(§b§oFrom y32 -> y5§8)");
        lore.add("§8» §fDigging straight down is allowed.");
        lore.add("§8» §fMining to sounds is allowed.");
        lore.add("§8» §fMining to coords is allowed.");
        lore.add("§8» §fMining to nametags is allowed.");
        lore.add(" ");
        miningMeta.setLore(lore);
        mining.setItemMeta(miningMeta);
        inv.setItem(32, mining);
        lore.clear();

        ItemStack rates = new ItemStack (Material.APPLE);
        ItemMeta ratesMeta = rates.getItemMeta();
        ratesMeta.setDisplayName("§8» §b§lRATES §8«");
        lore.add(" ");
        lore.add("§8» §fApple: §b" + Main.applerate + "%");
        lore.add("§8» §fShears: " + (Settings.getData().getBoolean("rates.shears") == true ? "§aEnabled" : "§cDisabled"));
        lore.add("§8» §fTrees: §bOak §fand §bDark oak§f.");
        lore.add(" ");
        lore.add("§8» §fFlint: §b" + Main.flintrate + "%");
        lore.add(" ");
        ratesMeta.setLore(lore);
        rates.setItemMeta(ratesMeta);
        inv.setItem(34, rates);
        lore.clear();

        ItemStack healing = new ItemStack (Material.GOLDEN_APPLE);
        ItemMeta healingMeta = healing.getItemMeta();
        healingMeta.setDisplayName("§8» §b§lHEALING §8«");
        lore.add(" ");
        lore.add("§8» §fGolden apple: §c❤❤");
        lore.add("§8» §fGolde head: §c❤❤❤❤");
        lore.add(" ");
        lore.add("§8» §fNotch apple: §cDisabled§f.");
        lore.add(" ");
        healingMeta.setLore(lore);
        healing.setItemMeta(healingMeta);
        inv.setItem(22, healing);
        lore.clear();

        player.openInventory(inv);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equalsIgnoreCase("§7Game info:")) {
            e.setCancelled(true);
            if(e.getSlot() == 14) {
                openInfo(player);
            }
        }
    }
}
