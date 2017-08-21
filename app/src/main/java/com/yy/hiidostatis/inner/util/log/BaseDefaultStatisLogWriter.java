package com.yy.hiidostatis.inner.util.log;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class BaseDefaultStatisLogWriter implements IBaseStatisLogWriter {
    public static final int DEFAULT_MAX_LEN = 262144;
    private static final int MIN_LOG_SIZE = 8192;
    private boolean isFileExist;
    private String mFilePath;
    private int mHonoredOnExceeded;
    private int mLogMaxLen;
    private final boolean mWriteDebugLog;

    public BaseDefaultStatisLogWriter(String str, int i, boolean z) {
        this.mLogMaxLen = 262144;
        this.mHonoredOnExceeded = 8092;
        this.isFileExist = false;
        this.mFilePath = str;
        this.mLogMaxLen = Math.max(i, 8192);
        this.mHonoredOnExceeded = this.mLogMaxLen / 4;
        this.mWriteDebugLog = z;
        this.isFileExist = fileCreateIfNotExist(this.mFilePath);
    }

    public BaseDefaultStatisLogWriter(String str, boolean z) {
        this(str, 262144, z);
    }

    private boolean fileCreateIfNotExist(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        boolean exists;
        try {
            File file = new File(this.mFilePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            exists = file.exists();
        } catch (Exception e) {
            exists = false;
        }
        return exists;
    }

    private boolean writeLogOrThrow(String str) throws Throwable {
        Throwable th;
        FileWriter fileWriter = null;
        if (!this.isFileExist) {
            return false;
        }
        if (str == null || str.length() == 0) {
            return true;
        }
        File file = new File(this.mFilePath);
        if (!file.canWrite()) {
            return false;
        }
        StringBuilder stringBuilder;
        if (file.length() > ((long) this.mLogMaxLen)) {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            stringBuilder = new StringBuilder(this.mHonoredOnExceeded);
            lineNumberReader.skip(file.length() - ((long) this.mHonoredOnExceeded));
            while (true) {
                String readLine = lineNumberReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
                stringBuilder.append("\n");
            }
            lineNumberReader.close();
            if (!file.delete()) {
                return false;
            }
        }
        stringBuilder = null;
        try {
            FileWriter fileWriter2 = new FileWriter(file, true);
            if (stringBuilder != null) {
                try {
                    stringBuilder.append("\n");
                    stringBuilder.append(str);
                    stringBuilder.append("\n");
                    fileWriter2.write(stringBuilder.toString());
                } catch (Throwable th2) {
                    th = th2;
                    fileWriter = fileWriter2;
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                    throw th;
                }
            }
            fileWriter2.write(str + "\n");
            fileWriter2.flush();
            if (fileWriter2 != null) {
                fileWriter2.close();
            }
            return true;
        } catch (Throwable th3) {
            th = th3;
            if (fileWriter != null) {
                fileWriter.close();
            }
            throw th;
        }
    }

    public boolean outputDebug() {
        return this.mWriteDebugLog;
    }

    public void write(int i, String str) {
        try {
            writeLogOrThrow(str);
        } catch (IOException e) {
            Log.d(BaseDefaultStatisLogWriter.class.toString(), "write exception=" + e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
