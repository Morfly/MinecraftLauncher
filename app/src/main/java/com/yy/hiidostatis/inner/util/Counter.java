package com.yy.hiidostatis.inner.util;

import android.os.Handler;

import com.yy.hiidostatis.inner.util.log.C1923L;

public class Counter implements Runnable {
    private final Callback NONE = new C19131();
    private final long INTERVAL;
    private final int STEP;
    private int counter;
    private Callback mCallback = NONE;
    private final Handler mHandler;
    private boolean mRunning = false;

    public interface Callback {
        void onCount(int i);
    }

    final class C19131 implements Callback {
        C19131() {
        }

        public void onCount(int i) {
        }
    }

    public Counter(Handler handler, int i, long j, boolean z) {
        this.mHandler = handler;
        this.counter = i;
        this.INTERVAL = j;
        this.STEP = z ? 1 : -1;
        C1923L.verbose(this, "create counter, from %d, interval %d, step %d", Integer.valueOf(this.counter), Long.valueOf(this.INTERVAL), Integer.valueOf(this.STEP));
    }

    public int count() {
        return this.counter;
    }

    public long getInterval() {
        return this.INTERVAL;
    }

    public Counter reset() {
        return setCounter(0);
    }

    public void run() {
        C1923L.verbose(this, "counter run ,hashCode =[%d],mRunning = %b", Integer.valueOf(hashCode()), Boolean.valueOf(this.mRunning));
        if (this.mRunning) {
            this.mCallback.onCount(this.counter);
            this.counter += this.STEP;
            this.mHandler.postDelayed(this, this.INTERVAL);
        }
    }

    public boolean running() {
        return this.mRunning;
    }

    public void setCallback(Callback callback) {
        if (callback == null) {
            callback = NONE;
        }
        this.mCallback = callback;
    }

    public Counter setCounter(int i) {
        this.counter = i;
        C1923L.verbose(this, "set to %d", Integer.valueOf(i));
        return this;
    }

    public Counter start(long j) {
        this.mHandler.removeCallbacks(this);
        this.mRunning = true;
        this.mHandler.postDelayed(this, j);
        C1923L.verbose(this, "counter start,hashCode =[%d],mRunning = %b", Integer.valueOf(hashCode()), Boolean.valueOf(this.mRunning));
        return this;
    }

    public Counter stop() {
        this.mHandler.removeCallbacks(this);
        this.mRunning = false;
        C1923L.verbose(this, "counter stop ,hashCode =[%d],mRunning = %b", Integer.valueOf(hashCode()), Boolean.valueOf(this.mRunning));
        return this;
    }

    public Counter toggle(boolean z) {
        return z ? start(0) : stop();
    }
}
