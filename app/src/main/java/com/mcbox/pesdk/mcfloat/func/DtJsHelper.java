package com.mcbox.pesdk.mcfloat.func;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DtJsHelper {
    private static final boolean DEBUG = false;
    public static final int JSFILEINDEX_FLYMODE = 5;
    public static final int JSFILEINDEX_GAMEMODE = 6;
    public static final int JSFILEINDEX_Invincible = 4;
    public static final int JSFILEINDEX_JIPAO = 0;
    public static final int JSFILEINDEX_MAX = 4;
    public static final int JSFILEINDEX_SHENGWUXIANXUE = 1;
    public static final int JSFILEINDEX_SIWANGBUDIAOXUE = 2;
    public static final int JSFILEINDEX_XIAODITU = 3;
    public static final String[] g_JSFileNameArray = new String[]{"100001", "100002", "100003", "100004"};
    public static String[] g_JSFileNameFullPathArray = new String[4];
    public static String[] g_JSFileNameNewNameArray = new String[4];
    public static int[] g_JSScriptLoadParamArray = new int[4];
    public static int[] g_JSScriptOptionAttributeArray = new int[]{3, 3, 3, 3};
    public static int[] g_JSScriptOptionParamArray = new int[4];
    private Context context;

    public static int GetJSCurOption(int i) {
        return (i >= 0 || i < 4) ? g_JSScriptOptionParamArray[i] : -1;
    }

    public static int GetJSFileAttributeByIndex(int i) {
        return i >= 4 ? -1 : g_JSScriptOptionAttributeArray[i];
    }

    public static int GetJSLoadParam(int i) {
        return i >= 4 ? 0 : g_JSScriptLoadParamArray[i];
    }

    public static String GetJSSrcFileNameByIndex(int i) {
        return i >= 4 ? null : g_JSFileNameArray[i];
    }

    public static boolean HLXRegisterLoadScript(int i) {
        if (GetJSLoadParam(i) != 0) {
            return false;
        }
        SetJSLoadParam(i, 1);
        return true;
    }

    public static void InitHLXLoadScript() {
        for (int i = 0; i < 4; i++) {
            SetJSLoadParam(i, 0);
        }
    }

    public static void InitJSFileFullPath() {
        if (3 == GetJSFileAttributeByIndex(0)) {
            g_JSFileNameNewNameArray[0] = g_JSFileNameArray[0];
        }
    }

    public static boolean SetJSCurOption(int i, int i2) {
        if (i < 0 && i >= 4) {
            return false;
        }
        g_JSScriptOptionParamArray[i] = i2;
        return true;
    }

    public static boolean SetJSLoadParam(int i, int i2) {
        if (i < 0 && i >= 4) {
            return false;
        }
        g_JSScriptLoadParamArray[i] = i2;
        return true;
    }

    public boolean CheckJsLoadOver() {
        for (int i = 0; i < 4; i++) {
            if (1 == GetJSLoadParam(i)) {
                return false;
            }
        }
        return true;
    }

    public String GetJSFileFullPath(int i) {
        String str = "data/" + GetJSSrcFileNameByIndex(i);
        String absolutePath = this.context.getFilesDir().getAbsolutePath();
        try {
            DtRandom.createRandomString(10);
            g_JSFileNameNewNameArray[i] = "10008";
            InputStream open = this.context.getResources().getAssets().open(str);
            str = absolutePath + "/" + "10008";
            DtFile.DeleteFolder(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            byte[] bArr = new byte[512];
            while (true) {
                int read = open.read(bArr);
                if (read <= 0) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    open.close();
                    return str;
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String GetJSFileFullPathByIndex(int i) {
        return i >= 4 ? null : g_JSFileNameFullPathArray[i];
    }

    public void initDTContext(Context context) {
        this.context = context;
    }
}
