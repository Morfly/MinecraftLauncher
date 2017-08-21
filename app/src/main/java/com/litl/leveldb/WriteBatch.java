package com.litl.leveldb;

import com.yy.hiidostatis.inner.BaseStatisContent;

import java.nio.ByteBuffer;

public class WriteBatch extends NativeObject {
    public WriteBatch() {
        super(nativeCreate());
    }

    private static native void nativeClear(long j);

    private static native long nativeCreate();

    private static native void nativeDelete(long j, ByteBuffer byteBuffer);

    private static native void nativeDestroy(long j);

    private static native void nativePut(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2);

    public void clear() {
        assertOpen("WriteBatch is closed");
        nativeClear(this.mPtr);
    }

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    protected void closeNativeObject(long j) {
        nativeDestroy(j);
    }

    public void delete(ByteBuffer byteBuffer) {
        assertOpen("WriteBatch is closed");
        if (byteBuffer == null) {
            throw new NullPointerException(BaseStatisContent.KEY);
        }
        nativeDelete(this.mPtr, byteBuffer);
    }

    public void put(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        assertOpen("WriteBatch is closed");
        if (byteBuffer == null) {
            throw new NullPointerException(BaseStatisContent.KEY);
        } else if (byteBuffer2 == null) {
            throw new NullPointerException("value");
        } else {
            nativePut(this.mPtr, byteBuffer, byteBuffer2);
        }
    }
}
