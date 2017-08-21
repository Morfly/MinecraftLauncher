package com.yy.hiidostatis.defs.controller;

import com.yy.hiidostatis.defs.interf.IOnStatisListener;
import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashController {
    private CrashHandler mCrashHandler;
    private OnCrashListener mOnCrashListener;
    private IOnStatisListener mOnStatisListener;
    private IStatisAPI mStatisAPI;

    public interface OnCrashListener {
        void handler();
    }

    class CrashHandler implements UncaughtExceptionHandler {
        private UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        public CrashHandler() {
            String str = "null";
            if (this.mDefaultHandler != null) {
                str = this.mDefaultHandler.getClass().getSimpleName();
            }
            C1923L.debug(this, "old DefaultUncaughtExceptionHandler is %s,new DefaultUncaughtExceptionHandler is %s", str, getClass().getSimpleName());
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

        private void handleException(final Throwable th) {
            ThreadPool.getPool().execute(new Thread() {
                public void run() {
                    CrashController.this.mStatisAPI.reportCrash(CrashController.this.mOnStatisListener.getCurrentUid(), th);
                }
            });
        }

        public void uncaughtException(Thread thread, Throwable th) {
            try {
                C1923L.warn(this, "crash occur crashMsg=[%s]", th);
                handleException(th);
                if (CrashController.this.mOnCrashListener != null) {
                    CrashController.this.mOnCrashListener.handler();
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                C1923L.error(this, "deal crash uncaughtException happen another exception=%s", e);
            }
            if (this.mDefaultHandler != null) {
                this.mDefaultHandler.uncaughtException(thread, th);
            }
        }
    }

    public CrashController(IStatisAPI iStatisAPI, IOnStatisListener iOnStatisListener, OnCrashListener onCrashListener) {
        this.mStatisAPI = iStatisAPI;
        this.mOnStatisListener = iOnStatisListener;
        this.mOnCrashListener = onCrashListener;
    }

    public void startCrashMonitor() {
        if (this.mCrashHandler != null) {
            C1923L.warn(this, "crash monitor has been started.", new Object[0]);
            return;
        }
        this.mCrashHandler = new CrashHandler();
        C1923L.info(this, "crash monitor start", new Object[0]);
    }
}
