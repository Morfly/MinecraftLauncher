package com.mcbox.pesdk.launcher;

import android.graphics.Bitmap;

public interface LauncherEventListener {
    void onCloseGame();

    void onFreeMap(String str);

    void onLoadMap(String str);

    void onLoadNetMap();

    void onNetConnect();

    void onScreenShotSucceed(Bitmap bitmap, String str);

    void onStartGame();
}
