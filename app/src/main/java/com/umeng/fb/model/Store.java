package com.umeng.fb.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Store {
    private static final String f3929a = Store.class.getName();
    private static Store f3930b = null;
    private static final String f3931d = "umeng_feedback_conversations";
    private static final String f3932e = "umeng_feedback_user_info";
    private static final String f3933f = "user";
    private static final String f3934g = "last_update_at";
    private static final String f3935h = "last_sync_at";
    private Context f3936c;

    private Store(Context context) {
        this.f3936c = context.getApplicationContext();
    }

    public static Store getInstance(Context context) {
        if (f3930b == null) {
            f3930b = new Store(context);
        }
        return f3930b;
    }

    void m5979a() {
        this.f3936c.getSharedPreferences(f3931d, 0).edit().clear().commit();
        this.f3936c.getSharedPreferences(f3932e, 0).edit().clear().commit();
    }

    public List<String> getAllConversationIds() {
        Map all = this.f3936c.getSharedPreferences(f3931d, 0).getAll();
        List<String> arrayList = new ArrayList();
        Set<String> set = all.keySet();
        for (String add : set) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public Conversation getConversationById(String str) {
        try {
            return new Conversation(str, new JSONArray(this.f3936c.getSharedPreferences(f3931d, 0).getString(str, "")), this.f3936c);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserInfo getUserInfo() {
        String string = this.f3936c.getSharedPreferences(f3932e, 0).getString(f3933f, "");
        if ("".equals(string)) {
            return null;
        }
        try {
            return new UserInfo(new JSONObject(string));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getUserInfoLastSyncAt() {
        return this.f3936c.getSharedPreferences(f3932e, 0).getLong(f3935h, 0);
    }

    public long getUserInfoLastUpdateAt() {
        return this.f3936c.getSharedPreferences(f3932e, 0).getLong(f3934g, 0);
    }

    public void saveCoversation(Conversation conversation) {
        this.f3936c.getSharedPreferences(f3931d, 0).edit().putString(conversation.getId(), conversation.m5978a().toString()).commit();
    }

    public void saveUserInfo(UserInfo userInfo) {
        this.f3936c.getSharedPreferences(f3932e, 0).edit().putString(f3933f, userInfo.toJson().toString()).putLong(f3934g, System.currentTimeMillis()).commit();
    }

    public void setUserInfoLastSyncAt(long j) {
        this.f3936c.getSharedPreferences(f3932e, 0).edit().putLong(f3935h, j).commit();
    }
}
