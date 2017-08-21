package com.yy.hiidostatis.inner.util.http;

import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

public class StatisHttpUtil extends AbstractStatisHttpUtil {
    private static final String[] ADDRESSES = new String[]{"183.61.2.91", "183.61.2.92", "183.61.2.93", "183.61.2.94", "183.61.2.95", "183.61.2.96", "183.61.2.97", "183.61.2.98"};
    private static final String URL_FORMAT = "http://%s/c.gif";
    private static final String URL_SERVICE = "http://ylog.hiido.com/c.gif";

    protected String[] getUrlAddress() {
        return ADDRESSES;
    }

    protected String getUrlFormat() {
        return URL_FORMAT;
    }

    protected String getUrlService() {
        return URL_SERVICE;
    }

    protected boolean sendContent(String str, String str2, int i) {
        C1923L.brief("hiido service address is %s", str);
        this.mThrowable = null;
        int i2 = i;
        while (true) {
            try {
                if (getLastTryTimes() <= 0 || Util.isNetworkReach()) {
                    if (i != i2) {
                        C1923L.brief("Try again to send %s with url %s, tried times %d.", str2, str, Integer.valueOf(i - i2));
                    }
                    this.lastTryTimes++;
                    if (get(str, str2)) {
                        this.mThrowable = null;
                        C1923L.debug(this, "Successfully sent %s to %s", str2, str);
                        return true;
                    }
                    C1923L.debug(this, "Failed to send %s to %s.", str2, str);
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        return false;
                    }
                    i2 = i3;
                } else {
                    C1923L.warn(this, "isNetworkReach false.", new Object[0]);
                    return false;
                }
            } catch (Throwable th) {
                this.mThrowable = th;
                C1923L.error(StatisHttpUtil.class, "guid:%s. http statis exception %s", parseParam(str2, BaseStatisContent.GUID), th);
            }
        }
    }
}
