package me.xtimdevx.buzzarduhc.events;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by xTimDevx on 24/08/2017.
 */
public class ServerEvents implements Listener{

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd("§bScyleUHC §8» §fFollow us on twitter, §b@ScyleNetwork§f! §8[§bEU§8]\n§8» " + (GameUtils.getTeamMode().equalsIgnoreCase("ffa") ? "§8[§fFFA, "  + GameUtils.getEnabledScenarios().replace("§b", "§f") + "§8]" : "§8[§fcTo" + Main.teamlimit + ", "  + GameUtils.getEnabledScenarios().replace("§b", "§f") + "§8]"));
        event.setMaxPlayers(Settings.getData().getInt("game.slots"));
    }
}
