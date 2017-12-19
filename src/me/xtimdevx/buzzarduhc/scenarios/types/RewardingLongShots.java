package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 24/10/2017.
 */
public class RewardingLongShots extends Scenario implements Listener{

    public RewardingLongShots() {
        super("RewardingLongshots", "When shooting and hitting people with a bow from a variable distance, you will be rewarded with various items.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    public String prefix() {
        return "§cRewardinglongshots §8» §f";
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Arrow damager = (Arrow) event.getDamager();

        if(!(damager.getShooter() instanceof Player)) {
            return;
        }

        Player killer = (Player) damager.getShooter();
        double distance = killer.getLocation().distance(player.getLocation());

        if(distance < 30) {
            return;
        }

        GameUtils.broadcast(prefix() + "§c" + killer.getName() + " §fjust got a longshot of §c" + NumberUtils.convertDouble(distance) + " metres!");

        if(distance <= 49) {
            killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            return;
        }
        if(distance <= 99) {
            killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
            killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            return;
        }
        if(distance <= 199) {
            killer.getInventory().addItem(new ItemStack(Material.DIAMOND));
            killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
            killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            return;
        }
        killer.getInventory().addItem(new ItemStack(Material.DIAMOND, 5));
        killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 3));
        killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 2));
    }
}
