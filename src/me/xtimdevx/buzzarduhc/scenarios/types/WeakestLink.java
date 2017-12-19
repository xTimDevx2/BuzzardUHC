package me.xtimdevx.buzzarduhc.scenarios.types;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

/**
 * Created by xTimDevx on 21/07/2017.
 */
public class WeakestLink extends Scenario implements Listener, CommandExecutor{

    public WeakestLink() {
        super("Weakestlink", "Every 10 minutes the player with the lowest health will die.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    private static final Optional<GameMode> SPECTATOR_GAMEMODE_OPTIONAL;

    static {
        GameMode spectatorGameMode = null;

        try {
            spectatorGameMode = GameMode.valueOf("SPECTATOR");
        } catch (Exception ignored) {}

        SPECTATOR_GAMEMODE_OPTIONAL = Optional.fromNullable(spectatorGameMode);
    }

    private static final Ordering<Player> BY_HEALTH = new Ordering<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return Double.compare(p1.getHealth(), p2.getHealth());
        }
    };

    /**
     * Spectator predicate.
     */
    private static final Predicate<Player> IS_SPECTATOR = new Predicate<Player>() {
        @Override
        public boolean apply(Player player) {
            return SPECTATOR_GAMEMODE_OPTIONAL.isPresent() ? player.getGameMode() == SPECTATOR_GAMEMODE_OPTIONAL.get() : player.getGameMode() == GameMode.CREATIVE;
        }
    };

    private static final Predicate<Player> NOT_SPECTATOR = Predicates.not(IS_SPECTATOR);


    public static Player getLowestPlayer() {
        Player lowest = BY_HEALTH.min(Iterables.filter(Bukkit.getOnlinePlayers(), NOT_SPECTATOR));

        if (isAllAtSameHealth() || lowest == null) {
            return null;
        }

        return lowest;
    }

    /**
     * Check if all online players are at the same health.
     * <p>
     * Doesn't count players in gamemode 3 (or gm 1 in 1.7).
     *
     * @return True if they are, false otherwise.
     */
    private static boolean isAllAtSameHealth() {
        boolean isSameHealth = true;
        Player last = null;

        for (Player online : Iterables.filter(Bukkit.getOnlinePlayers(), NOT_SPECTATOR)) {
            if (last != null && last.getHealth() != online.getHealth()) {
                isSameHealth = false;
                break;
            }

            last = online;
        }

        return isSameHealth;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("weakestlink")) {
            if (!ScenarioManager.getInstance().getScenario("weakestlink").isEnabled()) {
                sender.sendMessage("§3Weakestlink §8» §3Weakest Link §fis not enabled.");
                return true;
            }

            Player lowest = getLowestPlayer();

            if (lowest == null) {
                sender.sendMessage("§3Weakestlink §8» §fEveryone is at the same health.");
                return true;
            }

            sender.sendMessage("§3Weakestlink §8» §fThe lowest player is: §3" + lowest.getName());
            return true;
        }
        return true;
    }
}
