package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;
/*import aly.fo;
import aly.fp;
import aly.gc;*/

public final class C1834f {
    private static C1834f f3765a = new C1834f();
    private static Context f3766b = null;
    private static String f3767c = null;
    private static long f3768d = 1209600000;
    private static long f3769e = 2097152;
    private static final String f3770f = "age";
    private static final String f3771g = "sex";
    private static final String f3772h = "id";
    private static final String f3773i = "url";
    private static final String f3774j = "mobclick_agent_user_";
    private static final String f3775k = "mobclick_agent_online_setting_";
    private static final String f3776l = "mobclick_agent_header_";
    private static final String f3777m = "mobclick_agent_update_";
    private static final String f3778n = "mobclick_agent_state_";
    private static final String f3779o = "mobclick_agent_cached_";
    private static final String f3780p = "mobclick_agent_sealed_";

    public static C1834f m5858a(Context context) {
        if (f3766b == null) {
            f3766b = context.getApplicationContext();
        }
        if (f3767c == null) {
            f3767c = context.getPackageName();
        }
        return f3765a;
    }

    private static boolean m5859a(File file) {
        return file.exists() && file.length() > f3769e;
    }

    private SharedPreferences m5860k() {
        return f3766b.getSharedPreferences(f3774j + f3767c, 0);
    }

    private String m5861l() {
        return f3776l + f3767c;
    }

    private String m5862m() {
        return f3779o + f3767c /*+ fo.m9860c(f3766b)*/;
    }

    private String m5863n() {
        return f3780p + f3767c;
    }

    public void m5864a(int i, int i2) {
        Editor edit = C1834f.m5858a(f3766b).m5875g().edit();
        edit.putInt(C1823a.f3728h, i);
        edit.putLong(C1823a.f3729i, (long) i2);
        edit.commit();
    }

    public void m5865a(String str, String str2, int i, int i2) {
        Editor edit = m5860k().edit();
        if (!TextUtils.isEmpty(str)) {
            edit.putString("id", str);
        }
        if (!TextUtils.isEmpty(str2)) {
            edit.putString("url", str2);
        }
        if (i > 0) {
            edit.putInt(f3770f, i);
        }
        edit.putInt(f3771g, i2);
        edit.commit();
    }

    public void m5866a(byte[] bArr) {
        /*try {
            gc.m9941a(new File(f3766b.getFilesDir(), m5862m()), bArr);
        } catch (Exception e) {
            fp.m9884b(C1823a.f3725e, e.getMessage());
        }*/
    }

    public int[] m5867a() {
        SharedPreferences g = m5875g();
        int[] iArr = new int[2];
        if (g.getInt(C1823a.f3728h, -1) != -1) {
            iArr[0] = g.getInt(C1823a.f3728h, 1);
            iArr[1] = (int) g.getLong(C1823a.f3729i, 0);
        } else {
            iArr[0] = g.getInt(C1823a.f3731k, 1);
            iArr[1] = (int) g.getLong(C1823a.f3732l, 0);
        }
        return iArr;
    }

    public void m5868b(byte[] bArr) {
        /*try {
            gc.m9941a(new File(f3766b.getFilesDir(), m5863n()), bArr);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public byte[] m5869b() throws Throwable {
        InputStream openFileInput;
        Exception e;
        Throwable th;
        byte[] bArr = null;
        String m = m5862m();
        File file = new File(f3766b.getFilesDir(), m);
        if (C1834f.m5859a(file)) {
            file.delete();
        } else if (file.exists()) {
            try {
                openFileInput = f3766b.openFileInput(m);
                /*try {
                    bArr = gc.m9945b(openFileInput);
                    gc.m9947c(openFileInput);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        gc.m9947c(openFileInput);
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        gc.m9947c(openFileInput);
                        throw th;
                    }
                }*/
            } catch (Exception e3) {
                e = e3;
                openFileInput = null;
                e.printStackTrace();
                //gc.m9947c(openFileInput);
                return bArr;
            } catch (Throwable th3) {
                openFileInput = null;
                th = th3;
                //gc.m9947c(openFileInput);
                throw th;
            }
        }
        return bArr;
    }

    public Object[] m5870b(Context context) {
        SharedPreferences k = m5860k();
        Object[] objArr = new Object[4];
        if (k.contains("id")) {
            objArr[0] = k.getString("id", null);
        }
        if (k.contains("url")) {
            objArr[1] = k.getString("url", null);
        }
        if (k.contains(f3770f)) {
            objArr[2] = Integer.valueOf(k.getInt(f3770f, -1));
        }
        if (k.contains(f3771g)) {
            objArr[3] = Integer.valueOf(k.getInt(f3771g, -1));
        }
        return objArr;
    }

    public void m5871c() {
        f3766b.deleteFile(m5861l());
        f3766b.deleteFile(m5862m());
    }

    public byte[] m5872d() throws Throwable {
        InputStream openFileInput;
        Exception e;
        Throwable th;
        String n = m5863n();
        File file = new File(f3766b.getFilesDir(), n);
        try {
            if (!file.exists() || file.length() <= 0) {
                return null;
            }
            try {
                openFileInput = f3766b.openFileInput(n);
            } catch (Exception e2) {
                e = e2;
                openFileInput = null;
                /*try {
                    e.printStackTrace();
                    gc.m9947c(openFileInput);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    gc.m9947c(openFileInput);
                    throw th;
                }*/
            } catch (Throwable th3) {
                th = th3;
                openFileInput = null;
                //gc.m9947c(openFileInput);
                throw th;
            }
            /*try {
                byte[] b = gc.m9945b(openFileInput);
                gc.m9947c(openFileInput);
                return b;
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                gc.m9947c(openFileInput);
                return null;
            }*/
        } catch (Exception e4) {
            file.delete();
            e4.printStackTrace();
        }
        return new byte[0];
    }

    public void m5873e() {
        //fp.m9882a("--->", "delete envelope:" + f3766b.deleteFile(m5863n()));
    }

    public boolean m5874f() {
        File file = new File(f3766b.getFilesDir(), m5863n());
        return file.exists() && file.length() > 0;
    }

    public SharedPreferences m5875g() {
        return f3766b.getSharedPreferences(f3775k + f3767c, 0);
    }

    public SharedPreferences m5876h() {
        return f3766b.getSharedPreferences(f3776l + f3767c, 0);
    }

    public SharedPreferences m5877i() {
        return f3766b.getSharedPreferences(f3777m + f3767c, 0);
    }

    public SharedPreferences m5878j() {
        return f3766b.getSharedPreferences(f3778n + f3767c, 0);
    }
}
