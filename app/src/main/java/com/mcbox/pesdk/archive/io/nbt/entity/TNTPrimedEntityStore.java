package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.TNTPrimed;

import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

import java.util.List;

public class TNTPrimedEntityStore<T extends TNTPrimed> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Fuse")) {
            t.setFuseTicks(((ByteTag) tag).getValue().byteValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ByteTag("Fuse", t.getFuseTicks()));
        return save;
    }
}
