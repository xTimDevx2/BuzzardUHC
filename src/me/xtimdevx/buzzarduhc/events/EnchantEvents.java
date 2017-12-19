package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 30/06/2017.
 */
public class EnchantEvents implements Listener{

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if(Settings.getData().get("settings.fireaspect") != true) {
            if (event.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT)) {
                event.setCancelled(true);
                event.getEnchanter().sendMessage(PrefixUtils.PREFIX + "§bFire aspect §fis §cdisabled§f.");
            }
        }
        if(Settings.getData().get("settings.flame") != true) {
            if (event.getEnchantsToAdd().containsKey(Enchantment.ARROW_FIRE)) {
                event.setCancelled(true);
                event.getEnchanter().sendMessage(PrefixUtils.PREFIX + "§bFlame §fis §cdisabled§f.");
            }
        }
    }
}
