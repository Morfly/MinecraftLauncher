package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Painting;
import com.mcbox.pesdk.archive.util.Vector3f;

import org.spout.nbt.ByteTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

import java.util.List;

public class PaintingEntityStore<T extends Painting> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("Dir")) {
            t.setDirection(((ByteTag) tag).getValue().byteValue());
        } else if (name.equals("Motive")) {
            t.setArt(((StringTag) tag).getValue());
        } else if (name.equals("TileX")) {
            t.getBlockCoordinates().setX((float) ((IntTag) tag).getValue().intValue());
        } else if (name.equals("TileY")) {
            t.getBlockCoordinates().setY((float) ((IntTag) tag).getValue().intValue());
        } else if (name.equals("TileZ")) {
            t.getBlockCoordinates().setZ((float) ((IntTag) tag).getValue().intValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ByteTag("Dir", t.getDirection()));
        save.add(new StringTag("Motive", t.getArt()));
        Vector3f blockCoordinates = t.getBlockCoordinates();
        save.add(new IntTag("TileX", blockCoordinates.getBlockX()));
        save.add(new IntTag("TileY", blockCoordinates.getBlockY()));
        save.add(new IntTag("TileZ", blockCoordinates.getBlockZ()));
        return save;
    }
}
