package com.mcbox.pesdk.mcfloat.func;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class DtLocalStore {
    private static final String EMPTY_STRING = "";
    public static final String EquipmentBagName = "EquipmentBag";
    public static final String KEY_GUI_SCALE_VALUE = "gui_scale";
    public static final String MAP_SETTING_INVINCIABLE = "invinciable";
    public static final String MAP_SETTING_PLAYER_LOC1 = "player_loc1";
    public static final String MAP_SETTING_PLAYER_LOC2 = "player_loc2";
    public static final String MAP_SETTING_PLAYER_LOC3 = "player_loc3";
    public static final String NormalBagName = "InventoryBag";
    private static final String Separator = "#";
    private static final String SharedPreferencesFileName = "McFloat";
    private static Context context;

    public static boolean getKeyBoolVar(String str, String str2) {
        return (!hasInit() || str == null || str2 == null) ? false : context.getSharedPreferences(SharedPreferencesFileName, 0).getBoolean(str + Separator + str2, false);
    }

    public static int getKeyIntVar(String str) {
        return !hasInit() ? 0 : context.getSharedPreferences(SharedPreferencesFileName, 0).getInt(str, 0);
    }

    public static int getKeyIntVar(String str, String str2) {
        return (!hasInit() || str == null || str2 == null) ? 0 : context.getSharedPreferences(SharedPreferencesFileName, 0).getInt(str + Separator + str2, 0);
    }

    public static String getKeyVar(String str) {
        return !hasInit() ? null : context.getSharedPreferences(SharedPreferencesFileName, 0).getString(str, "");
    }

    public static String getKeyVar(String str, String str2) {
        return (!hasInit() || str == null || str2 == null) ? null : context.getSharedPreferences(SharedPreferencesFileName, 0).getString(str + Separator + str2, null);
    }

    private static boolean hasInit() {
        return context != null;
    }

    public static void init(Context context) {
        context = context;
    }

    public static void setKeyBoolVar(String str, String str2, boolean z) {
        if (hasInit() && str != null && str2 != null) {
            Editor edit = context.getSharedPreferences(SharedPreferencesFileName, 0).edit();
            edit.putBoolean(str + Separator + str2, z);
            edit.commit();
        }
    }

    public static void setKeyIntVar(String str, int i) {
        if (hasInit()) {
            Editor edit = context.getSharedPreferences(SharedPreferencesFileName, 0).edit();
            edit.putInt(str, i);
            edit.commit();
        }
    }

    public static void setKeyIntVar(String str, String str2, int i) {
        if (hasInit() && str != null && str2 != null) {
            Editor edit = context.getSharedPreferences(SharedPreferencesFileName, 0).edit();
            edit.putInt(str + Separator + str2, i);
            edit.commit();
        }
    }

    public static void setKeyVar(String str, String str2, String str3) {
        if (hasInit() && str != null && str2 != null) {
            Editor edit = context.getSharedPreferences(SharedPreferencesFileName, 0).edit();
            edit.putString(str + Separator + str2, str3);
            edit.commit();
        }
    }
}
