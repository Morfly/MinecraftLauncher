package com.mcbox.pesdk.archive.io.leveldb;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.litl.leveldb.WriteBatch;
import com.mcbox.pesdk.archive.Level;
import com.mcbox.pesdk.archive.entity.Entity;
import com.mcbox.pesdk.archive.io.EntityDataConverter.EntityData;
import com.mcbox.pesdk.archive.io.nbt.NBTConverter;
import com.mcbox.pesdk.archive.tileentity.TileEntity;
import com.mcbox.pesdk.archive.util.Vector3f;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.Tag;
import org.spout.nbt.stream.NBTInputStream;
import org.spout.nbt.stream.NBTOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class LevelDBConverter {
    private static final String LOCAL_PLAYER_KEY = "~local_player";

    private static byte[] bytes(String str) {
        return str.getBytes(Charset.forName("utf-8"));
    }

    public static DB openDatabase(File file) {
        File file2 = new File(file, "LOCK");
        int i = 0;
        while (i < 10) {
            int i2 = i + 1;
            try {
                DB db = new DB(file);
                db.open();
                return db;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                i = i2;
            }
        }
        return null;
    }

    public static EntityData readAllEntities(File file) throws IOException {
        DB openDatabase = openDatabase(file);
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        Iterator it = openDatabase.iterator();
        DBKey dBKey = new DBKey();
        it.seekToFirst();
        while (it.isValid()) {
            InputStream byteArrayInputStream;
            NBTInputStream nBTInputStream;
            dBKey.fromBytes(it.getKey());
            if (dBKey.getType() == 50) {
                byteArrayInputStream = new ByteArrayInputStream(it.getValue());
                nBTInputStream = new NBTInputStream(byteArrayInputStream, false, true);
                while (byteArrayInputStream.available() > 0) {
                    Entity readSingleEntity = NBTConverter.readSingleEntity((CompoundTag) nBTInputStream.readTag());
                    if (readSingleEntity == null) {
                        System.err.println("Not possible: null entity.");
                    } else {
                        arrayList.add(readSingleEntity);
                    }
                }
            }
            try {
                if (dBKey.getType() == 49) {
                    byteArrayInputStream = new ByteArrayInputStream(it.getValue());
                    nBTInputStream = new NBTInputStream(byteArrayInputStream, false, true);
                    while (byteArrayInputStream.available() > 0) {
                        TileEntity readSingleTileEntity = NBTConverter.readSingleTileEntity((CompoundTag) nBTInputStream.readTag());
                        if (readSingleTileEntity == null) {
                            System.err.println("Not possible: null tile entity.");
                        } else {
                            arrayList2.add(readSingleTileEntity);
                        }
                    }
                }
                it.next();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (openDatabase != null) {
                    openDatabase.close();
                }
                it.close();
            }
        }
        return new EntityData(arrayList, arrayList2);
    }

    public static void readLevel(Level level, File file) {
        DB openDatabase = openDatabase(file);
        if (openDatabase != null) {
            try {
                byte[] bArr = openDatabase.get(bytes(LOCAL_PLAYER_KEY));
                if (bArr != null) {
                    level.setPlayer(NBTConverter.readPlayer((CompoundTag) new NBTInputStream(new ByteArrayInputStream(bArr), false, true).readTag()));
                }
                openDatabase.close();
            } catch (Throwable th) {
                openDatabase.close();
            }
        }
    }

    public static void writeAllEntities(List<Entity> list, File file) throws Throwable {
        DB openDatabase;
        Exception e;
        DB db;
        Throwable th;
        Iterator iterator = null;
        Iterator it;
        try {
            openDatabase = openDatabase(file);
            try {
                HashMap<DBKey, OutputStream> hashMap = new HashMap();
                DBKey dBKey = new DBKey();
                dBKey.setType(50);
                for (Entity entity : list) {
                    Tag writeEntity = NBTConverter.writeEntity(entity);
                    Vector3f location = entity.getLocation();
                    dBKey.setX(((int) location.getX()) >> 4).setZ(((int) location.getZ()) >> 4);
                    OutputStream outputStream = (ByteArrayOutputStream) hashMap.get(dBKey);
                    if (outputStream == null) {
                        outputStream = new ByteArrayOutputStream();
                    }
                    DBKey dBKey2 = new DBKey(dBKey);
                    new NBTOutputStream(outputStream, false, true).writeTag(writeEntity);
                    hashMap.put(dBKey2, outputStream);
                }
                it = openDatabase.iterator();
                try {
                    WriteBatch writeBatch = new WriteBatch();
                    it.seekToFirst();
                    while (it.isValid()) {
                        dBKey.fromBytes(it.getKey());
                        if (dBKey.getType() == 50 && !hashMap.containsKey(dBKey)) {
                            writeBatch.delete(ByteBuffer.wrap(dBKey.toBytes()));
                        }
                        it.next();
                    }
                    it.close();
                    openDatabase.write(writeBatch);
                    writeBatch.clear();
                    for (Entry entry : hashMap.entrySet()) {
                        writeBatch.put(ByteBuffer.wrap(((DBKey) entry.getKey()).toBytes()), ByteBuffer.wrap(((ByteArrayOutputStream) entry.getValue()).toByteArray()));
                    }
                    openDatabase.write(writeBatch);
                    writeBatch.close();
                    openDatabase.close();
                    if (openDatabase != null) {
                        openDatabase.close();
                    }
                    if (it != null) {
                        it.close();
                    }
                } catch (Exception e2) {
                    e = e2;
                    iterator = it;
                    db = openDatabase;
                    try {
                        e.printStackTrace();
                        if (db != null) {
                            db.close();
                        }
                        if (iterator == null) {
                            iterator.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        openDatabase = db;
                        it = iterator;
                        if (openDatabase != null) {
                            openDatabase.close();
                        }
                        if (it != null) {
                            it.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (openDatabase != null) {
                        openDatabase.close();
                    }
                    if (it != null) {
                        it.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                db = openDatabase;
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
                if (iterator == null) {
                    iterator.close();
                }
            } catch (Throwable th4) {
                th = th4;
                it = null;
                if (openDatabase != null) {
                    openDatabase.close();
                }
                if (it != null) {
                    it.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            db = null;
            e.printStackTrace();
            if (db != null) {
                db.close();
            }
            if (iterator == null) {
                iterator.close();
            }
        } catch (Throwable th5) {
            th = th5;
            it = null;
            openDatabase = null;
            if (openDatabase != null) {
                openDatabase.close();
            }
            if (it != null) {
                it.close();
            }
            throw th;
        }
    }

    public static void writeLevel(Level level, File file) {
        DB openDatabase = openDatabase(file);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new NBTOutputStream(byteArrayOutputStream, false, true).writeTag(NBTConverter.writePlayer(level.getPlayer(), "", true));
            openDatabase.put(bytes(LOCAL_PLAYER_KEY), byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            openDatabase.close();
        }
    }
}
