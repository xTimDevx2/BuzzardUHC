package me.xtimdevx.buzzarduhc.scenarios.types;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by xTimDevx on 22/10/2017.
 */
public class VoidScape extends Scenario implements CommandExecutor, Listener{
    private ArrayList<Location> locations = new ArrayList<Location>();
    private int totalChunks;

    public static BukkitRunnable task = null;

    public VoidScape() {
        super("Voidscape", "All stone and bedrock is replaced with air.");

        Bukkit.getPluginCommand("void").setExecutor(this);
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onFlow(BlockFromToEvent event) {
        event.setCancelled(true);
    }

    public String prefix() {
        return "§4Voidscape §8» §f";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can generate the voidscape map.");
            return true;
        }

        final Player player = (Player) sender;

        if(!ScenarioManager.getInstance().getScenario("voidscape").isEnabled()) {
            sender.sendMessage(prefix() + "§4Voidscape §fis not enabled.");
            return true;
        }

        if(!sender.hasPermission("uhc.voidscape")) {
            sender.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }

        if(args.length == 0) {
            player.sendMessage("§cUsage: /void <radius>");
            return true;
        }
        if(!player.getWorld().getName().equalsIgnoreCase("UHC")) {
            player.sendMessage("§cYou have to be in the game world to use this command.");
            return true;
        }
        int radius;

        try {
            radius = Integer.parseInt(args[0]);
        }catch (Exception e) {
            player.sendMessage("§cThat is not a valid radius.");
            return true;
        }
        locations = new ArrayList<Location>();

        for(int x = -1 * radius; x < radius; x += 16) {
            for(int z = -1 * radius; z < radius; z +=16) {
                locations.add(new Location(player.getWorld(),x,1,z));
            }
        }

        totalChunks = locations.size();

        Game.broadcast(prefix() + "Started generating the §4voidscape §fmap. This should take arround half an hour.");
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(locations.size() == 0) {
                    Game.broadcast(prefix() + "Finished the §4voidscape§f map generation.");
                    cancel();
                    task = null;
                    return;
                }

                Location location = locations.remove(locations.size() -1);
                Chunk chunk = player.getWorld().getChunkAt(location);

                for(int y = 0; y < 128; y++) {
                    for(int x = 0; x < 16; x++) {
                        for(int z = 0; z < 17; z++) {
                            Block block = chunk.getBlock(x, y, z);

                            if(block.getType() == Material.STONE || block.getType() == Material.BEDROCK) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
                int percentCompleted = ((totalChunks - locations.size())*100/totalChunks);

                for(Player online : Bukkit.getOnlinePlayers()) {
                    GameUtils.sendAction(online, prefix() + "Voidscape genration, §4" + percentCompleted + "% §ffinished.");
                }
            }
        };
        task.runTaskTimer(Main.plugin, 1,1);
        return true;
    }
}
