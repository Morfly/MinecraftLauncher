package fb;

import android.util.Log;

public class C2325b {
    public static boolean f6237a = false;

    public static void m10285a(String str, String str2) {
        if (f6237a) {
            Log.i(str, str2);
        }
    }

    public static void m10286a(String str, String str2, Exception exception) {
        if (f6237a) {
            Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m10287b(String str, String str2) {
        if (f6237a) {
            Log.e(str, str2);
        }
    }

    public static void m10288b(String str, String str2, Exception exception) {
        if (f6237a) {
            Log.e(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.e(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m10289c(String str, String str2) {
        if (f6237a) {
            Log.d(str, str2);
        }
    }

    public static void m10290c(String str, String str2, Exception exception) {
        if (f6237a) {
            Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m10291d(String str, String str2) {
        if (f6237a) {
            Log.v(str, str2);
        }
    }

    public static void m10292d(String str, String str2, Exception exception) {
        if (f6237a) {
            Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m10293e(String str, String str2) {
        if (f6237a) {
            Log.w(str, str2);
        }
    }

    public static void m10294e(String str, String str2, Exception exception) {
        if (f6237a) {
            Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }
}
