package com.yy.hiidostatis.inner.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static void zipFiles(String str, String str2, ZipOutputStream zipOutputStream) throws Throwable {
        Throwable th;
        int i = 0;
        if (zipOutputStream != null) {
            File file = new File(str + str2);
            FileInputStream fileInputStream;
            try {
                if (file.isFile()) {
                    ZipEntry zipEntry = new ZipEntry(str2);
                    fileInputStream = new FileInputStream(file);
                    try {
                        zipOutputStream.putNextEntry(zipEntry);
                        byte[] bArr = new byte[100000];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            zipOutputStream.write(bArr, 0, read);
                        }
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e) {
                            }
                        }
                        throw th;
                    }
                }
                String[] list = file.list();
                if (list.length <= 0) {
                    zipOutputStream.putNextEntry(new ZipEntry(str2 + File.separator));
                    zipOutputStream.closeEntry();
                }
                while (i < list.length) {
                    zipFiles(str, str2 + File.separator + list[i], zipOutputStream);
                    i++;
                }
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                    }
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
    }

    public static void zipFolder(String str, String str2) throws Throwable {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(str2));
        File file = new File(str);
        zipFiles(file.getParent() + File.separator, file.getName(), zipOutputStream);
        zipOutputStream.finish();
        zipOutputStream.close();
    }
}
