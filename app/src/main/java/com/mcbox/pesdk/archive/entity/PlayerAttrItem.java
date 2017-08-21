package com.mcbox.pesdk.archive.entity;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class PlayerAttrItem {
    public static final String ATTR_NAME_BASE = "Base";
    public static final String ATTR_NAME_CURRENT = "Current";
    public static final String ATTR_NAME_NAME = "Name";
    private float base;
    private float current;
    private String name;

    public static PlayerAttrItem formTag(CompoundTag compoundTag) {
        PlayerAttrItem playerAttrItem = new PlayerAttrItem();
        if (compoundTag == null) {
            return playerAttrItem;
        }
        for (Tag tag : compoundTag.getValue()) {
            if (ATTR_NAME_NAME.equals(tag.getName())) {
                playerAttrItem.setName(((StringTag) tag).getValue());
            } else if (ATTR_NAME_BASE.equals(tag.getName())) {
                playerAttrItem.setBase(((FloatTag) tag).getValue().floatValue());
            } else if (ATTR_NAME_CURRENT.equals(tag.getName())) {
                playerAttrItem.setCurrent(((FloatTag) tag).getValue().floatValue());
            }
        }
        return playerAttrItem;
    }

    public float getBase() {
        return this.base;
    }

    public float getCurrent() {
        return this.current;
    }

    public String getName() {
        return this.name;
    }

    public void setBase(float f) {
        this.base = f;
    }

    public void setCurrent(float f) {
        this.current = f;
    }

    public void setName(String str) {
        this.name = str;
    }

    public CompoundTag toTag(String str) {
        List arrayList = new ArrayList();
        arrayList.add(new FloatTag(ATTR_NAME_BASE, this.base));
        arrayList.add(new FloatTag(ATTR_NAME_CURRENT, this.current));
        arrayList.add(new StringTag(ATTR_NAME_NAME, this.name));
        return new CompoundTag(str, arrayList);
    }
}
