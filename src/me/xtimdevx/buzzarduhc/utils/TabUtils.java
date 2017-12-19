package me.xtimdevx.buzzarduhc.utils;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

/**
 * Created by xTimDevx on 6/10/2017.
 */
public class TabUtils {

    public static BukkitRunnable tabTimber;


    public static void setTabList(Player player) {

        final CraftPlayer craft = (CraftPlayer) player;

        IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a(
                "{text:'§bScyleUHC §8- §f§o@ScyleNetwork" +
                        "\n§8» §fPing: " + GameUtils.getPing(player) + " §8«\n'}"
        );

        IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a(
                "{text:'\n§8» " + (GameUtils.getTeamMode().equalsIgnoreCase("ffa") ? "§fFFA" : "§fcTo" + Main.teamlimit) + " §8«\n§8» §f" + GameUtils.getEnabledScenarios().replace("§b", "§f") + " §8«" +
                        "\n§8» §f" + Bukkit.getOnlinePlayers().size() + "§8/§f" + Bukkit.getMaxPlayers() + " §8«   » §f" + Settings.getData().getString("game.host") + " §8«'}"
        );

        final PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(headerJSON);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, footerJSON);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("§cCould not send tab list packets to " + player.getName());
            return;
        }

        craft.getHandle().playerConnection.sendPacket(headerPacket);
    }
}

