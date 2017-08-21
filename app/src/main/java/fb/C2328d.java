package fb;

import fb.C2332f.C2330a;

public class C2328d extends C2327i {
    private static final String f6251a = C2328d.class.getName();

    public C2330a m10312a(C2335h c2335h) {
        C2332f c2332f = (C2332f) m10310a((C2334j) c2335h, C2332f.class);
        return c2332f == null ? C2330a.FAIL : c2332f.f6255a;
    }

    public void m10313a(C2335h c2335h, C2329e c2329e) {
        try {
            new C2333g(this, c2335h, c2329e).execute(new Integer[0]);
        } catch (Exception e) {
            C2325b.m10288b(f6251a, "", e);
            if (c2329e != null) {
                c2329e.m10315a(C2330a.FAIL);
            }
        }
    }
}
