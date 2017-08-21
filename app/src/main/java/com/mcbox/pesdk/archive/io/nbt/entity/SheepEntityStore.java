package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Sheep;

import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

import java.util.List;

public class SheepEntityStore<T extends Sheep> extends AnimalEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Color")) {
            t.setColor(((ByteTag) tag).getValue().byteValue());
        } else if (tag.getName().equals("Sheared")) {
            t.setSheared(((ByteTag) tag).getValue().byteValue() > (byte) 0);
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        save.add(new ByteTag("Color", t.getColor()));
        save.add(new ByteTag("Sheared", t.isSheared() ? (byte) 1 : (byte) 0));
        return save;
    }
}
