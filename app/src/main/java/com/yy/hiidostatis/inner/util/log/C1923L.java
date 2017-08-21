package com.yy.hiidostatis.inner.util.log;

import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.yy.hiidostatis.defs.obj.Elem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class C1923L {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PREFIX = "[HdLog] ";
    private static final String TAG = "HdLog";
    private static boolean mIsDebugSdConfig = false;
    private static boolean mIsLogOn = false;
    private static boolean mIsLogOnSdConfig = false;
    private static IBaseStatisLogTag sLogTag;
    private static IBaseStatisLogWriter sLogWriter;

    static {
        try {
            C1923L.initLogConfig();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void brief(String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                if (C1923L.outputDebug()) {
                    String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(null, str, objArr);
                    Log.d(C1923L.getTag(), str2);
                    C1923L.writeLog(str2, 1);
                }
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.brief exception=" + th);
            }
        }
    }

    public static void debug(Object obj, String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                if (C1923L.outputDebug()) {
                    String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr);
                    Log.d(C1923L.getTag(), str2);
                    C1923L.writeLog(str2, 1);
                }
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.debug exception=" + th);
            }
        }
    }

    public static void error(Object obj, String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                String formatErrorMsg = C1923L.formatErrorMsg(C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr), objArr);
                Log.e(C1923L.getTag(), formatErrorMsg);
                C1923L.writeLog(formatErrorMsg, 4);
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.error exception=" + th);
            }
        }
    }

    public static void errorOn(Object obj, String str, Object... objArr) {
        try {
            String formatErrorMsg = C1923L.formatErrorMsg(C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr), objArr);
            Log.e(C1923L.getTag(), formatErrorMsg);
            C1923L.writeLog(formatErrorMsg, 4);
        } catch (Throwable th) {
            Log.e(C1923L.getTag(), "Log.error exception=" + th);
        }
    }

    private static String formatErrorMsg(String str, Object... objArr) throws IOException {
        return (objArr.length <= 0 || !(objArr[objArr.length - 1] instanceof Throwable)) ? str : C1923L.logToFile(str, (Throwable) objArr[objArr.length - 1]);
    }

    private static String formatLog(int i, String str) {
        return String.format("%s\t%8s\t%s\t%s", new Object[]{C1923L.getTag(), C1923L.getTypeName(i), new SimpleDateFormat(DATE_FORMAT).format(Long.valueOf(System.currentTimeMillis())), str});
    }

    private static String getCallerFilename() {
        return Thread.currentThread().getStackTrace()[5].getFileName();
    }

    private static int getCallerLineNumber() {
        return Thread.currentThread().getStackTrace()[5].getLineNumber();
    }

    private static String getLogText(Object obj, String str, Object... objArr) {
        String format = String.format(str, objArr);
        return C1923L.msgForTextLog(obj, C1923L.getCallerFilename(), C1923L.getCallerLineNumber(), format);
    }

    protected static String getPreFix() {
        return sLogTag == null ? PREFIX : "[" + sLogTag.getLogPrefix() + "] ";
    }

    protected static String getTag() {
        return sLogTag == null ? TAG : sLogTag.getLogTag();
    }

    private static String getTypeName(int i) {
        String str = "";
        switch (i) {
            case 1:
                return "DEBUG";
            case 2:
                return "INFO";
            case 3:
                return "WARN";
            case 4:
                return "ERROR";
            default:
                return "UNKNOWN";
        }
    }

    public static void info(Object obj, String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr);
                Log.i(C1923L.getTag(), str2);
                C1923L.writeLog(str2, 2);
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.info exception=" + th);
            }
        }
    }

    public static void infoOn(Object obj, String str, Object... objArr) {
        try {
            String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr);
            Log.i(C1923L.getTag(), str2);
            C1923L.writeLog(str2, 2);
        } catch (Throwable th) {
            Log.e(C1923L.getTag(), "Log.info exception=" + th);
        }
    }

    public static void initLogConfig() throws Throwable {
        Exception e;
        InputStream inputStream;
        Throwable th;
        FileInputStream fileInputStream = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            stringBuffer.append(File.separator);
            stringBuffer.append("hdconfig.txt");
            String stringBuffer2 = stringBuffer.toString();
            stringBuffer.setLength(0);
            File file = new File(stringBuffer2);
            if (file.exists()) {
                Properties properties = new Properties();
                InputStream fileInputStream2 = new FileInputStream(file);
                try {
                    properties.load(fileInputStream2);
                    mIsDebugSdConfig = Boolean.parseBoolean(properties.getProperty("isDebug"));
                    mIsLogOnSdConfig = Boolean.parseBoolean(properties.getProperty("isLogOn"));
                    C1923L.infoOn(C1923L.class, "initLogConfig,mIsDebugSdConfig=%b,mIsLogOnSdConfig=%b", Boolean.valueOf(mIsDebugSdConfig), Boolean.valueOf(mIsLogOnSdConfig));
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                        return;
                    }
                    return;
                } catch (Exception e2) {
                    e = e2;
                    inputStream = fileInputStream2;
                    try {
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                            }
                        }
                        try {
                            throw th;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            }
            C1923L.infoOn(C1923L.class, "initLogConfig is empty", new Object[0]);
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                }
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    public static boolean isLogOn() {
        return mIsLogOnSdConfig || mIsLogOn;
    }

    private static String logToFile(String str, Throwable th) throws IOException {
        Writer stringWriter = new StringWriter();
        stringWriter.write(str);
        stringWriter.write("\n");
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        String stringWriter2 = stringWriter.toString();
        try {
            printWriter.close();
            stringWriter.close();
        } catch (IOException e) {
        }
        return stringWriter2;
    }

    private static String msgForTextLog(Object obj, String str, int i, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append("(P:");
        stringBuilder.append(Process.myPid());
        stringBuilder.append(")");
        stringBuilder.append("(T:");
        stringBuilder.append(Thread.currentThread().getId());
        stringBuilder.append(")");
        stringBuilder.append("(C:");
        stringBuilder.append(C1923L.objClassName(obj));
        stringBuilder.append(")");
        stringBuilder.append("at (");
        stringBuilder.append(str);
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(i);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static String objClassName(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj.getClass().getSimpleName();
    }

    public static boolean outputDebug() {
        IBaseStatisLogWriter iBaseStatisLogWriter = sLogWriter;
        return mIsDebugSdConfig || (iBaseStatisLogWriter != null && iBaseStatisLogWriter.outputDebug());
    }

    private static void printLog(String str, String str2, boolean z) {
        if (z) {
            C1923L.info(str, str2, new Object[0]);
        } else {
            C1923L.debug(str, str2, new Object[0]);
        }
    }

    public static void printStackTraces(StackTraceElement[] stackTraceElementArr, String str, String str2, boolean z, boolean z2) {
        C1923L.printLog(str, "------------------------------------", z2);
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            String stackTraceElement2 = stackTraceElement.toString();
            if (z || !(str2 == null || str2.length() <= 0 || stackTraceElement2.indexOf(str2) == -1)) {
                C1923L.printLog(str, stackTraceElement2, z2);
            }
        }
        C1923L.printLog(str, "------------------------------------", z2);
    }

    public static void printThreadStacks(String str, String str2, boolean z, boolean z2) {
        C1923L.printStackTraces(Thread.currentThread().getStackTrace(), str, str2, z, z2);
    }

    public static void setLogOn(boolean z) {
        mIsLogOn = z;
    }

    public static void setLogSetting(IBaseStatisLogWriter iBaseStatisLogWriter) {
        sLogWriter = iBaseStatisLogWriter;
    }

    public static void setLogTag(IBaseStatisLogTag iBaseStatisLogTag) {
        sLogTag = iBaseStatisLogTag;
    }

    public static String stackTraceOf(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String threadStack() {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            printWriter.println(stackTraceElement.toString());
        }
        return stringWriter.toString();
    }

    public static void verbose(Object obj, String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                if (C1923L.outputDebug()) {
                    Log.d(C1923L.getTag(), C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr));
                }
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.verbose exception=" + th);
            }
        }
    }

    public static void warn(Object obj, String str, Object... objArr) {
        if (C1923L.isLogOn()) {
            try {
                String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr);
                Log.w(C1923L.getTag(), str2);
                C1923L.writeLog(str2, 3);
            } catch (Throwable th) {
                Log.e(C1923L.getTag(), "Log.warn exception=" + th);
            }
        }
    }

    public static void warnOn(Object obj, String str, Object... objArr) {
        try {
            String str2 = C1923L.getPreFix() + " " + C1923L.getLogText(obj, str, objArr);
            Log.w(C1923L.getTag(), str2);
            C1923L.writeLog(str2, 3);
        } catch (Throwable th) {
            Log.e(C1923L.getTag(), "Log.warn exception=" + th);
        }
    }

    public static void writeLog(String str, int i) {
        IBaseStatisLogWriter iBaseStatisLogWriter = sLogWriter;
        if (iBaseStatisLogWriter != null) {
            try {
                iBaseStatisLogWriter.write(i, C1923L.formatLog(i, str));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
