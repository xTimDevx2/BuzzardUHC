package me.xtimdevx.buzzarduhc.utils;

import org.bukkit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class ScatterUtils {

    private static Material[] nospawn = { Material.STATIONARY_WATER, Material.WATER, Material.STATIONARY_LAVA, Material.LAVA, Material.CACTUS };

    public static List<Location> getScatterLocations(World world, int radius, int count) {
        ArrayList<Location> locs = new ArrayList<Location>();

        for (int i = 0; i < count; i++) {
            double min = 150;

            for (int j = 0; j < 4004; j++) {
                if (j == 4003) {
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "Could not scatter a player");
                    break;
                }

                Random rand = new Random();
                int x = rand.nextInt(radius * 2) - radius;
                int z = rand.nextInt(radius * 2) - radius;

                Location loc = new Location(world, x + 0.5, 0, z + 0.5);

                boolean close = false;
                for (Location l : locs) {
                    if (l.distanceSquared(loc) < min) {
                        close = true;
                    }
                }

                if (!close && isVaild(loc.clone())) {
                    double y = LocationUtils.highestTeleportableYAtLocation(loc);
                    loc.setY(y);
                    Location floc = new Location(world, x + 0.5, y, z + 0.5);
                    locs.add(floc);
                    break;
                } else {
                    min -= 1;
                }
            }
        }

        return locs;
    }

    private static boolean isVaild(Location loc) {
        loc.setY(loc.getWorld().getHighestBlockYAt(loc));

        Material m = loc.add(0, -1, 0).getBlock().getType();
        boolean vaild = true;

        if (loc.getBlockY() < 60) {
            vaild = false;
        }

        for (Material no : nospawn) {
            if (m == no) {
                vaild = false;
            }
        }
        return vaild;
    }
}
