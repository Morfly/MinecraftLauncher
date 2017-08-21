package com.yy.hiidostatis.inner.util.http;

import android.text.TextUtils;

import com.yy.hiidostatis.api.HiidoSDK.Options;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Random;

public abstract class AbstractStatisHttpUtil implements IStatisHttpUtil {
    protected final OnResultListener DUMMY = new C19171();
    protected static final int TRY_TIMES = 2;
    protected int lastTryTimes;
    protected Throwable mThrowable;
    protected String reasonPhrase;
    protected int statusCode;
    protected String testServer = null;

    final class C19171 implements OnResultListener {
        C19171() {
        }

        public void onFailed(String str, Object obj, Throwable th) {
        }

        public void onSuccess(String str, Object obj) {
        }
    }

    private boolean execute(HttpUriRequest httpUriRequest) throws IOException {
        this.statusCode = -1;
        this.reasonPhrase = null;
        DefaultHttpClient defaultHttpClient = getDefaultHttpClient();
        HttpResponse execute = defaultHttpClient.execute(httpUriRequest);
        int statusCode = execute.getStatusLine().getStatusCode();
        this.statusCode = statusCode;
        this.reasonPhrase = execute.getStatusLine().getReasonPhrase();
        try {
            HttpEntity entity = execute.getEntity();
            if (entity != null) {
                entity.consumeContent();
            }
            ClientConnectionManager connectionManager = defaultHttpClient.getConnectionManager();
            if (connectionManager != null) {
                connectionManager.closeExpiredConnections();
                connectionManager.shutdown();
            }
        } catch (Exception e) {
            C1923L.error(this, "consumeContent or  closeExpiredConnections Exception:%s", e);
        }
        if (statusCode != 200) {
            C1923L.error(this, "Status code of %s is %d.", httpUriRequest.getURI(), Integer.valueOf(statusCode));
        }
        return statusCode == 200;
    }

    private DefaultHttpClient getDefaultHttpClient() {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        defaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(Options.DEFAULT_BACKGROUND_DURATION_MILLIS_AS_QUIT));
        defaultHttpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(60000));
        defaultHttpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        return defaultHttpClient;
    }

    private boolean postByUrlConn(String str, String str2) throws Throwable {
        Throwable th;
        DataOutputStream dataOutputStream;
        HttpURLConnection httpURLConnection = null;
        boolean z = true;
        this.statusCode = -1;
        this.reasonPhrase = null;
        try {
            byte[] bytes = str2.getBytes();
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setConnectTimeout(Options.DEFAULT_BACKGROUND_DURATION_MILLIS_AS_QUIT);
                httpURLConnection2.setReadTimeout(60000);
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setInstanceFollowRedirects(true);
                httpURLConnection2.setRequestProperty("Connection", "close");
                httpURLConnection2.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
                httpURLConnection2.setRequestProperty("User-Agent", "Hiido");
                httpURLConnection2.connect();
                DataOutputStream dataOutputStream2 = new DataOutputStream(httpURLConnection2.getOutputStream());
                try {
                    dataOutputStream2.write(bytes);
                    dataOutputStream2.flush();
                    dataOutputStream2.close();
                    if (httpURLConnection2.getResponseCode() != 200) {
                        z = false;
                    }
                    this.statusCode = httpURLConnection2.getResponseCode();
                    this.reasonPhrase = httpURLConnection2.getResponseMessage();
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                        } catch (Exception e) {
                        }
                    }
                    if (dataOutputStream2 != null) {
                        dataOutputStream2.close();
                    }
                    return z;
                } catch (Throwable th2) {
                    httpURLConnection = httpURLConnection2;
                    th = th2;
                    dataOutputStream = dataOutputStream2;
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Exception e2) {
                            throw th;
                        }
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th22) {
                Throwable th3 = th22;
                dataOutputStream = null;
                httpURLConnection = httpURLConnection2;
                th = th3;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            dataOutputStream = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            throw th;
        }
    }

    protected String asUrl(String str) {
        return String.format(getUrlFormat(), new Object[]{str});
    }

    protected boolean get(String str, String str2) throws IOException {
        HttpUriRequest httpGet = new HttpGet(str + "?" + str2);
        httpGet.setHeader("Connection", "close");
        return execute(httpGet);
    }

    protected String[] getFallbackIps() {
        return (this.testServer == null || this.testServer.length() == 0) ? getUrlAddress() : new String[0];
    }

    public Throwable getLastError() {
        return this.mThrowable;
    }

    public int getLastTryTimes() {
        return this.lastTryTimes;
    }

    protected String getServerAddr() {
        String urlService = (this.testServer == null || this.testServer.length() == 0) ? getUrlService() : this.testServer;
        C1923L.brief("return hiido server %s", urlService);
        return urlService;
    }

    protected abstract String[] getUrlAddress();

    protected abstract String getUrlFormat();

    protected abstract String getUrlService();

    protected String parseParam(String str, String str2) {
        try {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (str2.equals(split2[0])) {
                    return split2[1];
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    protected boolean post(String str, String str2) throws Throwable {
        return postByUrlConn(str, str2);
    }

    protected abstract boolean sendContent(String str, String str2, int i);

    public boolean sendSync(String str, Object obj, OnResultListener onResultListener) {
        this.lastTryTimes = 0;
        C1923L.brief("to send content %s", str);
        C1923L.brief("to send content decoded %s", URLDecoder.decode(str));
        if (onResultListener == null) {
            onResultListener = DUMMY;
        }
        return sendSyncByTrying(str, obj, onResultListener);
    }

    protected boolean sendSyncByTrying(String str, Object obj, OnResultListener onResultListener) {
        if (sendContent(getServerAddr(), str, 2)) {
            onResultListener.onSuccess(str, obj);
            return true;
        }
        String[] fallbackIps = getFallbackIps();
        C1923L.brief("fallback IPs : %s", TextUtils.join(" ", fallbackIps));
        if (fallbackIps == null || fallbackIps.length == 0) {
            return false;
        }
        boolean sendContent = sendContent(asUrl(fallbackIps[new Random().nextInt(fallbackIps.length)]), str, 0);
        if (sendContent) {
            onResultListener.onSuccess(str, obj);
            return sendContent;
        }
        onResultListener.onFailed(str, obj, this.mThrowable);
        return sendContent;
    }

    public void setTestServer(String str) {
        this.testServer = str;
    }

    public void shutDown() {
    }
}
