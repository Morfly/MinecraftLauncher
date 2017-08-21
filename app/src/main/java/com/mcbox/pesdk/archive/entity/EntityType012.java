package com.mcbox.pesdk.archive.entity;

import java.util.HashMap;
import java.util.Map;

public enum EntityType012 {
    SQUID(17, Squid.class),
    BAT(19, Bat.class),
    IRON_GOLEM(20, IronGolem.class),
    SNOW_GOLEM(21, SnowGolem.class),
    CAVE_SPIDER(40, CaveSpider.class),
    GHAST(41, Ghast.class),
    LAVA_SLIME(42, LavaSlime.class),
    BLAZE(43, Blaze.class),
    ZOMBIE_VILLAGER(44, ZombieVillager.class),
    OCELOT(22, Ocelot.class);
    
    public static Map<Class<? extends Entity>, EntityType012> classMap;
    public static Map<Integer, EntityType012> idMap;
    private Class<? extends Entity> entityClass;
    private int id;

    static {
        idMap = new HashMap();
        classMap = new HashMap();
        EntityType012[] values = values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            EntityType012 entityType012 = values[i];
            idMap.put(Integer.valueOf(entityType012.getId()), entityType012);
            if (entityType012.getEntityClass() != null) {
                classMap.put(entityType012.getEntityClass(), entityType012);
            }
            i++;
        }
    }

    private EntityType012(int i, Class<? extends Entity> cls) {
        this.id = i;
        this.entityClass = cls;
    }

    public Class<? extends Entity> getEntityClass() {
        return this.entityClass;
    }

    public int getId() {
        return this.id;
    }
}
