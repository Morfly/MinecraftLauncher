package com.litl.leveldb;

public class Iterator extends NativeObject {
    Iterator(long j) {
        super(j);
    }

    private static native void nativeDestroy(long j);

    private static native byte[] nativeKey(long j);

    private static native void nativeNext(long j);

    private static native void nativePrev(long j);

    private static native void nativeSeek(long j, byte[] bArr);

    private static native void nativeSeekToFirst(long j);

    private static native void nativeSeekToLast(long j);

    private static native boolean nativeValid(long j);

    private static native byte[] nativeValue(long j);

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    protected void closeNativeObject(long j) {
        nativeDestroy(j);
    }

    public byte[] getKey() {
        assertOpen("Iterator is closed");
        return nativeKey(this.mPtr);
    }

    public byte[] getValue() {
        assertOpen("Iterator is closed");
        return nativeValue(this.mPtr);
    }

    public boolean isValid() {
        assertOpen("Iterator is closed");
        return nativeValid(this.mPtr);
    }

    public void next() {
        assertOpen("Iterator is closed");
        nativeNext(this.mPtr);
    }

    public void prev() {
        assertOpen("Iterator is closed");
        nativePrev(this.mPtr);
    }

    public void seek(byte[] bArr) {
        assertOpen("Iterator is closed");
        if (bArr == null) {
            throw new IllegalArgumentException();
        }
        nativeSeek(this.mPtr, bArr);
    }

    public void seekToFirst() {
        assertOpen("Iterator is closed");
        nativeSeekToFirst(this.mPtr);
    }

    public void seekToLast() {
        assertOpen("Iterator is closed");
        nativeSeekToLast(this.mPtr);
    }
}
