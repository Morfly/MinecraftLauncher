package com.mcbox.pesdk.mcfloat.util;

import com.mcbox.pesdk.launcher.LauncherManager;
import com.mcbox.pesdk.launcher.LauncherRuntime;
import com.mcbox.pesdk.mcfloat.func.DtContextHelper;
import com.mcbox.pesdk.mcfloat.func.DtLocalStore;
import com.mcbox.pesdk.util.McInstallInfoUtil;

import java.math.BigDecimal;

public class McFloatUtil {
    public static String LOC_SEPARATOR = ",";
    static LauncherRuntime launcherRuntime;

    public static String getLocationDesc(float f, float f2, float f3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("X:" + f).append(LOC_SEPARATOR + " Y:" + f2).append(LOC_SEPARATOR + " Z:" + f3);
        return stringBuilder.toString();
    }

    public static String getLocationDesc(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String[] split = str.split(LOC_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("X:" + split[0]).append(LOC_SEPARATOR + " Y:" + split[1]).append(LOC_SEPARATOR + " Z:" + split[2]);
        return stringBuilder.toString();
    }

    public static String getLocationString(float f, float f2, float f3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f).append(LOC_SEPARATOR + f2).append(LOC_SEPARATOR + f3);
        return stringBuilder.toString();
    }

    public static void markDiePosition() {
        if (McInstallInfoUtil.isV3() || McInstallInfoUtil.isV4() || McInstallInfoUtil.isV5()) {
            if (launcherRuntime == null) {
                launcherRuntime = LauncherManager.getInstance().getLauncherRuntime();
            }
            DtLocalStore.setKeyVar(DtContextHelper.getGameWorldDir(), "DIE_POSITION", getLocationString(new BigDecimal((double) launcherRuntime.getPlayerLoc(0)).setScale(2, 4).floatValue(), new BigDecimal((double) launcherRuntime.getPlayerLoc(1)).setScale(2, 4).floatValue(), new BigDecimal((double) launcherRuntime.getPlayerLoc(2)).setScale(2, 4).floatValue()));
        }
    }

    public static float parseFloat(String str) {
        try {
            return Float.valueOf(str).floatValue();
        } catch (Exception e) {
            return 0.0f;
        }
    }

    public static Integer parseInt(String str) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }
}
