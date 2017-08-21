package com.yy.hiidostatis.track;

import android.content.Context;
import android.os.Handler;

import com.yy.hiidostatis.inner.GeneralProxy;
import com.yy.hiidostatis.inner.util.Counter;
import com.yy.hiidostatis.inner.util.Counter.Callback;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.json.JSONObject;

public class BgReportTimer {
    private static final long DEFAULT_INTERVAL = 1800000;
    private static final boolean DEFAULT_IS_REPORT_TIMER = true;
    private static final long MIN_INTERVAL = 60000;
    private static Callback mReportExecutor;
    private static Counter mReportInvoker;

    public interface IConfigListener {
        JSONObject getConfig();
    }

    static final class C19261 implements Runnable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ IConfigListener val$listener;

        class C19251 implements Callback {
            C19251() {
            }

            public void onCount(int i) {
                GeneralProxy.flushCacheForce(C19261.this.val$context);
                C1923L.brief("flushCacheForce: %d times", Integer.valueOf(i + 1));
            }
        }

        C19261(IConfigListener iConfigListener, Handler handler, Context context) {
            this.val$listener = iConfigListener;
            this.val$handler = handler;
            this.val$context = context;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r8 = this;
            r1 = 0;
            r0 = 1;
            r2 = com.yy.hiidostatis.track.BgReportTimer.mReportInvoker;	 Catch:{ Exception -> 0x0087 }
            if (r2 == 0) goto L_0x0009;
        L_0x0008:
            return;
        L_0x0009:
            r2 = 1800000; // 0x1b7740 float:2.522337E-39 double:8.89318E-318;
            r4 = r8.val$listener;	 Catch:{ Exception -> 0x0087 }
            r4 = r4.getConfig();	 Catch:{ Exception -> 0x0087 }
            if (r4 == 0) goto L_0x008b;
        L_0x0014:
            r5 = "isBgReportTimer";
            r5 = r4.has(r5);	 Catch:{ Exception -> 0x0087 }
            if (r5 == 0) goto L_0x0024;
        L_0x001c:
            r5 = "isBgReportTimer";
            r5 = r4.getInt(r5);	 Catch:{ Exception -> 0x0087 }
            if (r0 != r5) goto L_0x0089;
        L_0x0024:
            r1 = "bgReportInterval";
            r1 = r4.has(r1);	 Catch:{ Exception -> 0x0087 }
            if (r1 == 0) goto L_0x008b;
        L_0x002c:
            r1 = "bgReportInterval";
            r4 = r4.getLong(r1);	 Catch:{ Exception -> 0x0087 }
            r6 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
            r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r1 < 0) goto L_0x008b;
        L_0x0039:
            r1 = "isBgReportTimer:%b,bgReportInterval:%d";
            r2 = 2;
            r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x0087 }
            r3 = 0;
            r6 = java.lang.Boolean.valueOf(r0);	 Catch:{ Exception -> 0x0087 }
            r2[r3] = r6;	 Catch:{ Exception -> 0x0087 }
            r3 = 1;
            r6 = java.lang.Long.valueOf(r4);	 Catch:{ Exception -> 0x0087 }
            r2[r3] = r6;	 Catch:{ Exception -> 0x0087 }
            com.yy.hiidostatis.inner.util.log.C1923L.brief(r1, r2);	 Catch:{ Exception -> 0x0087 }
            if (r0 == 0) goto L_0x0008;
        L_0x0051:
            r1 = new com.yy.hiidostatis.inner.util.Counter;	 Catch:{ Exception -> 0x0087 }
            r2 = r8.val$handler;	 Catch:{ Exception -> 0x0087 }
            r3 = 0;
            r6 = 1;
            r1.<init>(r2, r3, r4, r6);	 Catch:{ Exception -> 0x0087 }
            com.yy.hiidostatis.track.BgReportTimer.mReportInvoker = r1;	 Catch:{ Exception -> 0x0087 }
            r0 = new com.yy.hiidostatis.track.BgReportTimer$1$1;	 Catch:{ Exception -> 0x0087 }
            r0.<init>();	 Catch:{ Exception -> 0x0087 }
            com.yy.hiidostatis.track.BgReportTimer.mReportExecutor = r0;	 Catch:{ Exception -> 0x0087 }
            r0 = com.yy.hiidostatis.track.BgReportTimer.mReportInvoker;	 Catch:{ Exception -> 0x0087 }
            r1 = com.yy.hiidostatis.track.BgReportTimer.mReportExecutor;	 Catch:{ Exception -> 0x0087 }
            r0.setCallback(r1);	 Catch:{ Exception -> 0x0087 }
            r0 = com.yy.hiidostatis.track.BgReportTimer.mReportInvoker;	 Catch:{ Exception -> 0x0087 }
            r0.start(r4);	 Catch:{ Exception -> 0x0087 }
            r0 = "ReportTimer start. interval:%d ms";
            r1 = 1;
            r1 = new java.lang.Object[r1];	 Catch:{ Exception -> 0x0087 }
            r2 = 0;
            r3 = java.lang.Long.valueOf(r4);	 Catch:{ Exception -> 0x0087 }
            r1[r2] = r3;	 Catch:{ Exception -> 0x0087 }
            com.yy.hiidostatis.inner.util.log.C1923L.brief(r0, r1);	 Catch:{ Exception -> 0x0087 }
            goto L_0x0008;
        L_0x0087:
            r0 = move-exception;
            goto L_0x0008;
        L_0x0089:
            r0 = r1;
            goto L_0x0024;
        L_0x008b:
            r4 = r2;
            goto L_0x0039;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.track.BgReportTimer.1.run():void");
        }
    }

    public static void start(Handler handler, Context context, IConfigListener iConfigListener) {
        ThreadPool.getPool().execute(new C19261(iConfigListener, handler, context));
    }

    public static void stop(Context context) {
        if (mReportInvoker != null) {
            try {
                mReportInvoker.stop();
                mReportInvoker = null;
                mReportExecutor = null;
            } catch (Exception e) {
            }
        }
    }
}
