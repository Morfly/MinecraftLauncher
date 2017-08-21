package com.mcbox.pesdk.archive.io.leveldb;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class DBKey {
    public static final int CHUNK = 48;
    public static final int ENTITY = 50;
    public static final int PLACEHOLDER = 118;
    public static final int TILE_ENTITY = 49;
    private int type;
    private int f3578x;
    private int f3579z;

    public DBKey() {
        this(0, 0, 0);
    }

    public DBKey(int i, int i2, int i3) {
        this.f3578x = i;
        this.f3579z = i2;
        this.type = i3;
    }

    public DBKey(DBKey dBKey) {
        this(dBKey.f3578x, dBKey.f3579z, dBKey.type);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DBKey)) {
            return false;
        }
        DBKey dBKey = (DBKey) obj;
        return dBKey.f3578x == this.f3578x && dBKey.f3579z == this.f3579z && dBKey.type == this.type;
    }

    public void fromBytes(byte[] bArr) {
        this.f3578x = ((bArr[0] | (bArr[1] << 8)) | (bArr[2] << 16)) | (bArr[3] << 24);
        this.f3579z = ((bArr[4] | (bArr[5] << 8)) | (bArr[6] << 16)) | (bArr[7] << 24);
        this.type = bArr[8] & 255;
    }

    public int getType() {
        return this.type;
    }

    public int getX() {
        return this.f3578x;
    }

    public int getZ() {
        return this.f3579z;
    }

    public int hashCode() {
        return ((((this.f3578x + 31) * 31) + this.f3579z) * 31) + this.type;
    }

    public DBKey setType(int i) {
        this.type = i;
        return this;
    }

    public DBKey setX(int i) {
        this.f3578x = i;
        return this;
    }

    public DBKey setZ(int i) {
        this.f3579z = i;
        return this;
    }

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(9);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(Integer.reverseBytes(this.f3578x));
            dataOutputStream.writeInt(Integer.reverseBytes(this.f3579z));
            dataOutputStream.writeByte(this.type);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + this.f3578x + "_" + this.f3579z + "_" + this.type;
    }
}
