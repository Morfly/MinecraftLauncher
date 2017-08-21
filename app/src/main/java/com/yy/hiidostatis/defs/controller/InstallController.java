package com.yy.hiidostatis.defs.controller;

import android.content.Context;

import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

public class InstallController {
    private static final String INVALID_VERSIONNAME = "";
    private static final int INVALID_VERSIONNO = -1;
    private static final String PREF_KEY_VERSION_NAME = "PREF_KEY_VERSION_NAME";
    private static final String PREF_KEY_VERSION_NO = "PREF_KEY_VERSION_NO";
    private static final int TYPE_INSTALL = 1;
    private static final int TYPE_UPDATE = 0;
    private IStatisAPI statisAPI;

    public InstallController(IStatisAPI iStatisAPI) {
        this.statisAPI = iStatisAPI;
    }

    public void sendInstallationReportIfNotYet(Context context) {
        int prefInt;
        String prefString;
        int versionNo;
        Exception e;
        int i;
        String str = "";
        String str2 = "";
        try {
            prefInt = DefaultPreference.getPreference().getPrefInt(context, PREF_KEY_VERSION_NO, -1);
            try {
                prefString = DefaultPreference.getPreference().getPrefString(context, PREF_KEY_VERSION_NAME, "");
                try {
                    versionNo = Util.getVersionNo(context);
                } catch (Exception e2) {
                    e = e2;
                    versionNo = -1;
                    C1923L.error(InstallController.class, "sendInstallationReportIfNotYet exception = %s", e);
                    i = 0;
                    if (i == 0) {
                        if (prefInt == -1) {
                        }
                        C1923L.info(InstallController.class, "report Install %b", Boolean.valueOf(this.statisAPI.reportInstall(i)));
                        if (this.statisAPI.reportInstall(i)) {
                            DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_VERSION_NO, versionNo);
                            DefaultPreference.getPreference().setPrefString(context, PREF_KEY_VERSION_NAME, str2);
                        }
                    }
                }
            } catch (Exception e3) {
                e = e3;
                prefString = str;
                versionNo = -1;
                C1923L.error(InstallController.class, "sendInstallationReportIfNotYet exception = %s", e);
                i = 0;
                if (i == 0) {
                    if (prefInt == -1) {
                    }
                    C1923L.info(InstallController.class, "report Install %b", Boolean.valueOf(this.statisAPI.reportInstall(i)));
                    if (this.statisAPI.reportInstall(i)) {
                        DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_VERSION_NO, versionNo);
                        DefaultPreference.getPreference().setPrefString(context, PREF_KEY_VERSION_NAME, str2);
                    }
                }
            }
            try {
                str2 = Util.getVersionName(context);
                i = (prefInt == -1 || prefString.equals("") || prefInt != versionNo || !prefString.equals(str2)) ? 0 : 1;
            } catch (Exception e4) {
                e = e4;
                C1923L.error(InstallController.class, "sendInstallationReportIfNotYet exception = %s", e);
                i = 0;
                if (i == 0) {
                    if (prefInt == -1) {
                    }
                    C1923L.info(InstallController.class, "report Install %b", Boolean.valueOf(this.statisAPI.reportInstall(i)));
                    if (this.statisAPI.reportInstall(i)) {
                        DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_VERSION_NO, versionNo);
                        DefaultPreference.getPreference().setPrefString(context, PREF_KEY_VERSION_NAME, str2);
                    }
                }
            }
        } catch (Exception e5) {
            e = e5;
            prefString = str;
            prefInt = -1;
            versionNo = -1;
            C1923L.error(InstallController.class, "sendInstallationReportIfNotYet exception = %s", e);
            i = 0;
            if (i == 0) {
                if (prefInt == -1) {
                }
                C1923L.info(InstallController.class, "report Install %b", Boolean.valueOf(this.statisAPI.reportInstall(i)));
                if (this.statisAPI.reportInstall(i)) {
                    DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_VERSION_NO, versionNo);
                    DefaultPreference.getPreference().setPrefString(context, PREF_KEY_VERSION_NAME, str2);
                }
            }
        }
        if (i == 0) {
            i = (prefInt == -1 || !prefString.equals("")) ? 0 : 1;
            C1923L.info(InstallController.class, "report Install %b", Boolean.valueOf(this.statisAPI.reportInstall(i)));
            if (this.statisAPI.reportInstall(i)) {
                DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_VERSION_NO, versionNo);
                DefaultPreference.getPreference().setPrefString(context, PREF_KEY_VERSION_NAME, str2);
            }
        }
    }
}
