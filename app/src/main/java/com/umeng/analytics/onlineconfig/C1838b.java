package com.umeng.analytics.onlineconfig;

import com.umeng.fx;

import org.json.JSONObject;

import java.util.Locale;

public class C1838b extends fx {
    public JSONObject f3803a = null;
    boolean f3804b = false;
    int f3805c = -1;
    int f3806d = -1;
    String f3807e;
    private final String f3808f = "config_update";
    private final String f3809g = "report_policy";
    private final String f3810h = "online_params";
    private final String f3811i = "last_config_time";
    private final String f3812j = "report_interval";

    public C1838b(JSONObject jSONObject) {
        super(jSONObject);
        if (jSONObject != null) {
            m5912a(jSONObject);
            m5911a();
        }
    }

    private void m5911a() {
        if (this.f3805c < 0 || this.f3805c > 6) {
            this.f3805c = 1;
        }
    }

    private void m5912a(JSONObject jSONObject) {
        try {
            if (jSONObject.has("config_update") && !jSONObject.getString("config_update").toLowerCase(Locale.US).equals("no")) {
                if (jSONObject.has("report_policy")) {
                    this.f3805c = jSONObject.getInt("report_policy");
                    this.f3806d = jSONObject.optInt("report_interval") * 1000;
                    this.f3807e = jSONObject.optString("last_config_time");
                }
                this.f3803a = jSONObject.optJSONObject("online_params");
                this.f3804b = true;
            }
        } catch (Exception e) {
        }
    }
}
