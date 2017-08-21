package com.umeng.analytics.social;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.C1823a;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class UMSocialService {

    static class C1851a extends AsyncTask<Void, Void, C1856d> {
        String f3820a;
        String f3821b;
        C1852b f3822c;
        UMPlatformData[] f3823d;

        public C1851a(String[] strArr, C1852b c1852b, UMPlatformData[] uMPlatformDataArr) {
            this.f3820a = strArr[0];
            this.f3821b = strArr[1];
            this.f3822c = c1852b;
            this.f3823d = uMPlatformDataArr;
        }

        protected C1856d m5914a(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(TextUtils.isEmpty(this.f3821b) ? C1855c.m5931a(this.f3820a) : C1855c.m5932a(this.f3820a, this.f3821b));
                int optInt = jSONObject.optInt("st");
                C1856d c1856d = new C1856d(optInt == 0 ? C1857e.f3850t : optInt);
                String optString = jSONObject.optString("msg");
                if (!TextUtils.isEmpty(optString)) {
                    c1856d.m5935a(optString);
                }
                Object optString2 = jSONObject.optString("data");
                if (TextUtils.isEmpty((CharSequence) optString2)) {
                    return c1856d;
                }
                c1856d.m5937b((String) optString2);
                return c1856d;
            } catch (Exception e) {
                return new C1856d(-99, e);
            }
        }

        protected void m5915a(C1856d c1856d) {
            if (this.f3822c != null) {
                this.f3822c.m5917a(c1856d, this.f3823d);
            }
        }

        /*protected /* synthetic */ /*Object doInBackground(Object[] objArr) {
            return m5914a((Void[]) objArr);
        }

        protected /* synthetic */ /*void onPostExecute(Object obj) {
            m5915a((C1856d) obj);
        }*/

        @Override
        protected C1856d doInBackground(Void... params) {
            return null;
        }

        protected void onPreExecute() {
            if (this.f3822c != null) {
                this.f3822c.m5916a();
            }
        }
    }

    public interface C1852b {
        void m5916a();

        void m5917a(C1856d c1856d, UMPlatformData... uMPlatformDataArr);
    }

    private static void m5918a(Context context, C1852b c1852b, String str, UMPlatformData... uMPlatformDataArr) throws JSONException {
        int i = 0;
        if (uMPlatformDataArr != null) {
            try {
                int length = uMPlatformDataArr.length;
                while (i < length) {
                    if (uMPlatformDataArr[i].isValid()) {
                        i++;
                    } else {
                        throw new C1853a("parameter is not valid.");
                    }
                }
            } catch (Throwable e) {
                Log.e(C1823a.f3725e, "unable send event.", e);
                return;
            }
        }
        new C1851a(C1858f.m5944a(context, str, uMPlatformDataArr), c1852b, uMPlatformDataArr).execute(new Void[0]);
    }

    public static void share(Context context, String str, UMPlatformData... uMPlatformDataArr) throws JSONException {
        m5918a(context, null, str, uMPlatformDataArr);
    }

    public static void share(Context context, UMPlatformData... uMPlatformDataArr) throws JSONException {
        m5918a(context, null, null, uMPlatformDataArr);
    }
}
