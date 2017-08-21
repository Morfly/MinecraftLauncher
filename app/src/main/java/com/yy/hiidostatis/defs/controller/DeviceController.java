package com.yy.hiidostatis.defs.controller;

import android.content.Context;

import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceController {
    private static final String PREF_KEY_DEVICE_REPORT_DATE = "PREF_KEY_DEVICE_REPORT_DATE";
    private IStatisAPI statisAPI;

    public DeviceController(IStatisAPI iStatisAPI) {
        this.statisAPI = iStatisAPI;
    }

    public void reportDevice(Context context, long j) throws Throwable {
        if (context == null) {
            C1923L.error("BasicStatisAPI", "Null context when reporting to hiido, cancelled.", new Object[0]);
            return;
        }
        String prefString;
        boolean z;
        String str = "";
        try {
            str = new SimpleDateFormat("yyyyMMdd").format(new Date());
            prefString = DefaultPreference.getPreference().getPrefString(context, PREF_KEY_DEVICE_REPORT_DATE, "");
            boolean z2 = !Util.empty(prefString) && prefString.equals(str);
            String str2 = str;
            z = z2;
            prefString = str2;
        } catch (Exception e) {
            Exception exception = e;
            prefString = str;
            C1923L.warn(this, "reportDevice exception=%s", exception);
            z = false;
        }
        C1923L.brief("reportDevice:isReport:%b", Boolean.valueOf(z));
        if (!z && this.statisAPI.reportDevice(j)) {
            DefaultPreference.getPreference().setPrefString(context, PREF_KEY_DEVICE_REPORT_DATE, prefString);
        }
    }
}
