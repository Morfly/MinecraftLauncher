package fb;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class C2327i {
    private static final String f6249a = C2327i.class.getName();
    private Map<String, String> f6250b;

    private static String m10304a(InputStream inputStream) throws IOException {
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
                C2325b.m10288b(String.valueOf(stringBuilder), "Caught IOException in convertStreamToString()", e);
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    C2325b.m10288b(f6249a, "Caught IOException in convertStreamToString()", e2);
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    private JSONObject m10305a(String str) {
        int nextInt = new Random().nextInt(1000);
        try {
            String property = System.getProperty("line.separator");
            if (str.length() <= 1) {
                C2325b.m10287b(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tInvalid baseUrl.").toString());
                return null;
            }
            C2325b.m10285a(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tget: ").append(str).toString());
            HttpUriRequest httpGet = new HttpGet(str);
            if (this.f6250b != null && this.f6250b.size() > 0) {
                for (String str2 : this.f6250b.keySet()) {
                    httpGet.addHeader(str2, (String) this.f6250b.get(str2));
                }
            }
            HttpResponse execute = new DefaultHttpClient(m10307b()).execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = execute.getEntity();
                if (entity != null) {
                    InputStream inflaterInputStream;
                    InputStream content = entity.getContent();
                    Header firstHeader = execute.getFirstHeader("Content-Encoding");
                    if (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("gzip")) {
                        if (firstHeader != null) {
                            if (firstHeader.getValue().equalsIgnoreCase("deflate")) {
                                C2325b.m10285a(f6249a, new StringBuilder(String.valueOf(nextInt)).append("  Use InflaterInputStream get data....").toString());
                                inflaterInputStream = new InflaterInputStream(content);
                            }
                        }
                        inflaterInputStream = content;
                    } else {
                        C2325b.m10285a(f6249a, new StringBuilder(String.valueOf(nextInt)).append("  Use GZIPInputStream get data....").toString());
                        inflaterInputStream = new GZIPInputStream(content);
                    }
                    String a = C2327i.m10304a(inflaterInputStream);
                    C2325b.m10285a(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tresponse: ").append(property).append(a).toString());
                    return a == null ? null : new JSONObject(a);
                }
            } else {
                C2325b.m10289c(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tFailed to send message. StatusCode = ").append(execute.getStatusLine().getStatusCode()).append(C2340p.f6263a).append(str).toString());
            }
            return null;
        } catch (Exception e) {
            C2325b.m10290c(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tClientProtocolException,Failed to send message.").append(str).toString(), e);
            return null;
        }
    }

    private JSONObject m10306a(String str, JSONObject jSONObject) {
        String jSONObject2 = jSONObject.toString();
        int nextInt = new Random().nextInt(1000);
        C2325b.m10289c(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\trequest: ").append(str).append(C2340p.f6263a).append(jSONObject2).toString());
        HttpPost httpPost = new HttpPost(str);
        HttpClient defaultHttpClient = new DefaultHttpClient(m10307b());
        try {
            if (m10311a()) {
                byte[] a = C2339o.m10346a("content=" + jSONObject2, Charset.defaultCharset().toString());
                httpPost.addHeader("Content-Encoding", "deflate");
                httpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(a), (long) a.length));
            } else {
                List arrayList = new ArrayList(1);
                arrayList.add(new BasicNameValuePair("content", jSONObject2));
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
                String a2 = C2327i.m10304a(inflaterInputStream);
                C2325b.m10285a(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tresponse: ").append(C2340p.f6263a).append(a2).toString());
                return a2 == null ? null : new JSONObject(a2);
            } else {
                C2325b.m10289c(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tFailed to send message. StatusCode = ").append(execute.getStatusLine().getStatusCode()).append(C2340p.f6263a).append(str).toString());
                return null;
            }
        } catch (Exception e) {
            C2325b.m10290c(f6249a, new StringBuilder(String.valueOf(nextInt)).append(":\tClientProtocolException,Failed to send message.").append(str).toString(), e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return jSONObject;
    }

    private HttpParams m10307b() {
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
        HttpProtocolParams.setUserAgent(basicHttpParams, System.getProperty("http.agent"));
        return basicHttpParams;
    }

    private void m10308b(String str) {
        if (C2340p.m10364d(str)) {
            throw new RuntimeException("验证请求方式失败[" + str + "]");
        }
    }

    public C2327i m10309a(Map<String, String> map) {
        this.f6250b = map;
        return this;
    }

    public <T extends C2331k> T m10310a(C2334j c2334j, Class<T> cls) {
        String trim = c2334j.mo3522c().trim();
        m10308b(trim);
        JSONObject a = C2334j.f6260b.equals(trim) ? m10305a(c2334j.mo3521b()) : C2334j.f6259a.equals(trim) ? m10306a(c2334j.f6261c, c2334j.mo3520a()) : null;
        if (a == null) {
            return null;
        }
        try {
            return (T) cls.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{a});
        } catch (Exception e) {
            C2325b.m10288b(f6249a, "SecurityException", e);
            return null;
        }
    }

    public boolean m10311a() {
        return false;
    }
}
