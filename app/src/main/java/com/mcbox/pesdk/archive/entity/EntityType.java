package com.mcbox.pesdk.archive.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum EntityType {
    CHICKEN(10, Chicken.class),
    COW(11, Cow.class),
    PIG(12, Pig.class),
    SHEEP(13, Sheep.class),
    ZOMBIE(32, Zombie.class),
    CREEPER(33, Creeper.class),
    SKELETON(34, Skeleton.class),
    SPIDER(35, Spider.class),
    PIG_ZOMBIE(36, PigZombie.class),
    ITEM(64, Item.class),
    PRIMED_TNT(65, TNTPrimed.class),
    FALLING_BLOCK(66, FallingBlock.class),
    ARROW(80, Arrow.class),
    SNOWBALL(81, Snowball.class),
    EGG(82, Egg.class),
    PAINTING(83, Painting.class),
    MINECART(84, Minecart.class),
    UNKNOWN(-1, null),
    PLAYER(63, Player.class),
    WOLF(14, Wolf.class),
    VILLAGER(15, Villager.class),
    MUSHROOM_COW(16, MushroomCow.class),
    SLIME(37, Slime.class),
    ENDERMAN(38, Enderman.class),
    SILVERFISH(39, Silverfish.class),
    IRON_GOLEM(20, IronGolem.class),
    SNOW_GOLEM(21, SnowGolem.class),
    OCELOT(22, Ocelot.class),
    SQUID(17, Squid.class),
    BAT(19, Bat.class),
    CAVE_SPIDER(40, CaveSpider.class),
    GHAST(41, Ghast.class),
    LAVA_SLIME(42, LavaSlime.class),
    BLAZE(43, Blaze.class),
    ZOMBIE_VILLAGER(44, ZombieVillager.class),
    RABBIT(18, Rabbit.class),
    WITCH(45, Witch.class),
    STRAY(46, Stray.class),
    HUSK(47, Husk.class),
    HORSE(23, Horse.class),
    SKELETON_HORSE(26, SkeletonHorse.class),
    ZOMBIE_HORSE(27, ZombieHorse.class),
    MULE(25, Mule.class),
    DONKEY(24, Donkey.class);
    
    private static Map<Class<? extends Entity>, EntityType> classMap;
    private static Map<Integer, EntityType> idMap;
    private Class<? extends Entity> entityClass;
    private int id;

    static {
        idMap = new HashMap();
        classMap = new HashMap();
        Set keySet = EntityType012.idMap.keySet();
        EntityType[] values = values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            EntityType entityType = values[i];
            if (!keySet.contains(Integer.valueOf(entityType.getId()))) {
                idMap.put(Integer.valueOf(entityType.getId()), entityType);
                if (entityType.getEntityClass() != null) {
                    classMap.put(entityType.getEntityClass(), entityType);
                }
            }
            i++;
        }
    }

    private EntityType(int i, Class<? extends Entity> cls) {
        this.id = i;
        this.entityClass = cls;
    }

    public static void addNewEntityType012() {
        Set keySet = EntityType012.idMap.keySet();
        for (EntityType entityType : values()) {
            if (keySet.contains(Integer.valueOf(entityType.getId()))) {
                idMap.put(Integer.valueOf(entityType.getId()), entityType);
                if (entityType.getEntityClass() != null) {
                    classMap.put(entityType.getEntityClass(), entityType);
                }
            }
        }
    }

    public static EntityType getByClass(Class<? extends Entity> cls) {
        EntityType entityType = (EntityType) classMap.get(cls);
        return entityType == null ? UNKNOWN : entityType;
    }

    public static EntityType getById(int i) {
        EntityType entityType = (EntityType) idMap.get(Integer.valueOf(i));
        return entityType == null ? UNKNOWN : entityType;
    }

    public Class<? extends Entity> getEntityClass() {
        return this.entityClass;
    }

    public int getId() {
        return this.id;
    }
}
