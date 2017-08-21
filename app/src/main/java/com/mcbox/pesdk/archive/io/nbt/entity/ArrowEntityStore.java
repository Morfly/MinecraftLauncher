package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Arrow;

import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

import java.util.List;

public class ArrowEntityStore<T extends Arrow> extends ProjectileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("inData")) {
            t.setInData(((ByteTag) tag).getValue().byteValue());
        } else if (name.equals("player")) {
            t.setShotByPlayer(((ByteTag) tag).getValue().byteValue() != (byte) 0);
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        save.add(new ByteTag("inData", t.getInData()));
        save.add(new ByteTag("player", t.isShotByPlayer() ? (byte) 1 : (byte) 0));
        return save;
    }
}
