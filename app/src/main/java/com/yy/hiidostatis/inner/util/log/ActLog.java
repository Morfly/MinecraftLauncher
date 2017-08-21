package com.yy.hiidostatis.inner.util.log;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.ZipUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.SimpleDateFormat;

public class ActLog {
    private static final String FILE_PATH;
    private static final String SEND_FAIL_LOG_SUFFIX = "-flog";
    private static final String SEND_SUC_LOG_SUFFIX = "-slog";
    public static final String TYPE_ADD = "Add";
    public static final String TYPE_DISCARD = "Dis";
    public static final String TYPE_FAIL = "Fail";
    public static final String TYPE_SUC = "Suc";
    private static volatile boolean isDelete = false;
    private static volatile boolean isWriteFailLog = true;
    private static volatile boolean isWriteSucLog = false;
    private static Context mContext;
    private static String mLogNamePre = "hdstatis";
    private static volatile String mUploadUrl = "https://config.hiido.com/api/upload";
    private static int r0;

    public interface ILogConfigListener {
        JSONObject getLogConfig();
    }

    static final class C19181 implements Runnable {
        final /* synthetic */ String val$content;
        final /* synthetic */ Context val$ctx;
        final /* synthetic */ String val$extra;
        final /* synthetic */ String val$smkdata;
        final /* synthetic */ String val$type;

        C19181(String str, Context context, String str2, String str3, String str4) {
            this.val$content = str;
            this.val$ctx = context;
            this.val$type = str2;
            this.val$smkdata = str3;
            this.val$extra = str4;
        }

        public void run() {
            try {
                String format = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(System.currentTimeMillis()));
                String access$000 = ActLog.parseParam(this.val$content, BaseStatisContent.GUID);
                String access$0002 = ActLog.parseParam(this.val$content, BaseStatisContent.ACT);
                String access$0003 = ActLog.parseParam(this.val$content, "appkey");
                if (access$0003.length() > 4) {
                    access$0003 = access$0003.substring(0, 4);
                }
                Context context = this.val$ctx;
                String str = "%s\t%s\t%s\t%4s\t%s\t%s\t%s";
                Object[] objArr = new Object[7];
                objArr[0] = format;
                objArr[1] = access$000;
                objArr[2] = access$0003;
                objArr[3] = this.val$type;
                objArr[4] = access$0002;
                objArr[5] = this.val$smkdata == null ? "-" : this.val$smkdata;
                objArr[6] = this.val$extra == null ? "-" : this.val$extra;
                ActLog.write(context, null, str, objArr);
            } catch (Throwable th) {
                C1923L.error(ActLog.class, "writeActLog Exception = %s", th);
            }
        }
    }

    static final class C19192 implements Runnable {
        final /* synthetic */ Context val$ctx;
        final /* synthetic */ int val$dayAgo;

        C19192(Context context, int i) {
            this.val$ctx = context;
            this.val$dayAgo = i;
        }

        public void run() {
            try {
                File[] listFiles = new File(ActLog.getLogFilePath(this.val$ctx)).listFiles();
                if (listFiles != null) {
                    for (File name : listFiles) {
                        if (ActLog.matchFileName(name.getName(), this.val$dayAgo)) {
                            C1923L.debug(ActLog.class, "delLogFile [%s] = %b ", name.getName(), Boolean.valueOf(listFiles[r0].delete()));
                        }
                    }
                }
            } catch (Throwable th) {
                C1923L.debug(ActLog.class, "delLogFile exception = %s", th);
            }
        }
    }

    static final class C19203 implements Runnable {
        final /* synthetic */ Context val$c;
        final /* synthetic */ String val$content;
        final /* synthetic */ String val$host;
        final /* synthetic */ String val$smkdata;

        C19203(String str, Context context, String str2, String str3) {
            this.val$content = str;
            this.val$c = context;
            this.val$smkdata = str2;
            this.val$host = str3;
        }

        public void run() {
            try {
                String format = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(System.currentTimeMillis()));
                String access$000 = ActLog.parseParam(this.val$content, BaseStatisContent.GUID);
                String access$0002 = ActLog.parseParam(this.val$content, BaseStatisContent.ACT);
                String access$0003 = ActLog.parseParam(this.val$content, "appkey");
                if (access$0003.length() > 4) {
                    access$0003 = access$0003.substring(0, 4);
                }
                Context context = this.val$c;
                String str = ActLog.SEND_SUC_LOG_SUFFIX;
                String str2 = "%s\t%s\t%s\t%s\t%s\t%s\t%s";
                Object[] objArr = new Object[7];
                objArr[0] = format;
                objArr[1] = access$000;
                objArr[2] = access$0003;
                objArr[3] = access$0002;
                objArr[4] = this.val$smkdata == null ? "-" : this.val$smkdata;
                objArr[5] = this.val$content;
                objArr[6] = this.val$host == null ? "-" : this.val$host;
                ActLog.write(context, str, str2, objArr);
            } catch (Throwable th) {
                C1923L.error(ActLog.class, "writeSendSucLog Exception = %s", th);
            }
        }
    }

    static final class C19214 implements Runnable {
        final /* synthetic */ Context val$c;
        final /* synthetic */ String val$content;
        final /* synthetic */ String val$error;
        final /* synthetic */ String val$host;
        final /* synthetic */ String val$smkdata;

        C19214(String str, String str2, Context context, String str3, String str4) {
            this.val$host = str;
            this.val$content = str2;
            this.val$c = context;
            this.val$smkdata = str3;
            this.val$error = str4;
        }

        public void run() {
            String str;
            InputStream inputStream;
            BufferedReader bufferedReader;
            String str2;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader2;
            String format;
            String access$000;
            Context context;
            String str3;
            String str4;
            Object[] objArr;
            Throwable th;
            Throwable th2;
            InputStream inputStream2 = null;
            String str5 = this.val$host;
            if (str5 != null) {
                try {
                    str5 = str5 + "\n" + InetAddress.getByName(this.val$host).getHostAddress();
                    str = str5 + "\n" + TextUtils.join(" ", InetAddress.getAllByName(this.val$host));
                } catch (Exception e) {
                    str = str5;
                }
                try {
                    str5 = "";
                    InputStreamReader inputStreamReader2;
                    try {
                        inputStream = Runtime.getRuntime().exec("ping -c 3 -w 100 " + this.val$host).getInputStream();
                        try {
                            inputStreamReader2 = new InputStreamReader(inputStream);
                            try {
                                bufferedReader = new BufferedReader(inputStreamReader2);
                                try {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    String str6 = "";
                                    while (true) {
                                        str6 = bufferedReader.readLine();
                                        if (str6 == null) {
                                            break;
                                        }
                                        stringBuffer.append(str6).append("\n");
                                    }
                                    str2 = str + "\n" + stringBuffer.toString();
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e2) {
                                        }
                                    }
                                    if (inputStreamReader2 != null) {
                                        try {
                                            inputStreamReader2.close();
                                        } catch (Exception e3) {
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                            str5 = str2;
                                        } catch (Exception e4) {
                                            str5 = str2;
                                        }
                                    } else {
                                        str5 = str2;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    inputStream2 = inputStream;
                                    th2 = th;
                                    if (inputStream2 != null) {
                                        try {
                                            inputStream2.close();
                                        } catch (Exception e5) {
                                        }
                                    }
                                    if (inputStreamReader2 != null) {
                                        try {
                                            inputStreamReader2.close();
                                        } catch (Exception e6) {
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Exception e7) {
                                        }
                                    }
                                    throw th2;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                bufferedReader = null;
                                inputStream2 = inputStream;
                                th2 = th;
                                if (inputStream2 != null) {
                                    inputStream2.close();
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                throw th2;
                            }
                        } catch (Throwable th5) {
                            bufferedReader = null;
                            InputStream inputStream3 = inputStream;
                            th2 = th5;
                            inputStreamReader2 = null;
                            inputStream2 = inputStream3;
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (inputStreamReader2 != null) {
                                inputStreamReader2.close();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th2;
                        }
                    } catch (Throwable th6) {
                        th2 = th6;
                        inputStreamReader2 = null;
                        bufferedReader = null;
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th2;
                    }
                } catch (Throwable th22) {
                    C1923L.error(ActLog.class, "writeSendFailLog Exception = %s", th22);
                    return;
                }
            }
            format = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(System.currentTimeMillis()));
            access$000 = ActLog.parseParam(this.val$content, BaseStatisContent.GUID);
            str = ActLog.parseParam(this.val$content, BaseStatisContent.ACT);
            str2 = ActLog.parseParam(this.val$content, "appkey");
            if (str2.length() > 4) {
                str2 = str2.substring(0, 4);
            }
            context = this.val$c;
            str3 = ActLog.SEND_FAIL_LOG_SUFFIX;
            str4 = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s";
            objArr = new Object[8];
            objArr[0] = format;
            objArr[1] = access$000;
            objArr[2] = str2;
            objArr[3] = str;
            if (this.val$smkdata == null) {
            }
            objArr[4] = this.val$smkdata == null ? "-" : this.val$smkdata;
            objArr[5] = this.val$content;
            if (str5 == null) {
                str5 = "-";
            }
            objArr[6] = str5;
            objArr[7] = this.val$error;
            try {
                ActLog.write(context, str3, str4, objArr);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    static final class C19225 implements Runnable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ ILogConfigListener val$logConfigListener;

        C19225(ILogConfigListener iLogConfigListener, Context context) {
            this.val$logConfigListener = iLogConfigListener;
            this.val$context = context;
        }

        public void run() {
            try {
                String str = ActLog.mLogNamePre + "_uploadDate";
                String format = new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(System.currentTimeMillis()));
                C1923L.debug(ActLog.class, "uploadDate = %s,isReport = %b", format, Boolean.valueOf(format.equals(DefaultPreference.getPreference().getPrefString(ActLog.mContext, str, null))));
                if (!format.equals(DefaultPreference.getPreference().getPrefString(ActLog.mContext, str, null))) {
                    JSONObject logConfig = this.val$logConfigListener.getLogConfig();
                    if (logConfig.has("sdkConfig")) {
                        JSONObject jSONObject = logConfig.getJSONObject("sdkConfig");
                        if (jSONObject.has("uploadUrl")) {
                            ActLog.mUploadUrl = jSONObject.getString("uploadUrl");
                        }
                        ActLog.isWriteSucLog = jSONObject.has("suc") ? "1".equals(jSONObject.get("suc")) : ActLog.isWriteSucLog;
                        C1923L.debug(ActLog.class, "isWriteSucLog = %b ", Boolean.valueOf(ActLog.isWriteSucLog));
                        ActLog.isWriteFailLog = jSONObject.has("fai") ? "1".equals(jSONObject.get("fai")) : ActLog.isWriteFailLog;
                        C1923L.debug(ActLog.class, "isWriteFailLog = %b ", Boolean.valueOf(ActLog.isWriteFailLog));
                        if (Util.isWifiActive(this.val$context) && ActLog.upload()) {
                            DefaultPreference.getPreference().setPrefString(this.val$context, str, format);
                            return;
                        }
                        return;
                    }
                    C1923L.debug(ActLog.class, "sdkConfig is null", new Object[0]);
                }
            } catch (Throwable th) {
                C1923L.error(ActLog.class, "uploadLog exception = %s", th);
            }
        }
    }

    static {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Environment.getExternalStorageDirectory().getAbsolutePath()).append(File.separator).append("hiidosdk").append(File.separator).append("#businessType#").append(File.separator).append("#packageName#").append(File.separator);
        FILE_PATH = stringBuffer.toString();
        stringBuffer.setLength(0);
    }

    private static synchronized void delLogFile(Context context, int i) {
        synchronized (ActLog.class) {
            if (!isDelete) {
                isDelete = true;
                ThreadPool.getPool().execute(new C19192(context, i));
            }
        }
    }

    public static String getFileName(Context context) {
        return getLogName().replaceAll("#yyyyMMdd#", new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(System.currentTimeMillis())));
    }

    public static String getFullFileName(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getLogFilePath(context)).append(File.separator).append(getFileName(context));
        return stringBuffer.toString();
    }

    public static String getLogFilePath(Context context) {
        return FILE_PATH.replaceAll("#packageName#", Util.getPackageName(context)).replaceAll("#businessType#", mLogNamePre);
    }

    private static String getLogName() {
        return getLogNamePre() + "_#yyyyMMdd#.log";
    }

    private static String getLogNamePre() {
        return mLogNamePre;
    }

    public static String getUploadUrl() {
        return mUploadUrl;
    }

    private static boolean matchFileName(String str, int i) {
        boolean z = true;
        try {
            if (!str.matches(getLogNamePre() + "_[\\d]{8}.log.*")) {
                return false;
            }
            if (Util.daysBetween(new SimpleDateFormat("yyyyMMdd").parse(str.substring(str.lastIndexOf("_") + 1, str.lastIndexOf("."))).getTime(), System.currentTimeMillis()) <= i) {
                z = false;
            }
            return z;
        } catch (Throwable th) {
            C1923L.warn(ActLog.class, "matchFileName excetion = %s", th);
            return false;
        }
    }

    private static String parseParam(String str, String str2) {
        try {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (str2.equals(split2[0])) {
                    return split2[1];
                }
            }
        } catch (Throwable th) {
        }
        return "-";
    }

    public static void setLogNamePre(String str) {
        mLogNamePre = str;
    }

    public static void setUploadUrl(String str) {
        mUploadUrl = str;
    }

    private static boolean upload() {
        try {
            C1923L.brief("upload begin,waiting...", new Object[0]);
            String logFilePath = getLogFilePath(mContext);
            File file = new File(logFilePath);
            if (!file.exists() || file.listFiles() == null || file.listFiles().length == 0) {
                C1923L.brief("no upload file, end", new Object[0]);
                return true;
            }
            String str = mLogNamePre + "_" + Util.getPackageName(mContext) + "_" + Util.getIMEI(mContext) + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Long.valueOf(System.currentTimeMillis())) + ".zip";
            String str2 = file.getParent() + File.separator + str;
            ZipUtil.zipFolder(logFilePath, str2);
            C1923L.debug(ActLog.class, "create zip=%s", str2);
            boolean uploadFile = uploadFile(str2, str);
            C1923L.debug(ActLog.class, "upload zip=%s isUpload=%b", str2, Boolean.valueOf(uploadFile));
            File file2 = new File(str2);
            C1923L.debug(ActLog.class, "zip=%s length =%s ", str2, Long.valueOf(file2.length()));
            C1923L.debug(ActLog.class, "delete zip=%s, delete =%b", str2, Boolean.valueOf(file2.delete()));
            C1923L.brief(uploadFile ? "upload file success!" : "upload file fail!", new Object[0]);
            if (!uploadFile) {
                return uploadFile;
            }
            isDelete = false;
            delLogFile(mContext, 1);
            return uploadFile;
        } catch (Throwable th) {
            C1923L.error(ActLog.class, "upload error = %s", th);
            C1923L.brief("upload file fail!", new Object[0]);
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean uploadFile(java.lang.String r13, java.lang.String r14) {
        /*
        r12 = -1;
        r3 = 0;
        r2 = 1;
        r1 = 0;
        r6 = "\r\n";
        r7 = "--";
        r8 = "*****";
        r0 = 0;
        r4 = 0;
        r5 = 0;
        r9 = mUploadUrl;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        if (r9 != 0) goto L_0x002a;
    L_0x0011:
        r2 = "upload url is null";
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r2, r6);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        if (r3 == 0) goto L_0x001e;
    L_0x001b:
        r0.close();	 Catch:{ Exception -> 0x0169 }
    L_0x001e:
        if (r3 == 0) goto L_0x0023;
    L_0x0020:
        r4.close();	 Catch:{ Exception -> 0x016c }
    L_0x0023:
        if (r3 == 0) goto L_0x0028;
    L_0x0025:
        r5.close();	 Catch:{ Exception -> 0x016f }
    L_0x0028:
        r0 = r1;
    L_0x0029:
        return r0;
    L_0x002a:
        r0 = com.yy.hiidostatis.inner.util.log.ActLog.class;
        r4 = "uploadUrl  =%s";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r10 = 0;
        r5[r10] = r9;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        com.yy.hiidostatis.inner.util.log.C1923L.debug(r0, r4, r5);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r0 = new java.net.URL;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r0.<init>(r9);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r0 = r0.openConnection();	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = 1;
        r0.setDoInput(r4);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = 1;
        r0.setDoOutput(r4);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = 0;
        r0.setUseCaches(r4);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = "POST";
        r0.setRequestMethod(r4);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = "Connection";
        r5 = "Keep-Alive";
        r0.setRequestProperty(r4, r5);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = "Charset";
        r5 = "UTF-8";
        r0.setRequestProperty(r4, r5);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = "Content-Type";
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r5.<init>();	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r9 = "multipart/form-data;boundary=";
        r5 = r5.append(r9);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r5 = r5.append(r8);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r5 = r5.toString();	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r0.setRequestProperty(r4, r5);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r5 = new java.io.DataOutputStream;	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = r0.getOutputStream();	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r5.<init>(r4);	 Catch:{ Throwable -> 0x0191, all -> 0x0156 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4.<init>();	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.append(r7);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.append(r8);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r5.writeBytes(r4);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4.<init>();	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r9 = "Content-Disposition: form-data; name=\"file\";filename=\"";
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.append(r14);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r9 = "\"";
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r5.writeBytes(r4);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r5.writeBytes(r6);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r4.<init>(r13);	 Catch:{ Throwable -> 0x0196, all -> 0x0187 }
        r9 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r9 = new byte[r9];	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
    L_0x00c6:
        r10 = r4.read(r9);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        if (r10 == r12) goto L_0x00f4;
    L_0x00cc:
        r11 = 0;
        r5.write(r9, r11, r10);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        goto L_0x00c6;
    L_0x00d1:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        r4 = r5;
    L_0x00d5:
        r5 = com.yy.hiidostatis.inner.util.log.ActLog.class;
        r6 = "%s";
        r7 = 1;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x018c }
        r8 = 0;
        r7[r8] = r0;	 Catch:{ all -> 0x018c }
        com.yy.hiidostatis.inner.util.log.C1923L.error(r5, r6, r7);	 Catch:{ all -> 0x018c }
        if (r4 == 0) goto L_0x00e7;
    L_0x00e4:
        r4.close();	 Catch:{ Exception -> 0x0178 }
    L_0x00e7:
        if (r3 == 0) goto L_0x00ec;
    L_0x00e9:
        r3.close();	 Catch:{ Exception -> 0x017b }
    L_0x00ec:
        if (r2 == 0) goto L_0x00f1;
    L_0x00ee:
        r2.close();	 Catch:{ Exception -> 0x017e }
    L_0x00f1:
        r0 = r1;
        goto L_0x0029;
    L_0x00f4:
        r5.writeBytes(r6);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r9 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r9.<init>();	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r9 = r9.append(r7);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r8 = r9.append(r8);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r7 = r8.append(r7);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r6 = r7.append(r6);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r6 = r6.toString();	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r5.writeBytes(r6);	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r4.close();	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r5.flush();	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r3 = r0.getInputStream();	 Catch:{ Throwable -> 0x00d1, all -> 0x018a }
        r0 = new java.lang.StringBuffer;	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        r0.<init>();	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
    L_0x0122:
        r6 = r3.read();	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        if (r6 == r12) goto L_0x0132;
    L_0x0128:
        r6 = (char) r6;	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        r0.append(r6);	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        goto L_0x0122;
    L_0x012d:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        goto L_0x00d5;
    L_0x0132:
        r6 = "result=%s";
        r7 = 1;
        r7 = new java.lang.Object[r7];	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        r8 = 0;
        r0 = r0.toString();	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        r7[r8] = r0;	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r6, r7);	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        r5.close();	 Catch:{ Throwable -> 0x012d, all -> 0x018a }
        if (r5 == 0) goto L_0x0149;
    L_0x0146:
        r5.close();	 Catch:{ Exception -> 0x0172 }
    L_0x0149:
        if (r4 == 0) goto L_0x014e;
    L_0x014b:
        r4.close();	 Catch:{ Exception -> 0x0174 }
    L_0x014e:
        if (r3 == 0) goto L_0x0153;
    L_0x0150:
        r3.close();	 Catch:{ Exception -> 0x0176 }
    L_0x0153:
        r0 = r2;
        goto L_0x0029;
    L_0x0156:
        r0 = move-exception;
        r4 = r3;
        r5 = r3;
    L_0x0159:
        if (r5 == 0) goto L_0x015e;
    L_0x015b:
        r5.close();	 Catch:{ Exception -> 0x0181 }
    L_0x015e:
        if (r4 == 0) goto L_0x0163;
    L_0x0160:
        r4.close();	 Catch:{ Exception -> 0x0183 }
    L_0x0163:
        if (r3 == 0) goto L_0x0168;
    L_0x0165:
        r3.close();	 Catch:{ Exception -> 0x0185 }
    L_0x0168:
        throw r0;
    L_0x0169:
        r0 = move-exception;
        goto L_0x001e;
    L_0x016c:
        r0 = move-exception;
        goto L_0x0023;
    L_0x016f:
        r0 = move-exception;
        goto L_0x0028;
    L_0x0172:
        r0 = move-exception;
        goto L_0x0149;
    L_0x0174:
        r0 = move-exception;
        goto L_0x014e;
    L_0x0176:
        r0 = move-exception;
        goto L_0x0153;
    L_0x0178:
        r0 = move-exception;
        goto L_0x00e7;
    L_0x017b:
        r0 = move-exception;
        goto L_0x00ec;
    L_0x017e:
        r0 = move-exception;
        goto L_0x00f1;
    L_0x0181:
        r1 = move-exception;
        goto L_0x015e;
    L_0x0183:
        r1 = move-exception;
        goto L_0x0163;
    L_0x0185:
        r1 = move-exception;
        goto L_0x0168;
    L_0x0187:
        r0 = move-exception;
        r4 = r3;
        goto L_0x0159;
    L_0x018a:
        r0 = move-exception;
        goto L_0x0159;
    L_0x018c:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x0159;
    L_0x0191:
        r0 = move-exception;
        r2 = r3;
        r4 = r3;
        goto L_0x00d5;
    L_0x0196:
        r0 = move-exception;
        r2 = r3;
        r4 = r5;
        goto L_0x00d5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.inner.util.log.ActLog.uploadFile(java.lang.String, java.lang.String):boolean");
    }

    public static void uploadLog(Context context, ILogConfigListener iLogConfigListener) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        ThreadPool.getPool().execute(new C19225(iLogConfigListener, context));
    }

    private static void write(Context context, String str, String str2, Object... objArr) throws Throwable {
        Throwable th;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter2;
        try {
            String fullFileName = getFullFileName(context);
            if (str != null) {
                fullFileName = fullFileName + str;
            }
            C1923L.debug(ActLog.class, "logPath = %s", fullFileName);
            String format = String.format(str2, objArr);
            File file = new File(fullFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fileWriter2 = new FileWriter(file, true);
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
                try {
                    bufferedWriter2.write(format);
                    bufferedWriter2.newLine();
                    fileWriter2.flush();
                    bufferedWriter2.flush();
                    if (fileWriter2 != null) {
                        try {
                            fileWriter2.close();
                        } catch (IOException e) {
                        }
                    }
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter = bufferedWriter2;
                    if (fileWriter2 != null) {
                        fileWriter2.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (fileWriter2 != null) {
                    fileWriter2.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileWriter2 = null;
            if (fileWriter2 != null) {
                fileWriter2.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            throw th;
        }
    }

    public static void writeActLog(Context context, String str, String str2, String str3, String str4) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        ThreadPool.getPool().execute(new C19181(str2, context, str, str3, str4));
        delLogFile(context, 7);
    }

    public static void writeSendFailLog(Context context, String str, String str2, String str3, String str4) {
        if (C1923L.isLogOn() || isWriteFailLog) {
            Context context2 = context == null ? mContext : context;
            if (context2 == null) {
                C1923L.brief("writeSendFailLog context is null", new Object[0]);
            } else {
                ThreadPool.getPool().execute(new C19214(str2, str3, context2, str, str4));
            }
        }
    }

    public static void writeSendSucLog(Context context, String str, String str2, String str3) {
        if (C1923L.isLogOn() || isWriteSucLog) {
            if (context == null) {
                context = mContext;
            }
            if (context == null) {
                C1923L.brief("writeSendSucLog context is null", new Object[0]);
            } else {
                ThreadPool.getPool().execute(new C19203(str3, context, str, str2));
            }
        }
    }
}
