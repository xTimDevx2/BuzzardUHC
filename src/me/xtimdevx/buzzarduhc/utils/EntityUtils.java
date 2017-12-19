package me.xtimdevx.buzzarduhc.utils;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.Set;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class EntityUtils {

    protected static final Set<EntityType> clearable = ImmutableSet.of(
            EntityType.ARROW, EntityType.BAT, EntityType.BLAZE, EntityType.BOAT, EntityType.CAVE_SPIDER,
            EntityType.CREEPER, EntityType.DROPPED_ITEM, EntityType.EGG, EntityType.ENDERMAN, EntityType.ENDERMITE,
            EntityType.ENDER_PEARL, EntityType.EXPERIENCE_ORB, EntityType.FIREBALL, EntityType.FISHING_HOOK,
            EntityType.GHAST, EntityType.GIANT, EntityType.GUARDIAN, EntityType.IRON_GOLEM, EntityType.MAGMA_CUBE,
            EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG_ZOMBIE, EntityType.PRIMED_TNT,
            EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SMALL_FIREBALL,
            EntityType.SNOWBALL, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.VILLAGER, EntityType.WITCH,
            EntityType.WITHER, EntityType.WITHER_SKULL, EntityType.ZOMBIE
    );
    public static boolean isButcherable(EntityType type) {
        return clearable.contains(type);
    }

    public static Vector randomOffset() {
        Random rand = new Random();

        double offsetX = rand.nextDouble() / 16;
        double offsetZ = rand.nextDouble() / 16;

        offsetX = offsetX - (rand.nextDouble() / 16);
        offsetZ = offsetZ - (rand.nextDouble() / 16);

        return new Vector(offsetX, 0.2, offsetZ);
    }
}
