package com.mcbox.pesdk.launcher;

import android.app.Activity;

import java.util.Set;

public interface LauncherFunc {
    void clearEnabledScripts();

    void enableSkin(boolean z);

    String getPlayerSkin();

    boolean isEnabledSkin();

    void saveEnabledScripts(Set<String> set);

    void startMcWithFloatWindow(Activity activity);
}
