package com.mcbox.pesdk.mcfloat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LauncherUtil {
    public static final String ITEMS_COLLECT = "items_collect";
    protected static Context mContext = null;

    public static Set<String> getEnabledScripts() {
        String string = getPrefs(1).getString("enabledScripts", "");
        return string.equals("") ? new HashSet() : new HashSet(Arrays.asList(string.split(";")));
    }

    public static Set<String> getItemsCollects(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(ITEMS_COLLECT, null);
        return (string == null || string.trim().length() != 0) ? new HashSet(Arrays.asList(string.split(";"))) : new HashSet();
    }

    public static SharedPreferences getPrefs(int i) {
        requireInit();
        switch (i) {
            case 0:
                return PreferenceManager.getDefaultSharedPreferences(mContext);
            case 1:
                return mContext.getSharedPreferences("mcpelauncherprefs", 0);
            case 2:
                return mContext.getSharedPreferences("safe_mode_counter", 0);
            default:
                return null;
        }
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static String joinString(String[] strArr, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            stringBuilder.append(strArr[i]);
            if (i < strArr.length - 1) {
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }

    protected static void requireInit() {
        if (mContext == null) {
            throw new RuntimeException("Tried to work with Utils class without context");
        }
    }

    public static void setItemsCollects(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(ITEMS_COLLECT, str).commit();
    }
}
