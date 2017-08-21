package com.mcbox.pesdk.archive;

import android.os.Environment;

import com.mcbox.pesdk.archive.entity.Player;
import com.mcbox.pesdk.archive.io.LevelDataConverter;
import com.mcbox.pesdk.archive.util.Vector3f;
import com.mcbox.pesdk.util.LauncherMiscUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SeedLevelManager {
    private static short airTick = (short) 300;
    private static short attackTime = (short) 0;
    private static boolean bSleep = false;
    private static boolean bSpawnMob = true;
    private static int bedPosX = 0;
    private static int bedPosY = 0;
    private static int bedPosZ = 0;
    private static long curTime = 0;
    private static short deathTime = (short) 0;
    private static int dimension = 0;
    private static short fallen = (short) 0;
    private static short fireTick = (short) -20;
    private static String folder = "/games/com.mojang/minecraftWorlds/";
    private static int generator = 1;
    private static short heath = (short) 20;
    private static short hurtTime = (short) 0;
    private static Vector3f location = new Vector3f(0.0f, 0.0f, 0.0f);
    private static float pitch = 0.0f;
    private static int platform = 2;
    private static int score = 0;
    private static short sleepTick = (short) 0;
    private static int spawnX;
    private static int spawnY;
    private static int spawnZ;
    private static int storageVersion = 4;
    private static long timePerDay = 78;
    private static Vector3f velocity = new Vector3f(-1.4E-45f, 0.0784f, -1.4E-45f);
    private static float yaw = 0.0f;

    static {
        spawnX = 0;
        spawnY = 0;
        spawnZ = 0;
        spawnX = 940;
        spawnY = 128;
        spawnZ = 24;
    }

    public static String createLevelBySeed(long j, String str, String str2, int i) {
        String str3 = (Environment.getExternalStorageDirectory().getAbsolutePath() + folder) + str;
        if (!LauncherMiscUtil.isNullStr(str2)) {
            String[] split = str2.split(";");
            if (split != null && split.length == 3) {
                spawnX = Integer.valueOf(split[0]).intValue();
                spawnY = Integer.valueOf(split[1]).intValue();
                spawnZ = Integer.valueOf(split[2]).intValue();
            }
        }
        Level level = new Level();
        level.setGameType(i);
        level.setLastPlayed(System.currentTimeMillis() / 1000);
        level.setLevelName(str);
        level.setPlatform(platform);
        level.setPlayer(createPlayer());
        level.setRandomSeed(j);
        level.setSizeOnDisk(0);
        level.setSpawnX(spawnX);
        level.setSpawnY(spawnY);
        level.setSpawnZ(spawnZ);
        level.setStorageVersion(storageVersion);
        level.setTime(curTime);
        level.setDayCycleStopTime(timePerDay);
        level.setSpawnMobs(bSpawnMob);
        level.setDimension(dimension);
        level.setGenerator(generator);
        if (new File(str3).exists()) {
            do {
                str3 = str3 + "-";
            } while (new File(str3).exists());
        }
        new File(str3).mkdirs();
        try {
            LevelDataConverter.write(level, new File(str3, "level.dat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createLevelBySeed(String str, String str2, String str3, int i) {
        try {
            return createLevelBySeed((long) str.hashCode(), str2, str3, i);
        } catch (Exception e) {
            return null;
        }
    }

    private static Player createPlayer() {
        byte b = (byte) 0;
        Player player = new Player();
        player.setLocation(location);
        player.setVelocity(velocity);
        player.setAirTicks(airTick);
        player.setFireTicks(fireTick);
        player.setFallDistance((float) fallen);
        player.setYaw(yaw);
        player.setPitch(pitch);
        player.setOnGround(true);
        player.setAttackTime(attackTime);
        player.setDeathTime(deathTime);
        player.setHealth(heath);
        player.setHurtTime(hurtTime);
        List arrayList = new ArrayList();
        ItemStack itemStack = new ItemStack((short) 0, (short) 0, 0);
        arrayList.add(itemStack);
        arrayList.add(itemStack);
        arrayList.add(itemStack);
        arrayList.add(itemStack);
        player.setArmor(arrayList);
        player.setBedPositionX(bedPosX);
        player.setBedPositionY(bedPosY);
        player.setBedPositionZ(bedPosZ);
        player.setDimension(0);
        arrayList = new ArrayList();
        itemStack = new ItemStack((short) -1, (short) -1, 225);
        while (b < (byte) 9) {
            InventorySlot inventorySlot = new InventorySlot(b, itemStack);
            b = (byte) (b + 1);
        }
        player.setInventory(arrayList);
        player.setScore(score);
        player.setSleeping(bSleep);
        player.setSleepTimer(sleepTick);
        player.setSpawnX(spawnX);
        player.setSpawnY(spawnY);
        player.setSpawnZ(spawnZ);
        player.setRiding(null);
        return player;
    }

    private static String getWorldDirName() {
        return null;
    }
}
