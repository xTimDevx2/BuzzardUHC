package me.xtimdevx.buzzarduhc.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class Teams {

    private Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
    private List<Team> teams = new ArrayList<Team>();

    private static Teams manager = new Teams();

    public static Teams getInstance() {
        return manager;
    }

    public void setup() {
        ArrayList<String> list = new ArrayList<String>();
        teams.clear();
        list.add(ChatColor.DARK_BLUE.toString());
        list.add(ChatColor.DARK_GREEN.toString());
        list.add(ChatColor.DARK_AQUA.toString());
        list.add(ChatColor.DARK_RED.toString());
        list.add(ChatColor.DARK_PURPLE.toString());
        list.add(ChatColor.GOLD.toString());
        list.add(ChatColor.GRAY.toString());
        list.add(ChatColor.BLUE.toString());
        list.add(ChatColor.GREEN.toString());
        list.add(ChatColor.AQUA.toString());
        list.add(ChatColor.RED.toString());
        list.add(ChatColor.LIGHT_PURPLE.toString());
        list.add(ChatColor.YELLOW.toString());

        Collections.shuffle(list);

        ArrayList<String> tempList = new ArrayList<String>();

        for (String li : list) {
            tempList.add(li + ChatColor.ITALIC);
        }

        for (String li : list) {
            tempList.add(li + ChatColor.BOLD);
        }

        for (String li : list) {
            tempList.add(li + ChatColor.UNDERLINE);
        }

        for (String li : list) {
            tempList.add(li + ChatColor.STRIKETHROUGH);
        }

        tempList.remove(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString());

        list.addAll(tempList);
        Team spec = (sb.getTeam("spec") == null ? sb.registerNewTeam("spec") : sb.getTeam("spec"));
        spec.setDisplayName("spec");
        spec.setPrefix("o");
        spec.setSuffix("f");

        spec.setAllowFriendlyFire(false);
        spec.setCanSeeFriendlyInvisibles(true);
        spec.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

        for (int i = 0; i < list.size(); i++) {
            String teamName = "UHC" + (i + 1);

            Team team = (sb.getTeam(teamName) == null ? sb.registerNewTeam(teamName) : sb.getTeam(teamName));
            team.setDisplayName(teamName);
            team.setPrefix(list.get(i));
            team.setSuffix("");

            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(true);
            team.setNameTagVisibility(NameTagVisibility.ALWAYS);

            teams.add(team);
        }

        Bukkit.getLogger().info("[ScyleUHC] Setup " + (teams.size() + 1) + " teams.");
    }

    public Team findAvailableTeam() {
        for (Team team : getTeams()) {
            if (team.getSize() == 0) {
                return team;
            }
        }
        return null;
    }
    public void joinTeam(Team team, OfflinePlayer player) {
        if (team != null) {
            team.addEntry(player.getName());
        }
    }

    public void joinTeam(String name, OfflinePlayer player) {
        Team team = sb.getTeam(name);

        if (team != null) {
            team.addEntry(player.getName());
        }
    }

    public void leaveTeam(OfflinePlayer player) {
        Team team = ((Player) player).getScoreboard().getEntryTeam(player.getName());

        if (team != null) {
            team.removeEntry(player.getName());
        }
    }
    public void sendMessage(Team team, String message) {
        for (String entry : team.getEntries()) {
            Player teammate = Bukkit.getServer().getPlayer(entry);

            if (teammate != null) {
                teammate.sendMessage(message);
            }
        }
    }
    public Set<OfflinePlayer> getPlayers(Team team) {
        return team.getPlayers();
    }

    public Team getTeam(OfflinePlayer player) {
        return sb.getEntryTeam(player.getName());
    }
    public Team getTeam(String name) {
        return sb.getEntryTeam(name);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Team> getTeamsWithPlayers() {
        ArrayList<Team> list = new ArrayList<Team>();

        for (Team team : teams) {
            if (team.getSize() > 0) {
                list.add(team);
            }
        }

        return list;
    }
}
