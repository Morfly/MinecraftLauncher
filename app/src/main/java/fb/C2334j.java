package fb;

import org.json.JSONObject;

public abstract class C2334j {
    protected static String f6259a = "POST";
    protected static String f6260b = "GET";
    protected String f6261c;

    public C2334j(String str) {
        this.f6261c = str;
    }

    public abstract JSONObject mo3520a();

    public void m10319a(String str) {
        this.f6261c = str;
    }

    public abstract String mo3521b();

    protected String mo3522c() {
        return f6259a;
    }

    public String m10322d() {
        return this.f6261c;
    }
}
