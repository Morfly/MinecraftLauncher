package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.ChestTileEntity;
import com.mcbox.pesdk.archive.tileentity.FurnaceTileEntity;
import com.mcbox.pesdk.archive.tileentity.NetherReactorTileEntity;
import com.mcbox.pesdk.archive.tileentity.SignTileEntity;
import com.mcbox.pesdk.archive.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Map;

public class TileEntityStoreLookupService {
    public static Map<String, Class> classMap = new HashMap();
    public static TileEntityStore<TileEntity> defaultStore = new TileEntityStore();
    public static Map<String, TileEntityStore> idMap = new HashMap();

    static {
        addStore("Furnace", new FurnaceTileEntityStore(), FurnaceTileEntity.class);
        addStore("Chest", new ChestTileEntityStore(), ChestTileEntity.class);
        addStore("NetherReactor", new NetherReactorTileEntityStore(), NetherReactorTileEntity.class);
        addStore("Sign", new SignTileEntityStore(), SignTileEntity.class);
    }

    public static void addStore(String str, TileEntityStore tileEntityStore, Class cls) {
        String toUpperCase = str.toUpperCase();
        idMap.put(toUpperCase, tileEntityStore);
        classMap.put(toUpperCase, cls);
    }

    public static TileEntity createTileEntityById(String str) {
        Class cls = (Class) classMap.get(str.toUpperCase());
        if (cls == null) {
            return new TileEntity();
        }
        try {
            return (TileEntity) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new TileEntity();
        }
    }

    public static TileEntityStore getStoreById(String str) {
        return (TileEntityStore) idMap.get(str.toUpperCase());
    }
}
