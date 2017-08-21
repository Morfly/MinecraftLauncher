package com.yy.hiidostatis.inner.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
    private static ThreadPool pool;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();

    private ThreadPool() {
    }

    public static ThreadPool getPool() {
        if (pool == null) {
            synchronized (ThreadPool.class) {
                if (pool == null) {
                    pool = new ThreadPool();
                }
            }
        }
        return pool;
    }

    public void execute(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void executeQueue(Runnable runnable) {
        this.singleExecutorService.execute(runnable);
    }

    public void shutdown() {
        this.executorService.shutdown();
        this.singleExecutorService.shutdown();
    }

    public void shutdownNow() {
        this.executorService.shutdownNow();
        this.singleExecutorService.shutdownNow();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return this.executorService.submit(callable);
    }

    public <T> Future<T> submitQueue(Callable<T> callable) {
        return this.singleExecutorService.submit(callable);
    }
}
