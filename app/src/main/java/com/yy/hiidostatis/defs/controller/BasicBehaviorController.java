package com.yy.hiidostatis.defs.controller;

import android.content.Context;
import android.os.Handler;

import com.yy.hiidostatis.defs.interf.IOnStatisListener;
import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.defs.obj.AppaElemInfo;
import com.yy.hiidostatis.defs.obj.AppaInfo;
import com.yy.hiidostatis.defs.obj.EventElementInfo;
import com.yy.hiidostatis.defs.obj.EventInfo;
import com.yy.hiidostatis.defs.obj.Info;
import com.yy.hiidostatis.defs.obj.PageElemInfo;
import com.yy.hiidostatis.defs.obj.PageInfo;
import com.yy.hiidostatis.defs.obj.Property;
import com.yy.hiidostatis.inner.implementation.RawDataSerializer;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.ProcessUtil;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BasicBehaviorController {
    public static final boolean EMPTY_DATA_FORBIDDEN = false;
    private static final long EMPTY_VALUE = 0;
    private static final String FILE_APPA = "Hiido_BasicBehavior_Appa_v3";
    private static final String FILE_EVENT = "Hiido_BasicBehavior_Event_v3";
    private static final String FILE_PAGE = "Hiido_BasicBehavior_Page_v3";
    private static final String KEY_LAST_ONPAUSE_TIME = "PREF_KEY_StatisSDK_LAST_ONPAUSE_TIME";
    private static final String KEY_QUIT_TIME = "PREF_KEY_StatisSDK_QuitTime";
    private static final String KEY_SESSION = "PREF_KEY_StatisSDK_SESSION";
    private static final String KEY_UID = "PREF_KEY_StatisSDK_UID";
    private static final long UN_INITED = -1;
    private String appaFileName = FILE_APPA;
    private String eventFileName = FILE_EVENT;
    private final AppActionReporter mAppActionCollector = new AppActionReporter();
    private long mBackgroundDurationMillisAsQuit;
    private final Context mContext;
    private final EventReporter mEventCollector = new EventReporter();
    private volatile boolean mIsLoaded = false;
    private long mLastQuitTimeMillis = -1;
    private long mLastReportCpuMillis;
    private int mMaxbehaviorSendThreshold;
    private final IOnStatisListener mOnStatisListener;
    private final PageActionReporter mPageActionCollector = new PageActionReporter();
    private IStatisAPI mStatisAPI;
    private int mbehaviorSendThreshold;
    private String pageFileName = FILE_PAGE;
    private long r2;
    private long r0;
    private String r4;

    public class AppActionReporter {
        final /* synthetic */ boolean $assertionsDisabled = (!BasicBehaviorController.class.desiredAssertionStatus());
        private final AppaInfo mAppaInfo = new AppaInfo();
        private long mBeginStartCpuTimeMillis;
        private volatile AppaElemInfo mElemInfo;
        private long mEndStartCpuTimeMillis;

        private void createElemIfNull() {
            if (this.mElemInfo == null) {
                this.mElemInfo = new AppaElemInfo();
            }
        }

        private boolean isStartCalled() {
            return this.mBeginStartCpuTimeMillis != 0;
        }

        private boolean isStartedCalled() {
            return this.mEndStartCpuTimeMillis != 0;
        }

        private void onExitApp(boolean z, boolean z2, boolean z3) {
            long lastOnPauseTime;
            C1923L.brief("appa onExitApp: shutdown %b flush commands %b. isNormal %b", Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3));
            AppaElemInfo elem = this.mElemInfo;
            long wallTimeMillis = Util.wallTimeMillis();
            if (z3) {
                lastOnPauseTime = BasicBehaviorController.this.getLastOnPauseTime();
                long access$300 = BasicBehaviorController.this.mBackgroundDurationMillisAsQuit;
                if (lastOnPauseTime < wallTimeMillis && lastOnPauseTime - this.mBeginStartCpuTimeMillis > 0 && wallTimeMillis - lastOnPauseTime > access$300 - (access$300 / 2) && wallTimeMillis - lastOnPauseTime < access$300 + (access$300 / 2)) {
                    C1923L.brief("appa onExitApp:get the lastOnPauseTime[%d] instead of quitTime[%d]", Long.valueOf(lastOnPauseTime), Long.valueOf(wallTimeMillis));
                    if (elem == null && isStartCalled() && isStartedCalled()) {
                        wallTimeMillis = this.mBeginStartCpuTimeMillis;
                        C1923L.brief("Start CPU time millis is %d", Long.valueOf(wallTimeMillis));
                        if (wallTimeMillis != 0) {
                            access$300 = lastOnPauseTime - wallTimeMillis;
                            C1923L.brief("Calculated usage time, begin %d,end %d, lasts %d", Long.valueOf(wallTimeMillis), Long.valueOf(lastOnPauseTime), Long.valueOf(access$300));
                            if (access$300 != 0) {
                                C1923L.brief("set app linger time %d sec", Long.valueOf(access$300));
                                elem.setLingerTime(access$300);
                            } else {
                                C1923L.error(this, "appa onExitApp:Cannot calculate app action linger time.", new Object[0]);
                            }
                            if (access$300 > 21600000 || access$300 < 0) {
                                C1923L.warn(this, "appa onExitApp:app action linger time [%d] is off normal.", Long.valueOf(access$300));
                            } else {
                                C1923L.brief("appa onExitApp:normal", Long.valueOf(access$300));
                            }
                            this.mAppaInfo.addElem(elem);
                        }
                    } else {
                        C1923L.error(this, "appa onExitApp:Failed to statis app usage time .elemInfo[%s] is null or mBeginStartCpuTimeMillis[%d]=0 or mEndStartCpuTimeMillis[%d]=0", elem, Long.valueOf(this.mBeginStartCpuTimeMillis), Long.valueOf(this.mEndStartCpuTimeMillis));
                        BasicBehaviorController.this.clearStoredAppaInfo();
                    }
                    resetData();
                    BasicBehaviorController.this.saveQuitTimeMillis(lastOnPauseTime);
                    BasicBehaviorController.this.saveUid();
                    BasicBehaviorController.this.sendReportForce(false);
                }
            }
            lastOnPauseTime = wallTimeMillis;
            if (elem == null) {
            }
            C1923L.error(this, "appa onExitApp:Failed to statis app usage time .elemInfo[%s] is null or mBeginStartCpuTimeMillis[%d]=0 or mEndStartCpuTimeMillis[%d]=0", elem, Long.valueOf(this.mBeginStartCpuTimeMillis), Long.valueOf(this.mEndStartCpuTimeMillis));
            BasicBehaviorController.this.clearStoredAppaInfo();
            resetData();
            BasicBehaviorController.this.saveQuitTimeMillis(lastOnPauseTime);
            BasicBehaviorController.this.saveUid();
            BasicBehaviorController.this.sendReportForce(false);
        }

        private void onRecordPagePath(String... strArr) {
            addParams(strArr);
        }

        private void onSaveAppaFile(final AppaInfo appaInfo) {
            ThreadPool.getPool().executeQueue(new Runnable() {
                public void run() {
                    try {
                        BasicBehaviorController.this.saveAppaInfo(appaInfo);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }

        private void onSaveTmpAppa(String str) {
            AppaInfo appaInfo = new AppaInfo();
            appaInfo.add(this.mAppaInfo);
            AppaElemInfo copy = this.mElemInfo.copy();
            copy.setLingerTime(Util.wallTimeMillis() - this.mBeginStartCpuTimeMillis);
            if (!Util.empty(str)) {
                copy.addParam(str);
            }
            appaInfo.addElem(copy);
            onSaveAppaFile(appaInfo);
        }

        private void resetData() {
            this.mElemInfo = null;
            this.mEndStartCpuTimeMillis = 0;
            this.mBeginStartCpuTimeMillis = 0;
        }

        public void addParams(String... strArr) {
            if (this.mElemInfo == null) {
                createElemIfNull();
            }
            if (strArr != null) {
                try {
                    for (String addParam : strArr) {
                        this.mElemInfo.addParam(addParam);
                    }
                } catch (Exception e) {
                    C1923L.warn(this, "addParams :exception %s", e);
                }
            }
        }

        void clear() {
            this.mAppaInfo.clear();
            onSaveAppaFile(this.mAppaInfo);
        }

        AppaInfo getAppaInfo() {
            return this.mAppaInfo;
        }

        public void onAppStarted() {
            C1923L.brief("appa onAppStarted: entry", new Object[0]);
            if (isStartedCalled()) {
                C1923L.error(this, "appa onAppStarted : already called. mEndStartCpuTimeMillis is %d", Long.valueOf(this.mEndStartCpuTimeMillis));
                return;
            }
            this.mEndStartCpuTimeMillis = Util.wallTimeMillis();
            long j = 0;
            if (isStartCalled()) {
                j = this.mEndStartCpuTimeMillis - this.mBeginStartCpuTimeMillis;
                C1923L.brief("appa :launch delayed : %d millis", Long.valueOf(j));
                if (this.mElemInfo != null) {
                    this.mElemInfo.setDtime(j);
                }
            }
            C1923L.brief("appa onAppStarted: mBeginStartCpuTimeMillis [%d],mEndStartCpuTimeMillis[%d],Dtimes[%d] ", Long.valueOf(this.mBeginStartCpuTimeMillis), Long.valueOf(this.mEndStartCpuTimeMillis), Long.valueOf(j));
        }

        public void onExitApp(boolean z, boolean z2) {
            onExitApp(false, z, z2);
        }

        public void onStartApp() {
            C1923L.brief("appa onStartApp: init app data", new Object[0]);
            resetData();
            createElemIfNull();
            this.mBeginStartCpuTimeMillis = Util.wallTimeMillis();
            C1923L.brief("Begin Start Cpu Time Millis is %d", Long.valueOf(this.mBeginStartCpuTimeMillis));
            if (this.mElemInfo != null) {
                this.mElemInfo.setStime(this.mBeginStartCpuTimeMillis);
            }
            long access$600 = BasicBehaviorController.this.getLastQuitTime();
            if ($assertionsDisabled || access$600 != -1) {
                C1923L.brief("Loaded last quit time is %d", Long.valueOf(access$600));
                if (access$600 != 0) {
                    C1923L.brief("set ftime wall time %d - last quit time %d = %d", Long.valueOf(this.mBeginStartCpuTimeMillis), Long.valueOf(access$600), Long.valueOf(this.mBeginStartCpuTimeMillis - access$600));
                    if (this.mElemInfo != null) {
                        this.mElemInfo.setFtime(r2);
                        return;
                    }
                    return;
                }
                C1923L.debug(this, "Last quit time is empty value %d", Long.valueOf(access$600));
                return;
            }
            throw new AssertionError();
        }
    }

    public class EventReporter {
        private final EventInfo mEventInfo = new EventInfo();

        private void onSaveEventFile(final EventInfo eventInfo) {
            ThreadPool.getPool().executeQueue(new Runnable() {
                public void run() {
                    try {
                        BasicBehaviorController.this.saveEventInfo(eventInfo);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }

        public void addCustomEvent(long j, String str, String str2, String str3, Property property) {
            if (Util.empty(str)) {
                C1923L.error(this, "EventId cannot be null or empty : addCustomEvent", new Object[0]);
                return;
            }
            EventElementInfo eventElementInfo = new EventElementInfo(str, str2);
            eventElementInfo.addParam(str3);
            eventElementInfo.setProperty(property);
            this.mEventInfo.addElem(eventElementInfo);
            BasicBehaviorController.this.onNewDataAdded(j);
            onSaveEventFile(this.mEventInfo);
        }

        public void addTimesEvent(long j, String str, int i, String str2, Property property) {
            if (Util.empty(str)) {
                C1923L.warn(this, "EventId cannot be null or empty : addTimesEvent", new Object[0]);
                return;
            }
            if (i <= 0) {
                C1923L.warn(this, "Report times value %d corrected to 1 for %s, uid %d", Integer.valueOf(1), str, Long.valueOf(j));
                i = 1;
            }
            EventElementInfo eventElementInfo = new EventElementInfo(str, i);
            eventElementInfo.addParam(str2);
            eventElementInfo.setProperty(property);
            this.mEventInfo.addElem(eventElementInfo);
            BasicBehaviorController.this.onNewDataAdded(j);
            onSaveEventFile(this.mEventInfo);
        }

        void clear() {
            int elemsCount = this.mEventInfo.getElemsCount();
            this.mEventInfo.clear();
            C1923L.brief("Clear event info , old %d", Integer.valueOf(elemsCount));
            onSaveEventFile(this.mEventInfo);
        }

        EventInfo getEventInfo() {
            return this.mEventInfo;
        }
    }

    public class PageActionReporter {
        private long mEnterTimeStamp;
        private PageElemInfo mPageElemInfo;
        private final PageInfo mPageInfo = new PageInfo();
        private long mStartJumpingTimeStamp;

        private void onSavePageFile(final PageInfo pageInfo) {
            ThreadPool.getPool().executeQueue(new Runnable() {
                public void run() {
                    try {
                        BasicBehaviorController.this.savePageInfo(pageInfo);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }

        private void onSaveTmpPage() {
            PageInfo pageInfo = new PageInfo();
            pageInfo.add(this.mPageInfo);
            pageInfo.addElem(this.mPageElemInfo);
            onSavePageFile(pageInfo);
            BasicBehaviorController.this.saveTmpAppa(this.mPageElemInfo.getPage());
        }

        void clear() {
            this.mPageInfo.clear();
            onSavePageFile(this.mPageInfo);
        }

        public void clearCurPageElement() {
            this.mPageElemInfo = null;
            this.mEnterTimeStamp = 0;
            this.mStartJumpingTimeStamp = 0;
            C1923L.brief("clear curpage element !", new Object[0]);
        }

        PageInfo getPageInfo() {
            return this.mPageInfo;
        }

        public void onFinishGotoUI(long j, String str, boolean z) {
            if (this.mPageElemInfo != null) {
                String page = this.mPageElemInfo.getPage();
                if (Util.empty(page) || this.mStartJumpingTimeStamp == 0 || this.mEnterTimeStamp == 0) {
                    C1923L.error(this, "page onFinishGotoUI [%s]: Illegal state exception.pageid[%s] is null or mEnterTimeStamp[%d]=0 or mStartJumpingTimeStamp[%d]=0 ", page, page, Long.valueOf(this.mEnterTimeStamp), Long.valueOf(this.mStartJumpingTimeStamp));
                    return;
                }
                if (z) {
                    this.mPageElemInfo.setDestinationPage(null);
                    this.mPageElemInfo.setDtime(0);
                } else {
                    long wallTimeMillis = Util.wallTimeMillis();
                    this.mPageElemInfo.setDestinationPage(str);
                    this.mPageElemInfo.setDtime(wallTimeMillis - this.mStartJumpingTimeStamp);
                }
                if (this.mPageElemInfo.getDelayedTime() > BasicBehaviorController.this.mBackgroundDurationMillisAsQuit * 3) {
                    C1923L.warn(this, "page onFinishGotoUI [%s]: Dtime[%d] is off normal,this page data not report", page, Long.valueOf(this.mPageElemInfo.getDelayedTime()));
                    clearCurPageElement();
                    return;
                }
                C1923L.brief("page onFinishGotoUI [%s]:normal. report from page [%s] to destPageId [%s]", page, page, str);
                this.mPageInfo.addElem(this.mPageElemInfo);
                clearCurPageElement();
                C1923L.brief("Page elements %d", Integer.valueOf(this.mPageInfo.getElemsCount()));
                BasicBehaviorController.this.onNewDataAdded(j);
                onSavePageFile(this.mPageInfo);
                BasicBehaviorController.this.recordPagePath(page);
                BasicBehaviorController.this.saveTmpAppa(null);
                return;
            }
            C1923L.error(this, "page onFinishGotoUI , Illegal state exception, is onResumeUI,onLeavingUI not called? mPageElemInfo is null", new Object[0]);
        }

        public void onLeavingUI(String str, String str2) {
            if (this.mPageElemInfo == null) {
                C1923L.error(this, "page onLeavingUI [%s]: Illegal state exception, is onResumeUI not called? mPageElemInfo is null. ", str);
                return;
            }
            String page = this.mPageElemInfo.getPage();
            if (Util.empty(page) || Util.empty(str) || str.equals(page)) {
                if (page == null) {
                    C1923L.brief("page onLeavingUI [%s]:onResumeUI page[%s] is null,the onLeavingUI page instead of it", str, page, str);
                    this.mPageElemInfo.setPage(str);
                } else {
                    str = page;
                }
                if (Util.empty(str) || this.mEnterTimeStamp == 0 || this.mStartJumpingTimeStamp != 0) {
                    C1923L.error(this, "page onLeavingUI[%s], Illegal state exception. pageid[%s] is null or mEnterTimeStamp[%d] = 0 or mStartJumpingTimeStamp[%d]!=0.", str, str, Long.valueOf(this.mEnterTimeStamp), Long.valueOf(this.mStartJumpingTimeStamp));
                    return;
                }
                this.mStartJumpingTimeStamp = Util.wallTimeMillis();
                this.mPageElemInfo.setLtime(this.mStartJumpingTimeStamp - this.mEnterTimeStamp);
                this.mPageElemInfo.setDestinationPage(str2);
                C1923L.brief("page onLeavingUI [%s]:normal. pageid[%s], lingerTimeMillis[%d], mStartJumpingTimeStamp[%d]", str, str, Long.valueOf(r0), Long.valueOf(this.mStartJumpingTimeStamp));
                onSaveTmpPage();
                return;
            }
            C1923L.error(this, "page onLeavingUI [%s]: onLeavingUI page[%s] is not euqal onResumeUI page[%s]", page, str, page);
        }

        public void onResumeUI(long j, String str) {
            if (this.mPageElemInfo != null) {
                onFinishGotoUI(j, str, false);
            }
            clearCurPageElement();
            this.mPageElemInfo = new PageElemInfo();
            this.mPageElemInfo.setPage(str);
            this.mEnterTimeStamp = Util.wallTimeMillis();
            this.mPageElemInfo.setStime(this.mEnterTimeStamp);
            C1923L.brief("page onResumeUI [%s]:normal. init page data,pageid[%s],mEnterTimeStamp[%d]", str, str, Long.valueOf(this.mEnterTimeStamp));
        }
    }

    public BasicBehaviorController(Context context, Handler handler, IOnStatisListener iOnStatisListener, IStatisAPI iStatisAPI, long j, int i, int i2) {
        this.mContext = context;
        this.mOnStatisListener = iOnStatisListener;
        this.mStatisAPI = iStatisAPI;
        this.appaFileName = ProcessUtil.getFileNameBindProcess(context, this.appaFileName);
        this.pageFileName = ProcessUtil.getFileNameBindProcess(context, this.pageFileName);
        this.eventFileName = ProcessUtil.getFileNameBindProcess(context, this.eventFileName);
        this.mBackgroundDurationMillisAsQuit = j;
        this.mbehaviorSendThreshold = i;
        this.mMaxbehaviorSendThreshold = i2;
        loadStoredAsync();
    }

    private void clearStoredAppaInfo() {
        clearStoredInfo(this.appaFileName);
    }

    private void clearStoredEventInfo() {
        clearStoredInfo(this.eventFileName);
    }

    private void clearStoredInfo(String str) {
        RawDataSerializer rawDataSerializer = new RawDataSerializer(str);
        try {
            if (rawDataSerializer.openForWrite(this.mContext)) {
                if (rawDataSerializer.haveData()) {
                    rawDataSerializer.dropAll();
                }
                rawDataSerializer.close();
            }
        } finally {
            rawDataSerializer.close();
        }
    }

    private void clearStoredPageInfo() {
        clearStoredInfo(this.pageFileName);
    }

    private static boolean empty(Info<?> info) {
        return info == null || info.getElemsCount() == 0;
    }

    private boolean emptyInfo(Info<?> info) {
        return info == null || info.getElemsCount() == 0;
    }

    private long getLastQuitTime() {
        this.mLastQuitTimeMillis = DefaultPreference.getPreference().getPrefLong(this.mContext, KEY_QUIT_TIME, 0);
        return this.mLastQuitTimeMillis;
    }

    private int getThreshold() {
        int i = this.mbehaviorSendThreshold;
        int i2 = this.mMaxbehaviorSendThreshold;
        i = Math.max(1, Math.min(i, i2));
        if (i < 1 || i > i2) {
            C1923L.error(this, "Error : logical error , threshold result : %d", Integer.valueOf(i));
        }
        return i;
    }

    private AppaInfo loadStoredAppaInfo() throws Throwable {
        return (AppaInfo) loadStoredInfo(this.appaFileName);
    }

    private void loadStoredAsync() {
        if (!this.mIsLoaded) {
            this.mIsLoaded = true;
            C1923L.brief("Load stored async", new Object[0]);
            loadStoredAsyncSend();
        }
    }

    private void loadStoredAsyncSend() {
        final Context context = this.mContext;
        if (context == null) {
            C1923L.error(this, "Illegal state error : no Context set.", new Object[0]);
            return;
        }
        ThreadPool.getPool().executeQueue(new Runnable() {
            public void run() {
                try {
                    AppaInfo access$1300 = BasicBehaviorController.this.loadStoredAppaInfo();
                    PageInfo access$1400 = BasicBehaviorController.this.loadStoredPageInfo();
                    EventInfo access$1500 = BasicBehaviorController.this.loadStoredEventInfo();
                    if (BasicBehaviorController.this.emptyInfo(access$1300) && BasicBehaviorController.this.emptyInfo(access$1400) && BasicBehaviorController.this.emptyInfo(access$1500)) {
                        C1923L.brief("None loaded info", new Object[0]);
                        return;
                    }
                    C1923L.brief("clear stored info", new Object[0]);
                    BasicBehaviorController.this.clearStoredEventInfo();
                    BasicBehaviorController.this.clearStoredPageInfo();
                    BasicBehaviorController.this.clearStoredAppaInfo();
                    int i = 0;
                    int elemsCount = access$1300 == null ? 0 : access$1300.getElemsCount();
                    while (i < elemsCount) {
                        int i2;
                        AppaElemInfo appaElemInfo = (AppaElemInfo) access$1300.getElem(i);
                        if (appaElemInfo.getStime() <= 0 || appaElemInfo.getLingerTime() <= 0) {
                            C1923L.warn(this, "stime[%d] <=0 || lTime[%d)]<=0. giva up current elem", Long.valueOf(appaElemInfo.getStime()), Long.valueOf(appaElemInfo.getLingerTime()));
                            access$1300.removeElem(appaElemInfo);
                            i2 = elemsCount - 1;
                        } else {
                            i2 = elemsCount;
                        }
                        i++;
                        elemsCount = i2;
                    }
                    C1923L.brief("Send old behavior report, for uid %d, session %s", Long.valueOf(BasicBehaviorController.this.getStoredUid(0)), BasicBehaviorController.this.getStoredSession());
                    BasicBehaviorController.this.reportStoreBasicBehavior(context, r2, r4, access$1300, access$1400, access$1500);
                } catch (Exception e) {
                    C1923L.error(this, "loadStoredAsyncSend exception = %S", e);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    private EventInfo loadStoredEventInfo() throws Throwable {
        return (EventInfo) loadStoredInfo(this.eventFileName);
    }

    private <T> T loadStoredInfo(String str) throws Throwable {
        ObjectInputStream objectInputStream;
        Exception e;
        Throwable th;
        RawDataSerializer rawDataSerializer = new RawDataSerializer(str);
        ObjectInputStream objectInputStream2 = null;
        try {
            if (!rawDataSerializer.openForRead(this.mContext)) {
                if (null != null) {
                    try {
                        objectInputStream2.close();
                    } catch (Exception e2) {
                    }
                }
                rawDataSerializer.close();
                return null;
            } else if (rawDataSerializer.haveData()) {
                InputStream inputStream = rawDataSerializer.getInputStream();
                if (inputStream == null) {
                    C1923L.error(this, "Unexpected occasion : have data but failed to get InputStream.", new Object[0]);
                    if (null != null) {
                        try {
                            objectInputStream2.close();
                        } catch (Exception e3) {
                        }
                    }
                    rawDataSerializer.close();
                    return null;
                }
                C1923L.brief("Input stream length is %d for %s", Integer.valueOf(inputStream.available()), str);
                objectInputStream = new ObjectInputStream(inputStream);
                try {
                    C1923L.debug(this, "load info %s from file %s", objectInputStream.readObject(), str);
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (Exception e4) {
                        }
                    }
                    rawDataSerializer.close();
                    T r1 = null;
                    return r1;
                } catch (Exception e5) {
                    e = e5;
                    try {
                        C1923L.error(this, "Failed to load event info from file for %s", e);
                        if (objectInputStream != null) {
                            try {
                                objectInputStream.close();
                            } catch (Exception e6) {
                            }
                        }
                        rawDataSerializer.close();
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (objectInputStream != null) {
                            try {
                                objectInputStream.close();
                            } catch (Exception e7) {
                            }
                        }
                        rawDataSerializer.close();
                        throw th;
                    }
                }
            } else {
                if (null != null) {
                    try {
                        objectInputStream2.close();
                    } catch (Exception e8) {
                    }
                }
                rawDataSerializer.close();
                return null;
            }
        } catch (Exception e9) {
            e = e9;
            objectInputStream = null;
            C1923L.error(this, "Failed to load event info from file for %s", e);
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            rawDataSerializer.close();
            return null;
        } catch (Throwable th3) {
            objectInputStream = null;
            th = th3;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            rawDataSerializer.close();
            throw th;
        }
    }

    private PageInfo loadStoredPageInfo() throws Throwable {
        return (PageInfo) loadStoredInfo(this.pageFileName);
    }

    private void onNewDataAdded(long j) {
        sendReportIfReach(getThreshold());
    }

    private void recordPagePath(String str) {
        getAppActionCollector().onRecordPagePath(str);
    }

    private void reportBasicBehavior(Context context, long j, AppaInfo appaInfo, PageInfo pageInfo, EventInfo eventInfo) {
        String str = null;
        if (context == null) {
            C1923L.error("BasicStatisAPI", "Null context when reporting to hiido, cancelled.", new Object[0]);
            return;
        }
        if (empty(appaInfo) && empty(pageInfo) && empty(eventInfo)) {
            C1923L.debug(BasicBehaviorController.class, "BasicBehaviour discarded, None of appa, page, event has value, %s, %s, %s.", appaInfo, pageInfo, eventInfo);
        }
        C1923L.brief("To report Appa info %s", appaInfo);
        C1923L.brief("To report Page info %s", pageInfo);
        C1923L.brief("To report Event info %s", eventInfo);
        String result = appaInfo == null ? null : appaInfo.getResult();
        String result2 = pageInfo == null ? null : pageInfo.getResult();
        if (eventInfo != null) {
            str = eventInfo.getResult();
        }
        reportBasicBehavior(context, j, result, result2, str);
    }

    private void reportBasicBehavior(Context context, long j, String str, String str2, String str3) {
        if (Util.empty(str) && Util.empty(str2) && Util.empty(str3)) {
            C1923L.brief("Input appa is null && page is null && event is null ", new Object[0]);
            return;
        }
        if (!Util.empty(str)) {
            this.mStatisAPI.reportLanuch(j, str);
        }
        if (!Util.empty(str2)) {
            this.mStatisAPI.reportPage(j, str2);
        }
        if (!Util.empty(str3)) {
            this.mStatisAPI.reportEvent(j, str3);
        }
    }

    private void reportStoreBasicBehavior(Context context, long j, String str, AppaInfo appaInfo, PageInfo pageInfo, EventInfo eventInfo) {
        String str2 = null;
        String result = appaInfo == null ? null : appaInfo.getResult();
        String result2 = pageInfo == null ? null : pageInfo.getResult();
        if (eventInfo != null) {
            str2 = eventInfo.getResult();
        }
        if (Util.empty(result) && Util.empty(result2) && Util.empty(str2)) {
            C1923L.brief("Input appa is null && page is null && event is null ", new Object[0]);
            return;
        }
        IStatisAPI create = this.mStatisAPI.create();
        create.setSession(str);
        create.init(context, this.mStatisAPI.getOption());
        C1923L.info(this, "report stored basicBehavior with new statisAPI [%s]", create);
        if (!Util.empty(result)) {
            create.reportLanuch(j, result);
        }
        if (!Util.empty(result2)) {
            create.reportPage(j, result2);
        }
        if (!Util.empty(str2)) {
            create.reportEvent(j, str2);
        }
    }

    private void saveAppaInfo(AppaInfo appaInfo) throws Throwable {
        saveInfo(appaInfo, this.appaFileName);
    }

    private void saveEventInfo(EventInfo eventInfo) throws Throwable {
        saveInfo(eventInfo, this.eventFileName);
    }

    private <T> void saveInfo(Info<?> info, String str) throws Throwable {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        Throwable th2;
        RawDataSerializer rawDataSerializer = new RawDataSerializer(str);
        ObjectOutputStream objectOutputStream2 = null;
        try {
            if (rawDataSerializer.openForWrite(this.mContext)) {
                rawDataSerializer.dropAll();
                objectOutputStream = new ObjectOutputStream(rawDataSerializer.getOutputStream());
                try {
                    objectOutputStream.writeObject(info);
                    objectOutputStream.flush();
                    C1923L.debug(this, "Saved info %s to file %s", info, str);
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (Exception e) {
                        }
                    }
                    rawDataSerializer.close();
                } catch (IOException e2) {
                    objectOutputStream2 = objectOutputStream;
                    try {
                        C1923L.error(this, "Failed to save %s to %s", info, str);
                        if (objectOutputStream2 != null) {
                            try {
                                objectOutputStream2.close();
                            } catch (Exception e3) {
                            }
                        }
                        rawDataSerializer.close();
                        saveUid();
                        saveSession();
                    } catch (Throwable th3) {
                        th = th3;
                        objectOutputStream = objectOutputStream2;
                        th2 = th;
                        if (objectOutputStream != null) {
                            try {
                                objectOutputStream.close();
                            } catch (Exception e4) {
                            }
                        }
                        rawDataSerializer.close();
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    rawDataSerializer.close();
                    throw th2;
                }
                saveUid();
                saveSession();
            }
            C1923L.error(this, "Failed to open storage %s for write.", str);
            if (objectOutputStream2 != null) {
                try {
                    objectOutputStream2.close();
                } catch (Exception e5) {
                }
            }
            rawDataSerializer.close();
        } catch (IOException e6) {
            C1923L.error(this, "Failed to save %s to %s", info, str);
            if (objectOutputStream2 != null) {
                objectOutputStream2.close();
            }
            rawDataSerializer.close();
            saveUid();
            saveSession();
        } catch (Throwable th32) {
            th = th32;
            objectOutputStream = objectOutputStream2;
            th2 = th;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            rawDataSerializer.close();
            throw th2;
        }
    }

    private void savePageInfo(PageInfo pageInfo) throws Throwable {
        saveInfo(pageInfo, this.pageFileName);
    }

    private void saveQuitTimeMillis(long j) {
        DefaultPreference.getPreference().setPrefLong(this.mContext, KEY_QUIT_TIME, j);
    }

    private void saveSession() {
        DefaultPreference.getPreference().setPrefString(this.mContext, KEY_SESSION, this.mStatisAPI.getSession());
    }

    private void saveTmpAppa(String str) {
        getAppActionCollector().onSaveTmpAppa(str);
    }

    private void saveUid() {
        DefaultPreference.getPreference().setPrefLong(this.mContext, KEY_UID, this.mOnStatisListener.getCurrentUid());
    }

    private void sendReportIfReach(int i) {
        Context context = this.mContext;
        if (context == null) {
            C1923L.error(this, "Illegal state : Context is null.", new Object[0]);
        }
        PageInfo pageInfo = this.mPageActionCollector.getPageInfo();
        int elemsCount = pageInfo.getElemsCount();
        EventInfo eventInfo = this.mEventCollector.getEventInfo();
        int realElemCount = eventInfo.getRealElemCount();
        AppaInfo appaInfo = this.mAppActionCollector.getAppaInfo();
        C1923L.brief("page %d event %d(%d) appa %d, threshold %d", Integer.valueOf(elemsCount), Integer.valueOf(realElemCount), Integer.valueOf(eventInfo.getElemsCount()), Integer.valueOf(appaInfo.getElemsCount()), Integer.valueOf(i));
        if (appaInfo.getElemsCount() >= i) {
            reportBasicBehavior(context, this.mOnStatisListener.getCurrentUid(), appaInfo, null, null);
            this.mAppActionCollector.clear();
        }
        if (elemsCount >= i) {
            reportBasicBehavior(context, this.mOnStatisListener.getCurrentUid(), null, pageInfo, null);
            this.mPageActionCollector.clear();
        }
        if (realElemCount >= i / 2) {
            reportBasicBehavior(context, this.mOnStatisListener.getCurrentUid(), null, null, eventInfo);
            this.mEventCollector.clear();
        }
    }

    public AppActionReporter getAppActionCollector() {
        return this.mAppActionCollector;
    }

    public EventReporter getEventCollector() {
        return this.mEventCollector;
    }

    public long getLastOnPauseTime() {
        return DefaultPreference.getPreference().getPrefLong(this.mContext, KEY_LAST_ONPAUSE_TIME, 0);
    }

    public long getLastReportCpuMillis() {
        return this.mLastReportCpuMillis;
    }

    public PageActionReporter getPageActionCollector() {
        return this.mPageActionCollector;
    }

    protected String getStoredSession() {
        return DefaultPreference.getPreference().getPrefString(this.mContext, KEY_SESSION, null);
    }

    protected long getStoredUid(long j) {
        return DefaultPreference.getPreference().getPrefLong(this.mContext, KEY_UID, j);
    }

    public boolean isReported() {
        return this.mLastReportCpuMillis != 0;
    }

    public void saveLastOnPauseTime(long j) {
        DefaultPreference.getPreference().setPrefLong(this.mContext, KEY_LAST_ONPAUSE_TIME, j);
    }

    public void sendReportForce(boolean z) {
        sendReportIfReach(z ? -1 : 1);
    }
}
