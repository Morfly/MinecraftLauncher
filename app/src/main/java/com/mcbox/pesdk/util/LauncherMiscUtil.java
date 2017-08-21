package com.mcbox.pesdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

public class LauncherMiscUtil {
    public static final String[] blankArray = new String[0];

    static {
        try {
            System.loadLibrary("launcherutil");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static native byte[] decryptModule(byte[] bArr, int i);

    public static ApplicationInfo getApplicationInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            return (packageManager == null || packageName == null) ? null : packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (Throwable e) {
            Log.w("LauncherMiscUtil", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    public static String getFileSizeWithByte(Context context, int i) {
        String str = "";
        if (i >= 0) {
            try {
                str = Formatter.formatFileSize(context, (long) i);
            } catch (Exception e) {
            }
        }
        return str;
    }

    public static String getFileSizeWithByte(Context context, String str) {
        String str2 = "未知";
        try {
            int intValue = Integer.valueOf(str.replaceAll("\\D+", "").replaceAll("\r", "").replaceAll("\n", "").trim()).intValue();
            if (intValue >= 0) {
                str2 = Formatter.formatFileSize(context, (long) intValue);
            }
        } catch (Exception e) {
        }
        return str2;
    }

    public static long getFolderSize(File file) {
        long j;
        Exception e;
        try {
            File[] listFiles = file.listFiles();
            j = 0;
            int i = 0;
            while (i < listFiles.length) {
                try {
                    j = listFiles[i].isDirectory() ? j + getFolderSize(listFiles[i]) : j + listFiles[i].length();
                    i++;
                } catch (Exception e2) {
                    e = e2;
                }
            }
        } catch (Exception e3) {
            Exception exception = e3;
            j = 0;
            e = exception;
            e.printStackTrace();
            return j;
        }
        return j;
    }

    public static boolean isLocalAddress(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        return (address[0] & 255) == 172 && (address[1] & 240) == 17;
    }

    public static boolean isNullStr(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String join(String[] strArr, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            stringBuilder.append(strArr[i]);
            if (i < strArr.length - 1) {
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }

    public static void saveFile(InputStream inputStream, File file) throws Throwable {
        Exception e;
        Throwable th;
        if (inputStream != null) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e6) {
                e = e6;
                fileOutputStream = null;
                e.printStackTrace();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        }
    }
}
