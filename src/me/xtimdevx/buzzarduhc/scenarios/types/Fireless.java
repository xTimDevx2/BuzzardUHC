package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by xTimDevx on 11/07/2017.
 */
public class Fireless extends Scenario{

    public Fireless() {
        super("Fireless", "Fire damage is disabled.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    public static void enableFireless() {
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 2, false, false));
        }
    }

    public static void enableFireless(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 2, false, false));
    }
}
