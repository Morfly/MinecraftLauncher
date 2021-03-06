package com.yy.hiidostatis.defs.interf;

import android.content.Context;

import org.json.JSONObject;

public interface IConfigAPI {
    JSONObject getAppListConfig(Context context, boolean z);

    String getOnlineConfigs(Context context, String str);

    JSONObject getSdkListConfig(Context context, boolean z);

    JSONObject getSdkVer(Context context, boolean z);
}
