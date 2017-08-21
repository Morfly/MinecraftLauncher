package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.onlineconfig.C1837a;
import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;

import java.util.HashMap;
import java.util.Map;
/*import aly.ac;
import aly.hv;
import aly.ia;
import aly.ib;
import aly.ih;
import aly.ii;
import aly.ij;
import aly.in;*/

public class C1832c /*implements ih*/ {
    private final C1837a f3753a = new C1837a();
    private Context f3754b = null;
    private C1824b f3755c;
    /*private ia f3756d = new ia();
    private in f3757e = new in();
    private ij f3758f = new ij();
    private ib f3759g;
    private hv f3760h;*/
    private boolean f3761i = false;

    /*C1832c() {
        this.f3756d.m10144a((ih) this);
    }*/

    private void m5831f(Context context) {
        if (!this.f3761i) {
            this.f3754b = context.getApplicationContext();
            /*this.f3759g = new ib(this.f3754b);
            this.f3760h = hv.m10124a(this.f3754b);*/
            this.f3761i = true;
        }
    }

    private void m5832g(Context context) {
        //this.f3758f.m10188c(context);
        if (this.f3755c != null) {
            this.f3755c.m5818a();
        }
    }

    private void m5833h(Context context) {
        /*this.f3758f.m10189d(context);
        this.f3757e.m10206a(context);*/
        if (this.f3755c != null) {
            this.f3755c.m5819b();
        }
        //this.f3760h.mo3518b();
    }

    public void m5834a(int i) {
        AnalyticsConfig.mVerticalType = i;
    }

    void m5835a(Context context) {
        if (context == null) {
            return;
        }
        this.f3753a.m5907a(context);
        /*try {
            hv.m10124a(context).m10127a(this.f3753a);
        } catch (Exception e) {
        }*/
    }

    void m5836a(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            if (context == null) {
                return;
            }
            try {
                if (!this.f3761i) {
                    m5831f(context);
                }
                //this.f3760h.mo3517a(new ac(str).m8366a(false));
            } catch (Exception e) {
            }
        }
    }

    void m5837a(Context context, final String str, final String str2) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3742c;

                public void mo2907a() {
                    //this.f3742c.f3759g.m10148a(str, str2);
                }
            });
        } catch (Exception e) {
        }
    }

    public void m5838a(Context context, String str, String str2, long j, int i) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            //this.f3759g.m10149a(str, str2, j, i);
        } catch (Exception e) {
        }
    }

    public void m5839a(Context context, String str, HashMap<String, Object> hashMap) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            //this.f3759g.m10150a(str, (Map) hashMap);
        } catch (Exception e) {
        }
    }

    void m5840a(Context context, final String str, final HashMap<String, Object> hashMap, final String str2) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3749d;

                public void mo2907a() {
                    //this.f3749d.f3759g.m10152a(str, hashMap, str2);
                }
            });
        } catch (Exception e) {
        }
    }

    void m5841a(Context context, String str, Map<String, Object> map, long j) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            //this.f3759g.m10151a(str, (Map) map, j);
        } catch (Exception e) {
        }
    }

    void m5842a(Context context, Throwable th) {
        if (context != null && th != null) {
            try {
                if (!this.f3761i) {
                    m5831f(context);
                }
                //this.f3760h.mo3517a(new ac(th).m8366a(false));
            } catch (Exception e) {
            }
        }
    }

    public void m5843a(C1824b c1824b) {
        this.f3755c = c1824b;
    }

    void m5844a(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        this.f3753a.m5908a(umengOnlineConfigureListener);
    }

    void m5845a(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                //this.f3757e.m10207a(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void m5846a(String str, String str2) {
        AnalyticsConfig.mWrapperType = str;
        AnalyticsConfig.mWrapperVersion = str2;
    }

    public void mo2908a(Throwable th) {
        try {
            /*this.f3757e.m10205a();
            if (this.f3754b != null) {
                if (!(th == null || this.f3760h == null)) {
                    this.f3760h.mo3519b(new ac(th));
                }
                m5833h(this.f3754b);
                ii.m10181a(this.f3754b).edit().commit();
            }*/
            C1833d.m5855a();
        } catch (Exception e) {
        }
    }

    void m5848b(final Context context) {
        if (context == null) {
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            //this.f3757e.m10207a(context.getClass().getName());
        }
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3737b;

                public void mo2907a() {
                    this.f3737b.m5832g(context.getApplicationContext());
                }
            });
        } catch (Exception e) {
        }
    }

    void m5849b(Context context, final String str, final String str2) {
        try {
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3745c;

                public void mo2907a() {
                    //this.f3745c.f3759g.m10153b(str, str2);
                }
            });
        } catch (Exception e) {
        }
    }

    void m5850b(String str) {
        if (!AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            try {
                //this.f3757e.m10208b(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void m5851c(final Context context) {
        if (context == null) {
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            //this.f3757e.m10208b(context.getClass().getName());
        }
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3739b;

                public void mo2907a() {
                    this.f3739b.m5833h(context.getApplicationContext());
                }
            });
        } catch (Exception e) {
        }
    }

    void m5852c(Context context, final String str, final String str2) {
        try {
            C1833d.m5856a(new C1825e(this) {
                /* synthetic */ C1832c f3752c;

                public void mo2907a() {
                    //this.f3752c.f3759g.m10154c(str, str2);
                }
            });
        } catch (Exception e) {
        }
    }

    void m5853d(Context context) {
        try {
            if (!this.f3761i) {
                m5831f(context);
            }
            //this.f3760h.mo3515a();
        } catch (Exception e) {
        }
    }

    void m5854e(Context context) {
        try {
            //this.f3757e.m10205a();
            m5833h(context);
            //ii.m10181a(context).edit().commit();
            C1833d.m5855a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
