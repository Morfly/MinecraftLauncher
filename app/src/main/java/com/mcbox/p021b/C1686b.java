package com.mcbox.p021b;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class C1686b {
    private static final long[] f3563a = new long[]{5888928, 5872756, 5850416, 5838340, 5884000, 5844672};
    private static final long[] f3564b = new long[]{5848640, 5836564, 5867368, 5842492};
    private static final long[] f3565c = new long[]{5896748, 5884656, 5867000, 5883028};
    private static final long[][] f3566d = new long[][]{new long[]{5893548, 5893572, 5893736}, new long[]{5881456, 5881480, 5881644}, new long[]{5862548, 5862572, 5862752}, new long[]{5887472, 5887496, 5887516}};
    private static final long[][] f3567e;
    private static final long[][] f3568f = new long[][]{new long[]{5854984, 5869072}, new long[]{5842908, 5856980}, new long[]{5867348, 5887032}, new long[]{5892940, 5839524}};
    private static final long[][] f3569g;
    private static Context f3570h;

    static {
        long[][] jArr = new long[4][];
        jArr[0] = new long[]{5898128};
        jArr[1] = new long[]{5886036};
        jArr[2] = new long[]{5916004};
        jArr[3] = new long[]{5867812};
        f3567e = jArr;
        jArr = new long[4][];
        jArr[0] = new long[]{5893528};
        jArr[1] = new long[]{5881436};
        jArr[2] = new long[]{5862528};
        jArr[3] = new long[]{5880316};
        f3569g = jArr;
    }

    public static void m5721a(String[] strArr) {
        Exception e;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(new File("F:\\" + File.separator + "libminecraftpe.so"), "rw");
            try {
                C1686b.m5723a(randomAccessFile, 0);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            randomAccessFile = null;
            e.printStackTrace();
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    public static boolean m5722a(RandomAccessFile randomAccessFile) {
        int i = 0;
        boolean z = false;
        while (i < f3563a.length) {
            z = C1686b.m5723a(randomAccessFile, i);
            if (z) {
                break;
            }
            i++;
        }
        return z;
    }

    public static boolean m5723a(RandomAccessFile randomAccessFile, int i) {
        boolean a = C1686b.m5724a(randomAccessFile, f3563a[i], "getKeyboardHeight");
        if (a) {
            C1686b.m5726b(randomAccessFile, f3563a[i], "getKeyboardHeittt");
        }
        return a;
    }

    private static boolean m5724a(RandomAccessFile randomAccessFile, long j, String str) {
        if (j == -1) {
            return true;
        }
        byte[] bArr = new byte[str.length()];
        try {
            randomAccessFile.seek(j);
            randomAccessFile.read(bArr, 0, str.length());
            if (new String(bArr).equalsIgnoreCase(str)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean m5725a(RandomAccessFile randomAccessFile, long[] jArr, String str) {
        int i = 0;
        if (jArr == null || jArr.length <= 0) {
            return true;
        }
        boolean z = false;
        while (i < jArr.length) {
            z = C1686b.m5724a(randomAccessFile, jArr[i], str);
            if (!z) {
                return z;
            }
            i++;
        }
        return z;
    }

    private static void m5726b(RandomAccessFile randomAccessFile, long j, String str) {
        if (j != -1) {
            try {
                randomAccessFile.seek(j);
                randomAccessFile.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void m5727b(RandomAccessFile randomAccessFile, long[] jArr, String str) {
        if (jArr != null && jArr.length > 0) {
            for (long b : jArr) {
                C1686b.m5726b(randomAccessFile, b, str);
            }
        }
    }
}
