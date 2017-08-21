package com.yy.hiidostatis.defs.controller;

import android.content.Context;

import com.yy.hiidostatis.defs.interf.IConfigAPI;
import com.yy.hiidostatis.defs.interf.IOnLineConfigListener;
import com.yy.hiidostatis.inner.util.Preference;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.json.JSONException;
import org.json.JSONObject;

public class OnLineConfigController {
    private static final String PREF_KEY_ONLINE_CONFIG_DATA = "PREF_KEY_ONLINE_CONFIG_DATA";
    private static Preference preference = new Preference("hd_online_config_pref", true);
    private boolean isFinishUpdateConfig = false;
    private IConfigAPI mConfigAPI;
    private IOnLineConfigListener onLineConfigListener;

    public OnLineConfigController(IConfigAPI iConfigAPI) {
        this.mConfigAPI = iConfigAPI;
    }

    private JSONObject getOnlineParamsJSON(Context context) throws JSONException {
        String prefString = preference.getPrefString(context, PREF_KEY_ONLINE_CONFIG_DATA, "");
        if (Util.empty(prefString)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(prefString);
        return (jSONObject == null || !(jSONObject.get("onlineParams") instanceof JSONObject)) ? null : jSONObject.getJSONObject("onlineParams");
    }

    public String getOnlineConfigParams(Context context, String str) {
        String str2 = "";
        try {
            JSONObject onlineParamsJSON = getOnlineParamsJSON(context);
            if (onlineParamsJSON != null && onlineParamsJSON.has(str)) {
                str2 = onlineParamsJSON.getString(str);
            }
        } catch (Exception e) {
            C1923L.error(OnLineConfigController.class, "getOnlineConfigParams error! %s", e);
        }
        return str2;
    }

    public boolean isFinishUpdateConfig() {
        return this.isFinishUpdateConfig;
    }

    public void setOnLineConfigListener(IOnLineConfigListener iOnLineConfigListener) {
        this.onLineConfigListener = iOnLineConfigListener;
    }

    public void updateOnlineConfigs(final Context context, final String str) {
        this.isFinishUpdateConfig = false;
        ThreadPool.getPool().execute(new Runnable() {
            public void run() {
                JSONObject access$400;
                IOnLineConfigListener access$300;
                try {
                    String onlineConfigs = OnLineConfigController.this.mConfigAPI.getOnlineConfigs(context, str);
                    C1923L.debug(OnLineConfigController.class, "the online config data is %s", onlineConfigs);
                    if (onlineConfigs != null && onlineConfigs.length() > 0) {
                        OnLineConfigController.preference.setPrefString(context, OnLineConfigController.PREF_KEY_ONLINE_CONFIG_DATA, onlineConfigs);
                    }
                    OnLineConfigController.this.isFinishUpdateConfig = true;
                    if (OnLineConfigController.this.onLineConfigListener != null) {
                        try {
                            access$400 = OnLineConfigController.this.getOnlineParamsJSON(context);
                        } catch (JSONException e) {
                            C1923L.error(this, "get getOnlineParamsJSON error! %s", e);
                            access$400 = null;
                        }
                        if (access$400 == null) {
                            access$400 = new JSONObject();
                        }
                        C1923L.debug(OnLineConfigController.class, "call onLineConfigListener.onDataReceived(data)", new Object[0]);
                        access$300 = OnLineConfigController.this.onLineConfigListener;
                        access$300.onDataReceived(access$400);
                    }
                } catch (Exception e2) {
                    C1923L.error(OnLineConfigController.class, "updateOnlineConfigs error! %s", e2);
                    OnLineConfigController.this.isFinishUpdateConfig = true;
                    if (OnLineConfigController.this.onLineConfigListener != null) {
                        try {
                            access$400 = OnLineConfigController.this.getOnlineParamsJSON(context);
                        } catch (JSONException e3) {
                            C1923L.error(this, "get getOnlineParamsJSON error! %s", e3);
                            access$400 = null;
                        }
                        if (access$400 == null) {
                            access$400 = new JSONObject();
                        }
                        C1923L.debug(OnLineConfigController.class, "call onLineConfigListener.onDataReceived(data)", new Object[0]);
                        access$300 = OnLineConfigController.this.onLineConfigListener;
                    }
                } catch (Throwable th) {
                    OnLineConfigController.this.isFinishUpdateConfig = true;
                    if (OnLineConfigController.this.onLineConfigListener != null) {
                        JSONObject access$4002;
                        try {
                            access$4002 = OnLineConfigController.this.getOnlineParamsJSON(context);
                        } catch (JSONException e4) {
                            C1923L.error(this, "get getOnlineParamsJSON error! %s", e4);
                            access$4002 = null;
                        }
                        if (access$4002 == null) {
                            access$4002 = new JSONObject();
                        }
                        C1923L.debug(OnLineConfigController.class, "call onLineConfigListener.onDataReceived(data)", new Object[0]);
                        OnLineConfigController.this.onLineConfigListener.onDataReceived(access$4002);
                    }
                }
            }
        });
    }
}
