package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzardworld.managers.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by xTimDevx on 30/10/2017.
 */
public class Dungeoneering extends Scenario implements CommandExecutor{

    public Dungeoneering() {
        super("Dungeoneering", "Caves and dungeons generate more often, but there is less iron/gold/diamonds, TNT is also an ore at lower levels, and there is no bedrock floor.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("createdungeon")) {
            if(!sender.isOp()) {
                return true;
            }
            long seed = Long.valueOf(Main.getRandom(0, 500000000));
                WorldManager.getInstance().createWorld("Dungeoneering", 2000, seed, World.Environment.NORMAL, WorldType.NORMAL);
            }
        sender.sendMessage("§cDungeoneering §8» §fCreating a dungeoneering world.");
        return true;
    }
}
