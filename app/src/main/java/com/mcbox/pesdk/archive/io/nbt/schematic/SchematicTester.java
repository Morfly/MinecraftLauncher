package com.mcbox.pesdk.archive.io.nbt.schematic;

import com.mcbox.pesdk.archive.geo.ChunkManager;
import com.mcbox.pesdk.archive.geo.CuboidClipboard;
import com.mcbox.pesdk.archive.util.Vector3f;

import java.io.File;

public final class SchematicTester {
    public static void main(String[] strArr) {
        try {
            ChunkManager chunkManager = new ChunkManager(new File(strArr[0]));
            CuboidClipboard read = SchematicIO.read(new File(strArr[1]));
            SchematicIO.save(read, new File(strArr[1] + ".new"));
            read.place(chunkManager, new Vector3f((float) Integer.parseInt(strArr[2]), (float) Integer.parseInt(strArr[3]), (float) Integer.parseInt(strArr[4])), false);
            System.out.println("Saving chunks...");
            System.out.println(chunkManager.saveAll() + " chunks saved");
            chunkManager.unloadChunks(false);
            chunkManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
