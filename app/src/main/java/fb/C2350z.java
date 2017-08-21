package fb;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;

import com.umeng.analytics.onlineconfig.C1837a;
import com.umeng.fb.model.Constants;

import org.json.JSONObject;

public class C2350z {
    private static final String f6277a = C2350z.class.getName();

    public static String m10408a() {
        return "RP" + String.valueOf(System.currentTimeMillis()) + String.valueOf((int) (1000.0d + (Math.random() * 9000.0d)));
    }

    public static String m10409a(Context context) {
        return "FB[" + C2324a.m10275o(context) + "_" + C2324a.m10267g(context) + "]" + String.valueOf(System.currentTimeMillis()) + String.valueOf((int) (1000.0d + (Math.random() * 9000.0d)));
    }

    public static JSONObject m10410b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("device_id", C2324a.m10266f(context));
            jSONObject.put(C1837a.f3793d, C2324a.m10267g(context));
            jSONObject.put("device_model", Build.MODEL);
            jSONObject.put("appkey", C2324a.m10275o(context));
            jSONObject.put(C1837a.f3792c, C2324a.m10280t(context));
            jSONObject.put("app_version", C2324a.m10264d(context));
            jSONObject.put(C1837a.f3794e, C2324a.m10263c(context));
            jSONObject.put("sdk_type", "Android");
            jSONObject.put(C1837a.f3796g, Constants.SDK_VERSION);
            jSONObject.put("os", "Android");
            jSONObject.put("os_version", VERSION.RELEASE);
            jSONObject.put("country", C2324a.m10274n(context)[0]);
            jSONObject.put("language", C2324a.m10274n(context)[1]);
            jSONObject.put("timezone", C2324a.m10273m(context));
            jSONObject.put("resolution", C2324a.m10277q(context));
            jSONObject.put("access", C2324a.m10270j(context)[0]);
            jSONObject.put("access_subtype", C2324a.m10270j(context)[1]);
            jSONObject.put("carrier", C2324a.m10268h(context));
            jSONObject.put("cpu", C2324a.m10253a());
            jSONObject.put(C1837a.f3791b, C2324a.m10281u(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
