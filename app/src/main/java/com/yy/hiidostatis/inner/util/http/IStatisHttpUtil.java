package com.yy.hiidostatis.inner.util.http;

public interface IStatisHttpUtil {

    public interface OnResultListener {
        void onFailed(String str, Object obj, Throwable th);

        void onSuccess(String str, Object obj);
    }

    Throwable getLastError();

    int getLastTryTimes();

    boolean sendSync(String str, Object obj, OnResultListener onResultListener);

    void setTestServer(String str);

    void shutDown();
}
