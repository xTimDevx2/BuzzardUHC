package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 17/06/2017.
 */
public class PotionEvents implements Listener{

    @EventHandler
    public void onBrew(BrewEvent event) {
        ItemStack strenght = new ItemStack(Material.POTION,1, (short) 1);
        if(Settings.getData().get("potions.strenght.enabled") == false) {
            if (event.getContents().contains(Material.BLAZE_POWDER)) {
                event.setCancelled(true);
            }
        }
    }
}
