package com.umeng.analytics.social;

import android.util.Log;

public class C1854b {
    public static void m5920a(String str, String str2) {
        if (C1857e.f3852v) {
            Log.i(str, str2);
        }
    }

    public static void m5921a(String str, String str2, Exception exception) {
        if (C1857e.f3852v) {
            Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m5922b(String str, String str2) {
        if (C1857e.f3852v) {
            Log.e(str, str2);
        }
    }

    public static void m5923b(String str, String str2, Exception exception) {
        if (C1857e.f3852v) {
            Log.e(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.e(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m5924c(String str, String str2) {
        if (C1857e.f3852v) {
            Log.d(str, str2);
        }
    }

    public static void m5925c(String str, String str2, Exception exception) {
        if (C1857e.f3852v) {
            Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m5926d(String str, String str2) {
        if (C1857e.f3852v) {
            Log.v(str, str2);
        }
    }

    public static void m5927d(String str, String str2, Exception exception) {
        if (C1857e.f3852v) {
            Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m5928e(String str, String str2) {
        if (C1857e.f3852v) {
            Log.w(str, str2);
        }
    }

    public static void m5929e(String str, String str2, Exception exception) {
        if (C1857e.f3852v) {
            Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }
}
