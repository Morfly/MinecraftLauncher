package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.FurnaceTileEntity;

import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class FurnaceTileEntityStore<T extends FurnaceTileEntity> extends ContainerTileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("BurnTime")) {
            t.setBurnTime(((ShortTag) tag).getValue().shortValue());
        } else if (name.equals("CookTime")) {
            t.setCookTime(((ShortTag) tag).getValue().shortValue());
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        save.add(new ShortTag("BurnTime", t.getBurnTime()));
        save.add(new ShortTag("CookTime", t.getCookTime()));
        return save;
    }
}
