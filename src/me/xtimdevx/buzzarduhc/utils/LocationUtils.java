package me.xtimdevx.buzzarduhc.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TravelAgent;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class LocationUtils {

    public static int highestTeleportableYAtLocation(Location location) {

        Location startingLocation = location.clone();
        startingLocation.setY(location.getWorld().getMaxHeight());

        boolean above2WasAir = false;
        boolean aboveWasAir = false;
        Block currentBlock = startingLocation.getBlock();

        while (currentBlock.getY() >= 0) {

            if (currentBlock.getType() != Material.AIR) {
                if (above2WasAir && aboveWasAir) {
                    return currentBlock.getY();
                }

                above2WasAir = aboveWasAir;
                aboveWasAir = false;
            } else {
                above2WasAir = aboveWasAir;
                aboveWasAir = true;
            }

            currentBlock = currentBlock.getRelative(BlockFace.DOWN);
        }

        return -1;
    }

    public static boolean isOutsideOfBorder(Location loc) {
        WorldBorder border = loc.getWorld().getWorldBorder();

        double size = border.getSize();
        double x = loc.getX() - border.getCenter().getX();
        double z = loc.getZ() - border.getCenter().getZ();

        return Math.abs(x) < size && Math.abs(z) < size;
    }

    public static Location findSafeLocationInsideBorder(Location loc, int buffer, TravelAgent travel) {
        WorldBorder border = loc.getWorld().getWorldBorder();
        Location centre = border.getCenter();

        Location pos = loc.subtract(centre);

        double size = border.getSize() / 2;
        double bufferSize = size - buffer;

        double x = pos.getX();
        double z = pos.getZ();
        boolean changed = false;

        if (Math.abs(x) > size) {
            pos.setX(x > 0 ? bufferSize : -bufferSize);
            changed = true;
        }

        if (Math.abs(z) > size) {
            pos.setZ(z > 0 ? bufferSize : -bufferSize);
            changed = true;
        }

        if (!changed) {
            return loc;
        }

        pos.setY(highestTeleportableYAtLocation(pos.add(centre)));

        Location to = travel.findOrCreate(pos);

        if (!isOutsideOfBorder(to)) {
            pos = to;
        }

        return pos;
    }
}
