package com.mcbox.pesdk.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.mcbox.pesdk.archive.entity.Options;
import com.mcbox.pesdk.archive.util.OptionsUtil;

public class McInstallInfoUtil  {
    public static int MC_CURRENT_VERSION = 0;
    public static final int MC_VERSION_10 = 10;
    public static final int MC_VERSION_11 = 11;
    public static final int MC_VERSION_12 = 12;
    public static final int MC_VERSION_13 = 13;
    public static final int MC_VERSION_14 = 14;
    public static final int MC_VERSION_14_2 = 142;
    public static final int MC_VERSION_15 = 15;
    public static final int MC_VERSION_NOT_FOUNT = -1;
    public static final String mcPackageName = "com.mojang.minecraftpe";
    public static LauncherMcVersion mcv;
    public static Options options;

    public McInstallInfoUtil() throws Throwable {
        options = OptionsUtil.getInstance().getOptions();
    }

    public static void checkMcVersion() {
        if (mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() >= 15) {
            MC_CURRENT_VERSION = 15;
        } else if (mcv.getMajor().intValue() < 0 || mcv.getMinor().intValue() < 14) {
            if (mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() >= 13) {
                MC_CURRENT_VERSION = 13;
            } else if (mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() >= 12) {
                MC_CURRENT_VERSION = 12;
            } else if (mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() >= 11) {
                MC_CURRENT_VERSION = 11;
            } else if (mcv.getMajor().intValue() < 0 || mcv.getMinor().intValue() < 10) {
                MC_CURRENT_VERSION = -1;
            } else {
                MC_CURRENT_VERSION = 10;
            }
        } else if (mcv.getPatch().intValue() >= 99) {
            MC_CURRENT_VERSION = 15;
        } else if (mcv.getPatch().intValue() >= 2) {
            MC_CURRENT_VERSION = 142;
        } else {
            MC_CURRENT_VERSION = 14;
        }
    }

    public static String getMCVersion(Context context) {
        return getVersionName(context, mcPackageName);
    }

    public static int getMCVersionCode(Context context) {
        return getVersionCode(context, mcPackageName);
    }

    public static int getVersionCode(Context context, String str) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return i;
        }
    }

    public static String getVersionName(Context context, String str) {
        String str2 = null;
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return str2;
        }
    }

    public static void init(Context context) {
        mcv = LauncherMcVersion.fromVersionString(getMCVersion(context));
        checkMcVersion();
    }

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isInstallMc(Context context) {
        return isAppInstalled(context, mcPackageName);
    }

    public static boolean isUseLevelDB() {
        return (options == null || options.getOld_game_version_major() == null) ? false : options.getOld_game_version_major().intValue() >= 1 ? true : options.getOld_game_version_major().equals(Integer.valueOf(0)) && options.getOld_game_version_minor().intValue() >= 9;
    }

    public static boolean isV1() {
        return mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() <= 10;
    }

    public static boolean isV2() {
        return mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() == 11;
    }

    public static boolean isV3() {
        return mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() == 12;
    }

    public static boolean isV4() {
        return mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() == 13;
    }

    public static boolean isV5() {
        return mcv.getMajor().intValue() >= 0 && mcv.getMinor().intValue() == 14 && mcv.getPatch().intValue() < 99;
    }

    public static boolean isV6() {
        return mcv.getMajor().intValue() >= 0 && (mcv.getMinor().intValue() == 15 || (mcv.getMinor().intValue() == 14 && mcv.getPatch().intValue() >= 99));
    }

    public static void killMc(Context context) {
        try {
            ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).killBackgroundProcesses(mcPackageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer[] versionDetail() {
        return new Integer[]{options.getOld_game_version_major(), options.getOld_game_version_minor(), options.getOld_game_version_patch(), options.getOld_game_version_beta()};
    }

    public static String versionStr() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = (options.getOld_game_version_major() == null || options.getOld_game_version_major().equals("")) ? "" : options.getOld_game_version_major() + ".";
        stringBuffer.append(str);
        str = (options.getOld_game_version_minor() == null || options.getOld_game_version_minor().equals("")) ? "" : options.getOld_game_version_minor() + ".";
        stringBuffer.append(str);
        str = (options.getOld_game_version_patch() == null || options.getOld_game_version_patch().equals("")) ? "" : options.getOld_game_version_patch() + ".";
        stringBuffer.append(str);
        str = (options.getOld_game_version_beta() == null || options.getOld_game_version_beta().equals("")) ? "" : options.getOld_game_version_beta() + ".";
        stringBuffer.append(str);
        return stringBuffer.length() > 0 ? stringBuffer.substring(0, stringBuffer.length() - 1) : "";
    }
}
