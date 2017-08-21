package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.io.nbt.NBTConverter;
import com.mcbox.pesdk.archive.tileentity.ContainerTileEntity;

import org.spout.nbt.ListTag;
import org.spout.nbt.Tag;

import java.util.List;

public class ContainerTileEntityStore<T extends ContainerTileEntity> extends TileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Items")) {
            t.setItems(NBTConverter.readInventory((ListTag) tag));
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(NBTConverter.writeInventory(t.getItems(), "Items"));
        return save;
    }
}
