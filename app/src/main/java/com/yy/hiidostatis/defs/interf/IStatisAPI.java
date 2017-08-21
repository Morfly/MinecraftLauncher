package com.yy.hiidostatis.defs.interf;

import android.content.Context;

import com.yy.hiidostatis.api.StatisOption;

public interface IStatisAPI {
    IStatisAPI create();

    StatisOption getOption();

    String getSession();

    void init(Context context, StatisOption statisOption);

    void reportAppList(long j, String str, String str2) throws Throwable;

    void reportCrash(long j, String str);

    void reportCrash(long j, Throwable th);

    boolean reportDevice(long j) throws Throwable;

    void reportEvent(long j, String str);

    boolean reportInstall(int i);

    void reportLanuch(long j, String str);

    void reportPage(long j, String str);

    void reportSdkList(long j, String str);

    void reportccList(long j, String str, String str2);

    void setSession(String str);
}
