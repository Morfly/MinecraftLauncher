package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class RawDataSerializer {
    private FileInputStream mInputStream;
    private boolean mIsOpen = false;
    private boolean mIsReadMode = false;
    private final String mName;
    private FileOutputStream mOutputStream;

    public RawDataSerializer(String str) {
        this.mName = str;
    }

    private int getFileSizeInBytes() {
        int i = -1;
        if (this.mIsOpen) {
            FileChannel channel;
            if (this.mIsReadMode) {
                FileInputStream fileInputStream = this.mInputStream;
                if (fileInputStream == null) {
                    return i;
                }
                channel = fileInputStream.getChannel();
            } else {
                FileOutputStream fileOutputStream = this.mOutputStream;
                if (fileOutputStream == null) {
                    return i;
                }
                channel = fileOutputStream.getChannel();
            }
            if (channel == null) {
                return i;
            }
            try {
                return (int) channel.size();
            } catch (IOException e) {
                C1923L.error(this, "Exception when access file size %s.", e);
                return i;
            }
        }
        C1923L.error(this, "Cannot check file size for not opened.", new Object[0]);
        return i;
    }

    private boolean open(Context context, boolean z) {
        if (this.mIsOpen) {
            return z == this.mIsReadMode;
        } else {
            C1923L.brief("Open stream[file=%s] for read %b.", this.mName, Boolean.valueOf(z));
            if (z) {
                try {
                    this.mInputStream = context.openFileInput(this.mName);
                    this.mIsOpen = true;
                } catch (Exception e) {
                    C1923L.debug(this, "exception when open %s for %s", this.mName, e);
                }
            } else {
                try {
                    this.mOutputStream = context.openFileOutput(this.mName, 32768);
                    this.mIsOpen = true;
                } catch (Exception e2) {
                    C1923L.error(this, "exception when open %s for %s", this.mName, e2);
                }
            }
            this.mIsReadMode = z;
            return this.mIsOpen;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
        r5 = this;
        r1 = 0;
        r4 = 0;
        r0 = r5.mIsOpen;
        if (r0 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r5.mIsOpen = r1;
        r0 = r5.mInputStream;	 Catch:{ IOException -> 0x001c }
        if (r0 == 0) goto L_0x0010;
    L_0x000d:
        r0.close();	 Catch:{ IOException -> 0x001c }
    L_0x0010:
        r0 = r5.mOutputStream;	 Catch:{ IOException -> 0x001c }
        if (r0 == 0) goto L_0x0017;
    L_0x0014:
        r0.close();	 Catch:{ IOException -> 0x001c }
    L_0x0017:
        r5.mInputStream = r4;
        r5.mOutputStream = r4;
        goto L_0x0006;
    L_0x001c:
        r0 = move-exception;
        r1 = "lcy Failed to close output stream for %s";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x002d }
        r3 = 0;
        r2[r3] = r0;	 Catch:{ all -> 0x002d }
        com.yy.hiidostatis.inner.util.log.C1923L.error(r5, r1, r2);	 Catch:{ all -> 0x002d }
        r5.mInputStream = r4;
        r5.mOutputStream = r4;
        goto L_0x0006;
    L_0x002d:
        r0 = move-exception;
        r5.mInputStream = r4;
        r5.mOutputStream = r4;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.inner.implementation.RawDataSerializer.close():void");
    }

    public void dropAll() {
        FileOutputStream fileOutputStream = this.mOutputStream;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getChannel().truncate(0);
            } catch (IOException e) {
                C1923L.error(this, "Failed to drop file contents for %s.", e);
            }
        }
    }

    public InputStream getInputStream() {
        if (this.mIsOpen && this.mIsReadMode) {
            return this.mInputStream;
        }
        C1923L.error(this, "Illegal state, cannot get InputStream : isOpen %b, isReadMode %b.", Boolean.valueOf(this.mIsOpen), Boolean.valueOf(this.mIsReadMode));
        return null;
    }

    public OutputStream getOutputStream() {
        if (this.mIsOpen && !this.mIsReadMode) {
            return this.mOutputStream;
        }
        C1923L.error(this, "Illegal state, cannot get OutputStream : isOpen %b, isReadMode %b.", Boolean.valueOf(this.mIsOpen), Boolean.valueOf(this.mIsReadMode));
        return null;
    }

    public boolean haveData() {
        return getFileSizeInBytes() > 0;
    }

    public boolean isExistFile(Context context) {
        boolean z = false;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(this.mName);
            z = true;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            z = false;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e4) {
                }
            }
        }
        return z;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public byte[] load() throws IOException {
        return loadOrThrow();
    }

    public byte[] loadOrThrow() throws IOException {
        if (this.mIsOpen && this.mIsReadMode) {
            FileInputStream fileInputStream = this.mInputStream;
            if (fileInputStream == null) {
                C1923L.error(this, "Illegal state, mInputStream is null.", new Object[0]);
                return null;
            }
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            return bArr;
        }
        C1923L.error(this, "Illegal state, cannot load commands : isOpen %b, isReadMode %b.", Boolean.valueOf(this.mIsOpen), Boolean.valueOf(this.mIsReadMode));
        return null;
    }

    public boolean openForRead(Context context) {
        return open(context, true);
    }

    public boolean openForWrite(Context context) {
        return open(context, false);
    }

    public boolean save(byte[] bArr) throws IOException {
        saveOrThrow(bArr);
        return true;
    }

    public void saveOrThrow(byte[] bArr) throws IOException {
        if (!Util.empty(bArr)) {
            if (!this.mIsOpen || this.mIsReadMode) {
                C1923L.error(this, "Illegal state, cannot save %s : isOpen %b, isReadMode %b.", bArr, Boolean.valueOf(this.mIsOpen), Boolean.valueOf(this.mIsReadMode));
                return;
            }
            FileOutputStream fileOutputStream = this.mOutputStream;
            if (fileOutputStream == null) {
                C1923L.error(this, "Illegal state, mOutputStream is null.", new Object[0]);
            } else {
                fileOutputStream.write(bArr);
            }
        }
    }
}
