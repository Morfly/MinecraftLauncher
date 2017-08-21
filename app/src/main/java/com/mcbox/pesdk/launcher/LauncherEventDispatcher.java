package com.mcbox.pesdk.launcher;

import android.graphics.Bitmap;

public class LauncherEventDispatcher {
    private static LauncherEventDispatcher instance = new LauncherEventDispatcher();
    private LauncherEventListener listener;

    private LauncherEventDispatcher() {
    }

    public static LauncherEventDispatcher getInstance() {
        return instance;
    }

    public void closeGame() {
        if (this.listener != null) {
            this.listener.onCloseGame();
        }
    }

    public void freeMap(String str) {
        if (this.listener != null) {
            this.listener.onFreeMap(str);
        }
    }

    public void loadMap(String str) {
        if (this.listener != null) {
            this.listener.onLoadMap(str);
        }
    }

    public void loadNetMap() {
        if (this.listener != null) {
            this.listener.onLoadNetMap();
        }
    }

    public void netConnect() {
        if (this.listener != null) {
            this.listener.onNetConnect();
        }
    }

    public void screenShotSucceed(Bitmap bitmap, String str) {
        if (this.listener != null) {
            this.listener.onScreenShotSucceed(bitmap, str);
        }
    }

    public void setListener(LauncherEventListener launcherEventListener) {
        this.listener = launcherEventListener;
    }

    public void startGame() {
        if (this.listener != null) {
            this.listener.onStartGame();
        }
    }
}
