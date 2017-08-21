package fb;

import org.json.JSONObject;

public class C2332f extends C2331k {
    public C2330a f6255a;

    public enum C2330a {
        SUCCESS,
        FAIL
    }

    public C2332f(JSONObject jSONObject) {
        super(jSONObject);
        if ("ok".equalsIgnoreCase(jSONObject.optString("status")) || "ok".equalsIgnoreCase(jSONObject.optString("success"))) {
            this.f6255a = C2330a.SUCCESS;
        } else {
            this.f6255a = C2330a.FAIL;
        }
    }
}
