package com.yy.hiidostatis.defs;

import android.content.Context;

import com.umeng.analytics.onlineconfig.C1837a;
import com.yy.hiidostatis.api.StatisContent;
import com.yy.hiidostatis.api.StatisOption;
import com.yy.hiidostatis.api.ga.GaListenerController;
import com.yy.hiidostatis.defs.controller.AppAnalyzeController;
import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.defs.listener.ActAdditionListenerController;
import com.yy.hiidostatis.defs.listener.ActListener;
import com.yy.hiidostatis.defs.obj.Act;
import com.yy.hiidostatis.defs.obj.EventElementInfo;
import com.yy.hiidostatis.defs.obj.EventInfo;
import com.yy.hiidostatis.defs.obj.Property;
import com.yy.hiidostatis.inner.AbstractConfig;
import com.yy.hiidostatis.inner.BaseStatisContent;
import com.yy.hiidostatis.inner.GeneralProxy;
import com.yy.hiidostatis.inner.GeneralStatisTool;
import com.yy.hiidostatis.inner.implementation.CommonFiller;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.cipher.Base64Util;
import com.yy.hiidostatis.inner.util.cipher.Coder;
import com.yy.hiidostatis.inner.util.log.C1923L;
import com.yy.hiidostatis.pref.HdStatisConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.UUID;

public class StatisAPI implements IStatisAPI {
    private static final String KEY_MAGIC = "HiidoData";
    private static final int MAX_EVENT_FIELD_BYTES = 256;
    private static final long PRIORITY_INNER = -1;
    private static final long PRIORITY_OUTER = 0;
    private AbstractConfig mAbstractConfig;
    private ActAdditionListenerController mActListernerController = new ActAdditionListenerController();
    private Context mContext;
    private GeneralStatisTool mGeneralStatisTool;
    private boolean mIsInit = false;
    private StatisOption mOption;
    private String sessionId = null;

    private String getErrorInfo(Throwable th) {
        try {
            Writer stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            String obj = stringWriter.toString();
            printWriter.close();
            stringWriter.close();
            return obj;
        } catch (Throwable th2) {
            C1923L.error(StatisAPI.class, "SDK Get Crash Error Info Exception!" + th2, new Object[0]);
            return "SDK Get Crash Error Info Exception!" + th2;
        }
    }

    private void reportDoInner(long j) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("imc", Util.getIMEI(getContext()) + "," + Util.getMacAddr(getContext()));
        try {
            if (GaListenerController.getListerner() != null) {
                statisContent.put("idfv", GaListenerController.getListerner().getGaClientId());
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        reportStatisticContentInner(Act.MBSDK_DO, statisContent, true, true, true);
    }

    private boolean reportStatisticContentAll(String str, StatisContent statisContent, boolean z, boolean z2, boolean z3, boolean z4, Long l) throws Throwable {
        if (!(this.mContext == null || Util.empty(str))) {
            if (!Util.empty(statisContent)) {
                BaseStatisContent copy = z ? statisContent.copy() : statisContent;
                StatisOption option = getOption();
                if (option != null) {
                    copy.put("app", option.getAppId());
                    copy.put("appkey", option.getAppkey());
                    copy.put(BaseStatisContent.FROM, option.getFrom());
                    copy.put(BaseStatisContent.VER, option.getVer());
                }
                copy.put("sessionid", this.sessionId);
                this.mGeneralStatisTool.reportCustom(this.mContext, str, copy, z2, z3, z4, l);
                return true;
            }
        }
        C1923L.error(StatisAPI.class, "Input error! context is null || act is null || content is null ", new Object[0]);
        return false;
    }

    private boolean reportStatisticContentInner(Act act, StatisContent statisContent, boolean z, boolean z2, boolean z3) {
        try {
            StatisContent actAddition = this.mActListernerController.getActAddition(this.mActListernerController.getListerner(act));
            if (actAddition != null) {
                statisContent.putContent(actAddition, false);
            }
            return reportStatisticContentAll(act.toString(), statisContent, false, z, z2, false, z3 ? Long.valueOf(-1) : null);
        } catch (Exception e) {
            C1923L.error(StatisAPI.class, "reportStatisticContentInner act:%s ,exception:%s", act.toString(), e);
            return false;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return z;
    }

    public void addActAdditionListener(ActListener actListener) {
        this.mActListernerController.add(actListener);
    }

    public IStatisAPI create() {
        return new StatisAPI();
    }

    public void generateSession() {
        try {
            this.sessionId = Coder.encryptMD5(UUID.randomUUID().toString()).substring(0, 20);
            C1923L.brief("generate new session:%s", this.sessionId);
        } catch (Exception e) {
            C1923L.error(this, "generateSession exception:%s", e);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public StatisOption getOption() {
        return this.mOption;
    }

    public String getSession() {
        return this.sessionId;
    }

    public void init(Context context, StatisOption statisOption) {
        this.mAbstractConfig = HdStatisConfig.getConfig(statisOption == null ? null : statisOption.getAppkey());
        if (this.mIsInit) {
            C1923L.warn(this, "statisAPI only be init once", new Object[0]);
            return;
        }
        this.mContext = context == null ? this.mContext : context.getApplicationContext();
        this.mOption = statisOption;
        if (this.mContext == null || this.mOption == null || Util.empty(statisOption.getAppkey())) {
            C1923L.errorOn(this, "init incorrect! Input context is null || mOption is null || Appkey is null", new Object[0]);
        } else {
            this.mGeneralStatisTool = GeneralProxy.getGeneralStatisInstance(this.mContext, this.mAbstractConfig);
            C1923L.infoOn(this, "init finish! appId:%s; appkey:%s; from:%s; ver:%s; sdkver:%s", this.mOption.getAppId(), this.mOption.getAppkey(), this.mOption.getFrom(), this.mOption.getVer(), this.mAbstractConfig.getSdkVer());
        }
        C1923L.info(this, "statisApi init. Context:%s ;api:%s", this.mContext, this);
        this.mIsInit = true;
    }

    public void removeActAdditionListener(ActListener actListener) {
        this.mActListernerController.remove(actListener);
    }

    public void reportAction(long j, String str, String str2, String str3) {
        if (Util.empty(str) && Util.empty(str2) && Util.empty(str3)) {
            C1923L.error(StatisAPI.class, "Input appa is null && page is null && event is null ", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("appa", str);
        statisContent.put("page", str2);
        statisContent.put("even", str3);
        reportStatisticContentInner(Act.MBSDK_ACTION, statisContent, true, false, false);
    }

    public void reportAppList(long j, String str, String str2) throws Throwable {
        Exception e;
        if (str2 == null || str2.length() == 0) {
            C1923L.debug(StatisAPI.class, "applist is empty，no report applist !", new Object[0]);
            return;
        }
        String act = Act.MBSDK_APPLIST.toString();
        BaseStatisContent statisContent = new StatisContent();
        CommonFiller.fillCommonNew(this.mContext, statisContent, act, this.mGeneralStatisTool.getConfig().getSdkVer());
        try {
            C1923L.debug(AppAnalyzeController.class, "des key is %s", Coder.encryptMD5(statisContent.get(BaseStatisContent.ACT) + statisContent.get(BaseStatisContent.TIME) + KEY_MAGIC).toLowerCase().substring(0, 8));
            act = Coder.encryptDES(str2, act);
            try {
                C1923L.debug(StatisAPI.class, "applist length is %d", Integer.valueOf(act.length()));
            } catch (Exception e2) {
                e = e2;
                C1923L.error(StatisAPI.class, "encrypt exception %s", e);
                statisContent.put("uid", j);
                statisContent.put(C1837a.f3790a, str);
                statisContent.put("applist", act);
                reportStatisticContentInner(Act.MBSDK_APPLIST, (StatisContent) statisContent, false, false, false);
            }
        } catch (Exception e3) {
            e = e3;
            act = str2;
            C1923L.error(StatisAPI.class, "encrypt exception %s", e);
            statisContent.put("uid", j);
            statisContent.put(C1837a.f3790a, str);
            statisContent.put("applist", act);
            reportStatisticContentInner(Act.MBSDK_APPLIST, (StatisContent) statisContent, false, false, false);
        }
        statisContent.put("uid", j);
        statisContent.put(C1837a.f3790a, str);
        statisContent.put("applist", act);
        reportStatisticContentInner(Act.MBSDK_APPLIST, (StatisContent) statisContent, false, false, false);
    }

    public void reportCountEvent(long j, String str, double d) {
        reportCountEvent(j, str, d, null);
    }

    public void reportCountEvent(long j, String str, double d, String str2) {
        reportCountEvent(j, str, d, str2, null);
    }

    public void reportCountEvent(long j, String str, double d, String str2, Property property) {
        if (Util.empty(str)) {
            C1923L.error(this, "eid is not allow null.", new Object[0]);
            return;
        }
        if (str.getBytes().length > 256) {
            C1923L.warn(this, "eid[%s] bytes[%d] must under %d bytes.", str, Integer.valueOf(str.getBytes().length), Integer.valueOf(256));
        }
        if (!Util.empty(str2) && str2.getBytes().length > 256) {
            C1923L.warn(this, "label[%s] bytes[%d] must under %d bytes.", str2, Integer.valueOf(str2.getBytes().length), Integer.valueOf(256));
        }
        EventInfo eventInfo = new EventInfo();
        EventElementInfo eventElementInfo = new EventElementInfo(str, String.valueOf(d));
        eventElementInfo.addParam(str2);
        eventElementInfo.setProperty(property);
        eventInfo.addElem(eventElementInfo);
        reportEvent(j, eventInfo.getResult());
    }

    public void reportCrash(long j, String str) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("crashmsg", str);
        reportStatisticContentInner(Act.MBSDK_CRASH, statisContent, true, true, false);
    }

    public void reportCrash(long j, Throwable th) {
        reportCrash(j, getErrorInfo(th));
    }

    public void reportCustomContent(long j, String str, String str2) {
        if (this.mContext == null || str2 == null || str2.length() == 0) {
            C1923L.error(StatisAPI.class, "Input context is null || content is null", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put(C1837a.f3790a, str);
        statisContent.put("content", str2);
        reportStatisticContentInner(Act.MBSDK_REPORT, statisContent, true, true, false);
    }

    public boolean reportDevice(long j) throws Throwable {
        if (this.mContext == null) {
            C1923L.debug(StatisAPI.class, "Input context is null", new Object[0]);
            return false;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("cpunum", Util.getCpuNum());
        statisContent.put("cpu", Util.getMaxCpuFreq());
        statisContent.put("memory", Util.getTotalMemory());
        statisContent.put("imsi", Util.getImsi(this.mContext));
        statisContent.put("arid", Util.getAndroidId(this.mContext));
        return reportStatisticContentInner(Act.MBSDK_SDKDEVICE, statisContent, true, true, false);
    }

    public void reportDo(final long j) {
        try {
            ThreadPool.getPool().execute(new Runnable() {
                public void run() {
                    StatisAPI.this.reportDoInner(j);
                }
            });
        } catch (Exception e) {
            reportDoInner(j);
        }
    }

    public void reportDo5(long j) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        reportStatisticContentInner(Act.MBSDK_DO5, statisContent, true, false, true);
    }

    public void reportError(long j, String str, String str2, String str3) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("eid", str);
        statisContent.put("emsg", str2);
        statisContent.put("parm", str3);
        reportStatisticContentInner(Act.MBSDK_ERROR, statisContent, true, false, false);
    }

    public void reportEvent(long j, String str) {
        if (Util.empty(str)) {
            C1923L.error(StatisAPI.class, "Input event is null ", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("event", str);
        statisContent.put("imc", Util.getIMEI(getContext()) + "," + Util.getMacAddr(getContext()));
        reportStatisticContentInner(Act.MBSDK_EVENT, statisContent, true, false, false);
    }

    public void reportFailure(long j, String str, String str2, String str3, String str4, String str5) {
        if (this.mContext == null) {
            C1923L.error(StatisAPI.class, "Input context is null!", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("actionid", str);
        statisContent.put(C1837a.f3790a, str2);
        statisContent.put("failcode", str3);
        statisContent.put("failmsg", str4);
        statisContent.put("parm", str5);
        reportStatisticContentInner(Act.MBSDK_FAILURE, statisContent, true, true, false);
    }

    public boolean reportFeedback(long j, String str, String str2, String str3, String str4) {
        if (this.mContext == null || Util.empty(str2)) {
            C1923L.warn(StatisAPI.class, "Input context is null||cont is null", new Object[0]);
            return false;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("fbid", str);
        statisContent.put("cont", str2);
        statisContent.put("link", str3);
        statisContent.put("remk", str4);
        return reportStatisticContentInner(Act.MBSDK_FBACK, statisContent, true, true, false);
    }

    public boolean reportInstall(int i) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("new", i);
        return reportStatisticContentInner(Act.MBSDK_INSTALL, statisContent, true, true, true);
    }

    public void reportLanuch(long j, String str) {
        if (Util.empty(str)) {
            C1923L.error(StatisAPI.class, "Input appa is null ", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("appa", str);
        reportStatisticContentInner(Act.MBSDK_LANUCH, statisContent, true, false, false);
    }

    public void reportLogin(long j) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        reportStatisticContentInner(Act.MBSDK_LOGIN, statisContent, true, false, false);
    }

    public void reportPage(long j, String str) {
        if (Util.empty(str)) {
            C1923L.error(StatisAPI.class, "Input page is null ", new Object[0]);
            return;
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("page", str);
        reportStatisticContentInner(Act.MBSDK_PAGE, statisContent, true, false, false);
    }

    public void reportRun(long j) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        reportStatisticContentInner(Act.MBSDK_RUN, statisContent, true, true, true);
    }

    public void reportSdkList(long j, String str) {
        if (this.mContext == null || str == null || str.length() == 0) {
            C1923L.debug(StatisAPI.class, "Input context is null || sdkList is null", new Object[0]);
            return;
        }
        try {
            str = Base64Util.encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            C1923L.error(StatisAPI.class, "encrypt exception %s", e);
        }
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("sdklist", str);
        reportStatisticContentInner(Act.MBSDK_SDKLIST, statisContent, true, false, false);
    }

    public void reportStatisticContent(String str, StatisContent statisContent, boolean z, boolean z2) throws Throwable {
        if (!z) {
            CommonFiller.fillKey(statisContent, str);
        }
        reportStatisticContentAll(str, statisContent, true, z, z, z2, null);
    }

    public void reportStatisticContent(String str, StatisContent statisContent, boolean z, boolean z2, boolean z3) throws Throwable {
        if (!z) {
            CommonFiller.fillKey(statisContent, str);
        }
        reportStatisticContentAll(str, statisContent, true, z, z, z2, z3 ? Long.valueOf(0) : null);
    }

    public void reportStatisticContentWithNoComm(Context context, String str, StatisContent statisContent) throws Throwable {
        if (!((context == null ? this.mContext : context.getApplicationContext()) == null || Util.empty(str))) {
            if (!Util.empty(statisContent)) {
                StatisContent copy = statisContent.copy();
                CommonFiller.fillKey(copy, str);
                reportStatisticContentAll(str, copy, false, false, false, false, null);
                return;
            }
        }
        C1923L.error(StatisAPI.class, "Input error! context is null || act is null || content is null ", new Object[0]);
    }

    public void reportStatisticContentWithNoComm(Context context, String str, StatisContent statisContent, boolean z) throws Throwable {
        if (!((context == null ? this.mContext : context.getApplicationContext()) == null || Util.empty(str))) {
            if (!Util.empty(statisContent)) {
                BaseStatisContent copy = statisContent.copy();
                CommonFiller.fillKey(copy, str);
                reportStatisticContentAll(str, (StatisContent) copy, false, false, false, false, z ? Long.valueOf(0) : null);
                return;
            }
        }
        C1923L.error(StatisAPI.class, "Input error! context is null || act is null || content is null ", new Object[0]);
    }

    public void reportSuccess(long j, String str, String str2, long j2, String str3) {
        StatisContent statisContent = new StatisContent();
        statisContent.put("uid", j);
        statisContent.put("actionid", str);
        statisContent.put(C1837a.f3790a, str2);
        statisContent.put("duration", j2);
        statisContent.put("parm", str3);
        reportStatisticContentInner(Act.MBSDK_SUCCESS, statisContent, true, true, false);
    }

    public void reportTimesEvent(long j, String str) {
        reportTimesEvent(j, str, null);
    }

    public void reportTimesEvent(long j, String str, String str2) {
        reportTimesEvent(j, str, str2, null);
    }

    public void reportTimesEvent(long j, String str, String str2, Property property) {
        if (Util.empty(str)) {
            C1923L.error(this, "eid is not allow null.", new Object[0]);
            return;
        }
        if (str.getBytes().length > 256) {
            C1923L.warn(this, "eid[%s] bytes[%d] must under %d bytes.", str, Integer.valueOf(str.getBytes().length), Integer.valueOf(256));
        }
        if (!Util.empty(str2) && str2.getBytes().length > 256) {
            C1923L.warn(this, "label[%s] bytes[%d] must under %d bytes.", str2, Integer.valueOf(str2.getBytes().length), Integer.valueOf(256));
        }
        EventInfo eventInfo = new EventInfo();
        EventElementInfo eventElementInfo = new EventElementInfo(str, 1);
        eventElementInfo.addParam(str2);
        eventElementInfo.setProperty(property);
        eventInfo.addElem(eventElementInfo);
        reportEvent(j, eventInfo.getResult());
    }

    public void reportccList(long j, String str, String str2) {
        StatisContent statisContent = new StatisContent();
        try {
            str2 = Base64Util.encode(str2.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            C1923L.error(StatisAPI.class, "encrypt exception %s", e);
        }
        statisContent.put("no", str);
        statisContent.put("cclist", str2);
        reportStatisticContentInner(Act.MBSDK_CCLIST, statisContent, true, false, false);
    }

    public void setSession(String str) {
        this.sessionId = str;
    }

    public void setTestServer(String str) {
        ((HdStatisConfig) this.mAbstractConfig).setTestServer(str);
    }
}
