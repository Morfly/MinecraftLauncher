package fb;

import android.content.Context;

import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.Store;
import com.umeng.fb.model.UserReply;
import com.umeng.fb.model.UserTitleReply;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class C2342r {
    public static final String f6266a = "http://feedback.umeng.com/feedback";
    public static final String f6267b = "http://feedback.umeng.com/feedback/reply";
    public static final String f6268c = "http://feedback.umeng.com/feedback/reply";
    public static final String f6269d = "http://feedback.umeng.com/feedback/feedbacks";
    private static final String f6270e = C2342r.class.getName();
    private static final int f6271g = 30000;
    private Context f6272f;

    public C2342r(Context context) {
        this.f6272f = context;
    }

    private void m10369a(JSONObject jSONObject) {
        try {
            JSONObject b = C2350z.m10410b(this.f6272f);
            C2325b.m10289c(f6270e, "addRequestHeader: " + b.toString());
            Iterator keys = b.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                jSONObject.put(str, b.get(str));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean m10370a(UserReply userReply) {
        try {
            JSONObject toJson = userReply.toJson();
            m10369a(toJson);
            m10372b(toJson);
            C2344t a = m10374a(new C2343s("reply", toJson, "http://feedback.umeng.com/feedback/reply"));
            if (a != null && "ok".equalsIgnoreCase(a.m10379a().get("state").toString())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean m10371a(UserTitleReply userTitleReply) {
        try {
            JSONObject toJson = userTitleReply.toJson();
            m10369a(toJson);
            m10372b(toJson);
            C2344t a = m10374a(new C2343s("feedback", toJson, f6269d));
            if (a != null && "ok".equalsIgnoreCase(a.m10379a().get("state").toString())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void m10372b(JSONObject jSONObject) {
        try {
            long userInfoLastSyncAt = Store.getInstance(this.f6272f).getUserInfoLastSyncAt();
            long userInfoLastUpdateAt = Store.getInstance(this.f6272f).getUserInfoLastUpdateAt();
            C2325b.m10289c(f6270e, "addUserInfoIfNotSynced: last_sync_at=" + userInfoLastSyncAt + " last_update_at=" + userInfoLastUpdateAt);
            if (userInfoLastSyncAt < userInfoLastUpdateAt) {
                jSONObject.put("userinfo", Store.getInstance(this.f6272f).getUserInfo().toJson());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<DevReply> m10373a(List<String> list, String str, String str2) {
        if (list == null || list.size() == 0 || C2340p.m10364d(str2)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str3 : list) {
            String str32 = "";
            if (!C2340p.m10364d(str32)) {
                stringBuilder.append(str32);
                stringBuilder.append(",");
            }
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        }
        StringBuilder stringBuilder2 = new StringBuilder("http://feedback.umeng.com/feedback/reply");
        stringBuilder2.append("?appkey=" + str2);
        stringBuilder2.append("&feedback_id=" + stringBuilder);
        if (!C2340p.m10364d(str)) {
            stringBuilder2.append("&startkey=" + str);
        }
        C2325b.m10289c(f6270e, "getDevReply url: " + stringBuilder2);
        HttpClient defaultHttpClient = new DefaultHttpClient();
        HttpParams params = defaultHttpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        ConnManagerParams.setTimeout(params, 30000);
        try {
            HttpResponse execute = defaultHttpClient.execute(new HttpGet(stringBuilder2.toString()));
            if (execute.getStatusLine().getStatusCode() == 200) {
                String str32 = EntityUtils.toString(execute.getEntity());
                C2325b.m10289c(f6270e, "getDevReply resp: " + str32);
                JSONArray jSONArray = new JSONArray(str32);
                List<DevReply> arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                            try {
                                arrayList.add(new DevReply(jSONArray2.getJSONObject(i2)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                return arrayList;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public C2344t m10374a(C2343s c2343s) {
        int nextInt = new Random().nextInt(1000);
        String str = c2343s.f6275f;
        String str2 = c2343s.f6273d;
        JSONObject jSONObject = c2343s.f6274e;
        if (!(c2343s instanceof C2343s)) {
            C2325b.m10287b(f6270e, "request type error, request must be type of FbReportRequest");
            return null;
        } else if (str.length() <= 1) {
            C2325b.m10287b(f6270e, new StringBuilder(String.valueOf(nextInt)).append(":\tInvalid baseUrl.").toString());
            return null;
        } else {
            HttpGet httpGet;
            if (str2 != null) {
                C2325b.m10285a(f6270e, new StringBuilder(String.valueOf(nextInt)).append(": post: ").append(str).append(" ").append(jSONObject.toString()).toString());
                List arrayList = new ArrayList(1);
                arrayList.add(new BasicNameValuePair(str2, jSONObject.toString()));
                try {
                    HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
                    HttpRequest httpPost = new HttpPost(str);
                    httpPost.addHeader(urlEncodedFormEntity.getContentType());
                    ((HttpPost) httpPost).setEntity(urlEncodedFormEntity);
                    httpGet = (HttpGet) httpPost;
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
            }
            C2325b.m10285a(f6270e, new StringBuilder(String.valueOf(nextInt)).append(":\tget: ").append(str).toString());
            httpGet = new HttpGet(str);
            HttpClient defaultHttpClient = new DefaultHttpClient();
            HttpParams params = defaultHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            ConnManagerParams.setTimeout(params, 30000);
            try {
                HttpResponse execute = defaultHttpClient.execute(httpGet);
                if (execute.getStatusLine().getStatusCode() != 200) {
                    return null;
                }
                String entityUtils = EntityUtils.toString(execute.getEntity());
                C2325b.m10285a(f6270e, "res :" + entityUtils);
                return new C2344t(new JSONObject(entityUtils));
            } catch (Exception e2) {
                C2325b.m10290c(f6270e, new StringBuilder(String.valueOf(nextInt)).append(":\tClientProtocolException,Failed to send message.").append(str).toString(), e2);
                return null;
            }
        }
    }

    public boolean m10375a(Reply reply) {
        if (reply == null) {
            return true;
        }
        if (reply instanceof UserReply) {
            return m10370a((UserReply) reply);
        }
        if (reply instanceof UserTitleReply) {
            return m10371a((UserTitleReply) reply);
        }
        throw new IllegalArgumentException("Illegal argument: " + reply.getClass().getName() + ". reply must be " + UserReply.class.getName() + " or " + UserTitleReply.class.getName() + ".");
    }
}
