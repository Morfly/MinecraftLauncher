package com.umeng.analytics.social;

import android.text.TextUtils;

import com.umeng.analytics.C1823a;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public abstract class C1855c {
    private static String m5930a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine + "\n");
            } catch (Exception e) {
                stringBuilder = null;
                C1854b.m5923b(String.valueOf(stringBuilder), "Caught IOException in convertStreamToString()", e);
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    C1854b.m5923b(C1823a.f3725e, "Caught IOException in convertStreamToString()", e2);
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    protected static String m5931a(String str) {
        int nextInt = new Random().nextInt(1000);
        try {
            String property = System.getProperty("line.separator");
            if (str.length() <= 1) {
                C1854b.m5922b(C1823a.f3725e, nextInt + ":\tInvalid baseUrl.");
                return null;
            }
            HttpUriRequest httpGet = new HttpGet(str);
            C1854b.m5920a(C1823a.f3725e, nextInt + ": GET_URL: " + str);
            HttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
            HttpResponse execute = new DefaultHttpClient(basicHttpParams).execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = execute.getEntity();
                if (entity == null) {
                    return null;
                }
                InputStream gZIPInputStream;
                InputStream content = entity.getContent();
                Header firstHeader = execute.getFirstHeader("Content-Encoding");
                if (firstHeader != null && firstHeader.getValue().equalsIgnoreCase("gzip")) {
                    C1854b.m5920a(C1823a.f3725e, nextInt + "  Use GZIPInputStream get data....");
                    gZIPInputStream = new GZIPInputStream(content);
                } else if (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("deflate")) {
                    gZIPInputStream = content;
                } else {
                    C1854b.m5920a(C1823a.f3725e, nextInt + "  Use InflaterInputStream get data....");
                    gZIPInputStream = new InflaterInputStream(content);
                }
                String a = C1855c.m5930a(gZIPInputStream);
                C1854b.m5920a(C1823a.f3725e, nextInt + ":\tresponse: " + property + a);
                return a != null ? a : null;
            } else {
                C1854b.m5920a(C1823a.f3725e, nextInt + ":\tFailed to get message." + str);
                return null;
            }
        } catch (Exception e) {
            C1854b.m5925c(C1823a.f3725e, nextInt + ":\tClientProtocolException,Failed to send message." + str, e);
            return null;
        }
    }

    protected static String m5932a(String str, String str2) {
        int nextInt = new Random().nextInt(1000);
        String property = System.getProperty("line.separator");
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
        HttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
        C1854b.m5920a(C1823a.f3725e, nextInt + ": POST_URL: " + str);
        try {
            HttpPost httpPost = new HttpPost(str);
            if (!TextUtils.isEmpty(str2)) {
                C1854b.m5920a(C1823a.f3725e, nextInt + ": POST_BODY: " + str2);
                List arrayList = new ArrayList(1);
                arrayList.add(new BasicNameValuePair("data", str2));
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
            }
            HttpResponse execute = defaultHttpClient.execute(httpPost);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = execute.getEntity();
                if (entity == null) {
                    return null;
                }
                InputStream content = entity.getContent();
                Header firstHeader = execute.getFirstHeader("Content-Encoding");
                InputStream inflaterInputStream = (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("deflate")) ? content : new InflaterInputStream(content);
                String a = C1855c.m5930a(inflaterInputStream);
                C1854b.m5920a(C1823a.f3725e, nextInt + ":\tresponse: " + property + a);
                return a == null ? null : a;
            } else {
                C1854b.m5924c(C1823a.f3725e, nextInt + ":\tFailed to send message." + str);
                return null;
            }
        } catch (Exception e) {
            C1854b.m5925c(C1823a.f3725e, nextInt + ":\tClientProtocolException,Failed to send message." + str, e);
            return null;
        }
    }
}
