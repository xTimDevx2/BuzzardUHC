package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by xTimDevx on 11/07/2017.
 */
public class FastSmelting extends Scenario implements Listener{

    public FastSmelting() {
        super("Fastsmelting", "Furnaces smelt stuff faster.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    private void startUpdate(final Furnace tile, final int increase)
    {
        new BukkitRunnable() {
            public void run() {
                if ((tile.getCookTime() > 0) || (tile.getBurnTime() > 0)) {
                    tile.setCookTime((short)(tile.getCookTime() + increase));
                    tile.setBurnTime((short)(tile.getBurnTime() + increase));
                    tile.update();
                }
                else {
                    cancel();
                }
            }
        }
                .runTaskTimer(Main.plugin, 1L, 1L);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        startUpdate((Furnace)event.getBlock().getState(), 4);
    }
}
