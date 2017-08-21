package com.yy.hiidostatis.inner;

public class DefaultConfig extends AbstractConfig {
    private static AbstractConfig instance = new DefaultConfig();

    private DefaultConfig() {
        this.isEncrypt = true;
        this.isEncryptTestServer = false;
        this.testServer = null;
        this.urlHost = "mlog.hiido.com";
        this.urlConfigServer = "https://config.hiido.com/";
        this.cacheFileName = "hdcommon_module_used_file";
        this.sdkVer = null;
    }

    public static AbstractConfig getSingleton() {
        return instance;
    }
}
