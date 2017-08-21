package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;
import com.umeng.analytics.social.C1857e;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.fp;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;
//import p023u.aly.fo;


public class MobclickAgent {
    private static final String f3704a = "input map is null";
    private static final C1832c f3705b = new C1832c();

    public static void flush(Context context) {
        f3705b.m5853d(context);
    }

    public static C1832c getAgent() {
        return f3705b;
    }

    public static String getConfigParams(Context context, String str) {
        return C1834f.m5858a(context).m5875g().getString(str, "");
    }

    public static void onEvent(Context context, String str) {
        f3705b.m5838a(context, str, null, -1, 1);
    }

    public static void onEvent(Context context, String str, int i) {
        f3705b.m5838a(context, str, null, -1, i);
    }

    public static void onEvent(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            fp.m9882a(C1823a.f3725e, "label is null or empty");
        } else {
            f3705b.m5838a(context, str, str2, -1, 1);
        }
    }

    public static void onEvent(Context context, String str, String str2, int i) {
        if (TextUtils.isEmpty(str2)) {
            fp.m9882a(C1823a.f3725e, "label is null or empty");
        } else {
            f3705b.m5838a(context, str, str2, -1, i);
        }
    }

    public static void onEvent(Context context, String str, Map<String, String> map) {
        if (map == null) {
            fp.m9884b(C1823a.f3725e, f3704a);
            return;
        }
        f3705b.m5841a(context, str, new HashMap(map), -1);
    }

    public static void onEventBegin(Context context, String str) {
        f3705b.m5837a(context, str, null);
    }

    public static void onEventBegin(Context context, String str, String str2) {
        f3705b.m5837a(context, str, str2);
    }

    public static void onEventDuration(Context context, String str, long j) {
        if (j <= 0) {
            fp.m9882a(C1823a.f3725e, "duration is not valid in onEventDuration");
        } else {
            f3705b.m5838a(context, str, null, j, 1);
        }
    }

    public static void onEventDuration(Context context, String str, String str2, long j) {
        if (TextUtils.isEmpty(str2)) {
            fp.m9882a(C1823a.f3725e, "label is null or empty");
        } else if (j <= 0) {
            fp.m9882a(C1823a.f3725e, "duration is not valid in onEventDuration");
        } else {
            f3705b.m5838a(context, str, str2, j, 1);
        }
    }

    public static void onEventDuration(Context context, String str, Map<String, String> map, long j) {
        if (j <= 0) {
            fp.m9882a(C1823a.f3725e, "duration is not valid in onEventDuration");
        } else if (map == null) {
            fp.m9884b(C1823a.f3725e, f3704a);
        } else {
            f3705b.m5841a(context, str, new HashMap(map), j);
        }
    }

    public static void onEventEnd(Context context, String str) {
        f3705b.m5849b(context, str, null);
    }

    public static void onEventEnd(Context context, String str, String str2) {
        f3705b.m5849b(context, str, str2);
    }

    public static void onEventValue(Context context, String str, Map<String, String> map, int i) {
        Map hashMap = map == null ? new HashMap() : new HashMap(map);
        hashMap.put("__ct__", Integer.valueOf(i));
        f3705b.m5841a(context, str, hashMap, -1);
    }

    public static void onKVEventBegin(Context context, String str, Map<String, String> map, String str2) {
        if (map == null) {
            fp.m9884b(C1823a.f3725e, f3704a);
        } else {
            f3705b.m5840a(context, str, new HashMap(map), str2);
        }
    }

    public static void onKVEventEnd(Context context, String str, String str2) {
        f3705b.m5852c(context, str, str2);
    }

    public static void onKillProcess(Context context) {
        f3705b.m5854e(context);
    }

    public static void onPageEnd(String str) {
        if (TextUtils.isEmpty(str)) {
            fp.m9884b(C1823a.f3725e, "pageName is null or empty");
        } else {
            f3705b.m5850b(str);
        }
    }

    public static void onPageStart(String str) {
        if (TextUtils.isEmpty(str)) {
            fp.m9884b(C1823a.f3725e, "pageName is null or empty");
        } else {
            f3705b.m5845a(str);
        }
    }

    public static void onPause(Context context) {
        f3705b.m5851c(context);
    }

    public static void onResume(Context context) {
        if (context == null) {
            fp.m9884b(C1823a.f3725e, "unexpected null context in onResume");
        } else {
            f3705b.m5848b(context);
        }
    }

    public static void onResume(Context context, String str, String str2) {
        if (context == null) {
            fp.m9884b(C1823a.f3725e, "unexpected null context in onResume");
        } else if (str == null || str.length() == 0) {
            fp.m9884b(C1823a.f3725e, "unexpected empty appkey in onResume");
        } else {
            AnalyticsConfig.setAppkey(str);
            AnalyticsConfig.setChannel(str2);
            f3705b.m5848b(context);
        }
    }

    public static void onSocialEvent(Context context, String str, UMPlatformData... uMPlatformDataArr) throws JSONException {
        if (context == null) {
            fp.m9884b(C1823a.f3725e, "context is null in onShareEvent");
            return;
        }
        C1857e.f3835e = "3";
        UMSocialService.share(context, str, uMPlatformDataArr);
    }

    public static void onSocialEvent(Context context, UMPlatformData... uMPlatformDataArr) throws JSONException {
        if (context == null) {
            fp.m9884b(C1823a.f3725e, "context is null in onShareEvent");
            return;
        }
        C1857e.f3835e = "3";
        UMSocialService.share(context, uMPlatformDataArr);
    }

    public static void openActivityDurationTrack(boolean z) {
        AnalyticsConfig.ACTIVITY_DURATION_OPEN = z;
    }

    public static void reportError(Context context, String str) {
        f3705b.m5836a(context, str);
    }

    public static void reportError(Context context, Throwable th) {
        f3705b.m5842a(context, th);
    }

    public static void setAutoLocation(boolean z) {
    }

    public static void setCatchUncaughtExceptions(boolean z) {
        AnalyticsConfig.CATCH_EXCEPTION = z;
    }

    public static void setDebugMode(boolean z) {
        fp.f6052a = z;
        C1857e.f3852v = z;
    }

    public static void setEnableEventBuffer(boolean z) {
        AnalyticsConfig.ENABLE_MEMORY_BUFFER = z;
    }

    public static void setOnlineConfigureListener(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        f3705b.m5844a(umengOnlineConfigureListener);
    }

    public static void setOpenGLContext(GL10 gl10) {
        if (gl10 != null) {
            /*String[] a = fo.m9856a(gl10);
            if (a.length == 2) {
                AnalyticsConfig.GPU_VENDER = a[0];
                AnalyticsConfig.GPU_RENDERER = a[1];
            }*/
        }
    }

    public static void setSessionContinueMillis(long j) {
        AnalyticsConfig.kContinueSessionMillis = j;
    }

    public static void setUserID(Context context, String str, String str2, Gender gender, int i) {
        if (TextUtils.isEmpty(str)) {
            fp.m9886c(C1823a.f3725e, "userID is null or empty");
            str = null;
        }
        if (TextUtils.isEmpty(str2)) {
            fp.m9882a(C1823a.f3725e, "id source is null or empty");
            str2 = null;
        }
        if (i <= 0 || i >= 200) {
            fp.m9882a(C1823a.f3725e, "not a valid age!");
            i = -1;
        }
        C1834f.m5858a(context).m5865a(str, str2, i, gender.value);
    }

    public static void setWrapper(String str, String str2) {
        f3705b.m5846a(str, str2);
    }

    public static void updateOnlineConfig(Context context) {
        f3705b.m5835a(context);
    }

    public static void updateOnlineConfig(Context context, String str, String str2) {
        if (str == null || str.length() == 0) {
            fp.m9884b(C1823a.f3725e, "unexpected empty appkey in onResume");
            return;
        }
        AnalyticsConfig.setAppkey(str);
        AnalyticsConfig.setChannel(str2);
        f3705b.m5835a(context);
    }
}
