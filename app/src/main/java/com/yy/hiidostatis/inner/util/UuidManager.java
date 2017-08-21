package com.yy.hiidostatis.inner.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.yy.hiidostatis.inner.util.cipher.Coder;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UuidManager {
    private static String UUID_SDCARD_PATH;

    static {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuffer.append(File.separator);
        stringBuffer.append(".android");
        stringBuffer.append(File.separator);
        stringBuffer.append("uuid.bck");
        UUID_SDCARD_PATH = stringBuffer.toString();
    }

    public static String fetchUUid(Context context) throws Throwable {
        String str = null;
        Object readUUid = readUUid(context);
        if (TextUtils.isEmpty((CharSequence) readUUid)) {
            String replace = UUID.randomUUID().toString().replace("-", "");
            saveUUid(UUID_SDCARD_PATH, replace);
            saveUUid(getDataDirectory(context), replace);
            try {
                str = Coder.encryptMD5(replace);
            } catch (Exception e) {
            }
        } else {
            C1923L.debug(UuidManager.class, "uuid exist:%s", readUUid);
            try {
                str = Coder.encryptMD5((String) readUUid);
            } catch (Exception e2) {
            }
        }
        return str;
    }

    private static String getDataDirectory(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(context.getFilesDir().getAbsolutePath());
            stringBuffer.append(File.separator);
            stringBuffer.append("uuid.bck");
            String stringBuffer2 = stringBuffer.toString();
            C1923L.debug(UuidManager.class, "data uuid path:" + stringBuffer2, new Object[0]);
            return stringBuffer2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String readUUid(Context context) throws Throwable {
        IOException e;
        Exception e2;
        Throwable th;
        Object obj = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File(getDataDirectory(context));
            if (file.exists() && file.canRead()) {
                int i = 1;
            } else {
                C1923L.debug(UuidManager.class, "uuid is not exist at /data/data/...", new Object[0]);
                file = new File(UUID_SDCARD_PATH);
            }
            String str;
            if (file.exists()) {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[32];
                    fileInputStream2.read(bArr);
                    str = new String(bArr);
                    if (obj != null) {
                        if (!new File(UUID_SDCARD_PATH).exists()) {
                            saveUUid(UUID_SDCARD_PATH, str);
                        }
                    } else if (!new File(getDataDirectory(context)).exists()) {
                        saveUUid(getDataDirectory(context), str);
                    }
                    if (fileInputStream2 == null) {
                        return str;
                    }
                    try {
                        fileInputStream2.close();
                        return str;
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        return str;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    fileInputStream = fileInputStream2;
                    try {
                        e2.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        return "";
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            }
            C1923L.debug(UuidManager.class, "coudn't read uuid from back up file, the file is not exist.", new Object[0]);
            str = "";
            if (fileInputStream == null) {
                return str;
            }
            try {
                fileInputStream.close();
                return str;
            } catch (IOException e7) {
                IOException e6 = e7;
            }
        } catch (Exception e8) {
            e2 = e8;
            e2.printStackTrace();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return "";
        }
        return null;
    }

    private static void saveUUid(String str, String str2) throws Throwable {
        IOException e;
        Exception e2;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(str);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(str2.getBytes());
                fileOutputStream2.flush();
                C1923L.debug(UuidManager.class, "saved uuid path:" + str, new Object[0]);
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e3) {
                        e = e3;
                    }
                }
            } catch (Exception e4) {
                e2 = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    e2.printStackTrace();
                    C1923L.debug(UuidManager.class, "occured exception:" + e2.getMessage(), new Object[0]);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e7) {
            e2 = e7;
            e2.printStackTrace();
            C1923L.debug(UuidManager.class, "occured exception:" + e2.getMessage(), new Object[0]);
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}
