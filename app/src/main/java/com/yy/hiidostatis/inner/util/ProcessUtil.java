package com.yy.hiidostatis.inner.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;

import com.yy.hiidostatis.inner.util.cipher.Coder;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.List;

public final class ProcessUtil {
    public static String getCurProcessName(Context context) {
        if (context == null) {
            return null;
        }
        int myPid = Process.myPid();
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String getFileNameBindProcess(Context context, String str) {
        try {
            if (!isMainProcess(context)) {
                str = str + "_" + Coder.encryptMD5(getCurProcessName(context)).hashCode();
            }
        } catch (Exception e) {
            C1923L.error(ProcessUtil.class, "fileName[%s] instead of it,exception on getFileNameBindProcess: %s ", str, e);
        }
        return str;
    }

    public static boolean isBackground(Context context) {
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(packageName)) {
                    return runningAppProcessInfo.importance == 400;
                }
            }
            return false;
        } catch (Exception e) {
            C1923L.error(Util.class, "isBackground exceptioon: %s", e);
            return false;
        }
    }

    public static boolean isMainProcess(Context context) {
        if (context == null) {
            return false;
        }
        return context.getApplicationInfo().processName.equals(getCurProcessName(context));
    }
}
