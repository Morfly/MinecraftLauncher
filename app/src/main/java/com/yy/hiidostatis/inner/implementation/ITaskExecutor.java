package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

public interface ITaskExecutor {

    public abstract class ExecutorTask implements Runnable {
        private Context mContext;
        private TaskData mData;

        public ExecutorTask(Context context, TaskData taskData) {
            this.mContext = context;
            this.mData = taskData;
        }

        public ExecutorTask(Context context, String str) {
            this.mContext = context;
            TaskData taskData = new TaskData();
            taskData.setContent(str);
            this.mData = taskData;
        }

        public String getContent() {
            return this.mData == null ? null : this.mData.getContent();
        }

        public Context getContext() {
            return this.mContext;
        }

        public TaskData getData() {
            return this.mData;
        }
    }

    public interface OnTaskRejectedListener {
        void onRejectedTask(ExecutorTask executorTask);
    }

    void awaitCompleted();

    boolean isTerminated();

    void shutDownNow();

    void submit(ExecutorTask executorTask);

    void submit(Runnable runnable);
}
