package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.State;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.GameUtils;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

/**
 * Created by xTimDevx on 15/10/2017.
 */
public class AchievementParanoia extends Scenario implements Listener{
    public static final String PREFIX = "§4AchParanoia §8» §f";

    public AchievementParanoia() {
        super("AchievementParanoia", "Achievements show up in chat like vanilla minecraft but at the end off each achievement it shows the coordinates of the player who earned that achievement.");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.awardAchievement(Achievement.OPEN_INVENTORY);
            online.removeAchievement(Achievement.MINE_WOOD);
        }
    }

    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        if(!State.isState(State.INGAME)) {
            return;
        }

        Achievement ach = event.getAchievement();
        Player player = event.getPlayer();

        GameUtils.broadcast(PREFIX + "§4" + player.getName() + " §fhas earned the achievement §4" + achievementName(ach) + " §fat " + locToString(player.getLocation()));
    }

    private String locToString(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ".";
    }

    private String achievementName(Achievement ach) {
        switch (ach) {
            case ACQUIRE_IRON:
                return "Acquire Hardware";
            case BAKE_CAKE:
                return "The lie";
            case BOOKCASE:
                return "Librarian";
            case BREED_COW:
                return "Repopulation";
            case BREW_POTION:
                return "Local Brewery";
            case BUILD_BETTER_PICKAXE:
                return "Getting an Upgrade";
            case BUILD_FURNACE:
                return "Hot Topic";
            case  BUILD_HOE:
                return "Time to Farm";
            case  BUILD_PICKAXE:
                return "Time to Mine";
            case BUILD_SWORD:
                return "Time to Strike";
            case COOK_FISH:
                return "Delicious Fish";
            case DIAMONDS_TO_YOU:
                return "Diamonds to you";
            case ENCHANTMENTS:
                return "Enchanter";
            case END_PORTAL:
                return "The End?";
            case EXPLORE_ALL_BIOMES:
                return "Adventure time";
            case FLY_PIG:
                return "When Pigs Fly";
            case FULL_BEACON:
                return "Beaconater";
            case GET_BLAZE_ROD:
                return "Into Fire";
            case GET_DIAMONDS:
                return "DIAMONDS";
            case GHAST_RETURN:
                return "Return to Sender";
            case KILL_COW:
                return "Cow Tipper";
            case KILL_ENEMY:
                return "Monster Hunster";
            case KILL_WITHER:
                return "The Beginning";
            case MAKE_BREAD:
                return "Bake Bread";
            case MINE_WOOD:
                return "Getting Wood";
            case NETHER_PORTAL:
                return "We Need to Go Deeper";
            case ON_A_RAIL:
                return "On A Rail";
            case OPEN_INVENTORY:
                return "Taking Inventory";
            case OVERKILL:
                return "Overkill";
            case OVERPOWERED:
                return "Overpowered";
            case SNIPE_SKELETON:
                return "Sniper Duel";
            case SPAWN_WITHER:
                return "The Beginning?";
            case THE_END:
                return "The end";
            default:
                return ach.name().toLowerCase().replace("_", " ");
        }
    }
}
