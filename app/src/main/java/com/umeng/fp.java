package com.umeng;

import android.util.Log;

public class fp {
    public static boolean f6052a = false;

    public static void m9882a(String str, String str2) {
        if (f6052a) {
            Log.i(str, str2);
        }
    }

    public static void m9883a(String str, String str2, Exception exception) {
        if (f6052a) {
            Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m9884b(String str, String str2) {
        if (f6052a) {
            Log.e(str, str2);
        }
    }

    public static void m9885b(String str, String str2, Exception exception) {
        if (f6052a) {
            Log.e(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.e(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m9886c(String str, String str2) {
        if (f6052a) {
            Log.d(str, str2);
        }
    }

    public static void m9887c(String str, String str2, Exception exception) {
        if (f6052a) {
            Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m9888d(String str, String str2) {
        if (f6052a) {
            Log.v(str, str2);
        }
    }

    public static void m9889d(String str, String str2, Exception exception) {
        if (f6052a) {
            Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m9890e(String str, String str2) {
        if (f6052a) {
            Log.w(str, str2);
        }
    }

    public static void m9891e(String str, String str2, Exception exception) {
        if (f6052a) {
            Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }
}
