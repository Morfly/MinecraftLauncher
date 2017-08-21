package com.mcbox.pesdk.launcher;

import com.mcbox.pesdk.util.LauncherMcVersion;

public class LauncherVersion {
    public static final int MC_010 = 1;
    public static final int MC_011 = 2;
    public static final int MC_012 = 4;
    public static final int MC_013 = 8;
    public static final int MC_014 = 16;
    public static final int MC_015 = 32;
    private static final int MC_TOTAL_VERSIONS = 15;
    public static final int REGION_ABROAD = 2;
    public static final int REGION_DOMESTIC = 1;
    private int region = 1;
    private int version = 0;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LauncherVersion launcherVersion = (LauncherVersion) obj;
        return this.region != launcherVersion.region ? false : this.version == launcherVersion.version;
    }

    public String getPluginFileName() {
        if (isAbroad()) {
            if (isMc012()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_ABROAD;
            }
            if (isMc013()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_ABROAD_V4;
            }
            if (isMc014()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_ABROAD_V5;
            }
            if (isMc015()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_ABROAD_V6;
            }
        } else if (isMc010()) {
            return LauncherConstants.PLUGIN_FILE_LAUNCHER_V1;
        } else {
            if (isMc011()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_V2;
            }
            if (isMc012()) {
                return LauncherConstants.PLUGIN_FILE_LAUNCHER_V3;
            }
        }
        return null;
    }

    public boolean isAbroad() {
        return this.region == 2;
    }

    public boolean isMCNotFound() {
        return this.version == 0;
    }

    public boolean isMc010() {
        return (this.version ^ 1) == 0;
    }

    public boolean isMc011() {
        return (this.version ^ 2) == 0;
    }

    public boolean isMc012() {
        return (this.version ^ 4) == 0;
    }

    public boolean isMc013() {
        return (this.version ^ 8) == 0;
    }

    public boolean isMc014() {
        return (this.version ^ 16) == 0;
    }

    public boolean isMc015() {
        return (this.version ^ 32) == 0;
    }

    public void reset() {
        this.version = 0;
        this.region = 1;
    }

    public void setVersion(int i, LauncherMcVersion launcherMcVersion) {
        this.region = i;
        if (this.region != 2) {
            this.region = 1;
        }
        if (launcherMcVersion == null) {
            this.version = 0;
        } else if (launcherMcVersion.getMajor().intValue() == 0 && launcherMcVersion.getMinor().intValue() == 10) {
            this.version = 1;
        } else if (launcherMcVersion.getMajor().intValue() == 0 && launcherMcVersion.getMinor().intValue() == 11) {
            this.version = 2;
        } else if (launcherMcVersion.getMajor().intValue() == 0 && launcherMcVersion.getMinor().intValue() == 12) {
            this.version = 4;
        } else if (launcherMcVersion.getMajor().intValue() == 0 && launcherMcVersion.getMinor().intValue() == 13) {
            this.version = 8;
        } else if (launcherMcVersion.getMajor().intValue() == 0 && launcherMcVersion.getMinor().intValue() == 14 && launcherMcVersion.getPatch().intValue() < 99) {
            this.version = 16;
        } else if (launcherMcVersion.getMajor().intValue() != 0 || (launcherMcVersion.getMinor().intValue() != 15 && (launcherMcVersion.getMinor().intValue() != 14 || launcherMcVersion.getPatch().intValue() < 99))) {
            this.version = 0;
        } else {
            this.version = 32;
        }
    }

    public String toString() {
        return "LauncherVersion [version=" + this.version + ", region=" + this.region + "]";
    }
}
