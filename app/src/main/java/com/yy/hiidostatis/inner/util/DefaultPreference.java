package com.yy.hiidostatis.inner.util;

public class DefaultPreference {
    private static final Object KEY = new Object();
    private static DefaultPreference instance;
    private static String mPrefName = "hdcommon_default_pref";
    private Preference preference = new Preference(mPrefName, true);

    private DefaultPreference() {
    }

    private static DefaultPreference getInstance() {
        if (instance == null) {
            synchronized (KEY) {
                if (instance == null) {
                    instance = new DefaultPreference();
                }
            }
        }
        return instance;
    }

    public static Preference getPreference() {
        return getInstance().preference;
    }

    public static void setPrefName(String str) {
        mPrefName = str;
    }
}
