package me.xtimdevx.buzzarduhc.events;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.timers.GameTimer;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.rmi.registry.LocateRegistry;

/**
 * Created by xTimDevx on 10/06/2017.
 */
public class PortalEvents implements Listener{

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        TravelAgent travel = event.getPortalTravelAgent();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        if(GameTimer.m > 40 || Settings.getData().get("world.nether").equals(false)) {
            event.setCancelled(true);
            player.sendMessage(PrefixUtils.PREFIX + "§bNether §fis §cdisabled§f.");
            return;
        }
        String fromName = from.getWorld().getName();
        String targetName;

        switch (from.getWorld().getEnvironment()) {
            case NETHER:
                if (!fromName.endsWith("_nether")) {
                    player.sendMessage(PrefixUtils.PREFIX + "Could not teleport you to the overworld, contact the staff now.");
                    return;
                }

                targetName = fromName.substring(0, fromName.length() - 7);
                break;
            case NORMAL:
                targetName = fromName + "_nether";
                break;
            default:
                return;
        }
        World world = Bukkit.getServer().getWorld(targetName);
        double multiplier = from.getWorld().getEnvironment() == World.Environment.NETHER ? 8D : 0.125D;
        Location to = new Location(world, from.getX() * multiplier, from.getY(), from.getZ() * multiplier, from.getYaw(), from.getPitch());

        to = travel.findOrCreate(to);

        to = LocationUtils.findSafeLocationInsideBorder(to, 10, travel);

        if (to == null || to.getY() < 0) {
            player.sendMessage(PrefixUtils.PREFIX + "Could not teleport you, contact the staff now.");
        } else {
            event.setTo(to);
            if(targetName.equalsIgnoreCase("UHC")) {
            }else {
                player.sendMessage("§c§lNether §8» §fYou entered the §cnether§f!");
                player.sendMessage("§c§lNether §8» §fEveryone will be teleported out of the nether at §c40 §fminutes in!");
            }
        }
    }
}
