package me.xtimdevx.buzzarduhc.scenarios.types;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import sun.nio.cs.US_ASCII;

/**
 * Created by xTimDevx on 14/07/2017.
 */
public class Teaminventory extends Scenario implements CommandExecutor{

    public Teaminventory() {
        super("Teaminventory", "Use /ti to upen op a team shared inventory.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!ScenarioManager.getInstance().getScenario("teaminventory").isEnabled()) {
                    sender.sendMessage(PrefixUtils.PREFIX + "§cTeaminventory §fis not enabled.");
                    return true;
                }
                Player player = (Player) sender;
                Team team = ScoreBoard.getInstance().board.getPlayerTeam(player);

                if (team == null) {
                    sender.sendMessage(PrefixUtils.PREFIX + "You are not on a team.");
                    return true;
                }

                if (!Main.getTeamInvs().containsKey(team)) {
                    Main.getTeamInvs().put(team, Bukkit.createInventory(player, 4 * 9, "§fTeam Inventory §8- §5" + team.getName()));
                }

                player.openInventory(Main.getTeamInvs().get(team));
            }
            return true;
        }
        if(args.length == 1) {
            if (!ScenarioManager.getInstance().getScenario("teaminventory").isEnabled()) {
                sender.sendMessage(PrefixUtils.PREFIX + "§cTeaminventory §fis not enabled.");
                return true;
            }
            Player player = (Player) sender;
            User user = User.get(player);
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            Team team = ScoreBoard.getInstance().board.getPlayerTeam(target);
            if(user.getMode() != User.Mode.HOST && user.getMode() != User.Mode.SPECTATOR) {
                player.sendMessage(PrefixUtils.NO_HOST_MSG);
                return true;
            }
            if(team == null) {
                sender.sendMessage(PrefixUtils.PREFIX + "§c" + target.getName() + " §fis not on a team.");
                return true;
            }

            if (!Main.getTeamInvs().containsKey(team)) {
                player.sendMessage(PrefixUtils.PREFIX + "§c" + target.getName() + " §fdoes not have a teaminventory yet.");
                return true;
            }

            player.openInventory(Main.getTeamInvs().get(team));
            player.sendMessage(PrefixUtils.PREFIX + "You opened §c" + target.getName() + "'s §fteaminventory. §8(§c" + team.getName() + "§8)");
        }
        return true;
    }
}
