package com.mcbox.p021b;

import android.content.Context;
import android.os.Build.VERSION;

import com.mcbox.pesdk.util.LauncherMiscUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class C1685a {
    public static boolean m5719a(Context context, String str) throws IOException {
        Exception e;
        boolean z = false;
        RandomAccessFile randomAccessFile = null;
        try {
            File dir = context.getDir("modLib", 0);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir.getAbsolutePath() + File.separator + "libminecraftpe.so");
            LauncherMiscUtil.saveFile(new FileInputStream(new File(str)), file);
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            try {
                if (VERSION.SDK_INT < 23) {
                    z = C1686b.m5722a(randomAccessFile2);
                }
            } catch (Exception e2) {
                e = e2;
                randomAccessFile = randomAccessFile2;
                e.printStackTrace();
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return z;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return z;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return z;
    }

    public void m5720a(String[] strArr) {
    }
}
