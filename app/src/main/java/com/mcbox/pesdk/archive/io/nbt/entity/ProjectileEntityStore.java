package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Projectile;

import org.spout.nbt.ByteTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class ProjectileEntityStore<T extends Projectile> extends EntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("inGround")) {
            t.setInGround(((ByteTag) tag).getValue().byteValue() != (byte) 0);
        } else if (name.equals("inTile")) {
            t.setInBlock(((ByteTag) tag).getValue().byteValue());
        } else if (name.equals("shake")) {
            t.setShake(((ByteTag) tag).getValue().byteValue());
        } else if (name.equals("xTile")) {
            t.setXTile(((ShortTag) tag).getValue().shortValue());
        } else if (name.equals("yTile")) {
            t.setYTile(((ShortTag) tag).getValue().shortValue());
        } else if (name.equals("zTile")) {
            t.setZTile(((ShortTag) tag).getValue().shortValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        save.add(new ByteTag("inGround", t.isInGround() ? (byte) 1 : (byte) 0));
        save.add(new ByteTag("inTile", t.getInBlock()));
        save.add(new ByteTag("shake", t.getShake()));
        save.add(new ShortTag("xTile", t.getXTile()));
        save.add(new ShortTag("yTile", t.getYTile()));
        save.add(new ShortTag("zTile", t.getZTile()));
        return save;
    }
}
