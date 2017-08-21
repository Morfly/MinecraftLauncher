package com.mcbox.pesdk.mcfloat.func;

import android.content.Context;

public class DtContextHelper {
    private static int DT_versionCode = 0;
    public static DtLevelContext levelContext = new DtLevelContext();

    public static int getEnterMapFlag() {
        return McFloatSettings.nOptionEnterMapFlag;
    }

    public static int getFirstSyncGameParam() {
        return McFloatSettings.nOptionFirstSyncFlag;
    }

    public static String getGameWorldDir() {
        return McFloatSettings.worldDir;
    }

    public static String getGameWorldName() {
        return McFloatSettings.worldName;
    }

    public static int getNetWorldMode() {
        return McFloatSettings.nOptionNetWorldMode;
    }

    public static void initMcFloat(Context context) {
        setFirstSyncGameParam(0);
        setEnterMapFlag(0);
        setNetWorldMode(0);
        DtItemInventory.init();
        DtJsHelper.InitJSFileFullPath();
        DtJsHelper.InitHLXLoadScript();
        DtFile.SetGamePackSupportPath(DtFile.gamePackSupportPath);
        DtLocalStore.init(context);
    }

    public static void setEnterMapFlag(int i) {
        McFloatSettings.nOptionEnterMapFlag = i;
    }

    public static void setFirstSyncGameParam(int i) {
        McFloatSettings.nOptionFirstSyncFlag = i;
    }

    public static void setGameWorldDir(String str) {
        McFloatSettings.worldDir = str;
    }

    public static void setGameWorldName(String str) {
        McFloatSettings.worldName = str;
    }

    public static void setNetWorldMode(int i) {
        McFloatSettings.nOptionNetWorldMode = i;
    }
}
