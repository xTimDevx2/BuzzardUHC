package me.xtimdevx.buzzarduhc.arena;

import com.avaje.ebeaninternal.server.lib.sql.Prefix;
import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import me.xtimdevx.buzzarduhc.utils.LocationUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import me.xtimdevx.buzzarduhc.utils.ScatterUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.persistence.Basic;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xTimDevx on 11/10/2017.
 */
public class ArenaCommand implements CommandExecutor{

    private List<Player> players = new ArrayList<Player>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("arena")) {
            if(args.length == 0) {
                if(Settings.getData().get("arena.enabled") != true) {
                    player.sendMessage(PrefixUtils.ARENA + "§cArena §fis not §cenabled§f.");
                    return true;
                }
                if(players.contains(player)) {
                    player.sendMessage(PrefixUtils.PREFIX + "Your already in arena, §c/arena leave §fto leave.");
                    return true;
                }
                Location loc;
                try {
                    loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC_arena"), (int) Bukkit.getWorld("UHC_arena").getWorldBorder().getSize() / 3, 1).get(0);
                }catch (Exception e) {
                    player.sendMessage("§cCould not teleport you to the arena.");
                    return true;
                }
                player.sendMessage(PrefixUtils.ARENA + "You joined the pregame arena.");
                player.sendMessage(PrefixUtils.ARENA + "Loaded kit: §cUHC§f.");

                players.add(player);
                ArenaUtils.giveKit(player);
                loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 2);

                player.teleport(loc);
                player.setGameMode(GameMode.SURVIVAL);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 7));
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(PrefixUtils.ARENA + "Arena commands:");
                    player.sendMessage("§8» §f/arena §8- §f§oJoin the arena.");
                    player.sendMessage("§8» §f/arena leave §8- §fLeave the arena.");
                    player.sendMessage("§8» §f/arena stats §8- §f§oReset the arena.");
                    if(player.hasPermission("uhc.staff")) {
                        player.sendMessage(" §8» §fStaff:");
                        player.sendMessage(" §8» §f/arena enable §8- §f§oEnable the arena.");
                        player.sendMessage(" §8» §f/arena disable §8- §f§oDisable the arena.");
                        player.sendMessage(" §8» §f/arena reset §8- §f§oReset the arena.");
                        player.sendMessage(" §8» §f/arena help §8- §f§oShow this menu.");
                    }
                }
                if(args[0].equalsIgnoreCase("leave")) {
                    players.remove(player);

                    player.sendMessage(PrefixUtils.ARENA + "You left the pregame arena.");
                    Game.teleport("hub", player);

                    if(player.isDead()) {
                        player.spigot().respawn();
                    }
                }
                if(args[0].equalsIgnoreCase("stats")) {
                    player.sendMessage("§cThis feature is coming soon.");
                }
                if(args[0].equalsIgnoreCase("enable")){
                    if(!player.hasPermission("uhc.staff")) {
                        player.sendMessage(PrefixUtils.NO_PERM_MSG);
                        return true;
                    }
                    GameUtils.broadcast(PrefixUtils.ARENA + "The §carena §fhas been §aenabled§f. §8[§c§o/arena§8]");
                    GameUtils.enableArena();
                }
                if(args[0].equalsIgnoreCase("disable")) {
                    if(!player.hasPermission("uhc.staff")) {
                        player.sendMessage(PrefixUtils.NO_PERM_MSG);
                        return true;
                    }
                    GameUtils.broadcast(PrefixUtils.ARENA + "The §carena §fhas been §cdisabled§f.");
                    GameUtils.disableArena();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        if(players.contains(online)) {
                            Game.teleport("hub", online);
                            online.getInventory().clear();
                            online.getInventory().setArmorContents(null);
                        }
                    }
                }
            }
        }
        return true;
    }
}
