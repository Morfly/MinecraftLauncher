package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;

import java.io.Serializable;

public class PropertyPair implements Serializable {
    private static final String DIVIDE_FIELD = "=";
    private static final int TYPE_COUNT = 0;
    private static final int TYPE_TIMES = 1;
    private static final long serialVersionUID = 807395322993363767L;
    private String key;
    private int type = 0;
    private String value;

    public PropertyPair(String str, double d) {
        this.key = str;
        this.value = String.valueOf(d);
    }

    public PropertyPair(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String getConnectedPair() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Util.replaceEncode(this.key, "="));
        stringBuilder.append("=");
        stringBuilder.append(Util.replaceEncode(this.value, "="));
        stringBuilder.append("=");
        stringBuilder.append(this.type);
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.key);
        stringBuilder.append("=");
        stringBuilder.append(this.value);
        stringBuilder.append("=");
        stringBuilder.append(this.type);
        return stringBuilder.toString();
    }
}
