package com.umeng.fb.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import fb.C2325b;
import fb.C2340p;

public class UserInfo {
    private static final String f3937e = UserInfo.class.getName();
    int f3938a;
    String f3939b;
    Map<String, String> f3940c;
    Map<String, String> f3941d;

    public UserInfo() {
        this.f3938a = -1;
        this.f3939b = "";
        this.f3940c = new HashMap();
        this.f3941d = new HashMap();
    }

    UserInfo(JSONObject jSONObject) throws JSONException {
        Iterator keys;
        this.f3938a = -1;
        this.f3939b = "";
        this.f3938a = jSONObject.optInt("age_group", -1);
        this.f3939b = jSONObject.optString("gender", "");
        this.f3940c = new HashMap();
        this.f3941d = new HashMap();
        JSONObject optJSONObject = jSONObject.optJSONObject("contact");
        if (optJSONObject != null) {
            keys = optJSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                this.f3940c.put(str, optJSONObject.getString(str));
            }
        }
        optJSONObject = jSONObject.optJSONObject("remark");
        C2325b.m10289c(f3937e, String.valueOf(optJSONObject));
        if (optJSONObject != null) {
            keys = optJSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                this.f3941d.put(str, optJSONObject.getString(str));
            }
        }
    }

    public int getAgeGroup() {
        return this.f3938a;
    }

    public Map<String, String> getContact() {
        return this.f3940c;
    }

    public String getGender() {
        return this.f3939b;
    }

    public Map<String, String> getRemark() {
        return this.f3941d;
    }

    public void setAgeGroup(int i) {
        this.f3938a = i;
    }

    public void setContact(Map<String, String> map) {
        this.f3940c = map;
    }

    public void setGender(String str) {
        this.f3939b = str;
    }

    public void setRemark(Map<String, String> map) {
        this.f3941d = map;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2;
            if (this.f3938a > -1) {
                jSONObject.put("age_group", this.f3938a);
            }
            if (!C2340p.m10364d(this.f3939b)) {
                jSONObject.put("gender", this.f3939b);
            }
            if (this.f3940c != null && this.f3940c.size() > 0) {
                jSONObject2 = new JSONObject();
                for (Entry entry : this.f3940c.entrySet()) {
                    jSONObject2.put((String) entry.getKey(), entry.getValue());
                }
                jSONObject.put("contact", jSONObject2);
            }
            if (this.f3941d != null && this.f3941d.size() > 0) {
                jSONObject2 = new JSONObject();
                for (Entry entry2 : this.f3941d.entrySet()) {
                    jSONObject2.put((String) entry2.getKey(), entry2.getValue());
                }
                jSONObject.put("remark", jSONObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
