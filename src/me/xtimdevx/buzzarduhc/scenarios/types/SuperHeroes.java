package me.xtimdevx.buzzarduhc.scenarios.types;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Created by xTimDevx on 16/07/2017.
 */
public class SuperHeroes extends Scenario{

    public SuperHeroes() {
        super("Superheroes", "Everyone gets a special superpower.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}


    public static void giveHeroesEffect(Player player) {
        Random r = new Random();
        int random = r.nextInt(6);
        if (random == 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0, true, true));
            player.sendMessage("§eSuperheroes §8» §fThe effect §espeed §fis now your superpower.");
        }
        if (random == 1) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 0, true, true));
            player.sendMessage("§eSuperheroes §8» §fThe effect §efire resistance §fis now your superpower.");
        }
        if (random == 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 0, true, true));
            player.sendMessage("§eSuperheroes §8» §fThe effect §estrenght §fis now your superpower.");
        }
        if (random == 3) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 0, true, true));
            player.sendMessage("§eSuperheroes §8» §fThe effect §ejumpboost §fis now your superpower.");
        }
        if (random == 4) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 0, true, true));
            player.sendMessage("§eSuperheroes §8» §fThe effect §eresistance §fis now your superpower.");
        }
        if (random == 5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000, 4, true, true));
            player.setHealth(40);
            player.sendMessage("§eSuperheroes §8» §fThe effect §ehealthboost §fis now your superpower.");
        }
    }
}
