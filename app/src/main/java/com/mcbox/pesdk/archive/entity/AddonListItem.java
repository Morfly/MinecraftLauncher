package com.mcbox.pesdk.archive.entity;

import android.content.pm.ApplicationInfo;

public class AddonListItem {
    private static String disabledString = " (disabled)";
    private static String enabledString = "";
    public final ApplicationInfo appInfo;
    public String displayName;
    public boolean enabled = true;

    public AddonListItem(ApplicationInfo applicationInfo, boolean z) {
        this.appInfo = applicationInfo;
        this.displayName = applicationInfo.packageName;
        this.enabled = z;
    }

    public String toString() {
        return this.displayName + (this.enabled ? enabledString : disabledString);
    }
}
