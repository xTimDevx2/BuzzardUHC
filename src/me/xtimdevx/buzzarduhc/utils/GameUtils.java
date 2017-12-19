package me.xtimdevx.buzzarduhc.utils;

import comp.xtimdevx.buzzardhavex.Game;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class GameUtils {
    private static GameUtils instance = new GameUtils();

    public  static GameUtils getInstance() {
        return instance;
    }
    public static void broadcast(String message) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(message);
        }
    }

    public static String getTeamMode() {
        return Settings.getData().getString("team.mode");
    }

    public static void sendAction(Player player, String msg) {
        CraftPlayer craft = (CraftPlayer) player;

        IChatBaseComponent actionJSON = IChatBaseComponent.ChatSerializer.a("{text:'" + msg + "'}");
        PacketPlayOutChat actionPacket = new PacketPlayOutChat(actionJSON, (byte) 2);

        craft.getHandle().playerConnection.sendPacket(actionPacket);
    }
    public static void removeEffects() {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            online.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            online.removePotionEffect(PotionEffectType.INVISIBILITY);
            online.setMaxHealth(20);
        }
    }
    public static String getEnabledScenarios() {
        StringBuilder list = new StringBuilder();
        int i = 1;

        for (Scenario scen : ScenarioManager.getInstance().getScenarios()) {
            if (scen.isEnabled()) {
                if (list.length() > 0) {
                    if (i == ScenarioManager.getInstance().getScenarios().size()) {
                        list.append("§8 and §f");
                    } else {
                        list.append("§8, §f");
                    }
                }
                i++;
                list.append(scen.getName());
            }
        }
        return list.toString();
    }
    public static String getEnabledScenariosShort() {
        StringBuilder list = new StringBuilder();
        int i = 1;

        for (Scenario scen : ScenarioManager.getInstance().getScenarios()) {
            if (scen.isEnabled()) {
                if (list.length() > 0) {
                    if (i == ScenarioManager.getInstance().getScenarios().size()) {
                        list.append("§8 and §f");
                    } else {
                        list.append("§8, §f");
                    }
                }
                i++;
                list.append(ScenarioUtils.getShortScenario(scen));
            }
        }
        return list.toString();
    }

    public static void enableArena() {
        Settings.getData().set("arena.enabled", true);
    }

    public static void disableArena() {
        Settings.getData().set("arena.enabled", false);
    }

    public static void setMuted(boolean mute) {
        Settings.getData().set("muted", mute);
        Settings.getInstance().saveData();
    }

    public static int getPing(Player player)
    {
        CraftPlayer craft = (CraftPlayer)player;
        return craft.getHandle().ping;
    }

    public static boolean hasEnough(Player player, Material material, int entered) {
        int total = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                continue;
            }

            if (item.getType() == material) {
                total = total + item.getAmount();
            }
        }

        return total >= entered;
    }

    public static void broadcastGameinfo() {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage("§8§m--------------]§r§b§lSCYLEMC§8§m[--------------");
            online.sendMessage("§8» §fWelcome in this game hosted on §bScyleMC§f.");
            online.sendMessage("§8» ");
            online.sendMessage("§8» §fHost §8- §b" + Settings.getData().get("game.host"));
            online.sendMessage("§8» ");
            online.sendMessage("§8» §fTeamsize §8- §b"  + (Settings.getData().get("team.mode").equals("FFA") ? "Free for all" : "Chosen team of " + Main.teamlimit));
            online.sendMessage("§8» ");
            online.sendMessage("§8» §fScenarios §8- §b" + GameUtils.getEnabledScenarios().replace("§b", "§f"));
            online.sendMessage("§8§m------------------------------------------");

        }
    }

    public static void broadcastGameinfo(Player player) {
        player.sendMessage("§8§m--------------]§r§b§lSCYLEMC§8§m[--------------");
        player.sendMessage("§8» §fWelcome in this game hosted on §bScyleMC§f.");
        player.sendMessage("§8» ");
        player.sendMessage("§8» §fHost §8- §b" + Settings.getData().get("game.host"));
        player.sendMessage("§8» ");
        player.sendMessage("§8» §fTeamsize §8- §b"  + (Settings.getData().get("team.mode").equals("FFA") ? "Free for all" : "Chosen team of " + Main.teamlimit));
        player.sendMessage("§8» ");
        player.sendMessage("§8» §fScenarios §8- §b" + GameUtils.getEnabledScenarios().replace("§b", "§f"));
        player.sendMessage("§8§m------------------------------------------");
    }

    public static boolean isMuted() {
        return Settings.getData().getBoolean("muted", false);
    }
}
