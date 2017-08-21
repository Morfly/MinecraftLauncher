package com.umeng.fb.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DevReply extends Reply {
    protected String f3924a;

    private DevReply(String str, String str2, String str3, String str4, String str5) {
        super(str, str2, str3, str4, TYPE.DEV_REPLY);
        this.f3924a = str5;
    }

    public DevReply(JSONObject jSONObject) throws JSONException {
        super(jSONObject);
        if (this.g != TYPE.DEV_REPLY) {
            throw new JSONException(new StringBuilder(String.valueOf(DevReply.class.getName())).append(".type must be ").append(TYPE.DEV_REPLY).toString());
        }
        this.f3924a = jSONObject.optString("user_name", "");
    }

    public JSONObject toJson() {
        JSONObject toJson = super.toJson();
        if (toJson != null) {
            try {
                toJson.put("user_name", this.f3924a);
                return toJson;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
