package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class Rodless extends Scenario implements Listener{

    public Rodless() {
        super("Rodless", "Fishing rods are disabled.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        if(itemType==Material.FISHING_ROD) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
