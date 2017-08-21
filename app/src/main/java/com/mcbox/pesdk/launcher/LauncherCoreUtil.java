package com.mcbox.pesdk.launcher;

import android.content.Context;
import android.os.Build;
import android.preference.PreferenceManager;

public class LauncherCoreUtil {
    public static boolean getNoReMaptext(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("no_remap_text", true);
    }

    public static boolean isMeizuMobile() {
        String str = Build.MANUFACTURER;
        return str != null && str.equalsIgnoreCase("Meizu");
    }
}
