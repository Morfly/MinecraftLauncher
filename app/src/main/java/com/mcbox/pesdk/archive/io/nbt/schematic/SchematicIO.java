package com.mcbox.pesdk.archive.io.nbt.schematic;

import com.mcbox.pesdk.archive.geo.CuboidClipboard;
import com.mcbox.pesdk.archive.util.Vector3f;

import org.spout.nbt.ByteArrayTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;
import org.spout.nbt.stream.NBTInputStream;
import org.spout.nbt.stream.NBTOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchematicIO {
    public static CuboidClipboard read(File file) throws IOException {
        byte[] bArr = null;
        int i = 0;
        NBTInputStream nBTInputStream = new NBTInputStream(new FileInputStream(file));
        CompoundTag compoundTag = (CompoundTag) nBTInputStream.readTag();
        nBTInputStream.close();
        byte[] bArr2 = null;
        int i2 = 0;
        short s = (short) 0;
        for (Tag tag : compoundTag.getValue()) {
            int i3 = 0;
            short shortValue;
            byte[] bArr3;
            String name = tag.getName();
            byte[] bArr4;
            if (name.equals("Width")) {
                bArr4 = bArr;
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = ((ShortTag) tag).getValue().shortValue();
                bArr3 = bArr4;
            } else if (name.equals("Height")) {
                shortValue = s;
                bArr4 = bArr2;
                i3 = i;
                short shortValue2 = ((ShortTag) tag).getValue().shortValue();
                bArr3 = bArr;
                bArr = bArr4;
            } else if (name.equals("Length")) {
                i = i2;
                shortValue = s;
                short shortValue3 = ((ShortTag) tag).getValue().shortValue();
                bArr3 = bArr;
                bArr = bArr2;
                short s2 = shortValue3;
            } else if (name.equals("Materials")) {
                ((StringTag) tag).getValue();
                bArr3 = bArr;
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = s;
            } else if (name.equals("Blocks")) {
                i3 = i;
                i = i2;
                shortValue = s;
                bArr4 = ((ByteArrayTag) tag).getValue();
                bArr3 = bArr;
                bArr = bArr4;
            } else if (name.equals("Data")) {
                bArr3 = ((ByteArrayTag) tag).getValue();
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = s;
            } else if (name.equals("Entities")) {
                bArr3 = bArr;
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = s;
            } else if (name.equals("TileEntities")) {
                bArr3 = bArr;
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = s;
            } else {
                System.err.println("WTF: invalid tag name: " + name);
                bArr3 = bArr;
                bArr = bArr2;
                i3 = i;
                i = i2;
                shortValue = s;
            }
            s = shortValue;
            i2 = i;
            i = i3;
            bArr2 = bArr;
            bArr = bArr3;
        }
        return new CuboidClipboard(new Vector3f((float) s, (float) i2, (float) i), bArr2, bArr);
    }

    public static void save(CuboidClipboard cuboidClipboard, File file) throws IOException {
        List arrayList = new ArrayList();
        arrayList.add(new ShortTag("Width", (short) cuboidClipboard.getWidth()));
        arrayList.add(new ShortTag("Height", (short) cuboidClipboard.getHeight()));
        arrayList.add(new ShortTag("Length", (short) cuboidClipboard.getLength()));
        arrayList.add(new StringTag("Materials", "Alpha"));
        arrayList.add(new ByteArrayTag("Blocks", cuboidClipboard.blocks));
        arrayList.add(new ByteArrayTag("Data", cuboidClipboard.metaData));
        arrayList.add(new ListTag("Entities", CompoundTag.class, Collections.EMPTY_LIST));
        arrayList.add(new ListTag("TileEntities", CompoundTag.class, Collections.EMPTY_LIST));
        Tag compoundTag = new CompoundTag("Schematic", arrayList);
        NBTOutputStream nBTOutputStream = new NBTOutputStream(new FileOutputStream(file));
        nBTOutputStream.writeTag(compoundTag);
        nBTOutputStream.close();
    }
}
