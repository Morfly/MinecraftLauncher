package com.yy.hiidostatis.inner.util.http;

import android.text.TextUtils;

import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.cipher.AesCipher;
import com.yy.hiidostatis.inner.util.cipher.Base64Util;
import com.yy.hiidostatis.inner.util.cipher.RsaCipher;
import com.yy.hiidostatis.inner.util.log.ActLog;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Random;

public class StatisHttpEncryptUtil extends AbstractStatisHttpUtil {
    private static final String[] ADDRESSES = new String[]{"54.232.231.48", "52.76.34.9", "54.93.72.224", "54.67.72.47", "52.5.132.150", "175.100.204.142"};
    private static final String[] ADDRESSES_OTHER = new String[]{"218.61.196.187", "218.61.196.188", "14.17.112.232", "14.17.112.233", "183.232.137.31", "183.232.137.32", "117.144.234.35", "117.144.234.36"};
    private static final String DEFAULT_URL_HOST = "hlog.hiido.com";
    private static final String RSA_PUB_KEY = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRSvSVZEbyQwtFwNtNiZKkCAwEAAQ==";
    private static final String URL_FORMAT = "http://%s/c.gif";
    private static final String URL_PARAM = "act=mbsdkdata&smkdata=$smkdata&EC=$EC&appkey=$appkey&item=$item";
    private static volatile String ipAddress = null;
    private String mLastHost;
    private String mLastSmkData;
    private RsaCipher mRsaCipher = null;
    private String mUrlHost;
    private String mUrlService;

    public StatisHttpEncryptUtil(String str) {
        if (str == null) {
            str = DEFAULT_URL_HOST;
        }
        this.mUrlHost = str;
        this.mUrlService = String.format(URL_FORMAT, new Object[]{this.mUrlHost});
    }

    private String getIpAddress() {
        return ipAddress;
    }

    private RsaCipher getRsaCipher() throws Exception {
        if (this.mRsaCipher == null) {
            InputStream byteArrayInputStream = new ByteArrayInputStream(Base64Util.decode(RSA_PUB_KEY));
            RsaCipher rsaCipher = new RsaCipher();
            rsaCipher.loadPublicKey(byteArrayInputStream);
            this.mRsaCipher = rsaCipher;
        }
        return this.mRsaCipher;
    }

    public String getLastHost() {
        return this.mLastHost;
    }

    public String getLastSmkData() {
        return this.mLastSmkData;
    }

    protected String[] getOtherFallbackIps() {
        return Util.empty(this.testServer) ? ADDRESSES_OTHER : new String[0];
    }

    protected String[] getUrlAddress() {
        return ADDRESSES;
    }

    protected String getUrlFormat() {
        return URL_FORMAT;
    }

    protected String getUrlService() {
        return this.mUrlService;
    }

    protected boolean sendContent(String str, String str2, int i) {
        C1923L.brief("hiido service address is %s", str);
        this.mThrowable = null;
        this.mLastHost = null;
        this.mLastSmkData = null;
        String str3 = str + "?" + URL_PARAM;
        try {
            String str4 = "";
            str4 = Util.getRandStringEx(4);
            String encryptTlogBytesBase64 = new AesCipher(str4.getBytes()).encryptTlogBytesBase64(str2.getBytes("UTF-8"));
            str3 = str3 + "&enc=b64";
            Object encryptTlogAesKey = getRsaCipher().encryptTlogAesKey(str4.getBytes());
            String replace = str3.replace("$smkdata", (CharSequence) encryptTlogAesKey).replace("$appkey", parseParam(str2, "appkey")).replace("$item", parseParam(str2, BaseStatisContent.ACT));
            this.mLastSmkData = (String) encryptTlogAesKey;
            this.mLastHost = new URI(str).getHost();
            int i2 = i;
            while (true) {
                try {
                    str3 = replace.replace("$EC", ((i - i2) + 1) + "");
                    if (i != i2) {
                        C1923L.brief("Try again to send %s with url %s, tried times %d.", encryptTlogBytesBase64, str3, Integer.valueOf(i - i2));
                    }
                    this.lastTryTimes++;
                    if (post(str3, encryptTlogBytesBase64)) {
                        this.mThrowable = null;
                        ActLog.writeSendSucLog(null, this.mLastSmkData, this.mLastHost, str2);
                        C1923L.debug(this, "Successfully sent %s to %s", encryptTlogBytesBase64, str3);
                        break;
                    }
                    C1923L.debug(this, "Failed to send %s to %s.", encryptTlogBytesBase64, str3);
                    ActLog.writeSendFailLog(null, this.mLastSmkData, this.mLastHost, str2, this.statusCode + "|" + this.reasonPhrase + "|");
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        return false;
                    }
                    i2 = i3;
                } catch (Throwable th) {
                }
            }
            return true;
        } catch (Throwable th2) {
            C1923L.error(this, "encrypt exception = %s", th2);
            this.mThrowable = th2;
            ActLog.writeSendFailLog(null, this.mLastSmkData, this.mLastHost, str2, this.statusCode + "|encrypt exception=" + th2);
            return false;
        }
    }

    protected boolean sendSyncByTrying(String str, Object obj, OnResultListener onResultListener) {
        String ipAddress = getIpAddress();
        if (!Util.empty(ipAddress)) {
            Object[] split;
            split = ipAddress.split(",");
            C1923L.brief("send ip first，fallback IPs : %s", TextUtils.join(" ", split));
            if (!Util.empty(split) && sendContent(asUrl((String) split[new Random().nextInt(split.length)]), str, 1)) {
                onResultListener.onSuccess(str, obj);
                return true;
            }
        }
        C1923L.brief("sendSyncByTrying by old...", new Object[0]);
        if (sendContent(getServerAddr(), str, 1)) {
            onResultListener.onSuccess(str, obj);
            return true;
        }
        Object[] split = getFallbackIps();
        C1923L.brief("fallback IPs : %s", TextUtils.join(" ", split));
        if (Util.empty(split) || !sendContent(asUrl((String) split[new Random().nextInt(split.length)]), str, 1)) {
            split = getOtherFallbackIps();
            C1923L.brief("other fallback IPs : %s", TextUtils.join(" ", split));
            if (Util.empty(split) || !sendContent(asUrl((String) split[new Random().nextInt(split.length)]), str, 0)) {
                onResultListener.onFailed(str, obj, this.mThrowable);
                return false;
            }
            onResultListener.onSuccess(str, obj);
            return true;
        }
        onResultListener.onSuccess(str, obj);
        return true;
    }
}
