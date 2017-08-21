package com.umeng.analytics;

import android.content.Context;
/*import aly.fo;
import aly.ie;
import aly.ik;*/

public class ReportPolicy {
    public static final int BATCH_AT_LAUNCH = 1;
    public static final int BATCH_BY_INTERVAL = 6;
    public static final int BATCH_BY_SIZE = 7;
    public static final int DAILY = 4;
    public static final int REALTIME = 0;
    public static final int WIFIONLY = 5;
    static final int f3719a = 2;
    static final int f3720b = 3;

    public class C1817e {
        public boolean mo2906a(boolean z) {
            return true;
        }
    }

    public class C1818a extends C1817e {
        public boolean mo2906a(boolean z) {
            return z;
        }
    }

    public class C1819b extends C1817e {
        private long f3711a = 10000;
        private long f3712b;
        /*private ik f3713c;

        public C1819b(ik ikVar, long j) {
            this.f3713c = ikVar;
            if (j < this.f3711a) {
                j = this.f3711a;
            }
            this.f3712b = j;
        }*/

        public long m5813a() {
            return this.f3712b;
        }

        public boolean mo2906a(boolean z) {
            return true/*System.currentTimeMillis() - this.f3713c.f6219c >= this.f3712b*/;
        }
    }

    public class C1820c extends C1817e {
        /*private final int f3714a;
        private ie f3715b;

        public C1820c(ie ieVar, int i) {
            this.f3714a = i;
            this.f3715b = ieVar;
        }*/

        public boolean mo2906a(boolean z) {
            return true/*this.f3715b.m10164b() > this.f3714a*/;
        }
    }

    public class C1821d extends C1817e {
        /*private long f3716a = C1823a.f3733m;
        private ik f3717b;

        public C1821d(ik ikVar) {
            this.f3717b = ikVar;
        }*/

        public boolean mo2906a(boolean z) {
            return true/*System.currentTimeMillis() - this.f3717b.f6219c >= this.f3716a*/;
        }
    }

    public class C1822f extends C1817e {
        private Context f3718a = null;

        public C1822f(Context context) {
            this.f3718a = context;
        }

        public boolean mo2906a(boolean z) {
            return true/*fo.m9868k(this.f3718a)*/;
        }
    }
}
