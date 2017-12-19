package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


/**
 * Created by xTimDevx on 27/10/2017.
 */
public class MLGCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("mlg")) {
            Player player = (Player) sender;
            if(!State.isState(State.ENDING)) {
                player.sendMessage("§cError: You can only use this when the game is ending.");
                return true;
            }

            player.getInventory().setItem(0, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(1, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(2, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(3, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(5, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(6, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(7, new ItemStack(Material.WATER_BUCKET));
            player.getInventory().setItem(8, new ItemStack(Material.WATER_BUCKET));

            Vector direction = player.getLocation().getDirection().multiply(7);
            direction.setY(direction.getY() + 0.5);
            player.setVelocity(direction);

            player.sendMessage(PrefixUtils.PREFIX  + "MLG!");

            if(ScenarioManager.getInstance().getScenario("nofall").isEnabled()) {
                ScenarioManager.getInstance().getScenario("nofalll").setEnabled(false);
                Game.broadcast(PrefixUtils.SCENARIOS + "Disabled the scenario §eNoFall §ffor the MLG!");
            }
        }
        return true;
    }
}
