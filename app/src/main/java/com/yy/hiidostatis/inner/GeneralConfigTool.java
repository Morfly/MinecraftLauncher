package com.yy.hiidostatis.inner;

import android.content.Context;

import com.yy.hiidostatis.inner.util.Preference;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.cipher.Coder;
import com.yy.hiidostatis.inner.util.http.HttpClientUtil;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class GeneralConfigTool {
    private static final String KEY_CODE = "code";
    private static final String KEY_CODE_STATUS_NOT_MODIFIED = "2";
    private static final String KEY_CODE_STATUS_SUCCESS = "1";
    private static final String KEY_DATA = "data";
    private static final String KEY_LAST_MODIFIED_TIME = "lastModifiedTime";
    private static final String KEY_MAGIC = "HiidoData";
    private static final String KEY_MSG = "msg";
    private static final String KEY_TIME = "time";
    private static final long MAX_CACHE_TIME = 86400000;
    private static Preference preference = new Preference("hdcommon_config_cache_pref", true);
    private String URL_CONFIG_SERVER;
    private AbstractConfig mConfig;
    private String r1;
    private Exception e4;

    class C19051 implements Comparator<NameValuePair> {
        C19051() {
        }

        public int compare(NameValuePair nameValuePair, NameValuePair nameValuePair2) {
            return nameValuePair.getName().hashCode() > nameValuePair2.getName().hashCode() ? 1 : nameValuePair.getName().hashCode() < nameValuePair2.getName().hashCode() ? -1 : 0;
        }
    }

    public GeneralConfigTool(Context context, AbstractConfig abstractConfig) {
        this.mConfig = abstractConfig;
        this.URL_CONFIG_SERVER = abstractConfig.getUrlConfigServer();
    }

    private void addLastModifyTimeToParams(String str, List<NameValuePair> list) throws JSONException {
        if (!Util.empty(str)) {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(KEY_LAST_MODIFIED_TIME)) {
                String str2 = jSONObject.get(KEY_LAST_MODIFIED_TIME) + "";
                if (!Util.empty(str2)) {
                    List arrayList = new ArrayList();
                    if (list == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new BasicNameValuePair(KEY_LAST_MODIFIED_TIME, str2));
                }
            }
        }
    }

    private String assblyCacheKey(String str, List<NameValuePair> list) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append(this.URL_CONFIG_SERVER);
        if (str == null) {
            str = "";
        }
        stringBuffer.append(str).append("?");
        if (list != null) {
            sort(list);
            for (NameValuePair nameValuePair : list) {
                stringBuffer.append(nameValuePair.getName()).append("=").append(nameValuePair.getValue()).append("&");
            }
        }
        return Coder.encryptMD5(stringBuffer.toString());
    }

    private String assblyUrl(String str) {
        String str2 = this.URL_CONFIG_SERVER;
        return str != null ? str2 + str : str2;
    }

    private String parseData(JSONObject jSONObject, boolean z) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidKeySpecException, NoSuchPaddingException {
        if ("1".equals(jSONObject.getString(KEY_CODE))) {
            String string = jSONObject.getString("data");
            if (Util.empty(string)) {
                return string;
            }
            if (z) {
                C1923L.debug(GeneralConfigTool.class, "key is %s", Coder.encryptMD5(jSONObject.getString("time") + KEY_MAGIC).toLowerCase().substring(0, 8));
                C1923L.debug(GeneralConfigTool.class, "data before decrypt  is %s", string);
                C1923L.debug(GeneralConfigTool.class, "data after decrypt  is %s", Coder.decryptDES(string, r1));
                return Coder.decryptDES(string, r1);
            }
            C1923L.debug(GeneralConfigTool.class, "data without decrypt  is %s", string);
            return string;
        }
        C1923L.error(GeneralConfigTool.class, "http get fail! code is %s,msg is %s", jSONObject.getString(KEY_CODE), jSONObject.getString("msg"));
        return null;
    }

    private void sort(List<NameValuePair> list) {
        if (list != null) {
            try {
                Collections.sort(list, new C19051());
            } catch (Exception e) {
                C1923L.warn(GeneralConfigTool.class, "sort list error %s", e);
            }
        }
    }

    public String get(String str, List<NameValuePair> list, Context context, boolean z) throws Throwable {
        String prefString;
        Exception e;
        JSONObject jSONObject;
        String string;
        String assblyUrl = assblyUrl(str);
        String assblyCacheKey;
        String str2;
        try {
            assblyCacheKey = assblyCacheKey(str, list);
            try {
                prefString = preference.getPrefString(context, assblyCacheKey, null);
            } catch (Exception e2) {
                e = e2;
                prefString = null;
                C1923L.error(GeneralConfigTool.class, "get cache exception %s", e);
                str2 = HttpClientUtil.get(assblyUrl, list);
                jSONObject = new JSONObject(str2);
                string = jSONObject.getString(KEY_CODE);
                if (!"1".equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is modified", new Object[0]);
                    preference.setPrefString(context, assblyCacheKey, str2);
                } else if (KEY_CODE_STATUS_NOT_MODIFIED.equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is not modified,so get the cache data [%s]", prefString);
                    jSONObject = new JSONObject(prefString);
                }
                return parseData(jSONObject, z);
            }
            try {
                addLastModifyTimeToParams(prefString, list);
            } catch (Exception e3) {
                e = e3;
                C1923L.error(GeneralConfigTool.class, "get cache exception %s", e);
                str2 = HttpClientUtil.get(assblyUrl, list);
                jSONObject = new JSONObject(str2);
                string = jSONObject.getString(KEY_CODE);
                if (!"1".equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is modified", new Object[0]);
                    preference.setPrefString(context, assblyCacheKey, str2);
                } else if (KEY_CODE_STATUS_NOT_MODIFIED.equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is not modified,so get the cache data [%s]", prefString);
                    jSONObject = new JSONObject(prefString);
                }
                return parseData(jSONObject, z);
            }
            try {
                str2 = HttpClientUtil.get(assblyUrl, list);
                jSONObject = new JSONObject(str2);
                string = jSONObject.getString(KEY_CODE);
                if (!"1".equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is modified", new Object[0]);
                    preference.setPrefString(context, assblyCacheKey, str2);
                } else if (KEY_CODE_STATUS_NOT_MODIFIED.equals(string)) {
                    C1923L.debug(GeneralConfigTool.class, "data is not modified,so get the cache data [%s]", prefString);
                    jSONObject = new JSONObject(prefString);
                }
                return parseData(jSONObject, z);
            } catch (Exception e4) {
                C1923L.error(GeneralConfigTool.class, "http get [%s] error! %s", assblyUrl, e4);
                C1923L.error(GeneralConfigTool.class, "exception:%s", e4);
                return null;
            }
        } catch (Exception e5) {
            e4 = e5;
            prefString = null;
            assblyCacheKey = null;
            C1923L.error(GeneralConfigTool.class, "get cache exception %s", e4);
            str2 = HttpClientUtil.get(assblyUrl, list);
            jSONObject = new JSONObject(str2);
            string = jSONObject.getString(KEY_CODE);
            if (!"1".equals(string)) {
                C1923L.debug(GeneralConfigTool.class, "data is modified", new Object[0]);
                preference.setPrefString(context, assblyCacheKey, str2);
            } else if (KEY_CODE_STATUS_NOT_MODIFIED.equals(string)) {
                C1923L.debug(GeneralConfigTool.class, "data is not modified,so get the cache data [%s]", prefString);
                jSONObject = new JSONObject(prefString);
            }
            return parseData(jSONObject, z);
        }
    }

    public String getCache(String str, List<NameValuePair> list, Context context, boolean z) throws Throwable {
        String str2 = "";
        String str3 = "";
        long currentTimeMillis = System.currentTimeMillis();
        String assblyCacheKey = assblyCacheKey(str, list);
        String str4 = "prefKeyTime_" + assblyCacheKey;
        String str5 = "prefKeyData_" + assblyCacheKey;
        synchronized (assblyCacheKey) {
            long prefLong = preference.getPrefLong(context, str4, 0);
            str3 = preference.getPrefString(context, str5, "");
            C1923L.brief("prefKeyTime:%s", Long.valueOf(prefLong));
            C1923L.brief("prefKeyData:%s", str3);
            if (currentTimeMillis - prefLong <= 86400000) {
                C1923L.debug(GeneralConfigTool.class, "get cache success,result is %s", str3);
                str2 = str3;
            }
            if (Util.empty(str2)) {
                str2 = get(str, list, context, z);
                if (str2 != null) {
                    C1923L.debug(GeneralConfigTool.class, "get remote success,result is %s", str2);
                    preference.setPrefLong(context, str4, currentTimeMillis);
                    preference.setPrefString(context, str5, str2);
                } else {
                    C1923L.debug(GeneralConfigTool.class, "get cache because get remote is null", str2);
                    str2 = str3;
                }
            }
        }
        return str2;
    }

    public AbstractConfig getConfig() {
        return this.mConfig;
    }

    public void setmConfig(AbstractConfig abstractConfig) {
        this.mConfig = abstractConfig;
    }
}
