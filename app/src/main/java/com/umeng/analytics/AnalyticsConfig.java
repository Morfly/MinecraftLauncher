package com.umeng.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;


public class AnalyticsConfig {
    public static boolean ACTIVITY_DURATION_OPEN = true;
    public static boolean CATCH_EXCEPTION = true;
    public static boolean COMPRESS_DATA = true;
    public static boolean ENABLE_MEMORY_BUFFER = true;
    public static String GPU_RENDERER = "";
    public static String GPU_VENDER = "";
    private static String f3698a = null;
    private static String f3699b = null;
    private static double[] f3700c = null;
    private static int[] f3701d;
    public static long kContinueSessionMillis = 30000;
    public static int mVerticalType;
    public static String mWrapperType = null;
    public static String mWrapperVersion = null;
    public static int sAge;
    public static Gender sGender;
    public static String sId;
    public static String sSource;

    public static String getAppkey(Context context) {
        if (f3698a == null) {
            f3698a = m9872o(context);
        }
        return f3698a;
    }

    public static String getChannel(Context context) {
        if (f3699b == null) {
            f3699b = m9877t(context);
        }
        return f3699b;
    }

    public static String m9877t(Context context) {
        String str = "Unknown";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                Object obj = applicationInfo.metaData.get("UMENG_CHANNEL");
                if (obj != null) {
                    String obj2 = obj.toString();
                    if (obj2 != null) {
                        return obj2;
                    }
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String m9872o(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("UMENG_APPKEY");
                if (string != null) {
                    return string.trim();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static double[] getLocation() {
        return f3700c;
    }

    public static int[] getReportPolicy(Context context) {
        if (f3701d == null) {
            f3701d = C1834f.m5858a(context).m5867a();
        }
        return f3701d;
    }

    public static void setAppkey(String str) {
        f3698a = str;
    }

    public static void setChannel(String str) {
        f3699b = str;
    }

    public static void setLocation(double d, double d2) {
        if (f3700c == null) {
            f3700c = new double[2];
        }
        f3700c[0] = d;
        f3700c[1] = d2;
    }

    public static void setReportPolicy(int i, int i2) {
        if (f3701d == null) {
            f3701d = new int[2];
        }
        f3701d[0] = i;
        f3701d[1] = i2;
    }
}
