package com.yy.hiidostatis.inner.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.umeng.analytics.C1823a;
import com.yy.hiidostatis.defs.obj.Elem;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final int NET_2G = 1;
    private static final int NET_3G = 2;
    private static final int NET_4G = 4;
    private static final int NET_UNKNOWN = 0;
    private static final int NET_WIFI = 3;

    public static String asEmptyOnNull(String str) {
        return str == null ? "" : str;
    }

    public static boolean checkPermissions(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Exception e) {
            C1923L.warn(Util.class, "checkPermissions Exception: %s", e);
            return false;
        }
    }

    public static long cpuMillis() {
        return SystemClock.elapsedRealtime();
    }

    public static long cpuSec() {
        return millisToSec(cpuMillis());
    }

    public static int daysBetween(long j, long j2) {
        long j3 = j2 - j;
        long j4 = j3 / C1823a.f3733m;
        if (j3 % C1823a.f3733m != 0) {
            j4++;
        }
        return Integer.parseInt(String.valueOf(j4));
    }

    public static boolean empty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean empty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean empty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static boolean empty(int... iArr) {
        return iArr == null || iArr.length == 0;
    }

    public static <T> boolean empty(T... tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static String getAndroidId(Context context) {
        String str = null;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            C1923L.warn(Util.class, "Exception when getAndroidId %s", e);
        }
        return str;
    }

    public static String getCpuName() {
        try {
            String[] split = new BufferedReader(new FileReader("/proc/cpuinfo")).readLine().split(":\\s+", 2);
            for (int i = 0; i < split.length; i++) {
            }
            return split[1];
        } catch (Throwable th) {
            C1923L.warn(Util.class, "getCpuName exceptioon: %s", th);
            return null;
        }
    }

    public static int getCpuNum() {
        int i = 1;
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]", file.getName());
                }
            }).length;
        } catch (Throwable th) {
            Object[] objArr = new Object[i];
            objArr[0] = th;
            C1923L.warn(Util.class, "getCpuNum exceptioon: %s", objArr);
            return i;
        }
    }

    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager == null ? null : telephonyManager.getDeviceId();
        } catch (Exception e) {
            C1923L.warn(Util.class, "exception on getIMEI : %s", e);
            return "";
        }
    }

    public static String getImsi(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        } catch (Exception e) {
            C1923L.warn(Util.class, "Exception when getImsi %s", e);
            return null;
        }
    }

    public static String getInnerIP() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            C1923L.debug(Util.class, "getInnerIP ex=%s", e);
        }
        return "";
    }

    public static String getLang() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static String getLine1Number(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        } catch (Exception e) {
            C1923L.warn(Util.class, "Exception when getLineNumber %s", e);
            return null;
        }
    }

    public static String getMacAddr(Context context) {
        String macAddress;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager == null) {
                return null;
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            macAddress = connectionInfo == null ? null : connectionInfo.getMacAddress();
            return macAddress;
        } catch (Exception e) {
            C1923L.warn(Util.class, "exception on getMacAddr : %s", e);
            macAddress = null;
        }
        return macAddress;
    }

    public static String getMaxCpuFreq() {
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            InputStream inputStream = new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                stringBuilder.append(new String(bArr));
            }
            inputStream.close();
        } catch (Throwable th) {
            C1923L.warn(Util.class, "getMaxCpuFreq exceptioon: %s", th);
        }
        return stringBuilder.toString().trim();
    }

    public static String getMetaDataParam(Context context, String str) {
        if (context == null || empty(str)) {
            return "";
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (applicationInfo != null) {
                Bundle bundle = applicationInfo.metaData;
                if (bundle != null) {
                    Object obj = bundle.get(str);
                    if (obj != null) {
                        C1923L.debug(Util.class, "meta data key[%s] value is %s", str, obj);
                        return obj + "";
                    }
                }
            }
        } catch (Exception e) {
            C1923L.warn(Util.class, "read meta-data key[%s] from AndroidManifest.xml Exception.%s", str, e);
        }
        return "";
    }

    public static int getNetworkType(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 0;
            }
            int type = activeNetworkInfo.getType();
            if (type == 1) {
                return 3;
            }
            if (type != 0) {
                return 0;
            }
            int subtype = activeNetworkInfo.getSubtype();
            return (subtype == 7 || subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 10 || subtype == 9) ? 2 : (subtype < 12 || subtype > 15) ? 1 : 4;
        } catch (Exception e) {
            C1923L.warn(Util.class, "exception on get network info: %s", e);
            return 0;
        }
    }

    public static int getNetworkTypeNew(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 0;
            }
            int type = activeNetworkInfo.getType();
            if (type == 1) {
                return 3;
            }
            if (type != 0) {
                return 0;
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                case 16:
                    return 1;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 18:
                    return 2;
                case 13:
                    return 4;
                default:
                    return 0;
            }
        } catch (Exception e) {
            C1923L.warn(Util.class, "exception on get network info: %s", e);
            return 0;
        }
    }

    public static String getNtm(Context context) {
        Exception e;
        String simOperator;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return null;
            }
            simOperator = telephonyManager.getSimOperator();
            try {
                if (empty(simOperator)) {
                    return simOperator;
                }
                String[] split = simOperator.split(",");
                if (split.length > 0 && !empty(split[0])) {
                    simOperator = split[0];
                } else if (split.length == 2 && !empty(split[1])) {
                    simOperator = split[1];
                }
                return (simOperator.length() == 5 || simOperator.length() == 6) ? simOperator.substring(0, 3) + Elem.DIVIDER + simOperator.substring(3) : simOperator;
            } catch (Exception e2) {
                e = e2;
                C1923L.warn(Util.class, "Exception when getNtm %s", e);
                return simOperator;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            simOperator = null;
            e = exception;
            C1923L.warn(Util.class, "Exception when getNtm %s", e);
            return simOperator;
        }
    }

    public static String getOS() {
        return "Android" + VERSION.RELEASE;
    }

    public static String getOutNetIp() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://city.ip138.com/ip2city.asp").openConnection();
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine + "\n");
                }
                inputStream.close();
                String str = "";
                Matcher matcher = Pattern.compile("[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}").matcher(stringBuilder.toString());
                while (matcher.find()) {
                    str = matcher.group();
                }
                return str;
            }
        } catch (Exception e) {
            C1923L.debug(Util.class, "getOutNetIp ex=%s", e);
        }
        return "";
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            C1923L.warn(Util.class, "Failed to read package Name.", new Object[0]);
            return "";
        }
    }

    public static String getRandStringEx(int i) {
        byte[] bArr = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122};
        byte[] bArr2 = new byte[i];
        Random random = new Random();
        for (int i2 = 0; i2 < i; i2++) {
            bArr2[i2] = bArr[random.nextInt(bArr.length - 1)];
        }
        return new String(bArr2);
    }

    public static String getScreenResolution(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return "";
            }
            Point point = new Point();
            Display defaultDisplay = windowManager.getDefaultDisplay();
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
            return point.x + "x" + point.y;
        } catch (Exception e) {
            C1923L.warn(Util.class, "exception on getScreenResolution info: %s", e);
            return null;
        }
    }

    public static String getSerialNum(Context context) {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"ro.serialno"});
        } catch (Exception e) {
            C1923L.warn(Util.class, "Exception when getSerialNum %s", e);
            return null;
        }
    }

    public static String getSjm(Context context) {
        return Build.MODEL;
    }

    public static String getSjp(Context context) {
        return Build.MANUFACTURER;
    }

    public static long getTotalMemory() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        Throwable th2;
        String str = null;
        long j = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8);
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    str = readLine;
                }
                if (!empty(str)) {
                    j = Long.parseLong(str.substring(str.indexOf(58) + 1, str.indexOf(107)).trim().trim());
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    C1923L.warn(Util.class, "getTotalMemory exceptioon: %s", th);
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    return j;
                } catch (Throwable th4) {
                    th2 = th4;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            th2 = th5;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th2;
        }
        return j;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            C1923L.warn(Util.class, "Failed to read version Name.", new Object[0]);
            return "";
        }
    }

    public static int getVersionNo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            C1923L.warn(Util.class, "Failed to read version No.", new Object[0]);
            return -1;
        }
    }

    public static boolean hasData(String str) {
        return !empty(str);
    }

    public static boolean hasData(Collection<?> collection) {
        return !empty((Collection) collection);
    }

    public static <T> boolean hasData(T... tArr) {
        return !empty((Object[]) tArr);
    }

    public static boolean isExistClass(String str) {
        try {
            return (empty(str) || Class.forName(str) == null) ? false : true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isExistExternalStorage() {
        boolean z = false;
        try {
            z = Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            C1923L.warn(Util.class, "isExistSdCard Exception: %s", e);
        }
        return z;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
        } catch (Exception e) {
            C1923L.warn(Util.class, "isNetworkAvailable Exception: %s", e);
            return false;
        }
    }

    public static boolean isNetworkReach() throws IOException {
        boolean z = false;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("www.baidu.com", 80), 5000);
            z = socket.isConnected();
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            C1923L.warn(Util.class, "isNetworkReach Exception: %s", e2);
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable th) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e3) {
                }
            }
        }
        return z;
    }

    public static boolean isWifiActive(Context context) {
        if (context == null) {
            C1923L.warn(Util.class, "the Input context is null!", new Object[0]);
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            boolean z = activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
            return z;
        } catch (Exception e) {
            C1923L.warn(Util.class, "isWifiActive Exception: %s", e);
            return false;
        }
    }

    public static int longToInt(long j) {
        if (j >= 2147483647L) {
            C1923L.warn("Convert", "Failed to convert long %d to int.", Long.valueOf(j));
        }
        return (int) j;
    }

    public static long millisToSec(long j) {
        return j / 1000;
    }

    public static long millisToSec1(long j) {
        return (j % 1000 == 0 || j <= 0) ? j / 1000 : (j / 1000) + 1;
    }

    public static String replaceEncode(String str, String str2) {
        if (!empty(str)) {
            try {
                str = str.replace(str2, URLEncoder.encode(str2, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return str;
    }

    public static long wallTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long wallTimeSec() {
        return millisToSec(wallTimeMillis());
    }
}
