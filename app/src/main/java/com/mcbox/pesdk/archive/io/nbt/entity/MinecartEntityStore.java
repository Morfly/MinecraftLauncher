package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Minecart;

import org.spout.nbt.Tag;

import java.util.List;

public class MinecartEntityStore<T extends Minecart> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        super.loadTag(t, tag);
    }

    public List<Tag> save(T t) {
        return super.save(t);
    }
}
