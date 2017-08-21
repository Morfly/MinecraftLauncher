package com.umeng.analytics.onlineconfig;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C1823a;
import com.umeng.analytics.C1834f;
import com.umeng.fv;
import com.umeng.fw;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Iterator;

public class C1837a {
    public static final String f3790a = "type";
    public static final String f3791b = "package";
    public static final String f3792c = "channel";
    public static final String f3793d = "idmd5";
    public static final String f3794e = "version_code";
    public static final String f3795f = "appkey";
    public static final String f3796g = "sdk_version";
    private final String f3797h = "last_config_time";
    private final String f3798i = "report_policy";
    private final String f3799j = "online_config";
    private UmengOnlineConfigureListener f3800k = null;
    private C1839c f3801l = null;
    private long f3802m = 0;

    public class C1835a extends fw {
        final /* synthetic */ C1837a f3784a;
        private JSONObject f3785e;

        public C1835a(C1837a c1837a, JSONObject jSONObject) {
            super("");
            this.f3784a = c1837a;
            this.f3785e = jSONObject;
        }

        public JSONObject mo2909a() {
            return this.f3785e;
        }

        public String mo2910b() {
            return this.d;
        }
    }

    public class C1836b extends fv implements Runnable {
        Context f3788a;
        final /* synthetic */ C1837a f3789b;

        public C1836b(C1837a c1837a, Context context) {
            this.f3789b = c1837a;
            this.f3788a = context.getApplicationContext();
        }

        private void m5894b() {
            fw c1835a = new C1835a(this.f3789b, this.f3789b.m5902b(this.f3788a));
            String[] strArr = C1823a.f3727g;
            C1838b c1838b = null;
            for (String a : strArr) {
                c1835a.m5880a(a);
                c1838b = m5892a(c1835a, C1838b.class);
                if (c1838b != null) {
                    break;
                }
            }
            if (c1838b == null) {
                this.f3789b.m5901a(null);
            } else if (c1838b.f3804b) {
                if (this.f3789b.f3801l != null) {
                    this.f3789b.f3801l.mo3516a(c1838b.f3805c, (long) c1838b.f3806d);
                }
                this.f3789b.m5898a(this.f3788a, c1838b);
                this.f3789b.m5903b(this.f3788a, c1838b);
                this.f3789b.m5901a(c1838b.f3803a);
            } else {
                this.f3789b.m5901a(null);
            }
        }

        public boolean mo2911a() {
            return false;
        }

        public void run() {
            try {
                m5894b();
            } catch (Exception e) {
                this.f3789b.m5901a(null);
            }
        }
    }

    private void m5898a(Context context, C1838b c1838b) {
        Editor edit = C1834f.m5858a(context).m5875g().edit();
        if (!TextUtils.isEmpty(c1838b.f3807e)) {
            edit.putString(C1823a.f3730j, c1838b.f3807e);
            edit.commit();
        }
        if (c1838b.f3805c != -1) {
            C1834f.m5858a(context).m5864a(c1838b.f3805c, c1838b.f3806d);
        }
    }

    private void m5901a(JSONObject jSONObject) {
        if (this.f3800k != null) {
            this.f3800k.onDataReceived(jSONObject);
        }
    }

    private JSONObject m5902b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = f3790a;
            getClass();
            jSONObject.put(str, "online_config");
            jSONObject.put("appkey", AnalyticsConfig.getAppkey(context));
            jSONObject.put(f3794e, String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode));
            jSONObject.put(f3791b, context.getPackageName());
            jSONObject.put(f3796g, C1823a.f3723c);
            jSONObject.put(f3793d, m9943b(m9863f(context)));
            jSONObject.put(f3792c, AnalyticsConfig.getChannel(context));
            jSONObject.put("report_policy", C1834f.m5858a(context).m5867a()[0]);
            jSONObject.put("last_config_time", m5905c(context));
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    public static String m9873p(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", context.getPackageName()) == 0) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            return "";
        } catch (Exception e) {
        }
        return null;
    }

    public static String m9863f(Context context) {
        Object deviceId = new Object();
        String string;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        try {
            if (context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0) {
                deviceId = telephonyManager.getDeviceId();
                if (TextUtils.isEmpty((CharSequence) deviceId)) {
                    return (String) deviceId;
                }
                deviceId = m9873p(context);
                if (TextUtils.isEmpty((CharSequence) deviceId)) {
                    return (String) deviceId;
                }
                string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                return string;
            }
        } catch (Exception e) {
        }
        string = str;
        if (TextUtils.isEmpty((CharSequence) deviceId)) {
            return (String) deviceId;
        }
        deviceId = m9873p(context);
        if (TextUtils.isEmpty((CharSequence) deviceId)) {
            return (String) deviceId;
        }
        string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        return string;
    }

    public static String m9943b(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toHexString(b & 255));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private void m5903b(Context context, C1838b c1838b) {
        if (c1838b.f3803a != null && c1838b.f3803a.length() != 0) {
            Editor edit = C1834f.m5858a(context).m5875g().edit();
            try {
                JSONObject jSONObject = c1838b.f3803a;
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    edit.putString(str, jSONObject.getString(str));
                }
                edit.commit();
            } catch (Exception e) {
            }
        }
    }

    private String m5905c(Context context) {
        return C1834f.m5858a(context).m5875g().getString(C1823a.f3730j, "");
    }

    public void m5906a() {
        this.f3800k = null;
    }

    public void m5907a(Context context) {
        if (context == null) {

        } else if ((context.getApplicationInfo().flags & 2) != 0) {
            new Thread(new C1836b(this, context.getApplicationContext())).start();
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.f3802m > C1823a.f3734n) {
                this.f3802m = currentTimeMillis;
                new Thread(new C1836b(this, context.getApplicationContext())).start();
            }
        }
    }

    public void m5908a(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        this.f3800k = umengOnlineConfigureListener;
    }

    public void m5909a(C1839c c1839c) {
        this.f3801l = c1839c;
    }

    public void m5910b() {
        this.f3801l = null;
    }
}
