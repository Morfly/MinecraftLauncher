package com.yy.hiidostatis.inner.implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yy.hiidostatis.inner.AbstractConfig;
import com.yy.hiidostatis.inner.implementation.ITaskExecutor.ExecutorTask;
import com.yy.hiidostatis.inner.implementation.ITaskExecutor.OnTaskRejectedListener;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.http.IStatisHttpUtil;
import com.yy.hiidostatis.inner.util.http.StatisHttpEncryptUtil;
import com.yy.hiidostatis.inner.util.http.StatisHttpUtil;
import com.yy.hiidostatis.inner.util.log.ActLog;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.concurrent.RejectedExecutionException;

public class TaskManagerNew implements ITaskManager {
    private static final int MAX_CACHE_DAY = 5;
    private static final int MAX_RETRY_TIMES = 10000;
    private static final Object OBJ_KEY = new Object();
    private static final long SEND_FAIL_SLEEP_TIMES = 10000;
    private TaskDataMemoryCacheManager cacheManager;
    private volatile FailSendControler failSendControler = new FailSendControler(SEND_FAIL_SLEEP_TIMES);
    private boolean isEnableSend = true;
    private AbstractConfig mConfig;
    private Context mContext;
    private final TaskExecutor mExecutor;
    private volatile boolean mIsWorking = false;
    private ConnectionChangeReceiver mReceiver;
    private final TaskExecutor mSaveExecutor;
    private final Object noticeObj = new Object();

    class C19091 implements OnTaskRejectedListener {
        C19091() {
        }

        public void onRejectedTask(ExecutorTask executorTask) {
            C1923L.brief("Store rejected task %s", executorTask.getData().getDataId());
            TaskManagerNew.this.store(executorTask.getContext(), executorTask.getData());
        }
    }

    class ConnectionChangeReceiver extends BroadcastReceiver {
        private ConnectionChangeReceiver() {
        }

        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                TaskManagerNew.this.mExecutor.getExecutor().submit(new Runnable() {
                    public void run() {
                        if (Util.isNetworkAvailable(context)) {
                            C1923L.debug(this, "ConnectionChangeReceiver onReceive and notice send", new Object[0]);
                            TaskManagerNew.this.failSendControler.reset();
                            TaskManagerNew.this.flush(context);
                            return;
                        }
                        C1923L.debug(this, "ConnectionChangeReceiver onReceive .Network is not Available ,so no notice send.", new Object[0]);
                    }
                });
            }
        }

        public void registerReceiver(Context context) {
            C1923L.debug(this, "ConnectionChangeReceiver registerReceiver", new Object[0]);
            context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }

        public void unregisterReceiver(Context context) {
            C1923L.debug(this, "ConnectionChangeReceiver unregisterReceiver", new Object[0]);
            context.unregisterReceiver(this);
        }
    }

    class FailSendControler {
        private int failContinuous = 0;
        private long lastFailTime = 0;
        private final long sleepTime;

        public FailSendControler(long j) {
            this.sleepTime = j;
        }

        public int getFailContinuous() {
            return this.failContinuous;
        }

        public long getSleepTime() {
            return this.sleepTime;
        }

        public void increase() {
            this.lastFailTime = System.currentTimeMillis();
            this.failContinuous++;
        }

        public boolean isOverTime() {
            return System.currentTimeMillis() - this.lastFailTime > getSleepTime();
        }

        public void reset() {
            this.lastFailTime = 0;
            this.failContinuous = 0;
        }
    }

    public TaskManagerNew(Context context, AbstractConfig abstractConfig) {
        this.mConfig = abstractConfig;
        this.mContext = context;
        this.cacheManager = new TaskDataMemoryCacheManager(abstractConfig.getCacheFileName());
        this.mSaveExecutor = new TaskExecutor(new C19091());
        this.mExecutor = new TaskExecutor(null);
        registerReceiver(this.mContext);
    }

    private boolean doSend(Context context, TaskData taskData) {
        String str = null;
        IStatisHttpUtil httpUtil = getHttpUtil();
        boolean sendSync = httpUtil.sendSync(taskData.getContent(), null, null);
        int lastTryTimes = httpUtil.getLastTryTimes();
        httpUtil.shutDown();
        C1923L.debug(this, "Return value: %B to send command %s. ", Boolean.valueOf(sendSync), taskData.getContent());
        if (sendSync) {
            String lastHost;
            if (httpUtil instanceof StatisHttpEncryptUtil) {
                StatisHttpEncryptUtil statisHttpEncryptUtil = (StatisHttpEncryptUtil) httpUtil;
                str = statisHttpEncryptUtil.getLastSmkData();
                lastHost = statisHttpEncryptUtil.getLastHost();
            } else {
                lastHost = null;
            }
            ActLog.writeActLog(context, ActLog.TYPE_SUC, taskData.getContent(), str, lastHost);
            this.failSendControler.reset();
        } else {
            ActLog.writeActLog(context, ActLog.TYPE_FAIL, taskData.getContent(), null, null);
            taskData.setTryTimes(taskData.getTryTimes() + lastTryTimes);
            C1923L.debug(this, "data:%s ; all tryTimes:%d ; createTime:%d", taskData.getDataId(), Integer.valueOf(taskData.getTryTimes()), Long.valueOf(taskData.getTime()));
            this.failSendControler.increase();
        }
        return sendSync;
    }

    private IStatisHttpUtil getHttpUtil() {
        IStatisHttpUtil statisHttpEncryptUtil = isEncrypt() ? new StatisHttpEncryptUtil(this.mConfig.getUrlHost()) : new StatisHttpUtil();
        statisHttpEncryptUtil.setTestServer(this.mConfig.getTestServer());
        return statisHttpEncryptUtil;
    }

    private boolean isEncrypt() {
        C1923L.brief("isEncrypt[%b],isEncryptTestServer[%b],testServer[%s]", Boolean.valueOf(this.mConfig.isEncrypt()), Boolean.valueOf(this.mConfig.isEncryptTestServer()), this.mConfig.getTestServer() + "");
        return this.mConfig.isEncrypt() ? this.mConfig.isEncryptTestServer() || Util.empty(this.mConfig.getTestServer()) : false;
    }

    private boolean isOverMaxTryTimes(TaskData taskData) {
        return taskData.getTryTimes() >= 10000;
    }

    private boolean isOverdue(TaskData taskData) {
        try {
            return Util.daysBetween(taskData.getTime(), System.currentTimeMillis()) > 5;
        } catch (Exception e) {
            return false;
        }
    }

    private void noticeSend(Context context, boolean z) {
        if (this.mIsWorking) {
            C1923L.brief("send is mIsWorking...,no need for notice send. 111111111", new Object[0]);
        } else if (this.failSendControler.isOverTime()) {
            synchronized (this.noticeObj) {
                if (this.mIsWorking) {
                    C1923L.brief("send is mIsWorking...,no need for notice send. 22222222", new Object[0]);
                    return;
                }
                final Context context2 = context;
                final boolean z2 = z;
                try {
                    this.mExecutor.submit(new ExecutorTask(context, "") {
                        public void run() {
                            boolean z = true;
                            Object access$200 = new Object();
                            try {
                                access$200 = TaskManagerNew.this.sendNext(context2, z2);
                            } catch (Throwable th) {
                                Object[] objArr = new Object[1];
                                z = false;
                                objArr[0] = th;
                                C1923L.error(this, "exception:%s", objArr);
                                return;
                            } finally {
                                TaskManagerNew.this.mIsWorking = false;
                            }
                            if (access$200 != null) {
                                TaskManagerNew.this.noticeSend(context2, z);
                            }
                        }
                    });
                    this.mIsWorking = true;
                } catch (RejectedExecutionException e) {
                    C1923L.error(this, "noticeSend:RejectedExecutionException=%s,do nothing.", e);
                }
            }
        } else {
            C1923L.warn(this, "send fail Continuous [%d] times ,wait %d ms. can not notice send. 2", Integer.valueOf(this.failSendControler.getFailContinuous()), Long.valueOf(this.failSendControler.getSleepTime()));
        }
    }

    private void registerReceiver(Context context) {
        if (this.mReceiver == null) {
            synchronized (OBJ_KEY) {
                if (this.mReceiver == null) {
                    this.mReceiver = new ConnectionChangeReceiver();
                    this.mReceiver.registerReceiver(context);
                }
            }
        }
    }

    private void removeInvalid(Context context, TaskData taskData) {
        this.cacheManager.remove(context, taskData);
        ActLog.writeActLog(context, ActLog.TYPE_DISCARD, taskData.getContent(), null, null);
    }

    private boolean sendNext(Context context, boolean z) {
        if (!this.isEnableSend) {
            C1923L.brief("isEnableSend:false,end send.", new Object[0]);
            return false;
        } else if (Util.isNetworkAvailable(context)) {
            C1923L.brief("isSendFront:%b", Boolean.valueOf(z));
            TaskData first = z ? this.cacheManager.getFirst(context) : this.cacheManager.getLast(context);
            if (first == null) {
                C1923L.brief("data is null,end send. ", new Object[0]);
                return false;
            } else if (isOverdue(first) || isOverMaxTryTimes(first)) {
                C1923L.warn(this, "data:%s .overdue or over MaxTryTimes. give up the data. max cache day = [%d].MaxTryTimes = [%d] .dataTryTimes = [%d]", first.getDataId(), Integer.valueOf(5), Integer.valueOf(10000), Integer.valueOf(first.getTryTimes()));
                removeInvalid(context, first);
                return true;
            } else {
                boolean doSend = doSend(context, first);
                if (doSend) {
                    this.cacheManager.remove(context, first);
                    return doSend;
                } else if (isOverMaxTryTimes(first)) {
                    removeInvalid(context, first);
                    return doSend;
                } else {
                    store(context, first);
                    return doSend;
                }
            }
        } else {
            C1923L.brief("isNetworkAvailable:false,end send.", new Object[0]);
            return false;
        }
    }

    private boolean store(Context context, TaskData taskData) {
        return this.cacheManager.save(context, taskData);
    }

    private void storeAndSend(Context context, TaskData taskData) {
        final Context context2 = context;
        final TaskData taskData2 = taskData;
        try {
            this.mSaveExecutor.submit(new ExecutorTask(context, taskData) {
                public void run() {
                    TaskManagerNew.this.store(context2, taskData2);
                    TaskManagerNew.this.noticeSend(context2, true);
                }
            });
        } catch (RejectedExecutionException e) {
            store(context, taskData);
        }
    }

    private void unregisterReceiver(Context context) {
        if (this.mReceiver != null) {
            synchronized (OBJ_KEY) {
                if (this.mReceiver != null) {
                    this.mReceiver.unregisterReceiver(context);
                    this.mReceiver = null;
                }
            }
        }
    }

    public void awaitCompleted() {
        this.mExecutor.awaitCompleted();
        this.mSaveExecutor.awaitCompleted();
    }

    public void enableSend(boolean z) {
        this.isEnableSend = z;
    }

    public void flush(Context context) {
        noticeSend(context, true);
    }

    public TaskExecutor getExecutor() {
        return this.mExecutor;
    }

    public void send(Context context, String str) {
        ActLog.writeActLog(context, ActLog.TYPE_ADD, str, null, null);
        TaskData taskData = new TaskData();
        taskData.setContent(str);
        taskData.setDataId(taskData.createDataId());
        taskData.setVerifyMd5(taskData.createVerifyMd5());
        storeAndSend(context, taskData);
    }

    public void send(Context context, String str, Long l) {
        ActLog.writeActLog(context, ActLog.TYPE_ADD, str, null, null);
        TaskData taskData = new TaskData();
        taskData.setContent(str);
        taskData.setDataId(taskData.createDataId());
        taskData.setVerifyMd5(taskData.createVerifyMd5());
        if (l != null) {
            taskData.setOrder(l.longValue());
        }
        storeAndSend(context, taskData);
    }

    public void shutDownNow() {
        this.mExecutor.shutDownNow();
        this.mSaveExecutor.shutDownNow();
    }

    public void storePendingCommands(Context context, boolean z) {
        if (context != null) {
            try {
                this.cacheManager.storePendingCommands(context);
                C1923L.brief("storePendingCommands .", new Object[0]);
            } catch (RejectedExecutionException e) {
                C1923L.error(this, "Failed to store pending commands.", new Object[0]);
            }
        }
    }
}
