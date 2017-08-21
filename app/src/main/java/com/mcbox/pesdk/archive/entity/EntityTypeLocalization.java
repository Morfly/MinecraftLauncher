package com.mcbox.pesdk.archive.entity;

import com.mcbox.pesdk.C1687R;

import java.util.EnumMap;
import java.util.Map;

public class EntityTypeLocalization {
    public static Map<EntityType, Integer> namesMap = new EnumMap(EntityType.class);

    static {
        initNamesMap();
    }

    private EntityTypeLocalization() {
    }

    public static void addNewNamesMap012() {
        namesMap.put(EntityType.IRON_GOLEM, Integer.valueOf(C1687R.string.entity_IRON_GOLEM));
        namesMap.put(EntityType.SNOW_GOLEM, Integer.valueOf(C1687R.string.entity_SNOW_GOLEM));
        namesMap.put(EntityType.OCELOT, Integer.valueOf(C1687R.string.entity_OCELOT));
        namesMap.put(EntityType.SQUID, Integer.valueOf(C1687R.string.entity_SQUID));
        namesMap.put(EntityType.BAT, Integer.valueOf(C1687R.string.entity_BAT));
        namesMap.put(EntityType.CAVE_SPIDER, Integer.valueOf(C1687R.string.entity_CAVE_SPIDER));
        namesMap.put(EntityType.GHAST, Integer.valueOf(C1687R.string.entity_GHAST));
        namesMap.put(EntityType.LAVA_SLIME, Integer.valueOf(C1687R.string.entity_LAVA_SLIME));
        namesMap.put(EntityType.BLAZE, Integer.valueOf(C1687R.string.entity_BLAZE));
        namesMap.put(EntityType.ZOMBIE_VILLAGER, Integer.valueOf(C1687R.string.entity_ZOMBIE_VILLAGER));
    }

    public static void addNewNamesMap013() {
        namesMap.put(EntityType.RABBIT, Integer.valueOf(C1687R.string.entity_RABBIT));
    }

    public static void addNewNamesMap014() {
        namesMap.put(EntityType.WITCH, Integer.valueOf(C1687R.string.entity_WITCH));
    }

    public static void addNewNamesMap015() {
        namesMap.put(EntityType.STRAY, Integer.valueOf(C1687R.string.entity_STRAY));
        namesMap.put(EntityType.HUSK, Integer.valueOf(C1687R.string.entity_HUSK));
        namesMap.put(EntityType.HORSE, Integer.valueOf(C1687R.string.entity_HORSE));
        namesMap.put(EntityType.SKELETON_HORSE, Integer.valueOf(C1687R.string.entity_SKELETON_HORSE));
        namesMap.put(EntityType.ZOMBIE_HORSE, Integer.valueOf(C1687R.string.entity_ZOMBIE_HORSE));
        namesMap.put(EntityType.MULE, Integer.valueOf(C1687R.string.entity_MULE));
        namesMap.put(EntityType.DONKEY, Integer.valueOf(C1687R.string.entity_DONKEY));
    }

    public static void initNamesMap() {
        namesMap.clear();
        namesMap.put(EntityType.CHICKEN, Integer.valueOf(C1687R.string.entity_chicken));
        namesMap.put(EntityType.COW, Integer.valueOf(C1687R.string.entity_cow));
        namesMap.put(EntityType.PIG, Integer.valueOf(C1687R.string.entity_pig));
        namesMap.put(EntityType.SHEEP, Integer.valueOf(C1687R.string.entity_sheep));
        namesMap.put(EntityType.ZOMBIE, Integer.valueOf(C1687R.string.entity_zombie));
        namesMap.put(EntityType.CREEPER, Integer.valueOf(C1687R.string.entity_creeper));
        namesMap.put(EntityType.SKELETON, Integer.valueOf(C1687R.string.entity_skeleton));
        namesMap.put(EntityType.SPIDER, Integer.valueOf(C1687R.string.entity_spider));
        namesMap.put(EntityType.PIG_ZOMBIE, Integer.valueOf(C1687R.string.entity_pigzombie));
        namesMap.put(EntityType.UNKNOWN, Integer.valueOf(C1687R.string.entity_unknown));
        namesMap.put(EntityType.ITEM, Integer.valueOf(C1687R.string.entity_item));
        namesMap.put(EntityType.PRIMED_TNT, Integer.valueOf(C1687R.string.entity_primedtnt));
        namesMap.put(EntityType.FALLING_BLOCK, Integer.valueOf(C1687R.string.entity_falling_block));
        namesMap.put(EntityType.ARROW, Integer.valueOf(C1687R.string.entity_arrow));
        namesMap.put(EntityType.SNOWBALL, Integer.valueOf(C1687R.string.entity_snowball));
        namesMap.put(EntityType.EGG, Integer.valueOf(C1687R.string.entity_egg));
        namesMap.put(EntityType.PAINTING, Integer.valueOf(C1687R.string.entity_painting));
        namesMap.put(EntityType.PLAYER, Integer.valueOf(C1687R.string.entity_player));
        namesMap.put(EntityType.WOLF, Integer.valueOf(C1687R.string.entity_wolf));
        namesMap.put(EntityType.VILLAGER, Integer.valueOf(C1687R.string.entity_villager));
        namesMap.put(EntityType.MUSHROOM_COW, Integer.valueOf(C1687R.string.entity_mushroom_cow));
        namesMap.put(EntityType.SLIME, Integer.valueOf(C1687R.string.entity_slime));
        namesMap.put(EntityType.ENDERMAN, Integer.valueOf(C1687R.string.entity_enderman));
        namesMap.put(EntityType.SILVERFISH, Integer.valueOf(C1687R.string.entity_silverfish));
    }

    public static void removeByName(EntityType entityType) {
        namesMap.remove(entityType);
    }
}
