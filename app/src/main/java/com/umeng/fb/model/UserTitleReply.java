package com.umeng.fb.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTitleReply extends Reply {
    private static final String f3942j = "thread";
    protected String f3943a;

    public UserTitleReply(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4, TYPE.NEW_FEEDBACK);
        this.f3943a = str;
    }

    UserTitleReply(JSONObject jSONObject) throws JSONException {
        super(jSONObject);
        if (this.g != TYPE.NEW_FEEDBACK) {
            throw new JSONException(new StringBuilder(String.valueOf(UserTitleReply.class.getName())).append(".type must be ").append(TYPE.NEW_FEEDBACK).toString());
        }
        this.f3943a = jSONObject.optString(f3942j);
    }

    public JSONObject toJson() {
        JSONObject toJson = super.toJson();
        try {
            toJson.put(f3942j, this.f3943a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toJson;
    }
}
