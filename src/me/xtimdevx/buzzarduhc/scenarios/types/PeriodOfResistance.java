package me.xtimdevx.buzzarduhc.scenarios.types;

import com.avaje.ebeaninternal.server.lib.sql.Prefix;
import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.events.GameStartEvent;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by xTimDevx on 25/10/2017.
 */
public class PeriodOfResistance extends Scenario implements Listener, CommandExecutor{
    public static BukkitRunnable task;
    public static DamageType current;

    public PeriodOfResistance() {
        super("PeriodOfResistance", "Every 10 minutes the resistance type changes, during the next 10 minutes you cannot take damage from what the type was.");

        Bukkit.getPluginCommand("status").setExecutor(this);
    }

    @Override
    public void onDisable() {
        task.cancel();
        task = null;
    }

    @Override
    public void onEnable() {}

    public static String prefix() {
        return "§bPeriodOfResistance §8» §f";
    }

    public static void onStart(){
        current = DamageType.values()[new Random().nextInt(DamageType.values().length)];
        Game.broadcast(prefix() + "All damahe from §b" + current.name().toLowerCase().replaceAll("_", " ") + " §fwill no longer hurt you.");

        task = new BukkitRunnable() {
                int seconds = 600;
                public void run() {
                    seconds--;
                    switch (seconds)  {
                        case 300:
                            Game.broadcast(prefix() + "Changing resistant period in §b5 §fminutes!");
                            break;
                        case 60:
                            Game.broadcast(prefix() + "Changing resistant period in §b1 §fminute!");
                            break;
                        case 30:
                        case 10:
                        case 5:
                        case 4:
                        case 3:
                        case 2:
                            GameUtils.broadcast(prefix() + "Changing resistant period in §b" + seconds + " §fseconds!");
                        case 1:
                            GameUtils.broadcast(prefix() + "Changing resistant period in §b1 §fsecond!");
                        case 0:
                            current = DamageType.values()[new Random().nextInt(DamageType.values().length)];
                            Game.broadcast(prefix() + "§fAll damage from §b" + current.name().toLowerCase().replaceAll("_", " ") + "§f will no longer hurt you!");

                            seconds = 600;
                    }
            }
        };
        task.runTaskTimer(Main.plugin, 20, 20);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        EntityDamageEvent.DamageCause cause = event.getCause();

        if(cause == EntityDamageEvent.DamageCause.FALL && current == DamageType.FALLING) {
            event.setCancelled(true);
        }

        if (cause == EntityDamageEvent.DamageCause.POISON && current == DamageType.POISON) {
            event.setCancelled(true);
        }

        if (cause == EntityDamageEvent.DamageCause.SUFFOCATION && current == DamageType.SUFFOCATION) {
            event.setCancelled(true);
        }

        if ((cause == EntityDamageEvent.DamageCause.LAVA || cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK) && current == DamageType.LAVA_AND_FIRE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Entity damager = event.getDamager();

        if (damager instanceof Zombie && current == DamageType.ZOMBIES) {
            event.setCancelled(true);
        }

        if (damager instanceof Creeper && current == DamageType.CREEPERS) {
            event.setCancelled(true);
        }

        if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Skeleton && current == DamageType.SKELETONS) {
            event.setCancelled(true);
        }

        if ((damager instanceof Spider || damager instanceof CaveSpider) && current == DamageType.SPIDERS) {
            event.setCancelled(true);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ScenarioManager.getInstance().getScenario("periodofresistance").isEnabled()) {
            sender.sendMessage("§cError: PeriodOfResistance is not enabled.");
            return true;
        }
        sender.sendMessage(prefix() + "Resistance Type: §b" + current.name().toLowerCase().replaceAll("_", " "));
        return true;
    }

    public enum DamageType {
        ZOMBIES, SPIDERS, FALLING, SKELETONS, CREEPERS, LAVA_AND_FIRE, SUFFOCATION, POISION, POISON;
    }
}
