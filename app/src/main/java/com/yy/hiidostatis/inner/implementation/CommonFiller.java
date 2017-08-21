package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.UuidManager;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

public class CommonFiller {
    private static final int ANDROID_OS = 2;
    private static final String KEY_MAGIC = "HiidoYYSystem";
    private static String mImei = null;
    private static final String mImeiKey = "PREF_IMEI";
    private static final Object mImeiSyncKey = new Object();
    private static String mLang = null;
    private static String mMacAddress = null;
    private static final String mMacAddressKey = "PREF_MAC_ADDRESS";
    private static final Object mMacAddressSyncKey = new Object();
    private static String mOS = null;
    private static String mScreenResolution = null;

    private static String bytesToHexString(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bArr != null) {
            for (byte b : bArr) {
                stringBuilder.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
        }
        return stringBuilder.toString();
    }

    public static String calKey(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(str2);
        stringBuilder.append(KEY_MAGIC);
        return strMd5(stringBuilder.toString()).toLowerCase(Locale.getDefault());
    }

    public static BaseStatisContent fillCommonAll(Context context, BaseStatisContent baseStatisContent, String str, String str2) throws Throwable {
        fillCommonNew(context, baseStatisContent, str, str2);
        fillConcreteInfoNew(context, baseStatisContent);
        return baseStatisContent;
    }

    public static BaseStatisContent fillCommonNew(Context context, BaseStatisContent baseStatisContent, String str, String str2) throws Throwable {
        fillKey(baseStatisContent, str);
        baseStatisContent.put(BaseStatisContent.IMEI, getIMEI(context));
        baseStatisContent.put(BaseStatisContent.MAC, getMacAddr(context));
        baseStatisContent.put(BaseStatisContent.NET, getNetworkType(context));
        baseStatisContent.put(BaseStatisContent.ACT, str);
        baseStatisContent.put(BaseStatisContent.SDKVER, str2);
        baseStatisContent.put("sys", 2);
        baseStatisContent.put("opid", UuidManager.fetchUUid(context));
        return baseStatisContent;
    }

    public static void fillConcreteInfoNew(Context context, BaseStatisContent baseStatisContent) {
        baseStatisContent.put(BaseStatisContent.SJP, getSjp(context));
        baseStatisContent.put(BaseStatisContent.SJM, getSjm(context));
        baseStatisContent.put(BaseStatisContent.MBOS, getOS());
        baseStatisContent.put(BaseStatisContent.MBL, getLang());
        baseStatisContent.put(BaseStatisContent.SR, getScreenResolution(context));
        baseStatisContent.put(BaseStatisContent.NTM, getNtm(context));
    }

    public static BaseStatisContent fillKey(BaseStatisContent baseStatisContent, String str) {
        String valueOf = String.valueOf(Util.wallTimeSec());
        baseStatisContent.put(BaseStatisContent.ACT, str);
        baseStatisContent.put(BaseStatisContent.TIME, valueOf);
        baseStatisContent.put(BaseStatisContent.KEY, calKey(str, valueOf));
        valueOf = UUID.randomUUID().toString();
        try {
            valueOf = valueOf.substring(0, 20);
        } catch (Exception e) {
        }
        baseStatisContent.put(BaseStatisContent.GUID, valueOf);
        return baseStatisContent;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getIMEI(android.content.Context r4) {
        /*
        r0 = mImei;
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);
        if (r0 != 0) goto L_0x000b;
    L_0x0008:
        r0 = mImei;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = com.yy.hiidostatis.inner.util.DefaultPreference.getPreference();
        r1 = "PREF_IMEI";
        r2 = 0;
        r0 = r0.getPrefString(r4, r1, r2);
        mImei = r0;
        r0 = mImei;
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);
        if (r0 != 0) goto L_0x0023;
    L_0x0020:
        r0 = mImei;
        goto L_0x000a;
    L_0x0023:
        r1 = mImeiSyncKey;
        monitor-enter(r1);
        r0 = mImei;	 Catch:{ all -> 0x0032 }
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x0035;
    L_0x002e:
        r0 = mImei;	 Catch:{ all -> 0x0032 }
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x000a;
    L_0x0032:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        throw r0;
    L_0x0035:
        r0 = com.yy.hiidostatis.inner.util.Util.getIMEI(r4);	 Catch:{ all -> 0x0032 }
        mImei = r0;	 Catch:{ all -> 0x0032 }
        r0 = mImei;	 Catch:{ all -> 0x0032 }
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x004e;
    L_0x0043:
        r0 = com.yy.hiidostatis.inner.util.DefaultPreference.getPreference();	 Catch:{ all -> 0x0032 }
        r2 = "PREF_IMEI";
        r3 = mImei;	 Catch:{ all -> 0x0032 }
        r0.setPrefString(r4, r2, r3);	 Catch:{ all -> 0x0032 }
    L_0x004e:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        r0 = mImei;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.inner.implementation.CommonFiller.getIMEI(android.content.Context):java.lang.String");
    }

    public static String getLang() {
        if (mLang != null) {
            return mLang;
        }
        String lang = Util.getLang();
        mLang = lang;
        return lang;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMacAddr(android.content.Context r4) {
        /*
        r0 = mMacAddress;
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);
        if (r0 != 0) goto L_0x000b;
    L_0x0008:
        r0 = mMacAddress;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = com.yy.hiidostatis.inner.util.DefaultPreference.getPreference();
        r1 = "PREF_MAC_ADDRESS";
        r2 = 0;
        r0 = r0.getPrefString(r4, r1, r2);
        mMacAddress = r0;
        r0 = mMacAddress;
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);
        if (r0 != 0) goto L_0x0023;
    L_0x0020:
        r0 = mMacAddress;
        goto L_0x000a;
    L_0x0023:
        r1 = mMacAddressSyncKey;
        monitor-enter(r1);
        r0 = mMacAddress;	 Catch:{ all -> 0x0032 }
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x0035;
    L_0x002e:
        r0 = mMacAddress;	 Catch:{ all -> 0x0032 }
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x000a;
    L_0x0032:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        throw r0;
    L_0x0035:
        r0 = com.yy.hiidostatis.inner.util.Util.getMacAddr(r4);	 Catch:{ all -> 0x0032 }
        mMacAddress = r0;	 Catch:{ all -> 0x0032 }
        r0 = mMacAddress;	 Catch:{ all -> 0x0032 }
        r0 = com.yy.hiidostatis.inner.util.Util.empty(r0);	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x004e;
    L_0x0043:
        r0 = com.yy.hiidostatis.inner.util.DefaultPreference.getPreference();	 Catch:{ all -> 0x0032 }
        r2 = "PREF_MAC_ADDRESS";
        r3 = mMacAddress;	 Catch:{ all -> 0x0032 }
        r0.setPrefString(r4, r2, r3);	 Catch:{ all -> 0x0032 }
    L_0x004e:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        r0 = mMacAddress;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.inner.implementation.CommonFiller.getMacAddr(android.content.Context):java.lang.String");
    }

    public static int getNetworkType(Context context) {
        return Util.getNetworkTypeNew(context);
    }

    public static String getNtm(Context context) {
        return Util.getNtm(context);
    }

    public static String getOS() {
        if (mOS != null) {
            return mOS;
        }
        mOS = Util.getOS();
        return mOS;
    }

    public static String getScreenResolution(Context context) {
        if (mScreenResolution != null) {
            return mScreenResolution;
        }
        String screenResolution = Util.getScreenResolution(context);
        mScreenResolution = screenResolution;
        return screenResolution;
    }

    public static String getSjm(Context context) {
        return Util.getSjm(context);
    }

    public static String getSjp(Context context) {
        return Util.getSjp(context);
    }

    private static String strMd5(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(bytesToHexString(MessageDigest.getInstance("MD5").digest(str.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            C1923L.error(CommonFiller.class, "Exception when MD5 %s", e);
        }
        return stringBuffer.toString();
    }
}
