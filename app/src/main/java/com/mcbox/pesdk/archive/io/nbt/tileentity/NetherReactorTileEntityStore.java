package com.mcbox.pesdk.archive.io.nbt.tileentity;

import com.mcbox.pesdk.archive.tileentity.NetherReactorTileEntity;

import org.spout.nbt.ByteTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

import java.util.List;

public class NetherReactorTileEntityStore<T extends NetherReactorTileEntity> extends TileEntityStore<T> {
    public void loadTag(T t, Tag tag) {
        boolean z = true;
        if (tag.getName().equals("HasFinished")) {
            if (((ByteTag) tag).getValue().byteValue() == (byte) 0) {
                z = false;
            }
            t.setFinished(z);
        } else if (tag.getName().equals("IsInitialized")) {
            if (((ByteTag) tag).getValue().byteValue() == (byte) 0) {
                z = false;
            }
            t.setInitialized(z);
        } else if (tag.getName().equals("Progress")) {
            t.setProgress(((ShortTag) tag).getValue().shortValue());
        } else {
            super.loadTag(t, tag);
        }
    }

    public List<Tag> save(T t) {
        byte b = (byte) 1;
        List<Tag> save = super.save(t);
        save.add(new ByteTag("HasFinished", t.hasFinished() ? (byte) 1 : (byte) 0));
        String str = "IsInitialized";
        if (!t.isInitialized()) {
            b = (byte) 0;
        }
        save.add(new ByteTag(str, b));
        save.add(new ShortTag("Progress", t.getProgress()));
        return save;
    }
}
