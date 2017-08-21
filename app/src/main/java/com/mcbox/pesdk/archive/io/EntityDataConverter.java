package com.mcbox.pesdk.archive.io;

import com.mcbox.pesdk.archive.entity.Entity;
import com.mcbox.pesdk.archive.io.leveldb.LevelDBConverter;
import com.mcbox.pesdk.archive.io.nbt.NBTConverter;
import com.mcbox.pesdk.archive.tileentity.TileEntity;
import com.mcbox.pesdk.util.McInstallInfoUtil;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.stream.NBTInputStream;
import org.spout.nbt.stream.NBTOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class EntityDataConverter {
    public static final byte[] header = new byte[]{(byte) 69, (byte) 78, (byte) 84, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0};

    public static class EntityData {
        public List<Entity> entities;
        public List<TileEntity> tileEntities;

        public EntityData(List<Entity> list, List<TileEntity> list2) {
            this.entities = list;
            this.tileEntities = list2;
        }
    }

    public static void main(String[] strArr) throws Throwable {
        EntityData read = read(new File(strArr[0]));
        System.out.println(read);
        write(read.entities, read.tileEntities, new File(strArr[1]));
    }

    public static EntityData read(File file) throws IOException {
        if (McInstallInfoUtil.isUseLevelDB()) {
            return LevelDBConverter.readAllEntities(new File(file.getParentFile(), "db"));
        }
        InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.skip(12);
        EntityData readEntities = NBTConverter.readEntities((CompoundTag) new NBTInputStream(bufferedInputStream, false, true).readTag());
        bufferedInputStream.close();
        return readEntities;
    }

    public static void write(List<Entity> list, List<TileEntity> list2, File file) throws Throwable {
        if (McInstallInfoUtil.isUseLevelDB()) {
            LevelDBConverter.writeAllEntities(list, new File(file.getParentFile(), "db"));
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new NBTOutputStream(byteArrayOutputStream, false, true).writeTag(NBTConverter.writeEntities(list, list2));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int size = byteArrayOutputStream.size();
        dataOutputStream.write(header);
        dataOutputStream.writeInt(Integer.reverseBytes(size));
        byteArrayOutputStream.writeTo(dataOutputStream);
        dataOutputStream.close();
    }
}
