package com.umeng.analytics.social;


public class C1856d {
    private int f3827a = -1;
    private String f3828b = "";
    private String f3829c = "";
    private Exception f3830d = null;

    public C1856d(int i) {
        this.f3827a = i;
    }

    public C1856d(int i, Exception exception) {
        this.f3827a = i;
        this.f3830d = exception;
    }

    public Exception m5933a() {
        return this.f3830d;
    }

    public void m5934a(int i) {
        this.f3827a = i;
    }

    public void m5935a(String str) {
        this.f3828b = str;
    }

    public int m5936b() {
        return this.f3827a;
    }

    public void m5937b(String str) {
        this.f3829c = str;
    }

    public String m5938c() {
        return this.f3828b;
    }

    public String m5939d() {
        return this.f3829c;
    }

    public String toString() {
        return "status=" + this.f3827a + "\r\n" + "msg:  " + this.f3828b + "\r\n" + "data:  " + this.f3829c;
    }
}
