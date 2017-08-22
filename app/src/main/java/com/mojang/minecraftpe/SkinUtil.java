package com.mojang.minecraftpe;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.mcbox.pesdk.archive.entity.Options;
import com.mcbox.pesdk.archive.util.OptionsUtil;
import com.mcbox.pesdk.launcher.LauncherConstants;
import com.mcbox.pesdk.mcfloat.util.LauncherUtil;
import com.mcbox.pesdk.util.McInstallInfoUtil;
import com.umeng.analytics.MobclickAgent;
import com.yy.hiidostatis.api.HiidoSDK;
import com.yy.hiidostatis.defs.obj.Property;
import com.yy.hiidostatis.inner.BaseStatisContent;

import org.tlauncher.tlauncherpe.mc.McVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mcbox.pesdk.mcfloat.util.LauncherUtil.getPrefs;

/**
 * Created by Serhii on 22.08.2017.
 */

public class SkinUtil {

    public static void setSkinAndStartApp(Activity activity){
        LauncherUtil.init(activity);
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, true).apply();
        importInGame(Environment.getExternalStorageDirectory().toString()+ "/games/com.mojang/skin_packs/custom.png", activity);
        startMC(activity, true, false);
    }

    public static void importInGame(String str, Activity activity){
        String st = str;
        if (st == null) {
            try {
                st = "";
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        getPrefs(1).edit().putString(LauncherConstants.PREF_KEY_SKIN_PLAYER, st).apply();
        McVersion fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity));
        if (fromVersionString.getMajor().intValue() >= 0 && fromVersionString.getMinor().intValue() >= 0) {
            killMCProgress(activity);
            File c;
            Options options;
            if (st == null || st.trim().length() <= 0) {
                c = m4930c();
                if (c.exists()) {
                    c.delete();
                }
                try {
                    options = OptionsUtil.getInstance().getOptions();
                    options.setGame_skintype(0);
                    options.setGame_lastcustomskin(0);
                    options.setGame_skintypefull(Options.SKIN_TYPE_Steve);
                    options.setGame_lastcustomskinnew(Options.SKIN_TYPE_Steve);
                    OptionsUtil.getInstance().writeOptions(options);
                    return;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            c = new File(st);
            if (c.exists()) {
                try {
                    copyFile(c, m4930c());
                    options = OptionsUtil.getInstance().getOptions();
                    options.setGame_skintype(0);
                    options.setGame_lastcustomskin(0);
                    options.setGame_skintypefull(Options.SKIN_TYPE_Custom);
                    options.setGame_lastcustomskinnew(Options.SKIN_TYPE_Custom);
                    OptionsUtil.getInstance().writeOptions(options);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    public static void copyFile(File file, File file2) {
        int i = 0;
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1444];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        i += read;
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            //println("Coping file error!");
            e.printStackTrace();
        }

    }

    public static void killMCProgress(Activity activity) {
        if (isMcRunning(activity)) {
            McInstallInfoUtil.killMc(activity);
        }
    }

    public static boolean isMcRunning(Activity activity){
        try {
            ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
                    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                        if (McInstallInfoUtil.mcPackageName.equals(runningAppProcessInfo.processName)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static File m4930c() {
        return new File(/*"file://"+*/Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/custom.png");
    }

    public static void startMcWithCleanMem(Activity activity, int num) {
        /*if (if (2 === num) cleanTextureAndPlugin(activity) else true) {
            PrefUtil.setWorldLocation(activity, "")
            if (LauncherUtil.getPrefs(0)!!.getBoolean("isEnableMemClean", true)) {
                val intent = Intent(activity, MemoryCleanActivity::class.java)
                intent.putExtra("startType", num)
                activity.startActivity(intent)
            } else if (num == 1) {*/
        //startPlug(activity)
        //activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(McInstallInfoUtil.mcPackageName));
            /*} else {
                startPlug(activity)
            }
        }*/
    }

    public static void m4876a(Context context, String str, String str2, boolean z) {
        //if (!f2943b.booleanValue()) {
        String str3 = "";
        str3 = "";
        if (str.contains("/")) {
            String substring = str.substring(0, str.indexOf("/"));
            str3 = str.substring(str.indexOf("/") + 1);
            HashMap hashMap = new HashMap();
            hashMap.put("parm1", str3);
            hashMap.put("parm2", str2);
            MobclickAgent.onEvent(context, substring, hashMap);
            Property property = new Property();
            property.putString("parm1", str3);
            property.putString("parm2", str2);
            HiidoSDK.instance().reportTimesEvent(0, substring, null, property);
                /*if (z && f2942a != null) {
                    f2942a.m1902a(C0575m(str, str).m1843c(str2).mo1835a())
                    return
                }*/
            return;
        }
        //C0894a.m4881b(context, str)
        MobclickAgent.onEvent(context, str);
        HiidoSDK.instance().reportTimesEvent(0, str, null);
        //}
    }

    public static Set<String> getEnabledScripts(){
        String string = getPrefs(1).getString("enabledScripts", "");
        return string.equals("") ? new HashSet() : new HashSet(Arrays.asList(string.split(";")));
    }

    public static void startMC(Activity activity, Boolean z, Boolean z2) {
        try {
            //if (checkMcpeInstalled(activity)) {
            m4876a(activity, "start_mc_btn", "",false);
            McVersion fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity));
            if (fromVersionString.getMajor().intValue() > 0 || fromVersionString.getMinor().intValue() >= 12) {
                int size = getEnabledScripts().size();
                boolean z3 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, false);
                boolean z4 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_TEXTURE_ENABLE, false);
                boolean floatingWindowStatue = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("setFloatingWindowStatue", true);
                boolean pluginEnable = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("pluginEnable", false);
                if (fromVersionString.getMajor().intValue() <= 0 && fromVersionString.getMinor().intValue() <= 9) {
                    startMcWithCleanMem(activity, 1);
                    return;
                } else if (fromVersionString.getMajor().intValue() <= 0 && fromVersionString.getMinor().intValue() == 12 && fromVersionString.getBeta().intValue() > 0 && fromVersionString.getBeta().intValue() < 6) {
                    startMcWithCleanMem(activity, 1);
                    return;
                } else if (floatingWindowStatue) {
                    startMcWithCleanMem(activity, 2);
                    return;
                } else if (z3 || z4 || pluginEnable || size != 0) {
                    startMcWithCleanMem(activity, 2);
                    if (z3 && size > 0) {
                        MobclickAgent.onEvent(activity, "start_mc_skin_js");
                        HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin_js", null);
                        return;
                    } else if (z3) {
                        MobclickAgent.onEvent(activity, "start_mc_skin");
                        HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin", null);
                        return;
                    } else if (size > 0) {
                        MobclickAgent.onEvent(activity, "start_mc_js");
                        HiidoSDK.instance().reportTimesEvent(0, "start_mc_js", null);
                        return;
                    } else {
                        return;
                    }
                } else {
                    startMcWithCleanMem(activity, 1);
                    return;
                }
            }
            startMcWithCleanMem(activity, 1);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
