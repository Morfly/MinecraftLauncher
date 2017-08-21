package com.mcbox.pesdk.launcher;

public class LauncherConfig {
    public boolean forceReloadPlugin = false;
    public int region = 1;

    public String toString() {
        return "LauncherConfig [region=" + this.region + ", forceReloadPlugin=" + this.forceReloadPlugin + "]";
    }
}
