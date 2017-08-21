package com.mcbox.pesdk.archive.io.nbt.entity;

import com.mcbox.pesdk.archive.entity.Entity;
import com.mcbox.pesdk.archive.io.nbt.NBTConverter;

import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class EntityStore<T extends Entity> {
    public void load(T t, CompoundTag compoundTag) {
        for (Tag loadTag : compoundTag.getValue()) {
            loadTag(t, loadTag);
        }
    }

    public void loadTag(T t, Tag tag) {
        boolean z = false;
        String name = tag.getName();
        if (name.equals("Pos")) {
            t.setLocation(NBTConverter.readVector((ListTag) tag));
        } else if (name.equals("Motion")) {
            t.setVelocity(NBTConverter.readVector((ListTag) tag));
        } else if (name.equals("Air")) {
            t.setAirTicks(((ShortTag) tag).getValue().shortValue());
        } else if (name.equals("Fire")) {
            t.setFireTicks(((ShortTag) tag).getValue().shortValue());
        } else if (name.equals("FallDistance")) {
            t.setFallDistance(((FloatTag) tag).getValue().floatValue());
        } else if (name.equals("Riding")) {
            t.setRiding(NBTConverter.readSingleEntity((CompoundTag) tag));
        } else if (name.equals("Rotation")) {
            List value = ((ListTag) tag).getValue();
            t.setYaw(((FloatTag) value.get(0)).getValue().floatValue());
            t.setPitch(((FloatTag) value.get(1)).getValue().floatValue());
        } else if (name.equals("OnGround")) {
            if (((ByteTag) tag).getValue().byteValue() > (byte) 0) {
                z = true;
            }
            t.setOnGround(z);
        } else if (!name.equals("id")) {
            System.err.println("Unknown tag " + name + " for entity " + t.getClass().getSimpleName() + " : " + tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> arrayList = new ArrayList();
        arrayList.add(new ShortTag("Air", t.getAirTicks()));
        arrayList.add(new FloatTag("FallDistance", t.getFallDistance()));
        arrayList.add(new ShortTag("Fire", t.getFireTicks()));
        arrayList.add(NBTConverter.writeVector(t.getVelocity(), "Motion"));
        arrayList.add(NBTConverter.writeVector(t.getLocation(), "Pos"));
        if (t.getRiding() != null) {
            arrayList.add(NBTConverter.writeEntity(t.getRiding(), "Riding"));
        }
        List arrayList2 = new ArrayList(2);
        arrayList2.add(new FloatTag("", t.getYaw()));
        arrayList2.add(new FloatTag("", t.getPitch()));
        arrayList.add(new ListTag("Rotation", FloatTag.class, arrayList2));
        arrayList.add(new ByteTag("OnGround", t.isOnGround() ? (byte) 1 : (byte) 0));
        arrayList.add(new IntTag("id", t.getEntityTypeId()));
        return arrayList;
    }
}
