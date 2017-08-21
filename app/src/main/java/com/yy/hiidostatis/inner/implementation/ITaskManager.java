package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

public interface ITaskManager {
    void enableSend(boolean z);

    void flush(Context context);

    void send(Context context, String str);

    void send(Context context, String str, Long l);

    void shutDownNow();

    void storePendingCommands(Context context, boolean z);
}
