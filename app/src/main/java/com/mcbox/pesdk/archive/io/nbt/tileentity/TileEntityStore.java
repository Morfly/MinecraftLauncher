package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.TileEntity;
import com.yy.hiidostatis.defs.obj.Elem;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class TileEntityStore<T extends TileEntity> {
    public void load(T t, CompoundTag compoundTag) {
        for (Tag loadTag : compoundTag.getValue()) {
            loadTag(t, loadTag);
        }
    }

    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("x")) {
            t.setX(((IntTag) tag).getValue().intValue());
        } else if (tag.getName().equals("y")) {
            t.setY(((IntTag) tag).getValue().intValue());
        } else if (tag.getName().equals("z")) {
            t.setZ(((IntTag) tag).getValue().intValue());
        } else if (tag.getName().equals("id")) {
            t.setId(((StringTag) tag).getValue());
        } else {
            System.out.println("Unhandled tag " + tag.getName() + Elem.DIVIDER + tag.toString() + " for tile entity " + this);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> arrayList = new ArrayList();
        arrayList.add(new StringTag("id", t.getId()));
        arrayList.add(new IntTag("x", t.getX()));
        arrayList.add(new IntTag("y", t.getY()));
        arrayList.add(new IntTag("z", t.getZ()));
        return arrayList;
    }
}
