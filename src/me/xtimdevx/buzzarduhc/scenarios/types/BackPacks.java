package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xTimDevx on 15/07/2017.
 */
public class BackPacks extends Scenario implements Listener, CommandExecutor{

    public BackPacks() {
        super("Backpacks", "You can do /bp to open up a private inventory.");

        Bukkit.getPluginCommand("bp").setExecutor(this);
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Block block = player.getLocation().add(0, -1, 0).getBlock();

        block.setType(Material.CHEST);
        block.getState().update();

        Chest chest = (Chest) block.getState();

        for (ItemStack item : player.getEnderChest().getContents()) {
            if (item == null) {
                continue;
            }

            chest.getInventory().addItem(item);
        }

        player.getEnderChest().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use backpacks.");
            return true;
        }

        Player player = (Player) sender;

        if (!ScenarioManager.getInstance().getScenario("backpacks").isEnabled()) {
            player.sendMessage(PrefixUtils.PREFIX + "§bBackbacks §fis not enabled.");
            return true;
        }

        if(!State.isState(State.INGAME)) {
            player.sendMessage("§cThe game has not started yet.");
            return true;
        }
        player.openInventory(player.getEnderChest());
        player.sendMessage(PrefixUtils.PREFIX + "You opened your backpack.");
        return true;
    }
}
