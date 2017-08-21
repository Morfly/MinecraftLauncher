package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Item;
import com.mcbox.pesdk.archive.io.nbt.NBTConverter;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class ItemEntityStore<T extends Item> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Age")) {
            t.setAge(((ShortTag) tag).getValue().shortValue());
        } else if (tag.getName().equals("Health")) {
            t.setHealth(((ShortTag) tag).getValue().shortValue());
        } else if (tag.getName().equals("Item")) {
            t.setItemStack(NBTConverter.readItemStack((CompoundTag) tag));
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ShortTag("Age", t.getAge()));
        save.add(new ShortTag("Health", t.getHealth()));
        save.add(NBTConverter.writeItemStack(t.getItemStack(), "Item"));
        return save;
    }
}
