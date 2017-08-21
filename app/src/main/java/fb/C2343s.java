package fb;

import org.json.JSONObject;

public class C2343s extends C2334j {
    public String f6273d;
    public JSONObject f6274e;
    public String f6275f;
    private String a;

    public C2343s(String str) {
        super(str);
        this.f6275f = str;
    }

    public C2343s(String str, JSONObject jSONObject, String str2) {
        super(str2);
        this.f6273d = str;
        this.f6274e = jSONObject;
        this.f6275f = str2;
    }

    public JSONObject mo3520a() {
        return this.f6274e;
    }

    public String mo3521b() {
        return this.f6275f;
    }

    public String mo3522c() {
        return a;
    }
}
