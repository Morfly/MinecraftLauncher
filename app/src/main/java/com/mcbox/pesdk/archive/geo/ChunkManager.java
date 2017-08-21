package com.mcbox.pesdk.archive.geo;

import com.mcbox.pesdk.archive.geo.Chunk.Key;
import com.mcbox.pesdk.archive.io.region.RegionFile;
import com.yy.hiidostatis.defs.obj.Elem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChunkManager implements AreaChunkAccess {
    public static final int WORLD_HEIGHT = 128;
    public static final int WORLD_LENGTH = 256;
    public static final int WORLD_WIDTH = 256;
    public static CuboidRegion worldRegion = new CuboidRegion(0, 0, 0, 256, 128, 256);
    protected File chunkFile;
    protected Map<Key, Chunk> chunks = new HashMap();
    private Chunk lastChunk = null;
    private Key lastKey = null;
    protected RegionFile region;

    public ChunkManager(File file) {
        this.chunkFile = file;
        this.region = new RegionFile(file);
    }

    public void close() {
        try {
            this.region.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBlockData(int i, int i2, int i3) {
        return (i >= 256 || i2 >= 128 || i3 >= 256 || i < 0 || i2 < 0 || i3 < 0) ? 0 : getChunk(i >> 4, i3 >> 4).getBlockData(i & 15, i2, i3 & 15);
    }

    public int getBlockTypeId(int i, int i2, int i3) {
        return (i >= 256 || i2 >= 128 || i3 >= 256 || i < 0 || i2 < 0 || i3 < 0) ? 0 : getChunk(i >> 4, i3 >> 4).getBlockTypeId(i & 15, i2, i3 & 15);
    }

    public Chunk getChunk(int i, int i2) {
        if (this.lastKey != null && this.lastKey.getX() == i && this.lastKey.getZ() == i2) {
            return this.lastChunk;
        }
        Key key;
        Key key2;
        if (this.lastKey == null) {
            key2 = new Key(i, i2);
            this.lastKey = key2;
            key = key2;
        } else {
            key2 = this.lastKey;
            key2.setX(i);
            key2.setZ(i2);
            key = key2;
        }
        Chunk chunk = (Chunk) this.chunks.get(key);
        if (chunk == null) {
            chunk = loadChunk(key);
        }
        this.lastChunk = chunk;
        return chunk;
    }

    public int getHighestBlockYAt(int i, int i2) {
        return (i >= 256 || i2 >= 256 || i < 0 || i2 < 0) ? 0 : getChunk(i >> 4, i2 >> 4).getHighestBlockYAt(i & 15, i2 & 15);
    }

    public Chunk loadChunk(Key key) {
        Chunk chunk = new Chunk(key.getX(), key.getZ());
        byte[] chunkData = this.region.getChunkData(key.getX(), key.getZ());
        if (chunkData != null) {
            chunk.loadFromByteArray(chunkData);
        } else {
            System.err.println("WTF:" + key.getX() + Elem.DIVIDER + key.getZ());
        }
        this.chunks.put(new Key(key), chunk);
        return chunk;
    }

    public int saveAll() {
        int i = 0;
        for (Entry entry : this.chunks.entrySet()) {
            Key key = (Key) entry.getKey();
            Chunk chunk = (Chunk) entry.getValue();
            if (key.getX() == chunk.f3573x && key.getZ() == chunk.f3574z) {
                int i2;
                if (chunk.needsSaving) {
                    saveChunk(chunk);
                    i2 = i + 1;
                } else {
                    i2 = i;
                }
                i = i2;
            } else {
                throw new AssertionError("WTF: key x = " + key.getX() + " z = " + key.getZ() + " chunk x=" + chunk.f3573x + " chunk z=" + chunk.f3574z);
            }
        }
        return i;
    }

    protected void saveChunk(Chunk chunk) {
        byte[] saveToByteArray = chunk.saveToByteArray();
        this.region.write(chunk.f3573x, chunk.f3574z, saveToByteArray, saveToByteArray.length);
    }

    public void setBlockData(int i, int i2, int i3, int i4) {
        if (i < 256 && i2 < 128 && i3 < 256 && i >= 0 && i2 >= 0 && i3 >= 0) {
            getChunk(i >> 4, i3 >> 4).setBlockData(i & 15, i2, i3 & 15, i4);
        }
    }

    public void setBlockTypeId(int i, int i2, int i3, int i4) {
        if (i < 256 && i2 < 128 && i3 < 256 && i >= 0 && i2 >= 0 && i3 >= 0) {
            getChunk(i >> 4, i3 >> 4).setBlockTypeId(i & 15, i2, i3 & 15, i4);
        }
    }

    public void unloadChunks(boolean z) {
        if (z) {
            saveAll();
        }
        this.chunks.clear();
    }
}
