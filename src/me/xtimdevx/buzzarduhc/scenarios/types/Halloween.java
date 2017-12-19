package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by xTimDevx on 30/10/2017.
 */
public class Halloween extends Scenario implements Listener{

    public static BukkitRunnable task;

    public Halloween() {
        super("Halloween", "Random lightnining strikes around the player at random intervals causing bats to spawn around you and giving you nausea for 10 seconds if you get hit. Getting hit by a hostile mob causes 15 seconds of blindness. All spiders are replaced with cave spiders, witch rates are upped at last Zombies and Skellies spawn with jack o' lanterns.");
    }

    @Override
    public void onDisable() {
        task.cancel();
    }

    @Override
    public void onEnable() {}

    public static void startTimer() {
        task = new BukkitRunnable() {
            public void run() {
                Random ran = new Random();

                for (Player online : Bukkit.getOnlinePlayers()) {
                    Location loc = online.getLocation();

                    if (!loc.getWorld().equals(Bukkit.getWorld("UHC"))) {
                        continue;
                    }

                    int diffX = ran.nextInt(25 * 2) - 25;
                    int diffZ = ran.nextInt(25 * 2) - 25;

                    final Location hitLoc = loc.clone().add(diffX, 0, diffZ);
                    hitLoc.setY(LocationUtils.highestTeleportableYAtLocation(hitLoc) + 1);

                    hitLoc.getWorld().strikeLightning(hitLoc);

                    new BukkitRunnable() {
                        public void run() {
                            hitLoc.setY(LocationUtils.highestTeleportableYAtLocation(hitLoc) + 1);

                            for (int i = 0; i < 15; i++) {
                                hitLoc.getWorld().spawn(hitLoc, Bat.class).addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
                            }
                        }
                    }.runTaskLater(Main.plugin, 15);
                }
            }
        };

        task.runTaskTimer(Main.plugin, 6000, 700);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!State.isState(State.INGAME)) {
            return;
        }

        Entity entity = event.getEntity();

        if (entity instanceof Zombie || entity instanceof Skeleton) {
            LivingEntity living = (LivingEntity) entity;

            living.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
            return;
        }

        if (entity.getType() == EntityType.SPIDER) {
            event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.CAVE_SPIDER);
            event.setCancelled(true);
            return;
        }

        if (entity instanceof Witch) {
            return;
        }

        if (entity instanceof Bat) {
            return;
        }

        if (new Random().nextDouble() > 0.80) {
            event.getLocation().getWorld().spawn(event.getLocation(), Witch.class);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!State.isState(State.INGAME)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();

        if (cause != EntityDamageEvent.DamageCause.LIGHTNING) {
            return;
        }

        event.setDamage(event.getDamage() / 2);
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!State.isState(State.INGAME)) {
            return;
        }

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (entity instanceof Player && damager instanceof Monster) {
            Player player = (Player) entity;

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
        }
    }
}
