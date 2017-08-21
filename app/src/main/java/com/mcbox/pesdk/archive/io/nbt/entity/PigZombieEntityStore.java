package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.PigZombie;

import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class PigZombieEntityStore<T extends PigZombie> extends LivingEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Anger")) {
            t.setAnger(((ShortTag) tag).getValue().shortValue());
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        save.add(new ShortTag("Anger", t.getAnger()));
        return save;
    }
}
