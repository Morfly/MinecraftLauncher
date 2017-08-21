package com.mcbox.pesdk.archive;

import com.mcbox.pesdk.archive.entity.Entity;
import com.mcbox.pesdk.archive.entity.Player;
import com.mcbox.pesdk.archive.tileentity.TileEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private long dayCycleStopTime;
    private int dimension;
    private List<Entity> entities;
    private List<Object> extras;
    private int gameType;
    private int generator;
    private long lastPlayed;
    private String levelName;
    private int platform;
    private Player player;
    private long randomSeed;
    private long sizeOnDisk;
    private boolean spawnMobs;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private int storageVersion;
    private List<TileEntity> tileEntities;
    private long time;

    public Level() {
        this.dayCycleStopTime = -1;
        this.spawnMobs = true;
        this.dimension = 0;
        this.generator = 0;
        this.dayCycleStopTime = -1;
        this.spawnMobs = true;
        this.dimension = 0;
        this.generator = 0;
        this.extras = new ArrayList();
    }

    public long getDayCycleStopTime() {
        return this.dayCycleStopTime;
    }

    public int getDimension() {
        return this.dimension;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public List<Object> getExtraTags() {
        return this.extras;
    }

    public int getGameType() {
        return this.gameType;
    }

    public int getGenerator() {
        return this.generator;
    }

    public long getLastPlayed() {
        return this.lastPlayed;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public int getPlatform() {
        return this.platform;
    }

    public Player getPlayer() {
        return this.player;
    }

    public long getRandomSeed() {
        return this.randomSeed;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public boolean getSpawnMobs() {
        return this.spawnMobs;
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

    public int getStorageVersion() {
        return this.storageVersion;
    }

    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public long getTime() {
        return this.time;
    }

    public void setDayCycleStopTime(long j) {
        this.dayCycleStopTime = j;
    }

    public void setDimension(int i) {
        this.dimension = i;
    }

    public void setEntities(List<Entity> list) {
        this.entities = list;
    }

    public void setGameType(int i) {
        this.gameType = i;
    }

    public void setGenerator(int i) {
        this.generator = i;
    }

    public void setLastPlayed(long j) {
        this.lastPlayed = j;
    }

    public void setLevelName(String str) {
        this.levelName = str;
    }

    public void setPlatform(int i) {
        this.platform = i;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRandomSeed(long j) {
        this.randomSeed = j;
    }

    public void setSizeOnDisk(long j) {
        this.sizeOnDisk = j;
    }

    public void setSpawnMobs(boolean z) {
        this.spawnMobs = z;
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

    public void setStorageVersion(int i) {
        this.storageVersion = i;
    }

    public void setTileEntities(List<TileEntity> list) {
        this.tileEntities = list;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public String toString() {
        return "Level [gameType=" + this.gameType + ", lastPlayed=" + this.lastPlayed + ", levelName=" + this.levelName + ", platform=" + this.platform + ", player=" + this.player + ", randomSeed=" + this.randomSeed + ", sizeOnDisk=" + this.sizeOnDisk + ", spawnX=" + this.spawnX + ", spawnY=" + this.spawnY + ", spawnZ=" + this.spawnZ + ", storageVersion=" + this.storageVersion + ", time=" + this.time + ", dayCycleStopTime=" + this.dayCycleStopTime + ", spawnMobs=" + this.spawnMobs + ", dimension=" + this.dimension + ", generator=" + this.generator + ", entities=" + this.entities + ", tileEntities=" + this.tileEntities + "]";
    }
}
