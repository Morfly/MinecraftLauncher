package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.SignTileEntity;

import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

import java.util.List;

public class SignTileEntityStore<T extends SignTileEntity> extends TileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        if (tag.getName().equals("Text1")) {
            t.setLine(0, ((StringTag) tag).getValue());
        } else if (tag.getName().equals("Text2")) {
            t.setLine(1, ((StringTag) tag).getValue());
        } else if (tag.getName().equals("Text3")) {
            t.setLine(2, ((StringTag) tag).getValue());
        } else if (tag.getName().equals("Text4")) {
            t.setLine(3, ((StringTag) tag).getValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        List<Tag> save = super.save(t);
        String[] lines = t.getLines();
        for (int i = 0; i < lines.length; i++) {
            save.add(new StringTag("Text" + (i + 1), lines[i]));
        }
        return save;
    }
}
