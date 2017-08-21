package com.mcbox.pesdk.archive.io.nbt;

import com.mcbox.pesdk.archive.InventorySlot;
import com.mcbox.pesdk.archive.ItemStack;
import com.mcbox.pesdk.archive.Level;
import com.mcbox.pesdk.archive.entity.Chicken;
import com.mcbox.pesdk.archive.entity.Cow;
import com.mcbox.pesdk.archive.entity.Entity;
import com.mcbox.pesdk.archive.entity.EntityType;
import com.mcbox.pesdk.archive.entity.Item;
import com.mcbox.pesdk.archive.entity.Pig;
import com.mcbox.pesdk.archive.entity.Player;
import com.mcbox.pesdk.archive.entity.PlayerAbilities;
import com.mcbox.pesdk.archive.entity.PlayerAttributes;
import com.mcbox.pesdk.archive.entity.Sheep;
import com.mcbox.pesdk.archive.entity.Zombie;
import com.mcbox.pesdk.archive.io.EntityDataConverter.EntityData;
import com.mcbox.pesdk.archive.io.nbt.entity.EntityStore;
import com.mcbox.pesdk.archive.io.nbt.entity.EntityStoreLookupService;
import com.mcbox.pesdk.archive.io.nbt.tileentity.TileEntityStore;
import com.mcbox.pesdk.archive.io.nbt.tileentity.TileEntityStoreLookupService;
import com.mcbox.pesdk.archive.tileentity.TileEntity;
import com.mcbox.pesdk.archive.util.Vector3f;
import com.yy.hiidostatis.defs.obj.Elem;

import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.LongTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class NBTConverter {

    static final class C16881 implements Comparator<Tag> {
        C16881() {
        }

        public int compare(Tag tag, Tag tag2) {
            return tag.getName().compareTo(tag2.getName());
        }

        public boolean equals(Tag tag, Tag tag2) {
            return tag.getName().equals(tag2.getName());
        }
    }

    static final class C16892 implements Comparator<Tag> {
        C16892() {
        }

        public int compare(Tag tag, Tag tag2) {
            return tag.getName().compareTo(tag2.getName());
        }

        public boolean equals(Tag tag, Tag tag2) {
            return tag.getName().equals(tag2.getName());
        }
    }

    static final class C16903 implements Comparator<Tag> {
        C16903() {
        }

        public int compare(Tag tag, Tag tag2) {
            return tag.getName().compareTo(tag2.getName());
        }

        public boolean equals(Tag tag, Tag tag2) {
            return tag.getName().equals(tag2.getName());
        }
    }

    public static Entity createEntityById(int i) {
        switch (i) {
            case 10:
                return new Chicken();
            case 11:
                return new Cow();
            case 12:
                return new Pig();
            case 13:
                return new Sheep();
            case 32:
                return new Zombie();
            case 64:
                return new Item();
            default:
                EntityType byId = EntityType.getById(i);
                if (!(byId == null || byId.getEntityClass() == null)) {
                    try {
                        return (Entity) byId.getEntityClass().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("Unable to find entity class for entity type " + i);
                return new Entity();
        }
    }

    public static TileEntity createTileEntityById(String str) {
        return TileEntityStoreLookupService.createTileEntityById(str);
    }

    public static void readAbilities(CompoundTag compoundTag, PlayerAbilities playerAbilities) {
        for (Tag tag : compoundTag.getValue()) {
            String name = tag.getName();
            if (tag instanceof ByteTag) {
                boolean z = ((ByteTag) tag).getValue().byteValue() != (byte) 0;
                if (name.equals("flying")) {
                    playerAbilities.flying = z;
                } else if (name.equals("instabuild")) {
                    playerAbilities.instabuild = z;
                } else if (name.equals("invulnerable")) {
                    playerAbilities.invulnerable = z;
                } else if (name.equals("mayfly")) {
                    playerAbilities.mayFly = z;
                } else {
                    System.out.println("Unsupported tag in readAbilities: " + name);
                }
            } else {
                System.out.println("Unsupported tag in readAbilities: " + name);
            }
        }
    }

    public static List<ItemStack> readArmor(ListTag<CompoundTag> listTag) {
        List<ItemStack> arrayList = new ArrayList();
        for (CompoundTag readItemStack : listTag.getValue()) {
            arrayList.add(readItemStack(readItemStack));
        }
        return arrayList;
    }

    public static EntityData readEntities(CompoundTag compoundTag) {
        List list = null;
        List list2 = null;
        for (Tag tag : compoundTag.getValue()) {
            List list3;
            if (tag.getName().equals("Entities")) {
                List list4 = list;
                list = readEntitiesPart(((ListTag) tag).getValue());
                list3 = list4;
            } else if (tag.getName().equals("TileEntities")) {
                list3 = readTileEntitiesPart(((ListTag) tag).getValue());
                list = list2;
            } else {
                list3 = list;
                list = list2;
            }
            list2 = list;
            list = list3;
        }
        if (list == null) {
            list = new ArrayList();
        }
        return new EntityData(list2, list);
    }

    public static List<Entity> readEntitiesPart(List<CompoundTag> list) {
        List<Entity> arrayList = new ArrayList(list.size());
        for (CompoundTag compoundTag : list) {
            for (Tag tag : compoundTag.getValue()) {
                if (tag.getName().equals("id")) {
                    arrayList.add(readEntity(((IntTag) tag).getValue().intValue(), compoundTag));
                    break;
                }
            }
        }
        return arrayList;
    }

    public static Entity readEntity(int i, CompoundTag compoundTag) {
        Entity createEntityById = createEntityById(i);
        createEntityById.setEntityTypeId(i);
        EntityStore entityStore = (EntityStore) EntityStoreLookupService.idMap.get(Integer.valueOf(i));
        if (entityStore == null) {
            System.err.println("Warning: unknown entity id " + i + "; using default entity store.");
            entityStore = EntityStoreLookupService.defaultStore;
        }
        entityStore.load(createEntityById, compoundTag);
        return createEntityById;
    }

    public static List<InventorySlot> readInventory(ListTag<CompoundTag> listTag) {
        List<InventorySlot> arrayList = new ArrayList();
        for (CompoundTag readInventorySlot : listTag.getValue()) {
            arrayList.add(readInventorySlot(readInventorySlot));
        }
        return arrayList;
    }

    public static InventorySlot readInventorySlot(CompoundTag compoundTag) {
        List<Tag> value = compoundTag.getValue();
        ItemStack itemStack = new ItemStack();
        byte b = (byte) 0;
        for (Tag tag : value) {
            byte byteValue;
            if (tag.getName().equals("Slot")) {
                byteValue = ((ByteTag) tag).getValue().byteValue();
            } else if (tag.getName().equals("id")) {
                itemStack.setTypeId(((ShortTag) tag).getValue().shortValue());
                byteValue = b;
            } else if (tag.getName().equals("Damage")) {
                itemStack.setDurability(((ShortTag) tag).getValue().shortValue());
                byteValue = b;
            } else if (tag.getName().equals("Count")) {
                int byteValue2 = ((ByteTag) tag).getValue().byteValue();
                if (byteValue2 < 0) {
                    byteValue2 += 256;
                }
                itemStack.setAmount(byteValue2);
                byteValue = b;
            } else {
                itemStack.getExtras().add(tag);
                byteValue = b;
            }
            b = byteValue;
        }
        return new InventorySlot(b, itemStack);
    }

    public static ItemStack readItemStack(CompoundTag compoundTag) {
        List<Tag> value = compoundTag.getValue();
        ItemStack itemStack = new ItemStack();
        for (Tag tag : value) {
            if (tag.getName().equals("id")) {
                itemStack.setTypeId(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("Damage")) {
                itemStack.setDurability(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("Count")) {
                int byteValue = ((ByteTag) tag).getValue().byteValue();
                if (byteValue < 0) {
                    byteValue += 256;
                }
                itemStack.setAmount(byteValue);
            } else {
                itemStack.getExtras().add(tag);
            }
        }
        return itemStack;
    }

    public static Level readLevel(CompoundTag compoundTag) {
        StringBuffer stringBuffer = new StringBuffer();
        Level level = new Level();
        for (Tag tag : compoundTag.getValue()) {
            String name = tag.getName();
            stringBuffer.append(name + Elem.DIVIDER + tag.getValue().toString());
            stringBuffer.append("\t\n");
            if (name.equals("GameType")) {
                level.setGameType(((IntTag) tag).getValue().intValue());
            } else if (name.equals("LastPlayed")) {
                level.setLastPlayed(((LongTag) tag).getValue().longValue());
            } else if (name.equals("LevelName")) {
                level.setLevelName(((StringTag) tag).getValue());
            } else if (name.equals("Platform")) {
                level.setPlatform(((IntTag) tag).getValue().intValue());
            } else if (name.equals("Player")) {
                level.setPlayer(readPlayer((CompoundTag) tag));
            } else if (name.equals("RandomSeed")) {
                level.setRandomSeed(((LongTag) tag).getValue().longValue());
            } else if (name.equals("SizeOnDisk")) {
                level.setSizeOnDisk(((LongTag) tag).getValue().longValue());
            } else if (name.equals("SpawnX")) {
                level.setSpawnX(((IntTag) tag).getValue().intValue());
            } else if (name.equals("SpawnY")) {
                level.setSpawnY(((IntTag) tag).getValue().intValue());
            } else if (name.equals("SpawnZ")) {
                level.setSpawnZ(((IntTag) tag).getValue().intValue());
            } else if (name.equals("StorageVersion")) {
                level.setStorageVersion(((IntTag) tag).getValue().intValue());
            } else if (name.equals("Time")) {
                level.setTime(((LongTag) tag).getValue().longValue());
            } else if (name.equals("dayCycleStopTime")) {
                level.setDayCycleStopTime(((LongTag) tag).getValue().longValue());
            } else if (name.equals("spawnMobs")) {
                level.setSpawnMobs(((ByteTag) tag).getValue().byteValue() != (byte) 0);
            } else if (name.equals("Dimension")) {
                level.setDimension(((IntTag) tag).getValue().intValue());
            } else if (name.equals("Generator")) {
                level.setGenerator(((IntTag) tag).getValue().intValue());
            } else {
                System.out.println("Unhandled level tag: " + name + Elem.DIVIDER + tag);
                level.getExtraTags().add(tag);
            }
        }
        return level;
    }

    public static List<InventorySlot> readLoadout(CompoundTag compoundTag) {
        for (Tag tag : compoundTag.getValue()) {
            if (tag.getName().equals("Inventory")) {
                return readInventory((ListTag) tag);
            }
        }
        System.err.println("Why is this blank?!");
        return null;
    }

    public static Player readPlayer(CompoundTag compoundTag) {
        List<Tag> value = compoundTag.getValue();
        Player player = new Player();
        for (Tag tag : value) {
            String name = tag.getName();
            if (tag.getName().equals("Pos")) {
                player.setLocation(readVector((ListTag) tag));
            } else if (tag.getName().equals("Motion")) {
                player.setVelocity(readVector((ListTag) tag));
            } else if (tag.getName().equals("Air")) {
                player.setAirTicks(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("Fire")) {
                player.setFireTicks(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("FallDistance")) {
                player.setFallDistance(((FloatTag) tag).getValue().floatValue());
            } else if (tag.getName().equals("Rotation")) {
                List value2 = ((ListTag) tag).getValue();
                player.setYaw(((FloatTag) value2.get(0)).getValue().floatValue());
                player.setPitch(((FloatTag) value2.get(1)).getValue().floatValue());
            } else if (tag.getName().equals("OnGround")) {
                player.setOnGround(((ByteTag) tag).getValue().byteValue() > (byte) 0);
            } else if (tag.getName().equals("AttackTime")) {
                player.setAttackTime(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("DeathTime")) {
                player.setDeathTime(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("Health")) {
                player.setHealth(((ShortTag) tag).getValue().shortValue());
            } else if (tag.getName().equals("HurtTime")) {
                player.setHurtTime(((ShortTag) tag).getValue().shortValue());
            } else if (name.equals("Armor")) {
                player.setArmor(readArmor((ListTag) tag));
            } else if (name.equals("BedPositionX")) {
                player.setBedPositionX(((IntTag) tag).getValue().intValue());
            } else if (name.equals("BedPositionY")) {
                player.setBedPositionY(((IntTag) tag).getValue().intValue());
            } else if (name.equals("BedPositionZ")) {
                player.setBedPositionZ(((IntTag) tag).getValue().intValue());
            } else if (tag.getName().equals("Dimension")) {
                player.setDimension(((IntTag) tag).getValue().intValue());
            } else if (tag.getName().equals("Inventory")) {
                player.setInventory(readInventory((ListTag) tag));
            } else if (tag.getName().equals("Score")) {
                player.setScore(((IntTag) tag).getValue().intValue());
            } else if (tag.getName().equals("Sleeping")) {
                player.setSleeping(((ByteTag) tag).getValue().byteValue() != (byte) 0);
            } else if (name.equals("SleepTimer")) {
                player.setSleepTimer(((ShortTag) tag).getValue().shortValue());
            } else if (name.equals("SpawnX")) {
                player.setSpawnX(((IntTag) tag).getValue().intValue());
            } else if (name.equals("SpawnY")) {
                player.setSpawnY(((IntTag) tag).getValue().intValue());
            } else if (name.equals("SpawnZ")) {
                player.setSpawnZ(((IntTag) tag).getValue().intValue());
            } else if (name.equals("abilities")) {
                readAbilities((CompoundTag) tag, player.getAbilities());
            } else if (name.equals("Riding")) {
                player.setRiding(readSingleEntity((CompoundTag) tag));
            } else if (name.equals(PlayerAttributes.TAG_NAME)) {
                player.setAttributes(PlayerAttributes.fromTag((ListTag) tag));
            } else if (name.equals("PlayerLevel")) {
                player.setPlayerLevel(((IntTag) tag).getValue().intValue());
            } else if (name.equals("PlayerLevelProgress")) {
                player.setPlayerLevelProgress(((FloatTag) tag).getValue().floatValue());
            } else if (!name.equals("id")) {
                player.getExtraTags().add(tag);
            }
        }
        return player;
    }

    public static Entity readSingleEntity(CompoundTag compoundTag) {
        for (Tag tag : compoundTag.getValue()) {
            if (tag.getName().equals("id")) {
                return readEntity(((IntTag) tag).getValue().intValue(), compoundTag);
            }
        }
        return null;
    }

    public static TileEntity readSingleTileEntity(CompoundTag compoundTag) {
        for (Tag tag : compoundTag.getValue()) {
            if (tag.getName().equals("id")) {
                return readTileEntity(((StringTag) tag).getValue(), compoundTag);
            }
        }
        return null;
    }

    public static List<TileEntity> readTileEntitiesPart(List<CompoundTag> list) {
        List<TileEntity> arrayList = new ArrayList(list.size());
        for (CompoundTag compoundTag : list) {
            for (Tag tag : compoundTag.getValue()) {
                if (tag.getName().equals("id")) {
                    arrayList.add(readTileEntity(((StringTag) tag).getValue(), compoundTag));
                    break;
                }
            }
        }
        return arrayList;
    }

    public static TileEntity readTileEntity(String str, CompoundTag compoundTag) {
        TileEntity createTileEntityById = createTileEntityById(str);
        createTileEntityById.setId(str);
        TileEntityStore storeById = TileEntityStoreLookupService.getStoreById(str);
        if (storeById == null) {
            System.err.println("Warning: unknown tile entity id " + str + "; using default tileentity store.");
            storeById = TileEntityStoreLookupService.defaultStore;
        }
        storeById.load(createTileEntityById, compoundTag);
        return createTileEntityById;
    }

    public static Vector3f readVector(ListTag<FloatTag> listTag) {
        List value = listTag.getValue();
        return new Vector3f(((FloatTag) value.get(0)).getValue().floatValue(), ((FloatTag) value.get(1)).getValue().floatValue(), ((FloatTag) value.get(2)).getValue().floatValue());
    }

    public static CompoundTag writeAbilities(PlayerAbilities playerAbilities, String str) {
        byte b = (byte) 1;
        List arrayList = new ArrayList(4);
        arrayList.add(new ByteTag("flying", playerAbilities.flying ? (byte) 1 : (byte) 0));
        arrayList.add(new ByteTag("instabuild", playerAbilities.instabuild ? (byte) 1 : (byte) 0));
        arrayList.add(new ByteTag("invulnerable", playerAbilities.invulnerable ? (byte) 1 : (byte) 0));
        String str2 = "mayfly";
        if (!playerAbilities.mayFly) {
            b = (byte) 0;
        }
        arrayList.add(new ByteTag(str2, b));
        return new CompoundTag(str, arrayList);
    }

    public static ListTag<CompoundTag> writeArmor(List<ItemStack> list, String str) {
        List arrayList = new ArrayList(list.size());
        for (ItemStack writeItemStack : list) {
            arrayList.add(writeItemStack(writeItemStack, ""));
        }
        return new ListTag(str, CompoundTag.class, arrayList);
    }

    public static CompoundTag writeEntities(List<Entity> list, List<TileEntity> list2) {
        List arrayList = new ArrayList(list.size());
        for (Entity writeEntity : list) {
            arrayList.add(writeEntity(writeEntity));
        }
        ListTag listTag = new ListTag("Entities", CompoundTag.class, arrayList);
        arrayList = new ArrayList(list2.size());
        for (TileEntity writeTileEntity : list2) {
            arrayList.add(writeTileEntity(writeTileEntity));
        }
        ListTag listTag2 = new ListTag("TileEntities", CompoundTag.class, arrayList);
        arrayList = new ArrayList(2);
        arrayList.add(listTag);
        arrayList.add(listTag2);
        return new CompoundTag("", arrayList);
    }

    public static CompoundTag writeEntity(Entity entity) {
        return writeEntity(entity, "");
    }

    public static CompoundTag writeEntity(Entity entity, String str) {
        int entityTypeId = entity.getEntityTypeId();
        EntityStore entityStore = (EntityStore) EntityStoreLookupService.idMap.get(Integer.valueOf(entityTypeId));
        if (entityStore == null) {
            System.err.println("Warning: unknown entity id " + entityTypeId + "; using default entity store.");
            entityStore = EntityStoreLookupService.defaultStore;
        }
        List save = entityStore.save(entity);
        Collections.sort(save, new C16892());
        return new CompoundTag(str, save);
    }

    public static ListTag<CompoundTag> writeInventory(List<InventorySlot> list, String str) {
        List arrayList = new ArrayList(list.size());
        for (InventorySlot writeInventorySlot : list) {
            arrayList.add(writeInventorySlot(writeInventorySlot));
        }
        return new ListTag(str, CompoundTag.class, arrayList);
    }

    public static CompoundTag writeInventorySlot(InventorySlot inventorySlot) {
        List arrayList = new ArrayList(4);
        ItemStack contents = inventorySlot.getContents();
        arrayList.add(new ByteTag("Count", (byte) contents.getAmount()));
        arrayList.add(new ShortTag("Damage", contents.getDurability()));
        arrayList.add(new ByteTag("Slot", inventorySlot.getSlot()));
        arrayList.add(new ShortTag("id", contents.getTypeId()));
        if (contents.getExtras().size() > 0) {
            arrayList.addAll(contents.getExtras());
        }
        return new CompoundTag("", arrayList);
    }

    public static CompoundTag writeItemStack(ItemStack itemStack, String str) {
        List arrayList = new ArrayList(3);
        arrayList.add(new ByteTag("Count", (byte) itemStack.getAmount()));
        arrayList.add(new ShortTag("Damage", itemStack.getDurability()));
        arrayList.add(new ShortTag("id", itemStack.getTypeId()));
        if (itemStack.getExtras().size() > 0) {
            arrayList.addAll(itemStack.getExtras());
        }
        return new CompoundTag(str, arrayList);
    }

    public static CompoundTag writeLevel(Level level) {
        byte b = (byte) 0;
        List arrayList = new ArrayList(11);
        arrayList.add(new LongTag("dayCycleStopTime", level.getDayCycleStopTime()));
        if (!Integer.valueOf(0).equals(Integer.valueOf(level.getGenerator()))) {
            arrayList.add(new IntTag("GameType", level.getGameType()));
            arrayList.add(new IntTag("Generator", level.getGenerator()));
        }
        arrayList.add(new LongTag("LastPlayed", level.getLastPlayed()));
        arrayList.add(new StringTag("LevelName", level.getLevelName()));
        arrayList.add(new IntTag("Platform", level.getPlatform()));
        if (level.getPlayer() != null) {
            arrayList.add(writePlayer(level.getPlayer(), "Player"));
        }
        arrayList.add(new LongTag("RandomSeed", level.getRandomSeed()));
        arrayList.add(new LongTag("SizeOnDisk", level.getSizeOnDisk()));
        arrayList.add(new IntTag("SpawnX", level.getSpawnX()));
        arrayList.add(new IntTag("SpawnY", level.getSpawnY()));
        arrayList.add(new IntTag("SpawnZ", level.getSpawnZ()));
        arrayList.add(new IntTag("StorageVersion", level.getStorageVersion()));
        arrayList.add(new LongTag("Time", level.getTime()));
        String str = "spawnMobs";
        if (level.getSpawnMobs()) {
            b = (byte) 1;
        }
        arrayList.add(new ByteTag(str, b));
        arrayList.add(new IntTag("Dimension", level.getDimension()));
        for (Object add : level.getExtraTags()) {
            arrayList.add(add);
        }
        return new CompoundTag("", arrayList);
    }

    public static CompoundTag writeLevel(Level level, boolean z) {
        List arrayList = new ArrayList(11);
        arrayList.add(new LongTag("dayCycleStopTime", level.getDayCycleStopTime()));
        arrayList.add(new IntTag("GameType", level.getGameType()));
        arrayList.add(new IntTag("Generator", level.getGenerator()));
        arrayList.add(new LongTag("LastPlayed", level.getLastPlayed()));
        arrayList.add(new StringTag("LevelName", level.getLevelName()));
        arrayList.add(new IntTag("Platform", level.getPlatform()));
        if (level.getPlayer() != null) {
            arrayList.add(writePlayer(level.getPlayer(), "Player"));
        }
        arrayList.add(new LongTag("RandomSeed", level.getRandomSeed()));
        arrayList.add(new LongTag("SizeOnDisk", level.getSizeOnDisk()));
        arrayList.add(new IntTag("SpawnX", level.getSpawnX()));
        arrayList.add(new IntTag("SpawnY", level.getSpawnY()));
        arrayList.add(new IntTag("SpawnZ", level.getSpawnZ()));
        arrayList.add(new IntTag("StorageVersion", level.getStorageVersion()));
        arrayList.add(new LongTag("Time", level.getTime()));
        arrayList.add(new ByteTag("spawnMobs", level.getSpawnMobs() ? (byte) 1 : (byte) 0));
        arrayList.add(new IntTag("Dimension", level.getDimension()));
        for (Object add : level.getExtraTags()) {
            arrayList.add(add);
        }
        return new CompoundTag("", arrayList);
    }

    public static CompoundTag writeLoadout(List<InventorySlot> list) {
        return new CompoundTag("", Collections.<Tag>singletonList(writeInventory(list, "Inventory")));
    }

    public static CompoundTag writePlayer(Player player, String str) {
        return writePlayer(player, str, false);
    }

    public static CompoundTag writePlayer(Player player, String str, boolean z) {
        byte b = (byte) 1;
        List arrayList = new ArrayList();
        arrayList.add(new ShortTag("Air", player.getAirTicks()));
        arrayList.add(new FloatTag("FallDistance", player.getFallDistance()));
        arrayList.add(new ShortTag("Fire", player.getFireTicks()));
        arrayList.add(writeVector(player.getVelocity(), "Motion"));
        arrayList.add(writeVector(player.getLocation(), "Pos"));
        List arrayList2 = new ArrayList(2);
        arrayList2.add(new FloatTag("", player.getYaw()));
        arrayList2.add(new FloatTag("", player.getPitch()));
        arrayList.add(new ListTag("Rotation", FloatTag.class, arrayList2));
        arrayList.add(new ByteTag("OnGround", player.isOnGround() ? (byte) 1 : (byte) 0));
        arrayList.add(new ShortTag("AttackTime", player.getAttackTime()));
        arrayList.add(new ShortTag("DeathTime", player.getDeathTime()));
        arrayList.add(new ShortTag("Health", player.getHealth()));
        arrayList.add(new ShortTag("HurtTime", player.getHurtTime()));
        if (player.getArmor() != null) {
            arrayList.add(writeArmor(player.getArmor(), "Armor"));
        }
        arrayList.add(new IntTag("BedPositionX", player.getBedPositionX()));
        arrayList.add(new IntTag("BedPositionY", player.getBedPositionY()));
        arrayList.add(new IntTag("BedPositionZ", player.getBedPositionZ()));
        arrayList.add(new IntTag("Dimension", player.getDimension()));
        arrayList.add(writeInventory(player.getInventory(), "Inventory"));
        arrayList.add(new IntTag("Score", player.getScore()));
        String str2 = "Sleeping";
        if (!player.isSleeping()) {
            b = (byte) 0;
        }
        arrayList.add(new ByteTag(str2, b));
        arrayList.add(new ShortTag("SleepTimer", player.getSleepTimer()));
        arrayList.add(new IntTag("SpawnX", player.getSpawnX()));
        arrayList.add(new IntTag("SpawnY", player.getSpawnY()));
        arrayList.add(new IntTag("SpawnZ", player.getSpawnZ()));
        arrayList.add(writeAbilities(player.getAbilities(), "abilities"));
        if (player.getRiding() != null) {
            arrayList.add(writeEntity(player.getRiding(), "Riding"));
        }
        if (z) {
            arrayList.add(new IntTag("id", EntityType.PLAYER.getId()));
        }
        arrayList.add(new IntTag("PlayerLevel", player.getPlayerLevel()));
        arrayList.add(new FloatTag("PlayerLevelProgress", player.getPlayerLevelProgress()));
        arrayList.add(player.getAttributes().toTag());
        for (Object add : player.getExtraTags()) {
            arrayList.add(add);
        }
        Collections.sort(arrayList, new C16881());
        return new CompoundTag(str, arrayList);
    }

    public static CompoundTag writeTileEntity(TileEntity tileEntity) {
        String id = tileEntity.getId();
        TileEntityStore storeById = TileEntityStoreLookupService.getStoreById(id);
        if (storeById == null) {
            System.err.println("Warning: unknown tileentity id " + id + "; using default tileentity store.");
            storeById = TileEntityStoreLookupService.defaultStore;
        }
        List save = storeById.save(tileEntity);
        Collections.sort(save, new C16903());
        return new CompoundTag("", save);
    }

    public static ListTag<FloatTag> writeVector(Vector3f vector3f, String str) {
        List arrayList = new ArrayList(3);
        arrayList.add(new FloatTag("", vector3f.getX()));
        arrayList.add(new FloatTag("", vector3f.getY()));
        arrayList.add(new FloatTag("", vector3f.getZ()));
        return new ListTag(str, FloatTag.class, arrayList);
    }
}
