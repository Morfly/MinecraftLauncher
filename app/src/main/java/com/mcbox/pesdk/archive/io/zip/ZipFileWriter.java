package com.mcbox.pesdk.archive.io.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriter {
    public static void write(File file, ZipOutputStream zipOutputStream) throws IOException, Throwable {
        InputStream fileInputStream;
        Throwable th;
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[32768];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    zipOutputStream.write(bArr, 0, read);
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                zipOutputStream.closeEntry();
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    public static void write(File[] fileArr, File file) throws Throwable {
        Throwable th;
        ZipOutputStream zipOutputStream;
        FileOutputStream fileOutputStream = null;
        OutputStream outputStream;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            OutputStream bufferedOutputStream2;
            OutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream2, 32768);
            } catch (Throwable th2) {
                th = th2;
                zipOutputStream = null;
                bufferedOutputStream2 = fileOutputStream2;
                if (zipOutputStream != null) {
                    try {
                        zipOutputStream.close();
                    } catch (Exception e) {
                        throw th;
                    }
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
            try {
                zipOutputStream = new ZipOutputStream(bufferedOutputStream2);
            } catch (Throwable th3) {
                th = th3;
                zipOutputStream = null;
                outputStream = bufferedOutputStream2;
                bufferedOutputStream2 = fileOutputStream2;
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
            try {
                zipOutputStream.setLevel(-1);
                zipOutputStream.setMethod(8);
                for (File write : fileArr) {
                    write(write, zipOutputStream);
                }
                if (zipOutputStream != null) {
                    try {
                        zipOutputStream.close();
                    } catch (Exception e2) {
                        return;
                    }
                }
                if (bufferedOutputStream2 != null) {
                    bufferedOutputStream2.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
            } catch (Throwable th4) {
                th = th4;
                outputStream = bufferedOutputStream2;
                bufferedOutputStream2 = fileOutputStream2;
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            zipOutputStream = null;
            fileOutputStream = null;
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public static void zipFile(ZipOutputStream zipOutputStream, File file, String str, boolean z) throws Throwable {
        int i = 0;
        if (z) {
            try {
                if (file.isDirectory()) {
                    zipOutputStream.putNextEntry(new ZipEntry("/"));
                    str = str + file.getName();
                    z = false;
                } else {
                    str = file.getName();
                }
            } catch (Exception e) {
                System.out.println("zipFile error==" + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            String str2 = str + "/";
            while (i < listFiles.length) {
                zipFile(zipOutputStream, listFiles[i], str2 + listFiles[i].getName(), z);
                i++;
            }
            return;
        }
        write(file, zipOutputStream);
    }
}
