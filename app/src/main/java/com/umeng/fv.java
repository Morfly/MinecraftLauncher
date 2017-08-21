package com.umeng;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class fv {
    private static final String f3786a = fv.class.getName();
    private Map<String, String> f3787b;

    private static String m5886a(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            try {
                stringBuilder.append(new StringBuilder(String.valueOf(readLine)).append("\n").toString());
            } catch (Exception e) {
                stringBuilder = null;
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    fp.m9885b(f3786a, "Caught IOException in convertStreamToString()", e2);
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    private JSONObject m5887a(String str) {
        int nextInt = new Random().nextInt(1000);
        try {
            String property = System.getProperty("line.separator");
            if (str.length() <= 1) {
                fp.m9884b(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tInvalid baseUrl.").toString());
                return null;
            }
            fp.m9882a(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tget: ").append(str).toString());
            HttpUriRequest httpGet = new HttpGet(str);
            if (this.f3787b != null && this.f3787b.size() > 0) {
                for (String str2 : this.f3787b.keySet()) {
                    httpGet.addHeader(str2, (String) this.f3787b.get(str2));
                }
            }
            HttpResponse execute = new DefaultHttpClient(m5889b()).execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = execute.getEntity();
                if (entity != null) {
                    InputStream inflaterInputStream;
                    InputStream content = entity.getContent();
                    Header firstHeader = execute.getFirstHeader("Content-Encoding");
                    if (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("gzip")) {
                        if (firstHeader != null) {
                            if (firstHeader.getValue().equalsIgnoreCase("deflate")) {
                                fp.m9882a(f3786a, new StringBuilder(String.valueOf(nextInt)).append("  Use InflaterInputStream get data....").toString());
                                inflaterInputStream = new InflaterInputStream(content);
                            }
                        }
                        inflaterInputStream = content;
                    } else {
                        fp.m9882a(f3786a, new StringBuilder(String.valueOf(nextInt)).append("  Use GZIPInputStream get data....").toString());
                        inflaterInputStream = new GZIPInputStream(content);
                    }
                    String a = fv.m5886a(inflaterInputStream);
                    fp.m9882a(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tresponse: ").append(property).append(a).toString());
                    return a == null ? null : new JSONObject(a);
                }
            } else {
                fp.m9886c(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tFailed to send message. StatusCode = ").append(execute.getStatusLine().getStatusCode()).append(gc.f6069a).append(str).toString());
            }
            return null;
        } catch (Exception e) {
            fp.m9887c(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tClientProtocolException,Failed to send message.").append(str).toString(), e);
            return null;
        }
    }

    public static byte[] m9931a(byte[] bArr) throws Throwable {
        int f6068a;
        Throwable th;
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        Deflater deflater = new Deflater();
        deflater.setInput(bArr);
        deflater.finish();
        byte[] bArr2 = new byte[8192];
        f6068a = 0;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            while (!deflater.finished()) {
                try {
                    int deflate = deflater.deflate(bArr2);
                    f6068a += deflate;
                    byteArrayOutputStream.write(bArr2, 0, deflate);
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            deflater.end();
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            byteArrayOutputStream = null;
            th = th4;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            throw th;
        }
    }

    private JSONObject m5888a(String str, JSONObject jSONObject) {
        String jSONObject2 = jSONObject.toString();
        int nextInt = new Random().nextInt(1000);
        fp.m9886c(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\trequest: ").append(str).append(gc.f6069a).append(jSONObject2).toString());
        HttpPost httpPost = new HttpPost(str);
        HttpClient defaultHttpClient = new DefaultHttpClient(m5889b());
        try {
            if (mo2911a()) {
                byte[] a = gc.m9948d("content=" + jSONObject2) ? null : m9931a(("content=" + jSONObject2).getBytes(Charset.defaultCharset().toString()));
                httpPost.addHeader("Content-Encoding", "deflate");
                httpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(a), (long) a.length));
            } else {
                List arrayList = new ArrayList(1);
                arrayList.add(new BasicNameValuePair("content", jSONObject2));
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
            }
            HttpResponse execute = defaultHttpClient.execute((HttpUriRequest) httpPost);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = execute.getEntity();
                if (entity == null) {
                    return null;
                }
                InputStream content = entity.getContent();
                Header firstHeader = execute.getFirstHeader("Content-Encoding");
                InputStream inflaterInputStream = (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("deflate")) ? content : new InflaterInputStream(content);
                String a2 = fv.m5886a(inflaterInputStream);
                fp.m9882a(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tresponse: ").append(gc.f6069a).append(a2).toString());
                return a2 == null ? null : new JSONObject(a2);
            } else {
                fp.m9886c(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tFailed to send message. StatusCode = ").append(execute.getStatusLine().getStatusCode()).append(gc.f6069a).append(str).toString());
                return null;
            }
        } catch (Exception e) {
            fp.m9887c(f3786a, new StringBuilder(String.valueOf(nextInt)).append(":\tClientProtocolException,Failed to send message.").append(str).toString(), e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return jSONObject;
    }

    private HttpParams m5889b() {
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
        HttpProtocolParams.setUserAgent(basicHttpParams, System.getProperty("http.agent"));
        return basicHttpParams;
    }

    private void m5890b(String str) {
        if (gc.m9948d(str)) {
            throw new RuntimeException("验证请求方式失败[" + str + "]");
        }
    }

    public fv m5891a(Map<String, String> map) {
        this.f3787b = map;
        return this;
    }

    public <T extends fx> T m5892a(fw fwVar, Class<T> cls) {
        String trim = fwVar.m5882c().trim();
        m5890b(trim);
        JSONObject a = fw.f3782c.equals(trim) ? m5887a(fwVar.mo2910b()) : fw.f3781b.equals(trim) ? m5888a(fwVar.f3783d, fwVar.mo2909a()) : null;
        if (a == null) {
            return null;
        }
        try {
            return (T) cls.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{a});
        } catch (Exception e) {
            fp.m9885b(f3786a, "SecurityException", e);
            return null;
        }
    }

    public boolean mo2911a() {
        return false;
    }
}
