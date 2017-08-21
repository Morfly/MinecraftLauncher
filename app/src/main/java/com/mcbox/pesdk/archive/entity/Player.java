package com.mcbox.pesdk.archive.entity;

import com.mcbox.pesdk.archive.InventorySlot;
import com.mcbox.pesdk.archive.ItemStack;

import java.util.List;

public class Player extends LivingEntity {
    private PlayerAbilities abilities = new PlayerAbilities();
    private List<ItemStack> armorSlots;
    private PlayerAttributes attributes = new PlayerAttributes();
    private int bedPositionX = 0;
    private int bedPositionY = 0;
    private int bedPositionZ = 0;
    private int dimension;
    private List<InventorySlot> inventory;
    private int playerLevel = 0;
    private float playerLevelProgress = 0.0f;
    private int score;
    private short sleepTimer = (short) 0;
    private boolean sleeping = false;
    private int spawnX = 0;
    private int spawnY = 64;
    private int spawnZ = 0;

    public PlayerAbilities getAbilities() {
        return this.abilities;
    }

    public List<ItemStack> getArmor() {
        return this.armorSlots;
    }

    public PlayerAttributes getAttributes() {
        return this.attributes;
    }

    public int getBedPositionX() {
        return this.bedPositionX;
    }

    public int getBedPositionY() {
        return this.bedPositionY;
    }

    public int getBedPositionZ() {
        return this.bedPositionZ;
    }

    public int getDimension() {
        return this.dimension;
    }

    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

    public List<InventorySlot> getInventory() {
        return this.inventory;
    }

    public int getPlayerLevel() {
        return this.playerLevel;
    }

    public float getPlayerLevelProgress() {
        return this.playerLevelProgress;
    }

    public int getScore() {
        return this.score;
    }

    public short getSleepTimer() {
        return this.sleepTimer;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    public int getSpawnY() {
        return this.spawnY;
    }

    public int getSpawnZ() {
        return this.spawnZ;
    }

    public boolean isSleeping() {
        return this.sleeping;
    }

    public void setAbilities(PlayerAbilities playerAbilities) {
        this.abilities = playerAbilities;
    }

    public void setArmor(List<ItemStack> list) {
        this.armorSlots = list;
    }

    public void setAttributes(PlayerAttributes playerAttributes) {
        this.attributes = playerAttributes;
    }

    public void setBedPositionX(int i) {
        this.bedPositionX = i;
    }

    public void setBedPositionY(int i) {
        this.bedPositionY = i;
    }

    public void setBedPositionZ(int i) {
        this.bedPositionZ = i;
    }

    public void setDimension(int i) {
        this.dimension = i;
    }

    public void setInventory(List<InventorySlot> list) {
        this.inventory = list;
    }

    public void setPlayerLevel(int i) {
        this.playerLevel = i;
    }

    public void setPlayerLevelProgress(float f) {
        this.playerLevelProgress = f;
    }

    public void setScore(int i) {
        this.score = i;
    }

    public void setSleepTimer(short s) {
        this.sleepTimer = s;
    }

    public void setSleeping(boolean z) {
        this.sleeping = z;
    }

    public void setSpawnX(int i) {
        this.spawnX = i;
    }

    public void setSpawnY(int i) {
        this.spawnY = i;
    }

    public void setSpawnZ(int i) {
        this.spawnZ = i;
    }
}
