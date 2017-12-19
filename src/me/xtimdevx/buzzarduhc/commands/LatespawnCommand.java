package me.xtimdevx.buzzarduhc.commands;

import com.sun.org.apache.regexp.internal.RE;
import comp.xtimdevx.buzzardhavex.User;
import io.netty.channel.udt.UdtServerChannel;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.scenarios.types.Fireless;
import me.xtimdevx.buzzarduhc.scenarios.types.SuperHeroes;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xTimDevx on 28/06/2017.
 */
public class LatespawnCommand implements CommandExecutor{

    public static Map<String, String> latespawn = new HashMap<>();

    public static BukkitRunnable latespawntask;


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("latespawn")) {
            final Player player = (Player) sender;
            User user = User.get(player);
            if(!State.isState(State.INGAME)) {
                player.sendMessage(PrefixUtils.PREFIX + "You can only use this command when the game has started.");
                return true;
            }
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(args.length == 0) {
                player.sendMessage("§cUsage: /latespawn <player>");
                return true;
            }
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if(UHCCommand.ingame.containsKey(target.getName())) {
                    player.sendMessage(PrefixUtils.PREFIX + target.getName() + "is already ingame.");
                    return true;
                }
                if(!target.isOnline()) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.sendMessage(PrefixUtils.PREFIX + "Tell §b" + target.getName() + " §fto join the server so we can latespawn him!");
                    }
                    target.setWhitelisted(true);
                    latespawn.put(target.getName(), null);
                    latespawntask = new BukkitRunnable() {
                        public void run() {
                            if (latespawn.containsKey(player.getName())) {
                                player.setWhitelisted(false);
                                latespawn.remove(player.getName());
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    online.sendMessage("§c§l✖ §8» §c" + player.getName() + " §fdid not join in time.");
                                }
                            }
                        }
                    };
                    latespawntask.runTaskLater(Main.plugin, 6000);
                }else {
                    User userplayer = User.get(target);
                    userplayer.setMode(User.Mode.PLAYER);
                    LatespawnCommand.latespawn.remove(player.getName());
                    UHCCommand.ingame.put(player.getName(), null);
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(null);
                    Player host = Bukkit.getPlayer(Settings.getData().get("game.host").toString());
                    host.performCommand("scatter 1500 false " + player.getName());
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
                    if (ScenarioManager.getInstance().getScenario("fireless").isEnabled()) {
                        Fireless.enableFireless(player);
                    }
                    if (ScenarioManager.getInstance().getScenario("superheroes").isEnabled()) {
                        SuperHeroes.giveHeroesEffect(player);
                    }
                }
            }
        }
        return true;
    }
}
