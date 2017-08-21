package com.yy.hiidostatis.defs;

import android.content.Context;

import com.umeng.analytics.onlineconfig.C1837a;
import com.yy.hiidostatis.defs.interf.IConfigAPI;
import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.GeneralConfigTool;
import com.yy.hiidostatis.inner.GeneralProxy;
import com.yy.hiidostatis.inner.implementation.CommonFiller;
import com.yy.hiidostatis.inner.util.log.C1923L;
import com.yy.hiidostatis.pref.HdStatisConfig;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConfigAPI implements IConfigAPI {
    private GeneralConfigTool mGeneralConfigTool;

    public ConfigAPI(Context context, String str) {
        this.mGeneralConfigTool = GeneralProxy.getGeneralConfigInstance(context, HdStatisConfig.getConfig(str));
    }

    private JSONObject getConfig(String str, List<NameValuePair> list, Context context, boolean z, boolean z2) throws Throwable {
        String cache = z ? this.mGeneralConfigTool.getCache(str, list, context, z2) : this.mGeneralConfigTool.get(str, list, context, z2);
        return (cache == null || cache.length() == 0) ? null : new JSONObject(cache);
    }

    public JSONObject getAppListConfig(Context context, boolean z) {
        try {
            List arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("sys", "2"));
            arrayList.add(new BasicNameValuePair("mid", CommonFiller.getIMEI(context)));
            return getConfig("api/getAppConfig", arrayList, context, z, true);
        } catch (Exception e) {
            C1923L.error(ConfigAPI.class, "getAppListConfig error! %s", e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public String getOnlineConfigs(Context context, String str) {
        try {
            List arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("appkey", str));
            return this.mGeneralConfigTool.get("api/getOnlineConfig", arrayList, context, true);
        } catch (Exception e) {
            C1923L.error(ConfigAPI.class, "getSdkVer error! %s", e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return str;
    }

    public JSONObject getSdkListConfig(Context context, boolean z) {
        try {
            List arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("sys", "2"));
            return getConfig("api/getSdkListConfig", arrayList, context, z, true);
        } catch (Exception e) {
            C1923L.error(ConfigAPI.class, "geSdkListConfig error! %s", e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public JSONObject getSdkVer(Context context, boolean z) {
        try {
            List arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("sys", "2"));
            arrayList.add(new BasicNameValuePair(C1837a.f3790a, HdStatisConfig.SDK_TYPE));
            arrayList.add(new BasicNameValuePair(BaseStatisContent.VER, this.mGeneralConfigTool.getConfig().getSdkVer()));
            return getConfig("api/getSdkVer", arrayList, context, z, false);
        } catch (Exception e) {
            C1923L.error(ConfigAPI.class, "getSdkVer error! %s", e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
