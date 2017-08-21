package com.umeng.analytics;

public abstract class C1825e implements Runnable {


    public C1825e(C1832c c1832c) {
    }

    public abstract void mo2907a();

    public void run() {
        try {
            mo2907a();
        } catch (Throwable th) {
            if (th != null) {
                th.printStackTrace();
            }
        }
    }
}
