package com.umeng.analytics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class C1833d {
    private static ExecutorService f3762a = Executors.newSingleThreadExecutor();
    private static long f3763b = 5;
    private static ExecutorService f3764c = Executors.newSingleThreadExecutor();

    public static void m5855a() {
        try {
            if (!f3762a.isShutdown()) {
                f3762a.shutdown();
            }
            if (!f3764c.isShutdown()) {
                f3764c.shutdown();
            }
            f3762a.awaitTermination(f3763b, TimeUnit.SECONDS);
            f3764c.awaitTermination(f3763b, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public static void m5856a(Runnable runnable) {
        if (f3762a.isShutdown()) {
            f3762a = Executors.newSingleThreadExecutor();
        }
        f3762a.execute(runnable);
    }

    public static void m5857b(Runnable runnable) {
        if (f3764c.isShutdown()) {
            f3764c = Executors.newSingleThreadExecutor();
        }
        f3764c.execute(runnable);
    }
}
