package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by xTimDevx on 4/06/2017.
 */
public class ConsumeEvents implements Listener{

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        final float before = player.getSaturation();

        new BukkitRunnable() {
            public void run() {
                float change = player.getSaturation() - before;
                player.setSaturation((float) (before + change * 2.5D));
            }
        }.runTaskLater(Main.plugin, 1);
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§8» §b§LGolden Head §8«")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 25 * (4 * 2), 1));
        }
    }
}
