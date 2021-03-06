package com.yy.hiidostatis.inner.util.log;

public interface IBaseStatisLogWriter {
    public static final int TYPE_DEBUG = 1;
    public static final int TYPE_ERROR = 4;
    public static final int TYPE_INFO = 2;
    public static final int TYPE_WARN = 3;

    boolean outputDebug();

    void write(int i, String str);
}
