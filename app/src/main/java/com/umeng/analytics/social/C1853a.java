package com.umeng.analytics.social;

public class C1853a extends RuntimeException {
    private static final long f3824b = -4656673116019167471L;
    protected int f3825a = 5000;
    private String f3826c = "";

    public C1853a(int i, String str) {
        super(str);
        this.f3825a = i;
        this.f3826c = str;
    }

    public C1853a(String str) {
        super(str);
        this.f3826c = str;
    }

    public C1853a(String str, Throwable th) {
        super(str, th);
        this.f3826c = str;
    }

    public int m5919a() {
        return this.f3825a;
    }

    public String getMessage() {
        return this.f3826c;
    }
}
