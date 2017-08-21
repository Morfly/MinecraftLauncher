package com.mcbox.pesdk.archive.io;

import com.mcbox.pesdk.archive.Level;
import com.mcbox.pesdk.archive.io.leveldb.LevelDBConverter;
import com.mcbox.pesdk.archive.io.nbt.NBTConverter;

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

public final class LevelDataConverter {
    public static final byte[] header = new byte[]{(byte) 4, (byte) 0, (byte) 0, (byte) 0};

    public static void main(String[] strArr) throws IOException {
        Level read = read(new File(strArr[0]));
        System.out.println(read);
        write(read, new File(strArr[1]));
    }

    public static Level read(File file) throws IOException {
        InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.skip(8);
        Level readLevel = NBTConverter.readLevel((CompoundTag) new NBTInputStream(bufferedInputStream, false, true).readTag());
        bufferedInputStream.close();
        File file2 = new File(file.getParentFile(), "db");
        if (file2.exists()) {
            LevelDBConverter.readLevel(readLevel, file2);
        }
        return readLevel;
    }

    public static void write(Level level, File file) throws IOException {
        File file2 = new File(file.getParentFile(), "db");
        boolean z = file2.exists();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new NBTOutputStream(byteArrayOutputStream, false, true).writeTag(NBTConverter.writeLevel(level, z));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int size = byteArrayOutputStream.size();
        dataOutputStream.write(header);
        dataOutputStream.writeInt(Integer.reverseBytes(size));
        byteArrayOutputStream.writeTo(dataOutputStream);
        dataOutputStream.close();
        if (z) {
            LevelDBConverter.writeLevel(level, file2);
        }
    }
}
