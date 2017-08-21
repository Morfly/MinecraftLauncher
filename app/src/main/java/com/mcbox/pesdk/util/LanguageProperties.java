package com.mcbox.pesdk.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

public class LanguageProperties {
    private static final String defaultPath = (Environment.getExternalStorageDirectory() + "/mcpemaster/conf.properties");
    private static Properties language;
    private static Locale locale = Locale.US;

    public static String getLanguageSetting(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("setting_language", "");
        return "zh".equalsIgnoreCase(string) ? string + "_TW" : string;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static Properties getProperties(Context context) {
        if (language != null) {
            return language;
        }
        if (context == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            File file = new File(defaultPath);
            if (file.exists()) {
                properties.load(new FileInputStream(file));
                language = properties;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return language;
    }

    public static String getSystemLanguage(Context context) {
        if (context == null) {
            return "";
        }
        Configuration configuration = context.getResources().getConfiguration();
        String language = configuration.locale.getLanguage();
        return "zh".equalsIgnoreCase(language) ? ("CN".equalsIgnoreCase(configuration.locale.getCountry()) || "SG".equalsIgnoreCase(configuration.locale.getCountry())) ? language + "_CN" : language + "_TW" : language;
    }

    public static void resetLanguage(Context context) {
        String languageSetting = getLanguageSetting(context);
        String systemLanguage = getSystemLanguage(context);
        if (languageSetting.length() <= 0 || languageSetting.equalsIgnoreCase(systemLanguage)) {
            setLocale(systemLanguage);
            return;
        }
        setLanguage(context, languageSetting);
        setLocale(languageSetting);
    }

    private static void saveLanguageSetting(Context context, String str) {
        if (str != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("setting_language", str).commit();
        }
    }

    public static void setLanFromConf(Context context) {
        Properties properties = getProperties(context);
        if (properties != null && context != null) {
            setLanguage(context, (String) properties.get("lan"));
        }
    }

    public static void setLanguage(Context context, String str) {
        String trim = str.trim();
        if (!getSystemLanguage(context).equalsIgnoreCase(trim)) {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            String[] split = trim.split("_");
            if (split.length >= 2) {
                configuration.locale = new Locale(split[0], split[1]);
            } else {
                configuration.locale = new Locale(split[0]);
            }
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            saveLanguageSetting(context, trim);
        }
    }

    private static void setLocale(String str) {
        String[] split = str.split("_");
        if (split.length >= 2) {
            locale = new Locale(split[0], split[1]);
        } else {
            locale = new Locale(split[0]);
        }
    }
}
