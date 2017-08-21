package com.mcbox.pesdk.archive.io.region;

import com.mcbox.pesdk.archive.geo.Chunk;
import com.mcbox.pesdk.archive.geo.ChunkManager;

import java.io.File;

public class RegionTest {
    public static void main(String[] strArr) {
        try {
            int i;
            int i2;
            ChunkManager chunkManager = new ChunkManager(new File(strArr[0]));
            for (i = 0; i < 16; i++) {
                for (i2 = 0; i2 < 16; i2++) {
                    Chunk chunk = chunkManager.getChunk(i, i2);
                    System.out.println("Chunk " + i + "," + i2);
                    System.out.println("DIAMONDS: " + chunk.countDiamonds());
                    if (chunk.dirtyTableIsReallyGross()) {
                        System.out.println("Chunk " + i + "," + i2 + " has been modified.");
                    }
                }
            }
            for (i = 0; i < 16; i++) {
                for (i2 = 0; i2 < 16; i2++) {
                    if (chunkManager.getChunk(i, i2).dirtyTableIsReallyGross()) {
                    } else {
                    }
                }
                System.out.println("|");
            }
            for (int i3 = 0; i3 < 16; i3++) {
                for (i = 0; i < 128; i++) {
                    for (i2 = 0; i2 < 16; i2++) {
                        chunkManager.getChunk(2, 12).setBlockTypeId(i3, i, i2, 10);
                    }
                }
            }
            System.out.println("Saving chunks...");
            System.out.println(chunkManager.saveAll() + " chunks saved");
            chunkManager.unloadChunks(false);
            chunkManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
