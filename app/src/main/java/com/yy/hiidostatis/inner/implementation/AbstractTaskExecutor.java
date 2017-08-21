package com.yy.hiidostatis.inner.implementation;

import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTaskExecutor implements ITaskExecutor {

    class Task implements Runnable {
        private Runnable mRunnable;

        public Task(Runnable runnable) {
            this.mRunnable = runnable;
        }

        public void run() {
            Runnable runnable = this.mRunnable;
            if (runnable != null) {
                C1923L.brief("Begin run task %s", runnable);
                try {
                    runnable.run();
                } catch (RejectedExecutionException e) {
                    if (runnable instanceof ExecutorTask) {
                        AbstractTaskExecutor.this.getOnTaskRejectedListener().onRejectedTask((ExecutorTask) runnable);
                    }
                } catch (Throwable th) {
                    C1923L.error(this, "Exception when run task %s", th);
                }
                C1923L.brief("End run task.", new Object[0]);
            }
        }
    }

    public void awaitCompleted() {
        try {
            getExecutor().shutdown();
            getExecutor().awaitTermination(10000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract ExecutorService getExecutor();

    public abstract OnTaskRejectedListener getOnTaskRejectedListener();

    public boolean isTerminated() {
        return getExecutor().isShutdown() || getExecutor().isTerminated();
    }

    public void shutDownNow() {
        try {
            getExecutor().shutdownNow();
        } catch (SecurityException e) {
        }
    }

    public void submit(ExecutorTask executorTask) {
        getExecutor().submit(new Task(executorTask));
    }

    public void submit(Runnable runnable) {
        getExecutor().submit(new Task(runnable));
    }
}
