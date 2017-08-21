package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.ChestTileEntity;

import org.spout.nbt.IntTag;
import org.spout.nbt.Tag;

import java.util.List;

public class ChestTileEntityStore<T extends ChestTileEntity> extends ContainerTileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        String name = tag.getName();
        if (name.equals("pairx")) {
            t.setPairX(((IntTag) tag).getValue().intValue());
        } else if (name.equals("pairz")) {
            t.setPairZ(((IntTag) tag).getValue().intValue());
        } else {
            super.loadTag((T) t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save((T) t);
        if (t.getPairX() != -65535) {
            save.add(new IntTag("pairx", t.getPairX()));
            save.add(new IntTag("pairz", t.getPairZ()));
        }
        return save;
    }
}
