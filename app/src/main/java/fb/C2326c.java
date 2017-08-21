package fb;

import android.content.Context;
import android.content.res.Resources;

import com.umeng.AnalyticsEvents;

public class C2326c {
    private static final String f6238a = C2326c.class.getName();
    private static C2326c f6239b = null;
    private Resources f6240c;
    private final String f6241d;
    private final String f6242e = "drawable";
    private final String f6243f = "id";
    private final String f6244g = "layout";
    private final String f6245h = "anim";
    private final String f6246i = AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE;
    private final String f6247j = "string";
    private final String f6248k = "array";

    private C2326c(Context context) {
        this.f6240c = context.getResources();
        this.f6241d = context.getPackageName();
    }

    private int m10295a(String str, String str2) {
        int identifier = this.f6240c.getIdentifier(str, str2, this.f6241d);
        if (identifier != 0) {
            return identifier;
        }
        C2325b.m10287b(f6238a, "getRes(" + str2 + "/ " + str + ")");
        C2325b.m10287b(f6238a, "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
        return 0;
    }

    public static synchronized C2326c m10296a(Context context) {
        C2326c c2326c;
        synchronized (C2326c.class) {
            if (f6239b == null) {
                f6239b = new C2326c(context.getApplicationContext());
            }
            c2326c = f6239b;
        }
        return c2326c;
    }

    public int m10297a(String str) {
        return m10295a(str, "anim");
    }

    public int m10298b(String str) {
        return m10295a(str, "id");
    }

    public int m10299c(String str) {
        return m10295a(str, "drawable");
    }

    public int m10300d(String str) {
        return m10295a(str, "layout");
    }

    public int m10301e(String str) {
        return m10295a(str, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE);
    }

    public int m10302f(String str) {
        return m10295a(str, "string");
    }

    public int m10303g(String str) {
        return m10295a(str, "array");
    }
}
