package com.yy.hiidostatis.track;

import android.content.Context;

import com.yy.hiidostatis.api.HiidoSDK;
import com.yy.hiidostatis.api.ga.GaListenerController;
import com.yy.hiidostatis.inner.util.DefaultPreference;

import java.text.SimpleDateFormat;

public class Report {
    private static final String EVENT_ID = "HD_EVENT_TEST";
    private static final String PREF_GA_EVENT_KEY = "PREF_GA_EVENT_REPORT_DATE";
    private static String reportDate;

    public static void onError(String str) {
        if (GaListenerController.getListerner() != null) {
            try {
                GaListenerController.getListerner().reportErrorToGa("HD_ERROR:" + str);
            } catch (Throwable th) {
            }
        }
    }

    public static void onEvent(Context context) {
        try {
            if (GaListenerController.getListerner() != null) {
                if (reportDate == null) {
                    reportDate = DefaultPreference.getPreference().getPrefString(context, PREF_GA_EVENT_KEY, null);
                }
                String format = new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(System.currentTimeMillis()));
                if (!format.equals(reportDate)) {
                    try {
                        HiidoSDK.instance().reportTimesEvent(0, EVENT_ID);
                    } catch (Exception e) {
                    }
                    try {
                        GaListenerController.getListerner().reportEventToGa(EVENT_ID, null, 1);
                    } catch (Exception e2) {
                    }
                    DefaultPreference.getPreference().setPrefString(context, PREF_GA_EVENT_KEY, format);
                    reportDate = format;
                }
            }
        } catch (Throwable th) {
        }
    }
}
