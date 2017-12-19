package me.xtimdevx.buzzarduhc.commands;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.misc.ScoreBoard;
import me.xtimdevx.buzzarduhc.misc.Teams;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public class TeamCommand implements CommandExecutor, TabCompleter{


    public static HashMap<Player, List<Player>> invites = new HashMap<Player, List<Player>>();
    public static HashMap<String, List<String>> savedTeams = new HashMap<String, List<String>>();

    public String PREFIX = PrefixUtils.TEAMS;

    @SuppressWarnings({ "static-access", "deprecation" })
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Teams teams = Teams.getInstance();
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args.length > 1) {
            Player target = Bukkit.getServer().getPlayer(args[1]);

            if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("uhc.team")) {
                    sendHelp(sender);
                    return true;
                }

                if (target == null) {
                    sender.sendMessage(PrefixUtils.PREFIX + args[1] + " is not online.");
                    return true;
                }

                Team team = target.getScoreboard().getTeam(target.getName());

                if (!savedTeams.containsKey(team.getName())) {
                    ArrayList<String> players = new ArrayList<String>(team.getEntries());
                    TeamCommand.savedTeams.put(team.getName(), players);
                }

                StringBuilder list = new StringBuilder("");
                int i = 1;

                for (String entry : savedTeams.get(team.getName())) {
                    if (list.length() > 0) {
                        if (i == savedTeams.get(team.getName()).size()) {
                            list.append(" §fand §f");
                        } else {
                            list.append("§8, §7");
                        }
                    }

                    OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry);

                    if (teammates.isOnline()) {
                        list.append(ChatColor.GREEN + teammates.getName());
                    } else {
                        list.append(ChatColor.RED + teammates.getName());
                    }
                    i++;
                }

                sender.sendMessage(PREFIX + ChatColor.GREEN + target.getName() + "'s §fteam info:");
                sender.sendMessage(PREFIX + "Team: " + team.getPrefix() + team.getName());
                sender.sendMessage(PREFIX + "Teammates §8- §f§o(Names in red means they are offline)");
                sender.sendMessage(PREFIX + " " + list.toString().trim());
                return true;
            }

            if (args[0].equalsIgnoreCase("invite")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "Only players can create and manage teams.");
                    return true;
                }
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                Player player = (Player) sender;

                if (!Settings.getInstance().getData().get("team.mode").equals("Team")) {
                    sender.sendMessage(PREFIX + "Team management is currently disabled.");
                    return true;
                }

                Team team = player.getScoreboard().getEntryTeam(sender.getName());

                if (team == null) {
                    sender.sendMessage(PREFIX + "You are not on a team.");
                    return true;
                }

                if (target == null) {
                    sender.sendMessage(PREFIX + args[1] + " is not online.");
                    return true;
                }

                if (team.getSize() >= Settings.getData().getInt("team.limit")) {
                    sender.sendMessage(PREFIX + "Your team is currently full.");
                    return true;
                }

                Team team1 = player.getScoreboard().getEntryTeam(target.getName());

                if (team1 != null) {
                    sender.sendMessage(PREFIX + "That player is already on a team.");
                    return true;
                }

                teams.sendMessage(team, PREFIX+ ChatColor.RED + target.getName() + " §fhas been invited to your team.");

                if (!invites.containsKey(sender)) {
                    invites.put(player, new ArrayList<Player>());
                }
                invites.get(sender).add(target);
                target.sendMessage(PREFIX + "You have been invited to §9" + sender.getName() + "'s §fteam.");
                target.sendTitle("§9Teams", "§fYou have been invited to a team.");

                ComponentBuilder builder = new ComponentBuilder("");
                builder.append(PREFIX + "§9§oClick here to accept his request.");
                builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + sender.getName()));
                builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { new TextComponent("§f§oClick here to join the team") }));
                target.spigot().sendMessage(builder.create());
                return true;
            }

            if (args[0].equalsIgnoreCase("kick")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can create and manage teams.");
                    return true;
                }

                Player player = (Player) sender;
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                if (!Settings.getInstance().getData().get("team.mode").equals("Team")) {
                    sender.sendMessage(PREFIX + "Team management is currently disabled.");
                    return true;
                }

                Team team = player.getScoreboard().getEntryTeam(sender.getName());

                if (team == null) {
                    sender.sendMessage(PREFIX + "You are not on a team.");
                    return true;
                }

                if (target == null) {
                    sender.sendMessage(PREFIX + args[1] + " is not online.");
                    return true;
                }

                if (!team.getEntries().contains(target.getName())) {
                    sender.sendMessage(PREFIX + "That player is not on your team.");
                    return true;
                }

                team.removeEntry(target.getName());
                target.sendMessage(PREFIX + "You got kicked out of your team.");

                ArrayList<String> players = new ArrayList<String>(team.getEntries());
                TeamCommand.savedTeams.put(team.getName(), players);
                teams.sendMessage(team, PREFIX + ChatColor.RED + target.getName() + " §fwas kicked from your team.");
                return true;
            }

            if (args[0].equalsIgnoreCase("accept")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can create and manage teams.");
                    return true;
                }
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                Player player = (Player) sender;

                if (!Settings.getInstance().getData().get("team.mode").equals("Team")) {
                    sender.sendMessage(PREFIX + "Team management is currently disabled.");
                    return true;
                }

                if (target == null) {
                    sender.sendMessage(PREFIX + args[1] + " is not online.");
                    return true;
                }

                if (player.getScoreboard().getEntryTeam(player.getName()) != null) {
                    sender.sendMessage(PREFIX + "You are already on a team.");
                    return true;
                }

                if (invites.containsKey(target) && invites.get(target).contains(sender)) {
                    Team team = target.getScoreboard().getEntryTeam(target.getName());

                    if (team == null) {
                        sender.sendMessage(PREFIX + "That player is not on a team.");
                        return true;
                    }

                    if (team.getSize() >= Settings.getData().getInt("team.limit")) {
                        sender.sendMessage(PREFIX + "That team is currently full.");
                        return true;
                    }

                    sender.sendMessage(PREFIX + "Request accepted.");
                    team.addEntry(sender.getName());

                    teams.sendMessage(team, PREFIX + ChatColor.RED + sender.getName() + " §fjoined your team.");

                    ArrayList<String> players = new ArrayList<String>(team.getEntries());
                    TeamCommand.savedTeams.put(team.getName(), players);

                    invites.get(target).remove(sender);
                } else {
                    sender.sendMessage(PREFIX + ChatColor.RED + target.getName() + " §fhasn't sent you any requests.");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("deny")) {
                if (!Settings.getInstance().getData().get("team.mode").equals("Team")) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Team management is currently disabled.");
                    return true;
                }
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + args[1] + " is not online.");
                    return true;
                }

                if (invites.containsKey(target) && invites.get(target).contains(sender)) {
                    target.sendMessage(PREFIX + ChatColor.RED + sender.getName() + " §fdenied your request.");
                    sender.sendMessage(PREFIX + "Request denied.");

                    invites.get(target).remove(sender);
                } else {
                    sender.sendMessage(PREFIX + ChatColor.RED + target.getName() + " §fhasn't sent you any requests.");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("uhc.team")) {
                    sendHelp(sender);
                    return true;
                }
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                if (target == null) {
                    OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);

                    Team team = teams.getTeam(offline);

                    if (team == null) {
                        sender.sendMessage(ChatColor.RED + args[1] + " is not online.");
                        return true;
                    }

                    sender.sendMessage(PREFIX + ChatColor.RED + offline.getName() + " §fwas removed from his team.");
                    teams.leaveTeam(offline);

                    ArrayList<String> players = new ArrayList<String>(team.getEntries());
                    TeamCommand.savedTeams.put(team.getName(), players);
                    return true;
                }

                Team team = teams.getTeam(target);

                if (team == null) {
                    sender.sendMessage(PrefixUtils.PREFIX + "That player is not on a team.");
                    return true;
                }

                sender.sendMessage(PREFIX + ChatColor.RED + target.getName() + " §fwas removed from his team.");
                teams.leaveTeam(target);

                ArrayList<String> players = new ArrayList<String>(team.getEntries());
                savedTeams.put(team.getName(), players);
                return true;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                if (!sender.hasPermission("uhc.team")) {
                    sendHelp(sender);
                    return true;
                }
                if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                    sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                    return true;
                }
                Team team = teams.getTeam(args[1]);

                if (team == null) {
                    sender.sendMessage(PREFIX + "That team does not exist.");
                    return true;
                }

                for (String p : team.getEntries()) {
                    team.removeEntry(p);
                }

                ArrayList<String> players = new ArrayList<String>(team.getEntries());
                TeamCommand.savedTeams.put(team.getName(), players);

                sender.sendMessage(PREFIX + "Team §9" + team.getName() + " §fhas been deleted.");
                return true;
            }

            if (args[0].equalsIgnoreCase("friendlyfire")) {
                if (!sender.hasPermission("uhc.team")) {
                    sendHelp(sender);
                    return true;
                }

                boolean enable;

                if (args[1].equalsIgnoreCase("true")) {
                    enable = true;
                }
                else if (args[1].equalsIgnoreCase("false")) {
                    enable = false;
                }
                else {
                    sender.sendMessage(PREFIX + "Friendlyfire can only be §9true §for §9false§f.");
                    return true;
                }

                for (Team team : ScoreBoard.getInstance().board.getTeams()) {
                    team.setAllowFriendlyFire(enable);
                }

                Bukkit.getServer().broadcastMessage(PREFIX + "FriendlyFire is now " + (enable ? "§aenabled§f." : "§cdisabled§f."));
                return true;
            }
        }

        if (args.length > 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("uhc.team")) {
                    sendHelp(sender);
                    return true;
                }

                Team team = teams.getTeam(args[1]);

                if (team == null) {
                    sender.sendMessage(PREFIX + "That team does not exist.");
                    return true;
                }

                OfflinePlayer offline = Bukkit.getOfflinePlayer(args[2]);

                teams.joinTeam(team, offline);

                ArrayList<String> players = new ArrayList<String>(team.getEntries());
                TeamCommand.savedTeams.put(team.getName(), players);

                sender.sendMessage(PREFIX + ChatColor.RED + offline.getName() + "§f was added to team §9" + team.getName() + "§f.");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can create and manage teams.");
                return true;
            }

            Player player = (Player) sender;
            if(State.isState(State.INGAME) && Settings.getData().getBoolean("team.crossteaming") == false) {
                sender.sendMessage(PrefixUtils.PREFIX + "Crossteaming is disabled.");
                return true;
            }
            if (!Settings.getData().get("team.mode").equals("Team")) {
                sender.sendMessage(PREFIX + "Team management is currently disabled.");
                return true;
            }

            if (teams.getTeam(player) != null) {
                sender.sendMessage(PREFIX + "You are already on a team.");
                return true;
            }

            Team team = teams.findAvailableTeam();

            if (team == null) {
                sender.sendMessage(PREFIX + "There are no more available teams.");
                return true;
            }

            teams.joinTeam(team, player);

            ArrayList<String> players = new ArrayList<String>(team.getEntries());
            TeamCommand.savedTeams.put(team.getName(), players);

            sender.sendMessage(PREFIX + "Team created! Use §9/team invite <player>§f to invite a player.");
            return true;
        }

        if (args[0].equalsIgnoreCase("leave")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can create and manage teams.");
                return true;
            }

            Player player = (Player) sender;

            if (!Settings.getData().get("team.mode").equals("Team")) {
                sender.sendMessage(PREFIX + "Team management is currently disabled.");
                return true;
            }

            Team team = player.getScoreboard().getEntryTeam(sender.getName());

            if (team == null) {
                sender.sendMessage(PREFIX + "You are not on a team.");
                return true;
            }

            sender.sendMessage(PREFIX + "You left your team.");
            teams.leaveTeam(player);

            ArrayList<String> players = new ArrayList<String>(team.getEntries());
            TeamCommand.savedTeams.put(team.getName(), players);

            teams.sendMessage(team, PREFIX + sender.getName() + " left your team.");
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can create and manage teams.");
                return true;
            }

            Player player = (Player) sender;

            Team team = teams.getTeam(player);

            if (team == null) {
                sender.sendMessage(PREFIX + "You are not on a team.");
                return true;
            }

            if (!savedTeams.containsKey(team.getName())) {
                ArrayList<String> players = new ArrayList<String>(team.getEntries());
                TeamCommand.savedTeams.put(team.getName(), players);
            }

            StringBuilder list = new StringBuilder("");
            int i = 1;

            for (String entry : savedTeams.get(team.getName())) {
                if (list.length() > 0) {
                    if (i == savedTeams.get(team.getName()).size()) {
                        list.append(" §ffand §f");
                    } else {
                        list.append("§8, §7");
                    }
                }

                OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry);

                if (teammates.isOnline()) {
                    list.append(ChatColor.GREEN + teammates.getName());
                } else {
                    list.append(ChatColor.RED + teammates.getName());
                }
                i++;
            }

            sender.sendMessage(PREFIX + "Your teammates: §o(Names in red means they are offline)");
            sender.sendMessage(PREFIX + "" + list.toString().trim());
            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (!sender.hasPermission("uhc.team")) {
                sendHelp(sender);
                return true;
            }

            if (sender.hasPermission("uhc.team")) {
                for (Team team : ScoreBoard.getInstance().board.getTeams()) {
                    for (String p : team.getEntries()) {
                        team.removeEntry(p);
                    }
                }

                for (String key : savedTeams.keySet()) {
                    savedTeams.get(key).clear();
                }

                Bukkit.getServer().broadcastMessage(PREFIX + "All teams has been cleared.");
            } else {
                sendHelp(sender);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("uhc.team")) {
                sendHelp(sender);
                return true;
            }

            Bukkit.getServer().broadcastMessage(PREFIX + "All teams has been re-colored.");
            teams.setup();
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (teams.getTeamsWithPlayers().size() == 0) {
                sender.sendMessage(PREFIX + "There are no teams.");
                return true;
            }

            sender.sendMessage(PREFIX + "List of teams:");

            for (Team team : teams.getTeamsWithPlayers()) {
                StringBuilder list = new StringBuilder("");
                int i = 1;

                for (String entry : team.getEntries()) {
                    if (list.length() > 0) {
                        if (i == team.getEntries().size()) {
                            list.append(" and ");
                        } else {
                            list.append(", ");
                        }
                    }

                    list.append(entry);
                    i++;
                }

                sender.sendMessage(team.getPrefix() + team.getName() + ": §7" + list.toString().trim() + ".");
            }
            return true;
        }

        sendHelp(sender);
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> toReturn = new ArrayList<String>();

        if (args.length == 1) {
            ArrayList<String> types = new ArrayList<String>();
            types.add("create");
            types.add("invite");
            types.add("kick");
            types.add("accept");
            types.add("deny");
            types.add("info");
            types.add("list");

            if (sender.hasPermission("uhc.team")) {
                types.add("clear");
                types.add("add");
                types.add("remove");
                types.add("delete");
                types.add("friendlyfire");
            }

            if (args[0].equals("")) {
                for (String type : types) {
                    toReturn.add(type);
                }
            } else {
                for (String type : types) {
                    if (type.toLowerCase().startsWith(args[0].toLowerCase())) {
                        toReturn.add(type);
                    }
                }
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equals("")) {
                    for (Team teams : Teams.getInstance().getTeams()) {
                        toReturn.add(teams.getName());
                    }
                } else {
                    for (Team teams : Teams.getInstance().getTeams()) {
                        if (teams.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            toReturn.add(teams.getName());
                        }
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                if (args[1].equals("")) {
                    for (Team teams : Teams.getInstance().getTeams()) {
                        toReturn.add(teams.getName());
                    }
                } else {
                    for (Team teams : Teams.getInstance().getTeams()) {
                        if (teams.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            toReturn.add(teams.getName());
                        }
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("friendlyfire")) {
                toReturn.add("true");
                toReturn.add("false");
            } else {
                if (args[1].equals("")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        toReturn.add(online.getName());
                    }
                } else {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (online.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            toReturn.add(online.getName());
                        }
                    }
                }
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[2].equals("")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        toReturn.add(online.getName());
                    }
                } else {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (online.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                            toReturn.add(online.getName());
                        }
                    }
                }
            }
        }

        return toReturn;
    }

    /**
     * Sends the help list to a player.
     *
     * @param sender the player.
     */
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(PREFIX + "Team command:");
        sender.sendMessage(PREFIX + "/pm <message> §8-§f §oTalk in team chat.");
        sender.sendMessage(PREFIX + "/sendcoords §8-§f §oTell your coords to your teammates.");

        if (Settings.getData().get("team.mode").equals("Team")) {
            sender.sendMessage(PREFIX + "/team create §8-§f §oCreate a team.");
            sender.sendMessage(PREFIX + "/team leave §8-§f §oLeave your team.");
            sender.sendMessage(PREFIX + "/team invite <player> §8-§f §oInvite a player to your team.");
            sender.sendMessage(PREFIX + "/team kick <player> §8-§f §oKick a player to your team.");
            sender.sendMessage(PREFIX + "/team accept <player> §8-§f §oAccept the players request.");
            sender.sendMessage(PREFIX + "/team deny <player> §8-§f §oDeny the players request.");
        }

        if (sender.hasPermission("uhc.command.team")) {
            sender.sendMessage(PREFIX + "Team admin help:");
            sender.sendMessage(PREFIX + "/team info <player> §8-§f §oDisplay the targets team info.");
            sender.sendMessage(PREFIX + "/team add <team> <player> §8-§f §oAdd a player to a team.");
            sender.sendMessage(PREFIX + "/team remove <player> §8-§f §oRemove a player from his team.");
            sender.sendMessage(PREFIX + "/team delete <team> §8-§f §oEmpty a specific team.");
            sender.sendMessage(PREFIX + "/team friendlyfire <true|false> §8-§f §oToggle FriendlyFire.");
            sender.sendMessage(PREFIX + "/team clear §8-§f §oClear all teams.");
        }
    }
}
