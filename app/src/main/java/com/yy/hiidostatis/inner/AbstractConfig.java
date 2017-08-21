package com.yy.hiidostatis.inner;

import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.log.ActLog;
import com.yy.hiidostatis.inner.util.log.C1923L;
import com.yy.hiidostatis.inner.util.log.IBaseStatisLogTag;

public abstract class AbstractConfig {
    public static final int MAX_DATA_CACHE_DAY = 5;
    public static final int MAX_DATA_RETRY_TIME = 10000;
    protected String cacheFileName = "hdcommon_module_used_file";
    protected boolean isEncrypt = true;
    protected boolean isEncryptTestServer = false;
    protected String sdkVer;
    protected String testServer = null;
    protected String urlConfigServer = "https://config.hiido.com/";
    protected String urlHost = "mlog.hiido.com";
    protected String urlLogUpload = "https://config.hiido.com/api/upload";

    public String getCacheFileName() {
        return this.cacheFileName;
    }

    protected String getConfigKey() {
        return getClass().getName();
    }

    public String getSdkVer() {
        return this.sdkVer;
    }

    public String getTestServer() {
        return this.testServer;
    }

    public String getUrlConfigServer() {
        return this.urlConfigServer;
    }

    public String getUrlHost() {
        return this.urlHost;
    }

    public boolean isEncrypt() {
        return this.isEncrypt;
    }

    public boolean isEncryptTestServer() {
        return this.isEncryptTestServer;
    }

    protected void setActLogNamePre(String str) {
        ActLog.setLogNamePre(str);
    }

    protected void setActLogUploadUrl(String str) {
        ActLog.setUploadUrl(str);
    }

    protected void setDefaultPrefName(String str) {
        DefaultPreference.setPrefName(str);
    }

    protected void setLogTag(IBaseStatisLogTag iBaseStatisLogTag) {
        C1923L.setLogTag(iBaseStatisLogTag);
    }
}
