package com.yy.hiidostatis.pref;

import com.yy.hiidostatis.inner.AbstractConfig;
import com.yy.hiidostatis.inner.util.log.IBaseStatisLogTag;

import java.util.Hashtable;

public class HdStatisConfig extends AbstractConfig {
    public static final String META_DATA_KEY_APP_KEY = "HIIDO_APPKEY";
    public static final String META_DATA_KEY_CHANNEL = "HIIDO_CHANNEL";
    public static final String SDK_TYPE = "11";
    private static Hashtable<String, AbstractConfig> table = new Hashtable();
    private String mAppkey = null;

    class C19241 implements IBaseStatisLogTag {
        C19241() {
        }

        public String getLogPrefix() {
            return "StatisSDK";
        }

        public String getLogTag() {
            return "StatisSDK";
        }
    }

    private HdStatisConfig(String str) {
        this.mAppkey = str;
        this.isEncrypt = true;
        this.isEncryptTestServer = false;
        this.testServer = null;
        this.urlHost = "hlog.hiido.com";
        this.urlConfigServer = "https://config.hiido.com/";
        this.urlLogUpload = "http://52.5.188.119:8080/upload/UploadServlet";
        this.cacheFileName = "hdstatis_cache_" + str;
        this.sdkVer = "3.2.9";
        setLogTag(new C19241());
        setDefaultPrefName("hd_default_pref");
        setActLogNamePre("hdstatis");
        setActLogUploadUrl(this.urlLogUpload);
    }

    public static AbstractConfig getConfig(String str) {
        if (str == null || table.size() > 10) {
            str = "def_appkey";
        } else if (str.length() > 8) {
            str = str.substring(0, 8);
        }
        if (!table.containsKey(str)) {
            table.put(str, new HdStatisConfig(str));
        }
        return (AbstractConfig) table.get(str);
    }

    protected String getConfigKey() {
        return this.mAppkey;
    }

    public void setTestServer(String str) {
        this.testServer = str;
    }
}
