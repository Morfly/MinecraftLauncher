package com.umeng.analytics.social;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.umeng.analytics.C1823a;
import com.umeng.analytics.onlineconfig.C1837a;
import com.umeng.analytics.social.UMPlatformData.GENDER;
import com.yy.hiidostatis.inner.BaseStatisContent;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class C1858f {
    private static Map<String, String> f3853a;

    protected static String m5940a(Context context) {
        Object obj = C1857e.f3834d;
        if (TextUtils.isEmpty((CharSequence) obj)) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    String string = applicationInfo.metaData.getString("UMENG_APPKEY");
                    if (string != null) {
                        return string.trim();
                    }
                    C1854b.m5922b(C1823a.f3725e, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
                }
            } catch (Exception e) {
                C1854b.m5923b(C1823a.f3725e, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", e);
            }
            return null;
        }
        C1854b.m5922b(C1823a.f3725e, "use usefully appkey from constant field.");
        return (String) obj;
    }

    private static String m5941a(List<NameValuePair> list) {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new UrlEncodedFormEntity(list, "UTF-8").writeTo(byteArrayOutputStream);
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<NameValuePair> m5942a(UMPlatformData... uMPlatformDataArr) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        for (UMPlatformData uMPlatformData : uMPlatformDataArr) {
            stringBuilder.append(uMPlatformData.getMeida().toString());
            stringBuilder.append(',');
            stringBuilder2.append(uMPlatformData.getUsid());
            stringBuilder2.append(',');
            stringBuilder3.append(uMPlatformData.getWeiboId());
            stringBuilder3.append(',');
        }
        if (uMPlatformDataArr.length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
        }
        List<NameValuePair> arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("platform", stringBuilder.toString()));
        arrayList.add(new BasicNameValuePair("usid", stringBuilder2.toString()));
        if (stringBuilder3.length() > 0) {
            arrayList.add(new BasicNameValuePair("weiboid", stringBuilder3.toString()));
        }
        return arrayList;
    }

    private static boolean m5943a(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    protected static String[] m5944a(Context context, String str, UMPlatformData... uMPlatformDataArr) throws JSONException {
        if (uMPlatformDataArr == null || uMPlatformDataArr.length == 0) {
            throw new C1853a("platform data is null");
        }
        Object a = C1858f.m5940a(context);
        if (TextUtils.isEmpty((CharSequence) a)) {
            throw new C1853a("can`t get appkey.");
        }
        List arrayList = new ArrayList();
        String str2 = "http://log.umsns.com/share/api/" + a + "/";
        if (f3853a == null || f3853a.isEmpty()) {
            f3853a = C1858f.m5947c(context);
        }
        if (!(f3853a == null || f3853a.isEmpty())) {
            for (Entry entry : f3853a.entrySet()) {
                arrayList.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        arrayList.add(new BasicNameValuePair("date", String.valueOf(System.currentTimeMillis())));
        arrayList.add(new BasicNameValuePair(C1837a.f3792c, C1857e.f3835e));
        if (!TextUtils.isEmpty(str)) {
            arrayList.add(new BasicNameValuePair("topic", str));
        }
        arrayList.addAll(C1858f.m5942a(uMPlatformDataArr));
        String b = C1858f.m5945b(uMPlatformDataArr);
        if (b == null) {
            b = "null";
        }
        C1854b.m5924c(C1823a.f3725e, "URL:" + (str2 + "?" + C1858f.m5941a(arrayList)));
        C1854b.m5924c(C1823a.f3725e, "BODY:" + b);
        return new String[]{"", b};
    }

    private static String m5945b(UMPlatformData... uMPlatformDataArr) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (UMPlatformData uMPlatformData : uMPlatformDataArr) {
            GENDER gender = uMPlatformData.getGender();
            CharSequence name = uMPlatformData.getName();
            if (gender == null) {
                try {
                    if (TextUtils.isEmpty(name)) {
                    }
                } catch (Throwable e) {
                    throw new C1853a("build body exception", e);
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("gender", gender == null ? "" : String.valueOf(gender.value));
            jSONObject2.put("name", name == null ? "" : String.valueOf(name));
            jSONObject.put(uMPlatformData.getMeida().toString(), jSONObject2);
        }
        return jSONObject.length() == 0 ? null : jSONObject.toString();
    }

    public static Map<String, String> m5946b(Context context) {
        CharSequence deviceId;
        CharSequence d;
        CharSequence string;
        Map<String, String> hashMap = new HashMap();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            C1854b.m5928e(C1823a.f3725e, "No IMEI.");
        }
        try {
            if (C1858f.m5943a(context, "android.permission.READ_PHONE_STATE")) {
                deviceId = telephonyManager.getDeviceId();
                d = C1858f.m5948d(context);
                string = Secure.getString(context.getContentResolver(), "android_id");
                if (!TextUtils.isEmpty(d)) {
                    hashMap.put(BaseStatisContent.MAC, (String) d);
                }
                if (!TextUtils.isEmpty(deviceId)) {
                    hashMap.put(BaseStatisContent.IMEI, (String) deviceId);
                }
                if (!TextUtils.isEmpty(string)) {
                    hashMap.put("android_id", (String) string);
                }
                return hashMap;
            }
        } catch (Exception e) {
            C1854b.m5929e(C1823a.f3725e, "No IMEI.", e);
        }
        deviceId = null;
        d = C1858f.m5948d(context);
        string = Secure.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(d)) {
            hashMap.put(BaseStatisContent.MAC, (String) d);
        }
        if (TextUtils.isEmpty(deviceId)) {
            hashMap.put(BaseStatisContent.IMEI, (String) deviceId);
        }
        if (TextUtils.isEmpty(string)) {
            hashMap.put("android_id", (String) string);
        }
        return hashMap;
    }

    private static Map<String, String> m5947c(Context context) {
        HashMap<String, String> hashMap = new HashMap<>();
        Map b = C1858f.m5946b(context);
        if (b == null || b.isEmpty()) {
            throw new C1853a("can`t get device id.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        Set<Entry<String,String>> entries = b.entrySet();
        for (Entry<String, String> entry : entries) {
            if (!TextUtils.isEmpty((CharSequence) entry.getValue())) {
                stringBuilder2.append((String) entry.getKey()).append(",");
                stringBuilder.append((String) entry.getValue()).append(",");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            hashMap.put("deviceid", stringBuilder.toString());
        }
        if (stringBuilder2.length() > 0) {
            stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            hashMap.put("idtype", stringBuilder2.toString());
        }
        return hashMap;
    }

    private static String m5948d(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (C1858f.m5943a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            C1854b.m5928e(C1823a.f3725e, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return "";
        } catch (Exception e) {
            C1854b.m5928e(C1823a.f3725e, "Could not get mac address." + e.toString());
        }
        return null;
    }
}
