package me.xtimdevx.buzzarduhc.scenarios.types;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by xTimDevx on 25/10/2017.
 */
public class TimberPlus extends Scenario implements Listener{

    public TimberPlus() {
        super("Timber+", "It's the same as normal timber, but it breaks the leaves break aswell.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(!State.isState(State.INGAME)) {
            return;
        }
        if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
            Location loc = block.getLocation();
            Player player = event.getPlayer();
            int amount = 0;

            for (int x = loc.getBlockX() - 8; x <= loc.getBlockX() + 8; x++) {
                for (int y = loc.getBlockY() - 8; y <= loc.getBlockY() + 8; y++) {
                    for (int z = loc.getBlockZ() - 8; z <= loc.getBlockZ() + 8; z++) {
                        if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.LOG || loc.getWorld().getBlockAt(x, y, z).getType() == Material.LEAVES) {
                            loc.getWorld().getBlockAt(x, y, z).breakNaturally();
                        }
                        if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.LOG_2 || loc.getWorld().getBlockAt(x, y, z).getType() == Material.LEAVES_2) {
                            loc.getWorld().getBlockAt(x, y, z).breakNaturally();
                        }
                    }
                }
            }
            return;
        }
    }
}
