package com.yy.hiidostatis.defs.controller;

import android.content.Context;

import com.yy.hiidostatis.defs.interf.IConfigAPI;
import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.json.JSONObject;

public class SdkVerController {
    private static boolean mIsShowSdkUpdate = false;
    private IConfigAPI mConfigApi;

    public SdkVerController(IConfigAPI iConfigAPI) {
        this.mConfigApi = iConfigAPI;
    }

    public void startSdkVerCheck(final Context context) {
        if (!mIsShowSdkUpdate) {
            if (C1923L.isLogOn()) {
                ThreadPool.getPool().execute(new Runnable() {
                    public void run() {
                        try {
                            JSONObject sdkVer = SdkVerController.this.mConfigApi.getSdkVer(context, true);
                            if ("1".equals(sdkVer.has("isUpdate") ? sdkVer.getString("isUpdate") : "")) {
                                String string = sdkVer.has(BaseStatisContent.VER) ? sdkVer.getString(BaseStatisContent.VER) : "";
                                String string2 = sdkVer.has("changeLog") ? sdkVer.getString("changeLog") : "";
                                if (!Util.empty(string) && !Util.empty(string2)) {
                                    C1923L.info(SdkVerController.class, "统计SDK有新版本啦，欢迎使用新版本：V%s 。\n更新日志：\n%s", string, string2);
                                }
                            }
                        } catch (Exception e) {
                            C1923L.error(SdkVerController.class, "get startSdkVerCheck exception: %s", e);
                        }
                    }
                });
            }
            mIsShowSdkUpdate = true;
        }
    }
}
