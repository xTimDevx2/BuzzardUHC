package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class Timber extends Scenario implements Listener{

    public Timber() {
        super("Timber", "If you mine the bottom of a tree the whole tree will fall down.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(!block.getWorld().getName().equalsIgnoreCase("UHC")) {
            return;
        }
        if (block.getType() != Material.LOG && block.getType() != Material.LOG_2) {
            return;
        }

        block = block.getRelative(BlockFace.UP);

        while (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
            block.breakNaturally();
            block = block.getRelative(BlockFace.UP);
        }

        block = block.getRelative(BlockFace.DOWN);

        while (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
            block.breakNaturally();
            block = block.getRelative(BlockFace.DOWN);
        }
    }
}
