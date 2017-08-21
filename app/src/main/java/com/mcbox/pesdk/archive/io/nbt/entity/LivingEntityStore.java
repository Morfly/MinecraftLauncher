package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.LivingEntity;

import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class LivingEntityStore<T extends LivingEntity> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("AttackTime")) {
            t.setAttackTime(((ShortTag) tag).getValue().shortValue());
        } else if (tag.getName().equals("DeathTime")) {
            t.setDeathTime(((ShortTag) tag).getValue().shortValue());
        } else if (tag.getName().equals("Health")) {
            t.setHealth(((ShortTag) tag).getValue().shortValue());
        } else if (tag.getName().equals("HurtTime")) {
            t.setHurtTime(((ShortTag) tag).getValue().shortValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ShortTag("AttackTime", t.getAttackTime()));
        save.add(new ShortTag("DeathTime", t.getDeathTime()));
        save.add(new ShortTag("Health", t.getHealth()));
        save.add(new ShortTag("HurtTime", t.getHurtTime()));
        return save;
    }
}
