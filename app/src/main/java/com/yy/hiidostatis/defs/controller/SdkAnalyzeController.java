package com.yy.hiidostatis.defs.controller;

import android.content.Context;

import com.yy.hiidostatis.defs.interf.IConfigAPI;
import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SdkAnalyzeController {
    private static final String PACKAGE_NAME = "%PACKAGE_NAME%";
    private static final String PREF_KEY_SDK_ANALYZE_REPORT_DATE = "PREF_KEY_SDK_ANALYZE_REPORT_DATE";
    private IConfigAPI mConfigAPI;
    private IStatisAPI statisAPI;

    public SdkAnalyzeController(IStatisAPI iStatisAPI, IConfigAPI iConfigAPI) {
        this.statisAPI = iStatisAPI;
        this.mConfigAPI = iConfigAPI;
    }

    private String getSdkList(Context context, JSONArray jSONArray) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("sdkName");
                String string2 = jSONObject.has("sdkFileName") ? jSONObject.getString("sdkFileName") : null;
                String string3 = jSONObject.has("sdkClassName") ? jSONObject.getString("sdkClassName") : null;
                if (!Util.empty(Util.getMetaDataParam(context, jSONObject.has("sdkConfigKey") ? jSONObject.getString("sdkConfigKey") : null)) || Util.isExistClass(string3) || isExistFile(context, string2)) {
                    stringBuffer.append(string).append("|");
                    i++;
                } else {
                    i++;
                }
            } catch (JSONException e) {
                C1923L.error(SdkAnalyzeController.class, "getSdkList exception: %s", e);
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        C1923L.debug(SdkAnalyzeController.class, "sdklist length=%d,sdklist bypes length=%d", Integer.valueOf(stringBuffer.toString().length()), Integer.valueOf(stringBuffer.toString().getBytes().length));
        C1923L.debug(SdkAnalyzeController.class, "sdklist=%s", stringBuffer.toString());
        return stringBuffer.toString();
    }

    private boolean isExistFile(Context context, String str) {
        if (context == null || Util.empty(str)) {
            return false;
        }
        try {
            C1923L.debug(this, "fileName:%s,newFileName:%s,isExist:%b", str, str.replaceAll(PACKAGE_NAME, context.getPackageName()), Boolean.valueOf(new File(str.replaceAll(PACKAGE_NAME, context.getPackageName())).exists()));
            return new File(str.replaceAll(PACKAGE_NAME, context.getPackageName())).exists();
        } catch (Exception e) {
            return false;
        }
    }

    private void reportSdkList(Context context, long j, JSONArray jSONArray) {
        this.statisAPI.reportSdkList(j, getSdkList(context, jSONArray));
    }

    private void startSdkAnalyzeReport(final Context context, final long j) {
        ThreadPool.getPool().execute(new Runnable() {
            public void run() {
                boolean z;
                JSONArray jSONArray = new JSONArray();
                JSONObject sdkListConfig = SdkAnalyzeController.this.mConfigAPI.getSdkListConfig(context, true);
                if (sdkListConfig != null) {
                    try {
                        if (sdkListConfig.has("enable") && "1".equals(sdkListConfig.get("enable") + "")) {
                            z = true;
                            C1923L.debug(SdkAnalyzeController.class, "sdkAnalyze enable is %b", Boolean.valueOf(z));
                            if (z) {
                                try {
                                    jSONArray = sdkListConfig.getJSONArray("sdkListConfig");
                                } catch (JSONException e) {
                                    C1923L.error(SdkAnalyzeController.class, "get json.sdkListConfig exception: %s", e);
                                    jSONArray = null;
                                }
                                if (jSONArray != null || jSONArray.length() == 0) {
                                    C1923L.debug(SdkAnalyzeController.class, "get sdkListJsonArray is null!", new Object[0]);
                                } else {
                                    SdkAnalyzeController.this.reportSdkList(context, j, jSONArray);
                                    return;
                                }
                            }
                        }
                    } catch (JSONException e2) {
                        C1923L.error(SdkAnalyzeController.class, "get json.enable exception: %s", e2);
                    }
                }
                z = false;
                C1923L.debug(SdkAnalyzeController.class, "sdkAnalyze enable is %b", Boolean.valueOf(z));
                if (z) {
                    try {
                        jSONArray = sdkListConfig.getJSONArray("sdkListConfig");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (jSONArray != null) {
                    }
                    C1923L.debug(SdkAnalyzeController.class, "get sdkListJsonArray is null!", new Object[0]);
                }
            }
        });
    }

    public void reportSdkAnalyze(Context context, long j) {
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (DefaultPreference.getPreference().getPrefString(context, PREF_KEY_SDK_ANALYZE_REPORT_DATE, "").equals(format)) {
            C1923L.debug(AppAnalyzeController.class, "sdk Analyze is reported today[%s]，so not report again!", format);
            return;
        }
        startSdkAnalyzeReport(context, j);
        DefaultPreference.getPreference().setPrefString(context, PREF_KEY_SDK_ANALYZE_REPORT_DATE, format);
    }
}
