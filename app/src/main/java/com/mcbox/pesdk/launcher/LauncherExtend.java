package com.mcbox.pesdk.launcher;

import android.app.Activity;
import android.content.Context;

public interface LauncherExtend {
    void afterCreateActivity(Activity activity);

    void afterDestroyActivity(Activity activity);

    void beforeCreateActivity(Activity activity);

    void beforeFinishActivity(Activity activity);

    int getScreenShotWatermarkResId();

    void loadExternalModpkg();

    void loadExternalScripts();

    void onPause(Activity activity);

    void onResume(Activity activity);

    void reportCrash(String str, Context context);

    void reportCrash(String str, Exception exception, Context context);

    void reportEvent(Context context, String str, String str2);

    void reportEvent(Context context, String str, String str2, boolean z);

    void showMsgInGame(Activity activity, String str);
}
