package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

/**
 * Created by xTimDevx on 19/06/2017.
 */
public class BloodDiamonds extends Scenario implements Listener{

    public BloodDiamonds() {
        super("Blooddiamonds", "If you mine a diamond, you take half a heart of damage.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType() == Material.DIAMOND_ORE) {
            player.damage(1.0);
            player.sendMessage("§4Blooddiamonds §8» §fDamaged §c0.5 §fhearts due to mining §cDIAMONDS§f.");
        }
    }
}
