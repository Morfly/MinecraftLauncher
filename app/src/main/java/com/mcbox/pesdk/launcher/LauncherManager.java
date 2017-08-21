package com.mcbox.pesdk.launcher;

import android.content.Context;
import android.util.Log;

import com.mcbox.p019a.p020a.C1675a;
import com.mcbox.pesdk.launcher.exception.LauncherMgrInitException;
import com.mcbox.pesdk.launcher.exception.LauncherNotSupportException;
import com.mcbox.pesdk.mcfloat.util.LauncherUtil;
import com.mcbox.pesdk.util.LauncherMiscUtil;
import com.mcbox.pesdk.util.McInstallInfoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LauncherManager {
    private static String CLZ_NAME_CORE = ("com.mcbox.pesdk.launcher.impl" + ".LauncherCoreImpl");
    private static String CLZ_NAME_FUNC = ("com.mcbox.pesdk.launcher.impl" + ".LauncherFuncImpl");
    private static String CLZ_NAME_FUNCSPEC = ("com.mcbox.pesdk.launcher.impl" + ".LauncherFuncSpecImpl");
    private static String CLZ_NAME_RUNTIME = ("com.mcbox.pesdk.launcher.impl" + ".LauncherRuntimeImpl");
    public static String MC_PACKAGE_NAME = "net.zhuoweizhang.mcpelauncher";
    private static String PACKAGE_NAME = "com.mcbox.pesdk.launcher.impl";
    private static LauncherManager instance = new LauncherManager();
    private ClassLoader classLoader = null;
    private int lastClassLoaderHashCode = 0;
    private LauncherConfig launcherConfig;
    private LauncherCore launcherCore;
    private LauncherExtend launcherExtend;
    private LauncherFunc launcherFunc;
    private LauncherFuncSpec launcherFuncSpec;
    private LauncherRuntime launcherRuntime;
    private LauncherVersion launcherVersion;

    private LauncherManager() {
    }

    private static void descryptAndSaveFile(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (inputStream != null) {
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[inputStream.available()];
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        throw new LauncherMgrInitException("Failt to read plugin file!");
                    }
                    bArr = LauncherMiscUtil.decryptModule(bArr, read);
                    if (bArr.length == 0) {
                        throw new LauncherMgrInitException("Failt to descrypt plugin file!");
                    }
                    fileOutputStream.write(bArr);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        th.printStackTrace();
                        throw new LauncherMgrInitException("Failt to unpack plugin file!");
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        }
    }

    public static LauncherManager getInstance() {
        return instance;
    }

    private void loadAllServices(Context context) throws LauncherMgrInitException {
        try {
            this.launcherFunc = (LauncherFunc) this.classLoader.loadClass(CLZ_NAME_FUNC).newInstance();
            this.launcherFuncSpec = (LauncherFuncSpec) this.classLoader.loadClass(CLZ_NAME_FUNCSPEC).newInstance();
            this.launcherRuntime = (LauncherRuntime) this.classLoader.loadClass(CLZ_NAME_RUNTIME).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            String str = "Fail to load services! \nclassloader is:" + this.classLoader.toString() + "\nParent classloader is:" + this.classLoader.getParent().toString() + "\nError message: " + e.getMessage();
            if (!(this.launcherVersion.isMc010() || this.launcherVersion.isMc011() || this.launcherVersion.isMCNotFound() || this.launcherExtend == null)) {
                this.launcherExtend.reportCrash(str, e, context);
            }
            throw new LauncherMgrInitException(str);
        }
    }

    private void loadPlugin(Context context) throws LauncherNotSupportException, IllegalAccessException, NoSuchFieldException, IOException {
        File file = new File(LauncherMiscUtil.getApplicationInfo(context).dataDir, LauncherConstants.DEX_CACHE_FOLDER_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        File preparePluginFile = preparePluginFile(context);
        List arrayList = new ArrayList();
        arrayList.add(preparePluginFile);
        C1675a.m5683a(this.classLoader, file, arrayList);
    }

    private boolean needLoadPlugin() {
        try {
            this.classLoader.loadClass(CLZ_NAME_CORE);
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    private File preparePluginFile(Context context) throws LauncherNotSupportException {
        File file = new File(context.getFilesDir(), LauncherConstants.FILES_SUBDIR_PLUGIN);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pluginFileName = this.launcherVersion.getPluginFileName();
        if (pluginFileName == null) {
            throw new LauncherNotSupportException("No plugin for this version " + this.launcherVersion.toString());
        }
        File file2 = new File(file, pluginFileName.replace("dat", "apk"));
        Log.d(LauncherConstants.LOG_TAG, "plugin saved file path : " + file2);
        if (!file2.exists() || this.launcherConfig.forceReloadPlugin) {
            if (this.launcherConfig.forceReloadPlugin && file2.exists()) {
                Log.d(LauncherConstants.LOG_TAG, "to delete existed plugin file!");
                file2.delete();
            }
            InputStream inputStream = null;
            try {
                inputStream = context.getResources().getAssets().open(pluginFileName);
                Log.d(LauncherConstants.LOG_TAG, "Doing descryptAndSaveFile");
                LauncherMiscUtil.saveFile(inputStream, file2);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                Log.e(LauncherConstants.LOG_TAG, "Fail to descrypt and unpack plugin file! " + e2.getMessage());
                e2.printStackTrace();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            }
        }
        return file2;
    }

    public void checkVersionChanged(Context context) throws LauncherMgrInitException {
        Object obj = 1;
        McInstallInfoUtil.init(context);
        LauncherUtil.init(context);
        if (this.launcherVersion == null) {
            this.launcherVersion = new LauncherVersion();
            this.launcherVersion.setVersion(this.launcherConfig.region, McInstallInfoUtil.mcv);
        } else {
            LauncherVersion launcherVersion = new LauncherVersion();
            launcherVersion.setVersion(this.launcherConfig.region, McInstallInfoUtil.mcv);
            if (this.launcherVersion.equals(launcherVersion) && isPluginOK(context)) {
                obj = null;
            }
        }
        Log.d(LauncherConstants.LOG_TAG, "launcherVersion : " + this.launcherVersion);
        if (obj != null) {
            reloadPlugin(context);
        }
    }

    public LauncherCore getLauncherCore() {
        return this.launcherCore;
    }

    public LauncherExtend getLauncherExtend() {
        return this.launcherExtend;
    }

    public LauncherFunc getLauncherFunc() {
        return this.launcherFunc;
    }

    public LauncherFuncSpec getLauncherFuncSpec() {
        return this.launcherFuncSpec;
    }

    public LauncherRuntime getLauncherRuntime() {
        return this.launcherRuntime;
    }

    public LauncherVersion getLauncherVersion() {
        return this.launcherVersion;
    }

    public void init(LauncherConfig launcherConfig, LauncherExtend launcherExtend) {
        this.launcherConfig = launcherConfig;
        if (this.launcherConfig == null) {
            this.launcherConfig = new LauncherConfig();
        }
        Log.d(LauncherConstants.LOG_TAG, "launcherConfig : " + this.launcherConfig);
        this.launcherExtend = launcherExtend;
    }

    public boolean isPluginOK(Context context) {
        try {
            context.getClassLoader().loadClass(CLZ_NAME_FUNC);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadExternalScripts() {
        if (this.launcherExtend != null) {
            this.launcherExtend.loadExternalScripts();
        }
    }

    public void reloadPlugin(Context context) throws LauncherMgrInitException {
        if (!(this.lastClassLoaderHashCode == 0 || this.lastClassLoaderHashCode == context.getClassLoader().hashCode() || this.launcherExtend == null)) {
            this.launcherExtend.reportCrash("application and activity have different classloader!Old hashcode is " + this.lastClassLoaderHashCode + ", new hashcode is " + context.getClassLoader().hashCode(),context);
        }
        this.classLoader = context.getClassLoader();
        this.lastClassLoaderHashCode = this.classLoader.hashCode();
        if (needLoadPlugin()) {
            Log.d(LauncherConstants.LOG_TAG, "need to load plugin!");
            try {
                loadPlugin(context);
            } catch (Exception e) {
                e.printStackTrace();
                String str = "Fail to load plugin! \nclassloader is:" + this.classLoader.toString() + "\nParent classloader is:" + this.classLoader.getParent().toString() + "\nError message: " + e.getMessage();
                if (!(this.launcherVersion.isMc010() || this.launcherVersion.isMc011() || this.launcherVersion.isMCNotFound() || this.launcherExtend == null)) {
                    this.launcherExtend.reportCrash(str, e, context);
                }
                throw new LauncherMgrInitException(str);
            }
        }
        loadAllServices(context);
    }

    public void reportEvent(Context context, String str, String str2) {
        if (this.launcherExtend != null) {
            this.launcherExtend.reportEvent(context, str, str2);
        }
    }
}
