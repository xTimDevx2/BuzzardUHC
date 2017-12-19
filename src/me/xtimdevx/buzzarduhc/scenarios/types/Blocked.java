package me.xtimdevx.buzzarduhc.scenarios.types;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xTimDevx on 21/10/2017.
 */
public class Blocked extends Scenario implements Listener{
    private Map<String, List<Location>> mined = new HashMap<String, List<Location>>();

    public Blocked() {
        super("Blocked", "You can't break the blocks that you place. Other players can break blocks that you place, and you can break blocks that other players place.");
    }

    @Override
    public void onDisable() {
        mined.clear();
    }

    @Override
    public void onEnable() {
        mined.clear();
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if(!State.isState(State.INGAME)) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(!mined.containsKey(player.getName())) {
            mined.put(player.getName(), new ArrayList<Location>());
        }
        mined.get(player.getName()).add(block.getLocation());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if(!State.isState(State.INGAME)) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(!mined.containsKey(player.getName())){
            mined.put(player.getName(), new ArrayList<Location>());
        }

        if(mined.get(player.getName()).contains(block.getLocation())) {
            event.setCancelled(true);
            player.sendMessage("§cBlocked §8» §fYou can't mine blocks that you placed yourself.");
        }
    }
}
