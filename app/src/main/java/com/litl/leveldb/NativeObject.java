package com.litl.leveldb;

import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;

abstract class NativeObject implements Closeable {
    private static final String TAG = NativeObject.class.getSimpleName();
    protected long mPtr;
    private int mRefCount;

    protected NativeObject() {
        this.mRefCount = 0;
        ref();
    }

    protected NativeObject(long j) {
        this();
        if (j == 0) {
            throw new OutOfMemoryError("Failed to allocate native object");
        }
        this.mPtr = j;
    }

    protected void assertOpen(String str) {
        if (getPtr() == 0) {
            throw new IllegalStateException(str);
        }
    }

    public synchronized void close() {
        if (this.mPtr != 0) {
            unref();
        }
    }

    protected abstract void closeNativeObject(long j);

    protected void finalize() throws Throwable {
        if (this.mPtr != 0) {
            Class cls = getClass();
            Object simpleName = cls.getSimpleName();
            while (TextUtils.isEmpty((CharSequence) simpleName)) {
                cls = cls.getSuperclass();
                simpleName = cls.getSimpleName();
            }
            Log.w(TAG, "NativeObject " + simpleName + " refcount: " + this.mRefCount + " id: " + System.identityHashCode(this) + " was finalized before native resource was closed, did you forget to call close()?");
        }
        super.finalize();
    }

    protected synchronized long getPtr() {
        return this.mPtr;
    }

    synchronized void ref() {
        this.mRefCount++;
    }

    synchronized void unref() {
        if (this.mRefCount <= 0) {
            throw new IllegalStateException("Reference count is already 0");
        }
        this.mRefCount--;
        if (this.mRefCount == 0) {
            closeNativeObject(this.mPtr);
            this.mPtr = 0;
        }
    }
}
