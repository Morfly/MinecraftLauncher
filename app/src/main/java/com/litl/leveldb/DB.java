package com.litl.leveldb;

import com.yy.hiidostatis.inner.BaseStatisContent;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public class DB extends NativeObject {
    private static final String LOG_TAG = "leveldb.DB";
    private static final boolean debug = false;
    private static Set<Long> openIdSet = new HashSet();
    private boolean mDestroyOnClose = false;
    private final File mPath;
    private Long openId = null;

    public abstract class Snapshot extends NativeObject {
        Snapshot(long j) {
            super(j);
        }

        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }
    }

    public DB(File file) {
        System.loadLibrary("leveldbjni");
        if (file == null) {
            throw new NullPointerException();
        }
        this.mPath = file;
    }

    public static void destroy(File file) {
        nativeDestroy(file.getAbsolutePath());
    }

    private static native void nativeClose(long j);

    private static native void nativeDelete(long j, byte[] bArr);

    private static native void nativeDestroy(String str);

    private static native byte[] nativeGet(long j, long j2, ByteBuffer byteBuffer);

    private static native byte[] nativeGet(long j, long j2, byte[] bArr);

    private static native long nativeGetSnapshot(long j);

    private static native long nativeIterator(long j, long j2);

    private static native long nativeOpen(String str);

    private static native void nativePut(long j, byte[] bArr, byte[] bArr2);

    private static native void nativeReleaseSnapshot(long j, long j2);

    private static native void nativeWrite(long j, long j2);

    public static native String stringFromJNI();

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    protected void closeNativeObject(long j) {
        nativeClose(j);
        if (this.mDestroyOnClose) {
            destroy(this.mPath);
        }
    }

    public void delete(byte[] bArr) {
        assertOpen("Database is closed");
        if (bArr == null) {
            throw new NullPointerException();
        }
        nativeDelete(this.mPtr, bArr);
    }

    public void destroy() {
        this.mDestroyOnClose = true;
        if (getPtr() == 0) {
            destroy(this.mPath);
        }
    }

    public byte[] get(Snapshot snapshot, ByteBuffer byteBuffer) {
        assertOpen("Database is closed");
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        return nativeGet(this.mPtr, snapshot != null ? snapshot.getPtr() : 0, byteBuffer);
    }

    public byte[] get(Snapshot snapshot, byte[] bArr) {
        assertOpen("Database is closed");
        if (bArr == null) {
            throw new NullPointerException();
        }
        return nativeGet(this.mPtr, snapshot != null ? snapshot.getPtr() : 0, bArr);
    }

    public byte[] get(ByteBuffer byteBuffer) {
        return get(null, byteBuffer);
    }

    public byte[] get(byte[] bArr) {
        return get(null, bArr);
    }

    public Snapshot getSnapshot() {
        assertOpen("Database is closed");
        ref();
        return new Snapshot(nativeGetSnapshot(this.mPtr)) {
            protected void closeNativeObject(long j) {
                DB.nativeReleaseSnapshot(DB.this.getPtr(), getPtr());
                DB.this.unref();
            }
        };
    }

    public Iterator iterator() {
        return iterator(null);
    }

    public Iterator iterator(final Snapshot snapshot) {
        assertOpen("Database is closed");
        ref();
        if (snapshot != null) {
            snapshot.ref();
        }
        return new Iterator(nativeIterator(this.mPtr, snapshot != null ? snapshot.getPtr() : 0)) {
            protected void closeNativeObject(long j) {
                super.closeNativeObject(j);
                if (snapshot != null) {
                    snapshot.unref();
                }
                DB.this.unref();
            }
        };
    }

    public void open() {
        this.mPtr = nativeOpen(this.mPath.getAbsolutePath());
    }

    public void put(byte[] bArr, byte[] bArr2) {
        assertOpen("Database is closed");
        if (bArr == null) {
            throw new NullPointerException(BaseStatisContent.KEY);
        } else if (bArr2 == null) {
            throw new NullPointerException("value");
        } else {
            nativePut(this.mPtr, bArr, bArr2);
        }
    }

    public void write(WriteBatch writeBatch) {
        assertOpen("Database is closed");
        if (writeBatch == null) {
            throw new NullPointerException();
        }
        nativeWrite(this.mPtr, writeBatch.getPtr());
    }
}
