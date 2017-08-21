package com.yy.hiidostatis.inner;

import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class BaseStatisContent {
    public static final String ACT = "act";
    public static final String APPID = "app";
    public static final String APPKEY = "appkey";
    public static final String CHANNEL = "chn";
    protected final Comparator<String> COMPARATOR = new C19041();
    public static final String FROM = "from";
    public static final String GUID = "guid";
    public static final String IMEI = "imei";
    public static final String KEY = "key";
    public static final String MAC = "mac";
    public static final String MBL = "mbl";
    public static final String MBOS = "mbos";
    public static final String NET = "net";
    public static final String NTM = "ntm";
    public static final String SDKVER = "sdkver";
    public static final String SJM = "sjm";
    public static final String SJP = "sjp";
    public static final String SR = "sr";
    public static final String SYS = "sys";
    public static final String TIME = "time";
    public static final String VER = "ver";
    protected TreeMap<String, String> raw = new TreeMap(COMPARATOR);

    final class C19041 implements Comparator<String> {
        C19041() {
        }

        public int compare(String str, String str2) {
            boolean equals = BaseStatisContent.ACT.equals(str);
            boolean equals2 = BaseStatisContent.ACT.equals(str2);
            return (equals || equals2) ? (equals || !equals2) ? (!equals || equals2) ? (equals && equals2) ? 0 : 0 : -1 : 1 : str.compareTo(str2);
        }
    }

    public boolean containsKey(String str) {
        return this.raw.containsKey(str);
    }

    public BaseStatisContent copy() {
        BaseStatisContent baseStatisContent = new BaseStatisContent();
        baseStatisContent.raw = new TreeMap(COMPARATOR);
        baseStatisContent.raw.putAll(this.raw);
        return baseStatisContent;
    }

    public String get(String str) {
        return (String) this.raw.get(str);
    }

    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : this.raw.entrySet()) {
            String str = (String) entry.getValue();
            if (Util.empty(str)) {
                C1923L.debug(this, "No value for key %s", entry.getKey());
            } else {
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append("=");
                try {
                    stringBuilder.append(URLEncoder.encode(str, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    C1923L.error(this, "encoding fail for key %s", entry.getKey());
                }
                stringBuilder.append("&");
            }
        }
        if (stringBuilder.length() == 0) {
            C1923L.warn(this, "Warn : http content may be null?", new Object[0]);
            return null;
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("hd_p=E&");
        }
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return this.raw.isEmpty();
    }

    public String put(String str, double d) {
        return put(str, String.valueOf(d));
    }

    public String put(String str, int i) {
        return put(str, String.valueOf(i));
    }

    public String put(String str, long j) {
        return put(str, String.valueOf(j));
    }

    public String put(String str, String str2) {
        if (Util.empty(str)) {
            C1923L.error(BaseStatisContent.class, "key is invalid for value %s", str2);
            return null;
        }
        return (String) this.raw.put(str, Util.asEmptyOnNull(str2));
    }

    public String put(String str, String str2, boolean z) {
        if (Util.empty(str)) {
            C1923L.error(BaseStatisContent.class, "key is invalid for value %s", str2);
            return null;
        }
        String asEmptyOnNull = Util.asEmptyOnNull(str2);
        return z ? (String) this.raw.put(str, asEmptyOnNull) : this.raw.containsKey(str) ? (String) this.raw.get(str) : (String) this.raw.put(str, asEmptyOnNull);
    }

    public void putContent(BaseStatisContent baseStatisContent, boolean z) {
        if (baseStatisContent != null && !baseStatisContent.isEmpty()) {
            for (Entry entry : baseStatisContent.raw.entrySet()) {
                if (z) {
                    put((String) entry.getKey(), (String) entry.getValue());
                } else if (!containsKey((String) entry.getKey())) {
                    put((String) entry.getKey(), (String) entry.getValue());
                }
            }
        }
    }

    public String toString() {
        return getContent();
    }
}
