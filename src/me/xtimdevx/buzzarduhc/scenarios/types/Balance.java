package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by xTimDevx on 10/09/2017.
 */
public class Balance extends Scenario implements Listener{

    public Balance() {
        super("Balance", "After the 5th diamond, it gets progressively harder to obtain diamonds.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    private Map<String, Integer> minedAmount = new HashMap<String, Integer>();
    private Map<String, Integer> chance = new HashMap<String, Integer>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() != Material.DIAMOND_ORE) {
            return;
        }

        if (!minedAmount.containsKey(player.getName())) {
            minedAmount.put(player.getName(), 0);
        }

        int amount = minedAmount.get(player.getName());
        amount++;

        minedAmount.put(player.getName(), amount);

        if (amount < 5) {
            return;
        }

        if (amount == 5) {
            player.sendMessage("§6Uh oh! Diamonds might disappear when mined now!");
            return;
        }

        chance.put(player.getName(), amount - 3);
        int rand = new Random().nextInt(chance.get(player.getName()));

        if (rand == 1) {
            chance.put(player.getName(), chance.get(player.getName()) + 1);

            player.sendMessage("§6Diamonds now have a 1/" + chance.get(player.getName()) + " chance to drop!");
        } else {
            chance.put(player.getName(), chance.get(player.getName()) - 1);

            BlockUtils.blockBreak(player, block);
            BlockUtils.degradeDurabiliy(player);

            event.setCancelled(true);
            block.setType(Material.AIR);
        }
    }
}
