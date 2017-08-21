package com.yy.hiidostatis.inner.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preference {
    private boolean isBindProcess = false;
    private String name;
    private String newFileName = null;

    public Preference(String str) {
        this.name = str;
    }

    public Preference(String str, boolean z) {
        this.name = str;
        this.isBindProcess = z;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        if (this.newFileName == null) {
            this.newFileName = this.isBindProcess ? ProcessUtil.getFileNameBindProcess(context, this.name) : this.name;
        }
        return context.getSharedPreferences(this.newFileName, 0);
    }

    public void clearKey(Context context, String str) {
        Editor edit = getSharedPreferences(context).edit();
        edit.remove(str);
        edit.commit();
    }

    public void clearPreference(Context context) {
        Editor edit = getSharedPreferences(context).edit();
        edit.clear();
        edit.commit();
    }

    public boolean getPrefBoolean(Context context, String str, boolean z) {
        return getSharedPreferences(context).getBoolean(str, z);
    }

    public float getPrefFloat(Context context, String str, float f) {
        return getSharedPreferences(context).getFloat(str, f);
    }

    public int getPrefInt(Context context, String str, int i) {
        return getSharedPreferences(context).getInt(str, i);
    }

    public long getPrefLong(Context context, String str, long j) {
        return getSharedPreferences(context).getLong(str, j);
    }

    public String getPrefString(Context context, String str, String str2) {
        return getSharedPreferences(context).getString(str, str2);
    }

    public boolean hasKey(Context context, String str) {
        return getSharedPreferences(context).contains(str);
    }

    public void setPrefBoolean(Context context, String str, boolean z) {
        getSharedPreferences(context).edit().putBoolean(str, z).commit();
    }

    public void setPrefFloat(Context context, String str, float f) {
        getSharedPreferences(context).edit().putFloat(str, f).commit();
    }

    public void setPrefInt(Context context, String str, int i) {
        getSharedPreferences(context).edit().putInt(str, i).commit();
    }

    public void setPrefLong(Context context, String str, long j) {
        getSharedPreferences(context).edit().putLong(str, j).commit();
    }

    public void setPrefString(Context context, String str, String str2) {
        getSharedPreferences(context).edit().putString(str, str2).commit();
    }
}
