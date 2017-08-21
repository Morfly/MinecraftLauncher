package com.mcbox.p019a.p020a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class C1680f {
    private static final String f3544a = "MultiDex";
    private static final String f3545b = "classes";
    private static final String f3546c = ".dex";
    private static final String f3547d = ".classes";
    private static final String f3548e = ".zip";
    private static final int f3549f = 3;
    private static final String f3550g = "multidex.version";
    private static final String f3551h = "timestamp";
    private static final String f3552i = "crc";
    private static final String f3553j = "dex.number";
    private static final int f3554k = 16384;
    private static final long f3555l = -1;
    private static Method f3556m;

    static {
        try {
            f3556m = Editor.class.getMethod("apply", new Class[0]);
        } catch (NoSuchMethodException e) {
            f3556m = null;
        }
    }

    C1680f() {
    }

    private static SharedPreferences m5703a(Context context) {
        return context.getSharedPreferences(f3550g, VERSION.SDK_INT < 11 ? 0 : 4);
    }

    static List<File> m5704a(Context context, ApplicationInfo applicationInfo, File file, boolean z) throws IOException {
        List<File> a;
        Log.i(f3544a, "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + z + ")");
        File file2 = new File(applicationInfo.sourceDir);
        long c = C1680f.m5715c(file2);
        if (z || C1680f.m5712a(context, file2, c)) {
            Log.i(f3544a, "Detected that extraction must be performed.");
            a = C1680f.m5706a(file2, file);
            C1680f.m5707a(context, C1680f.m5714b(file2), c, a.size() + 1);
        } else {
            try {
                a = C1680f.m5705a(context, file2, file);
            } catch (Throwable e) {
                Log.w(f3544a, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e);
                a = C1680f.m5706a(file2, file);
                C1680f.m5707a(context, C1680f.m5714b(file2), c, a.size() + 1);
            }
        }
        Log.i(f3544a, "load found " + a.size() + " secondary dex files");
        return a;
    }

    private static List<File> m5705a(Context context, File file, File file2) throws IOException {
        Log.i(f3544a, "loading existing secondary dex files");
        String str = file.getName() + f3547d;
        int i = C1680f.m5703a(context).getInt(f3553j, 1);
        List<File> arrayList = new ArrayList(i);
        int i2 = 2;
        while (i2 <= i) {
            File file3 = new File(file2, str + i2 + ".zip");
            if (file3.isFile()) {
                arrayList.add(file3);
                if (C1680f.m5713a(file3)) {
                    i2++;
                } else {
                    Log.i(f3544a, "Invalid zip file: " + file3);
                    throw new IOException("Invalid ZIP file.");
                }
            }
            throw new IOException("Missing extracted secondary dex file '" + file3.getPath() + "'");
        }
        return arrayList;
    }

    private static List<File> m5706a(File file, File file2) throws IOException {
        String str = file.getName() + f3547d;
        C1680f.m5710a(file2, str);
        List<File> arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(file);
        try {
            ZipEntry entry = zipFile.getEntry(f3545b + 2 + f3546c);
            int i = 2;
            while (entry != null) {
                File file3 = new File(file2, str + i + ".zip");
                arrayList.add(file3);
                Log.i(f3544a, "Extraction is needed for file " + file3);
                boolean z = false;
                int i2 = 0;
                while (i2 < 3 && !z) {
                    int i3 = i2 + 1;
                    C1680f.m5711a(zipFile, entry, file3, str);
                    boolean a = C1680f.m5713a(file3);
                    if (!a) {
                        file3.delete();
                        if (file3.exists()) {
                            Log.w(f3544a, "Failed to delete corrupted secondary dex '" + file3.getPath() + "'");
                            z = a;
                            i2 = i3;
                        }
                    }
                    z = a;
                    i2 = i3;
                }
                if (z) {
                    i2 = i + 1;
                    entry = zipFile.getEntry(f3545b + i2 + f3546c);
                    i = i2;
                } else {
                    throw new IOException("Could not create zip file " + file3.getAbsolutePath() + " for secondary dex (" + i + ")");
                }
            }
            return arrayList;
        } finally {
            try {
                zipFile.close();
            } catch (Throwable e) {
                Log.w(f3544a, "Failed to close resource", e);
            }
        }
    }

    private static void m5707a(Context context, long j, long j2, int i) {
        Editor edit = C1680f.m5703a(context).edit();
        edit.putLong(f3551h, j);
        edit.putLong(f3552i, j2);
        edit.putInt(f3553j, i);
        C1680f.m5708a(edit);
    }

    private static void m5708a(Editor editor) {
        if (f3556m != null) {
            try {
                f3556m.invoke(editor, new Object[0]);
                return;
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
        editor.commit();
    }

    private static void m5709a(Closeable closeable) {
        try {
            closeable.close();
        } catch (Throwable e) {
            Log.w(f3544a, "Failed to close resource", e);
        }
    }

    private static void m5710a(File file, String str) throws IOException {
        file.mkdirs();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles(new C1681g(str));
            if (listFiles == null) {
                Log.w(f3544a, "Failed to list secondary dex dir content (" + file.getPath() + ").");
                return;
            }
            for (File file2 : listFiles) {
                Log.i(f3544a, "Trying to delete old file " + file2.getPath() + " of size " + file2.length());
                if (file2.delete()) {
                    Log.i(f3544a, "Deleted old file " + file2.getPath());
                } else {
                    Log.w(f3544a, "Failed to delete old file " + file2.getPath());
                }
            }
            return;
        }
        throw new IOException("Failed to create dex directory " + file.getPath());
    }

    private static void m5711a(ZipFile zipFile, ZipEntry zipEntry, File file, String str) {
        try {
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            File createTempFile = File.createTempFile(str, ".zip", file.getParentFile());
            Log.i(f3544a, "Extracting " + createTempFile.getPath());
            ZipOutputStream zipOutputStream;
            try {
                zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(createTempFile)));
                ZipEntry zipEntry2 = new ZipEntry("classes.dex");
                zipEntry2.setTime(zipEntry.getTime());
                zipOutputStream.putNextEntry(zipEntry2);
                byte[] bArr = new byte[16384];
                for (int read = inputStream.read(bArr); read != -1; read = inputStream.read(bArr)) {
                    zipOutputStream.write(bArr, 0, read);
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                Log.i(f3544a, "Renaming to " + file.getPath());
                if (createTempFile.renameTo(file)) {
                    C1680f.m5709a(inputStream);
                    createTempFile.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + createTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"");
            } catch (Throwable th) {
                C1680f.m5709a(inputStream);
                createTempFile.delete();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean m5712a(Context context, File file, long j) {
        SharedPreferences a = C1680f.m5703a(context);
        return (a.getLong(f3551h, -1) == C1680f.m5714b(file) && a.getLong(f3552i, -1) == j) ? false : true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean m5713a(java.io.File r4) {
        /*
        r0 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r0.<init>(r4);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r0.close();	 Catch:{ IOException -> 0x000a, ZipException -> 0x0029 }
        r0 = 1;
    L_0x0009:
        return r0;
    L_0x000a:
        r0 = move-exception;
        r0 = "MultiDex";
        r1 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r1.<init>();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r2 = "Failed to close zip file: ";
        r1 = r1.append(r2);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r2 = r4.getAbsolutePath();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r1 = r1.append(r2);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        android.util.Log.w(r0, r1);	 Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
    L_0x0027:
        r0 = 0;
        goto L_0x0009;
    L_0x0029:
        r0 = move-exception;
        r1 = "MultiDex";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "File ";
        r2 = r2.append(r3);
        r3 = r4.getAbsolutePath();
        r2 = r2.append(r3);
        r3 = " is not a valid zip file.";
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.w(r1, r2, r0);
        goto L_0x0027;
    L_0x004d:
        r0 = move-exception;
        r1 = "MultiDex";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Got an IOException trying to open zip file: ";
        r2 = r2.append(r3);
        r3 = r4.getAbsolutePath();
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.w(r1, r2, r0);
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mcbox.a.a.f.a(java.io.File):boolean");
    }

    private static long m5714b(File file) {
        long lastModified = file.lastModified();
        return lastModified == -1 ? lastModified - 1 : lastModified;
    }

    private static long m5715c(File file) throws IOException {
        long a = C1683i.m5716a(file);
        return a == -1 ? a - 1 : a;
    }
}
