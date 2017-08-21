package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.FallingBlock;

import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

import java.util.List;

public class FallingBlockEntityStore<T extends FallingBlock> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("Tile")) {
            t.setBlockId(((ByteTag) tag).getValue().byteValue() & 255);
        } else if (name.equals("Data")) {
            t.setBlockData(((ByteTag) tag).getValue().byteValue());
        } else if (name.equals("Time")) {
            t.setTime(((ByteTag) tag).getValue().byteValue() & 255);
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ByteTag("Data", t.getBlockData()));
        save.add(new ByteTag("Tile", (byte) t.getBlockId()));
        save.add(new ByteTag("Time", (byte) t.getTime()));
        return save;
    }
}
