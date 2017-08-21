package com.yy.hiidostatis.inner.implementation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TaskExecutor extends AbstractTaskExecutor {
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor(new C19081());
    private final OnTaskRejectedListener mOnTaskRejectedListener;

    class C19081 implements ThreadFactory {
        C19081() {
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("Statis_SDK_Worker");
            thread.setPriority(1);
            return thread;
        }
    }

    TaskExecutor(OnTaskRejectedListener onTaskRejectedListener) {
        this.mOnTaskRejectedListener = onTaskRejectedListener;
    }

    public ExecutorService getExecutor() {
        return this.mExecutor;
    }

    public OnTaskRejectedListener getOnTaskRejectedListener() {
        return this.mOnTaskRejectedListener;
    }
}
