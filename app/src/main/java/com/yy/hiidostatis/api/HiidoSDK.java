package com.yy.hiidostatis.api;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yy.hiidostatis.defs.ConfigAPI;
import com.yy.hiidostatis.defs.StatisAPI;
import com.yy.hiidostatis.defs.controller.AppAnalyzeController;
import com.yy.hiidostatis.defs.controller.BasicBehaviorController;
import com.yy.hiidostatis.defs.controller.BasicBehaviorController.AppActionReporter;
import com.yy.hiidostatis.defs.controller.BasicBehaviorController.PageActionReporter;
import com.yy.hiidostatis.defs.controller.ContactAnalyzeStatisAPI;
import com.yy.hiidostatis.defs.controller.CrashController;
import com.yy.hiidostatis.defs.controller.CrashController.OnCrashListener;
import com.yy.hiidostatis.defs.controller.DeviceController;
import com.yy.hiidostatis.defs.controller.InstallController;
import com.yy.hiidostatis.defs.controller.OnLineConfigController;
import com.yy.hiidostatis.defs.controller.SdkAnalyzeController;
import com.yy.hiidostatis.defs.controller.SdkVerController;
import com.yy.hiidostatis.defs.listener.ActListener;
import com.yy.hiidostatis.defs.obj.Property;
import com.yy.hiidostatis.inner.GeneralProxy;
import com.yy.hiidostatis.inner.implementation.CommonFiller;
import com.yy.hiidostatis.inner.util.Counter;
import com.yy.hiidostatis.inner.util.Counter.Callback;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.cipher.Coder;
import com.yy.hiidostatis.inner.util.log.ActLog;
import com.yy.hiidostatis.inner.util.log.ActLog.ILogConfigListener;
import com.yy.hiidostatis.inner.util.log.C1923L;
import com.yy.hiidostatis.pref.HdStatisConfig;
import com.yy.hiidostatis.track.BgReportTimer;
import com.yy.hiidostatis.track.BgReportTimer.IConfigListener;
import com.yy.hiidostatis.track.Report;

import org.json.JSONObject;

import java.util.UUID;

public class HiidoSDK {
    private static final int INTERVAL_HEART_BEAT = 900000;
    private static final int STATE_INVALID = -1;
    private static final int STATE_QUITED = 2;
    private static final int STATE_STARTED = 1;
    private static volatile boolean isResumeCall = false;
    private static AppAnalyzeController mAppAnalyzeController;
    private static BasicBehaviorController mBasicBehaviorController;
    private static ConfigAPI mConfigApi;
    private static ContactAnalyzeStatisAPI mContactAnalyzeStatisAPI;
    private static DeviceController mDeviceController;
    private static InstallController mInstallController;
    private static boolean mIsActionReportStarted = false;
    private static boolean mIsInit = false;
    private static OnLineConfigController mOnLineConfigController;
    private static SdkAnalyzeController mSdkAnalyzeController;
    private static SdkVerController mSdkVerController;
    private static StatisAPI mStatisAPI = new StatisAPI();
    private OnStatisListener nullListener = new C18741();
    private static final HiidoSDK sApi = new HiidoSDK();
    private volatile Callback mActionReportExecutor;
    private volatile Counter mActionReportInvoker;
    private volatile Context mContext;
    private CrashController mCrashController;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Counter mHeartbeatInvoker = new Counter(this.mHandler, 0, 900000, true);
    private final Counter mHeartbeatInvoker5 = new Counter(this.mHandler, 0, 300000, true);
    private volatile Callback mHeartbeatReportExecutor;
    private volatile Callback mHeartbeatReportExecutor5;
    private volatile OnStatisListener mOnStatisListener = nullListener;
    private volatile Options mOptions = new Options();
    private volatile QuitTimer mQuittimer = new QuitTimer();
    private int mState = -1;
    private volatile StatisOption mStatisOption = new StatisOption();

    final class C18741 implements OnStatisListener {
        C18741() {
        }

        public long getCurrentUid() {
            return 0;
        }
    }

    class C18752 implements ILogConfigListener {
        C18752() {
        }

        public JSONObject getLogConfig() {
            return HiidoSDK.mConfigApi.getAppListConfig(HiidoSDK.this.mContext, true);
        }
    }

    class C18763 implements IConfigListener {
        C18763() {
        }

        public JSONObject getConfig() {
            return HiidoSDK.mConfigApi.getAppListConfig(HiidoSDK.this.mContext, true);
        }
    }

    class C18785 implements Callback {
        C18785() {
        }

        public void onCount(int i) {
            HiidoSDK.this.reportDo(HiidoSDK.this.mContext, HiidoSDK.this.mOnStatisListener.getCurrentUid());
        }
    }

    class C18796 implements Callback {
        C18796() {
        }

        public void onCount(int i) {
            HiidoSDK.this.reportDo5(HiidoSDK.this.mContext, HiidoSDK.this.mOnStatisListener.getCurrentUid());
        }
    }

    class C18817 implements OnCrashListener {

        class C18801 implements Runnable {
            C18801() {
            }

            public void run() {
                HiidoSDK.this.quitApp(false);
            }
        }

        C18817() {
        }

        public void handler() {
            ThreadPool.getPool().execute(new C18801());
        }
    }

    public class Options {
        public static final int BEHAVIOR_SEND_THRESHOLD_DEFAULT = 10;
        public static final int BEHAVIOR_SEND_THRESHOLD_MAX = 100;
        public static final int DEFAULT_BACKGROUND_DURATION_MILLIS_AS_QUIT = 30000;
        public static final int DEFAULT_BASIC_BEHAVIOR_SEND_INTERVAL = 600000;
        public static final int MAX_BASIC_BEHAVIOR_SEND_INTERVAL = 1800000;
        public static final int MIN_BASIC_BEHAVIOR_SEND_INTERVAL = 60000;
        public long backgroundDurationMillisAsQuit = 30000;
        public int behaviorSendIntervalMillis = DEFAULT_BASIC_BEHAVIOR_SEND_INTERVAL;
        public int behaviorSendThreshold = 10;
        public boolean isLogOn = false;
        public boolean isOpenCrashMonitor = true;
        public boolean isOpenDo5 = true;
        public String testServer;
    }

    public enum PageActionReportOption {
        REPORT_ON_FUTURE_RESUME,
        DO_NOT_REPORT_ON_FUTURE_RESUME
    }

    class QuitTimer {
        private final Runnable mQuitTimer;

        class C18821 implements Runnable {
            C18821() {
            }

            public void run() {
                HiidoSDK.this.quitApp(true);
            }
        }

        private QuitTimer() {
            this.mQuitTimer = new C18821();
        }

        public void clearQuitTimer() {
            HiidoSDK.this.mHandler.removeCallbacks(this.mQuitTimer);
        }

        public void startQuitTimer() {
            HiidoSDK.this.mHandler.postDelayed(this.mQuitTimer, HiidoSDK.this.getOptions().backgroundDurationMillisAsQuit);
        }
    }

    private HiidoSDK() {
    }

    private AppActionReporter getAppActionReporter() {
        BasicBehaviorController behaviorCollector = getBehaviorCollector(getCtx(this.mContext));
        return behaviorCollector == null ? null : behaviorCollector.getAppActionCollector();
    }

    private BasicBehaviorController getBehaviorCollector(Context context) {
        Context ctx = getCtx(context);
        if (ctx == null) {
            C1923L.error(this, "Input context is null when getBehaviorCollector", new Object[0]);
            return null;
        }
        BasicBehaviorController basicBehaviorController = mBasicBehaviorController;
        if (basicBehaviorController != null) {
            return basicBehaviorController;
        }
        synchronized (this) {
            basicBehaviorController = mBasicBehaviorController;
            if (basicBehaviorController == null) {
                C1923L.brief("mOnStatisListener is %s", this.mOnStatisListener);
                BasicBehaviorController basicBehaviorController2 = new BasicBehaviorController(ctx, this.mHandler, this.mOnStatisListener, mStatisAPI, getOptions().backgroundDurationMillisAsQuit, getOptions().behaviorSendThreshold, 10);
                mBasicBehaviorController = basicBehaviorController2;
                basicBehaviorController = basicBehaviorController2;
            }
        }
        return basicBehaviorController;
    }

    private Context getCtx(Context context) {
        return context == null ? this.mContext : context;
    }

    private PageActionReporter getPageActionReporter() {
        BasicBehaviorController behaviorCollector = getBehaviorCollector(getCtx(this.mContext));
        return behaviorCollector == null ? null : behaviorCollector.getPageActionCollector();
    }

    private String getPageId(Activity activity) {
        return activity != null ? activity.getClass().getName() : "";
    }

    private void initData(Context context, StatisOption statisOption, OnStatisListener onStatisListener) {
        this.mContext = context == null ? this.mContext : context.getApplicationContext();
        if (onStatisListener == null) {
            C1923L.brief("the Input listener is null ,so get the default listener instead", new Object[0]);
            this.mOnStatisListener = nullListener;
        } else {
            this.mOnStatisListener = onStatisListener;
        }
        if (statisOption == null) {
            C1923L.brief("the Input sOption is null ,so get the default sOption instead", new Object[0]);
        } else {
            this.mStatisOption = statisOption;
        }
        if (Util.empty(this.mStatisOption.getAppkey())) {
            this.mStatisOption.setAppkey(Util.getMetaDataParam(this.mContext, HdStatisConfig.META_DATA_KEY_APP_KEY));
        }
        if (Util.empty(this.mStatisOption.getFrom())) {
            this.mStatisOption.setFrom(Util.getMetaDataParam(this.mContext, HdStatisConfig.META_DATA_KEY_CHANNEL));
        }
        if (Util.empty(this.mStatisOption.getVer())) {
            this.mStatisOption.setVer(Util.getVersionName(this.mContext));
        }
        mStatisAPI.init(this.mContext, this.mStatisOption);
        mStatisAPI.setTestServer(getOptions().testServer);
        mConfigApi = new ConfigAPI(this.mContext, this.mStatisOption.getAppkey());
        ActLog.uploadLog(this.mContext, new C18752());
        mSdkAnalyzeController = new SdkAnalyzeController(mStatisAPI, mConfigApi);
        mSdkVerController = new SdkVerController(mConfigApi);
        mAppAnalyzeController = new AppAnalyzeController(mStatisAPI, mConfigApi);
        mInstallController = new InstallController(mStatisAPI);
        mDeviceController = new DeviceController(mStatisAPI);
        mOnLineConfigController = new OnLineConfigController(mConfigApi);
        mContactAnalyzeStatisAPI = new ContactAnalyzeStatisAPI(mStatisAPI);
    }

    public static HiidoSDK instance() {
        return sApi;
    }

    private void onQuitApp(boolean z) {
        if (this.mContext == null) {
            C1923L.error(this, "No context, cannot do quit things properly, data lost.", new Object[0]);
            return;
        }
        GeneralProxy.exit(getContext(), z);
        Counter counter = this.mHeartbeatInvoker;
        Counter counter2 = this.mHeartbeatInvoker5;
        Counter counter3 = this.mActionReportInvoker;
        if (counter != null) {
            counter.stop();
        }
        if (counter2 != null) {
            counter2.stop();
        }
        if (counter3 != null) {
            counter3.stop();
        }
        this.mHeartbeatReportExecutor = null;
        this.mHeartbeatReportExecutor5 = null;
        this.mActionReportExecutor = null;
        this.mActionReportInvoker = null;
        mIsActionReportStarted = false;
        AppActionReporter peekAppaActionReporter = peekAppaActionReporter();
        if (peekAppaActionReporter != null) {
            peekAppaActionReporter.onExitApp(false, z);
        } else {
            C1923L.error(this, "No behavior reporter to report app action, sdk not initialized.", new Object[0]);
        }
        if (z) {
            BgReportTimer.start(this.mHandler, this.mContext, new C18763());
        }
    }

    private AppActionReporter peekAppaActionReporter() {
        BasicBehaviorController basicBehaviorController = mBasicBehaviorController;
        if (basicBehaviorController != null) {
            return basicBehaviorController.getAppActionCollector();
        }
        AppActionReporter appActionCollector;
        synchronized (this) {
            basicBehaviorController = mBasicBehaviorController;
            appActionCollector = basicBehaviorController == null ? null : basicBehaviorController.getAppActionCollector();
        }
        return appActionCollector;
    }

    private void quitApp(boolean z) {
        try {
            if (this.mState == 1) {
                if (!z) {
                    getPageActionReporter().onLeavingUI(null, null);
                    isResumeCall = false;
                }
                getPageActionReporter().onFinishGotoUI(this.mOnStatisListener == null ? 0 : this.mOnStatisListener.getCurrentUid(), null, true);
                onQuitApp(z);
                this.mState = 2;
                C1923L.info(this, "app quit. it is one appa finish. isNormal quit is [%b]。", Boolean.valueOf(z));
            }
        } catch (Exception e) {
            C1923L.error(this, "quitApp exception =%s", e);
        }
    }

    private void reportDo(Context context, long j) {
        try {
            mStatisAPI.reportDo(j);
            C1923L.info(this, "report heart beat for %d", Long.valueOf(j));
        } catch (Exception e) {
            C1923L.error(this, "report heart beat for %d.exception=%s", Long.valueOf(j), e);
        }
    }

    private void reportDo5(Context context, long j) {
        try {
            mStatisAPI.reportDo5(j);
            C1923L.info(this, "report heart beat 5 for %d", Long.valueOf(j));
        } catch (Exception e) {
            C1923L.error(this, "report heart beat 5 for %d.exception=%s", Long.valueOf(j), e);
        }
    }

    private void reportOnAppStartLaunch(Context context, OnStatisListener onStatisListener) {
        try {
            mSdkVerController.startSdkVerCheck(context);
            mStatisAPI.generateSession();
            getAppActionReporter().onStartApp();
            sendInstallationReportIfNotYet(context);
            reportRun(context, onStatisListener.getCurrentUid());
            reportDo(context, onStatisListener.getCurrentUid());
            mDeviceController.reportDevice(context, onStatisListener.getCurrentUid());
            mAppAnalyzeController.reportAppAnalyze(context, onStatisListener.getCurrentUid());
            mSdkAnalyzeController.reportSdkAnalyze(context, onStatisListener.getCurrentUid());
            startHeartbeatReport();
            startActionReportTimer(context);
            if (getOptions().isOpenDo5) {
                reportDo5(context, onStatisListener.getCurrentUid());
                startHeartbeatReport5();
            }
            GeneralProxy.flushCache(context);
            C1923L.brief("isContactReport = %s", Util.getMetaDataParam(context, "HIIDO_CONTACTS_REPORT"));
            C1923L.brief("isContactReport = %b", Boolean.valueOf(Boolean.parseBoolean(Util.getMetaDataParam(context, "HIIDO_CONTACTS_REPORT"))));
            if (Boolean.parseBoolean(Util.getMetaDataParam(context, "HIIDO_CONTACTS_REPORT"))) {
                mContactAnalyzeStatisAPI.reportContactAnalyze(context, onStatisListener.getCurrentUid());
            }
            BgReportTimer.stop(this.mContext);
        } catch (Exception e) {
            C1923L.error(this, "reportOnAppStartLaunch exception =%s", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void reportRun(Context context, long j) {
        try {
            if (this.mState == -1 || this.mState == 2) {
                mStatisAPI.reportRun(j);
                C1923L.info(this, "reportRun call", new Object[0]);
                return;
            }
            C1923L.warn(this, "reportRun has been called, one launch only one call!", new Object[0]);
        } catch (Exception e) {
            C1923L.error(this, "reportRun exception=%s", e);
        }
    }

    private void sendInstallationReportIfNotYet(Context context) {
        Context ctx = getCtx(context);
        if (ctx == null) {
            C1923L.error(this, "Input context is null,sdk is not init?", new Object[0]);
        } else {
            mInstallController.sendInstallationReportIfNotYet(ctx);
        }
    }

    private void startActionReportTimer(Context context) {
        if (!mIsActionReportStarted) {
            final BasicBehaviorController behaviorCollector = getBehaviorCollector(context);
            if (behaviorCollector == null) {
                C1923L.error(this, "Failed to create BasicBehaviorCollector, probably for context is null.", new Object[0]);
                return;
            }
            Callback callback = this.mActionReportExecutor;
            Counter counter = this.mActionReportInvoker;
            if (callback == null || counter == null || !counter.running()) {
                int i = getOptions().behaviorSendIntervalMillis;
                final int max = Math.max(Math.min(i, 1800000), 60000);
                if (i != max) {
                    C1923L.warn(this, "Sending behavior interval corrected to %d millis.", Integer.valueOf(max));
                }
                i = max / 60000;
                if (i == 0) {
                    i = 1;
                }
                counter = new Counter(this.mHandler, 0, 60000, true);
                this.mActionReportInvoker = counter;
                Callback c18774 = new Callback() {
                    private int mIgnored = 0;

                    public void onCount(int i) {
                        C1923L.brief("ActionReport Counter callback %d times, ignored %d times.", Integer.valueOf(i), Integer.valueOf(this.mIgnored));
                        try {
                            C1923L.brief("flush data: %d times", Integer.valueOf(i));
                            GeneralProxy.flushCache(HiidoSDK.this.getContext());
                            if (i % i != 0) {
                                this.mIgnored++;
                                return;
                            }
                        } catch (Exception e) {
                        }
                        if (!behaviorCollector.isReported()) {
                            behaviorCollector.sendReportForce(false);
                        } else if (Util.cpuMillis() - behaviorCollector.getLastReportCpuMillis() >= ((long) (max / 4))) {
                            behaviorCollector.sendReportForce(false);
                        } else {
                            this.mIgnored++;
                        }
                    }
                };
                this.mActionReportExecutor = c18774;
                counter.setCallback(c18774);
                counter.start(0);
                mIsActionReportStarted = true;
                C1923L.info(this, "ActionReportTimer start ", new Object[0]);
                return;
            }
            C1923L.warn(this, "ActionReportTimer has been started ", new Object[0]);
        }
    }

    private void startCrashMonitor() {
        C1923L.info(this, "isOpenCrashMonitor is %b", Boolean.valueOf(getOptions().isOpenCrashMonitor));
        if (!getOptions().isOpenCrashMonitor) {
            return;
        }
        if (this.mCrashController != null) {
            C1923L.warn(this, "crash monitor has been started.", new Object[0]);
            return;
        }
        this.mCrashController = new CrashController(mStatisAPI, this.mOnStatisListener, new C18817());
        this.mCrashController.startCrashMonitor();
        C1923L.info(this, "crash monitor start", new Object[0]);
    }

    private void startHeartbeatReport() {
        if (this.mHeartbeatReportExecutor != null) {
            C1923L.warn(this, "heart beat as for mbsdkdo has been started.", new Object[0]);
            return;
        }
        Callback c18785 = new C18785();
        this.mHeartbeatReportExecutor = c18785;
        this.mHeartbeatInvoker.setCallback(c18785);
        this.mHeartbeatInvoker.start(this.mHeartbeatInvoker.getInterval());
        C1923L.info(this, "start heart beat invoker for mbsdkdo.", new Object[0]);
    }

    private void startHeartbeatReport5() {
        if (this.mHeartbeatReportExecutor5 != null) {
            C1923L.warn(this, "heart beat as for mbsdkdo 5 has been started.", new Object[0]);
            return;
        }
        Callback c18796 = new C18796();
        this.mHeartbeatReportExecutor5 = c18796;
        this.mHeartbeatInvoker5.setCallback(c18796);
        this.mHeartbeatInvoker5.start(this.mHeartbeatInvoker5.getInterval());
        C1923L.info(this, "start heart beat invoker for mbsdkdo 5.", new Object[0]);
    }

    public void addActAdditionListener(ActListener actListener) {
        mStatisAPI.addActAdditionListener(actListener);
    }

    public void appStartLaunchWithAppKey(Context context, StatisOption statisOption, OnStatisListener onStatisListener) {
        if (mIsInit) {
            C1923L.warn(this, "sdk only be init once", new Object[0]);
            return;
        }
        mIsInit = true;
        C1923L.setLogOn(getOptions().isLogOn);
        initData(context, statisOption, onStatisListener);
        startCrashMonitor();
    }

    public void appStartLaunchWithAppKey(Context context, String str, String str2, String str3, OnStatisListener onStatisListener) {
        StatisOption statisOption = new StatisOption();
        statisOption.setAppId(str2);
        statisOption.setAppkey(str);
        statisOption.setFrom(str3);
        appStartLaunchWithAppKey(context, statisOption, onStatisListener);
    }

    public StatisAPI createNewStatisApi() {
        return new StatisAPI();
    }

    public String getAppId() {
        return this.mStatisOption.getAppId();
    }

    public String getAppKey() {
        return this.mStatisOption.getAppkey();
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDeviceId(Context context) {
        return CommonFiller.getIMEI(context);
    }

    public String getFrom() {
        return this.mStatisOption.getFrom();
    }

    public String getMac(Context context) {
        return CommonFiller.getMacAddr(context);
    }

    public OnStatisListener getOnStatisListener() {
        return this.mOnStatisListener;
    }

    public String getOnlineConfigParams(Context context, String str) {
        if (context == null) {
            context = this.mContext;
        }
        if (context != null) {
            return mOnLineConfigController.getOnlineConfigParams(context, str);
        }
        C1923L.warn(this, "getOnlineConfigParams error,Input context is null", new Object[0]);
        return null;
    }

    public Options getOptions() {
        return this.mOptions;
    }

    public StatisOption getStatisOption() {
        return this.mStatisOption;
    }

    public void onPause(Activity activity, PageActionReportOption pageActionReportOption) {
        onPause(getPageId(activity), pageActionReportOption);
    }

    public void onPause(String str, PageActionReportOption pageActionReportOption) {
        try {
            if (isResumeCall) {
                if (pageActionReportOption == PageActionReportOption.DO_NOT_REPORT_ON_FUTURE_RESUME) {
                    C1923L.info(this, " DO_NOT_REPORT_ON_FUTURE_RESUME,Clear current page element on page %s", str);
                    getPageActionReporter().clearCurPageElement();
                } else {
                    getPageActionReporter().onLeavingUI(str, null);
                }
                C1923L.debug(this, "startQuitTimer in onPause", new Object[0]);
                this.mQuittimer.startQuitTimer();
                isResumeCall = false;
                getBehaviorCollector(getCtx(this.mContext)).saveLastOnPauseTime(Util.wallTimeMillis());
                return;
            }
            C1923L.error(this, "call onPause() must call onResume() first", new Object[0]);
        } catch (Exception e) {
            C1923L.error(this, "onPause exception =%s", e);
        }
    }

    public void onResume(long j, Activity activity) {
        onResume(j, getPageId(activity));
    }

    public void onResume(long j, String str) {
        try {
            C1923L.debug(this, "clearQuitTimer in onResume", new Object[0]);
            this.mQuittimer.clearQuitTimer();
            if (this.mState == 2 || this.mState == -1) {
                C1923L.info(this, "app enter. it is a new appa begin", new Object[0]);
                reportOnAppStartLaunch(this.mContext, this.mOnStatisListener);
                AppActionReporter appActionReporter = getAppActionReporter();
                if (appActionReporter != null) {
                    appActionReporter.onAppStarted();
                }
                this.mState = 1;
            }
            PageActionReporter pageActionReporter = getPageActionReporter();
            if (pageActionReporter != null) {
                pageActionReporter.onResumeUI(j, str);
            }
            isResumeCall = true;
            Report.onEvent(getContext());
        } catch (Exception e) {
            C1923L.error(this, "onResume exception =%s", e);
        }
    }

    public void removeActAdditionListerner(ActListener actListener) {
        mStatisAPI.removeActAdditionListener(actListener);
    }

    public void reportCountEvent(long j, String str, double d) {
        reportCountEvent(j, str, d, null);
    }

    public void reportCountEvent(long j, String str, double d, String str2) {
        reportCountEvent(j, str, d, str2, null);
    }

    public void reportCountEvent(long j, String str, double d, String str2, Property property) {
        mStatisAPI.reportCountEvent(j, str, d, str2, property);
    }

    public void reportCrash(long j, String str) {
        mStatisAPI.reportCrash(j, str);
    }

    public void reportCrash(long j, Throwable th) {
        mStatisAPI.reportCrash(j, th);
    }

    public void reportCustomContent(long j, String str, String str2) {
        if (this.mContext == null) {
            C1923L.error(this, "Input context is null,sdk is not init?", new Object[0]);
        } else {
            mStatisAPI.reportCustomContent(j, str, str2);
        }
    }

    public void reportErrorEvent(long j, String str, String str2, String str3) {
        mStatisAPI.reportError(j, str, str2, str3);
    }

    public void reportFailure(long j, String str, String str2, String str3, String str4, String str5) {
        if (this.mContext == null) {
            C1923L.error(this, "Input context is null,sdk is not init?", new Object[0]);
        } else {
            mStatisAPI.reportFailure(j, str, str2, str3, str4, str5);
        }
    }

    public boolean reportFeedBack(String str, String str2, String str3) {
        String str4 = null;
        try {
            str4 = Coder.encryptMD5(UUID.randomUUID().toString());
        } catch (Exception e) {
        }
        return mStatisAPI.reportFeedback(this.mOnStatisListener.getCurrentUid(), str4, str, str2, str3);
    }

    public void reportLogin(long j) {
        mStatisAPI.reportLogin(j);
        mStatisAPI.reportDo(j);
        if (getOptions().isOpenDo5) {
            mStatisAPI.reportDo5(j);
        }
    }

    public void reportStatisticContent(String str, StatisContent statisContent) throws Throwable {
        mStatisAPI.reportStatisticContent(str, statisContent, true, true);
    }

    public void reportStatisticContent(String str, StatisContent statisContent, boolean z) throws Throwable {
        mStatisAPI.reportStatisticContent(str, statisContent, true, true, z);
    }

    public void reportStatisticContentWithNoComm(Context context, String str, StatisContent statisContent) throws Throwable {
        mStatisAPI.reportStatisticContentWithNoComm(getCtx(context), str, statisContent);
    }

    public void reportStatisticContentWithNoComm(Context context, String str, StatisContent statisContent, boolean z) throws Throwable {
        mStatisAPI.reportStatisticContentWithNoComm(getCtx(context), str, statisContent, z);
    }

    public void reportSuccess(long j, String str, String str2, long j2, String str3) {
        if (this.mContext == null) {
            C1923L.error(this, "Input context is null,sdk is not init?", new Object[0]);
        } else {
            mStatisAPI.reportSuccess(j, str, str2, j2, str3);
        }
    }

    public void reportTimesEvent(long j, String str) {
        reportTimesEvent(j, str, null);
    }

    public void reportTimesEvent(long j, String str, String str2) {
        reportTimesEvent(j, str, str2, null);
    }

    public void reportTimesEvent(long j, String str, String str2, Property property) {
        mStatisAPI.reportTimesEvent(j, str, str2, property);
    }

    public HiidoSDK setLogWriter(StatisLogWriter statisLogWriter) {
        C1923L.setLogSetting(statisLogWriter);
        return this;
    }

    public void setOnLineConfigListener(OnLineConfigListener onLineConfigListener) {
        mOnLineConfigController.setOnLineConfigListener(onLineConfigListener);
    }

    public void setOptions(Options options) {
        if (options == null) {
            this.mOptions = new Options();
        } else {
            this.mOptions = options;
        }
    }

    public void updateOnlineConfigs(Context context) {
        if (context == null) {
            context = this.mContext;
        }
        if (context == null) {
            C1923L.warn(this, "updateOnlineConfigs error,Input context is null", new Object[0]);
        } else {
            mOnLineConfigController.updateOnlineConfigs(context, getAppKey());
        }
    }
}
