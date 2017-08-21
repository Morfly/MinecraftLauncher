package com.umeng;

import org.json.JSONObject;

public abstract class fw {
    protected static String f3781b = "POST";
    protected static String f3782c = "GET";
    protected String f3783d;
    public String d;

    public fw(String str) {
        this.f3783d = str;
    }

    public abstract JSONObject mo2909a();

    public void m5880a(String str) {
        this.f3783d = str;
    }

    public abstract String mo2910b();

    protected String m5882c() {
        return f3781b;
    }

    public String m5883d() {
        return this.f3783d;
    }
}
