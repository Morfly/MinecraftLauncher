package com.yy.hiidostatis.inner.util.http;

import com.yy.hiidostatis.inner.util.log.C1923L;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil {
    private static String r0;
    private static Throwable th2;

    public static String get(String str, List<NameValuePair> list) throws Throwable {
        Throwable th;
        InputStream inputStream = null;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            defaultHttpClient = new DefaultHttpClient();
            try {
                HttpUriRequest httpGet;
                StringBuffer stringBuffer = new StringBuffer();
                if (list == null || list.size() <= 0) {
                    httpGet = new HttpGet(str);
                } else {
                    int i = 0;
                    for (NameValuePair nameValuePair : list) {
                        int i2 = i + 1;
                        if (i > 0) {
                            stringBuffer.append("&");
                        }
                        stringBuffer.append(nameValuePair.getName()).append("=").append(nameValuePair.getValue());
                        i = i2;
                    }
                    str = str + "?" + stringBuffer.toString();
                    httpGet = new HttpGet(str);
                }
                HttpResponse execute = defaultHttpClient.execute(httpGet);
                if (execute.getStatusLine().getStatusCode() == 200) {
                    C1923L.debug(HttpClientUtil.class, "get url=[%s] is ok", str);
                    inputStream = execute.getEntity().getContent();
                    C1923L.debug(HttpClientUtil.class, "the result is %s", inputStream2String(inputStream));
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                    if (defaultHttpClient != null) {
                        defaultHttpClient.getConnectionManager().shutdown();
                    }
                    return r0;
                }
                C1923L.warn(HttpClientUtil.class, "http get [%s] error! status:%d", str, Integer.valueOf(execute.getStatusLine().getStatusCode()));
                throw new RuntimeException("http get error! statuscode:" + execute.getStatusLine().getStatusCode());
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                    }
                }
                if (defaultHttpClient != null) {
                    defaultHttpClient.getConnectionManager().shutdown();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            Object obj = inputStream;
            if (inputStream != null) {
                inputStream.close();
            }
            if (defaultHttpClient != null) {
                defaultHttpClient.getConnectionManager().shutdown();
            }
            throw th;
        }
    }

    private static String inputStream2String(InputStream inputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                try {
                    int read = inputStream.read();
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(read);
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            String byteArrayOutputStream2 = byteArrayOutputStream.toString();
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            return byteArrayOutputStream2;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            throw th;
        }
    }

    public static String post(String str, List<NameValuePair> list) throws Throwable {
        List arrayList = new ArrayList();
        DefaultHttpClient defaultHttpClient = null;
        if (list == null) {
            try {
                arrayList = new ArrayList();
            } catch (Throwable th) {
                Throwable th2 = th;
                if (defaultHttpClient != null) {
                    defaultHttpClient.getConnectionManager().shutdown();
                }
                throw th2;
            }
        }
        HttpPost httpPost = new HttpPost(str);
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
        DefaultHttpClient defaultHttpClient2 = new DefaultHttpClient();
        try {
            HttpResponse execute = defaultHttpClient2.execute(httpPost);
            if (execute.getStatusLine().getStatusCode() == 200) {
                String entityUtils = EntityUtils.toString(execute.getEntity());
                if (defaultHttpClient2 != null) {
                    defaultHttpClient2.getConnectionManager().shutdown();
                }
                return entityUtils;
            }
            throw new RuntimeException("http post error! statuscode:" + execute.getStatusLine().getStatusCode());
        } catch (Throwable th3) {
            th2 = th3;
            defaultHttpClient = defaultHttpClient2;
            if (defaultHttpClient != null) {
                defaultHttpClient.getConnectionManager().shutdown();
            }
            throw th2;
        }
    }
}
