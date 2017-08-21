package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Animal;

import org.spout.nbt.IntTag;
import org.spout.nbt.Tag;

import java.util.List;

public class AnimalEntityStore<T extends Animal> extends LivingEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Age")) {
            t.setAge(((IntTag) tag).getValue().intValue());
        } else if (tag.getName().equals("InLove")) {
            t.setInLove(((IntTag) tag).getValue().intValue());
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        save.add(new IntTag("Age", t.getAge()));
        save.add(new IntTag("InLove", t.getInLove()));
        return save;
    }
}
