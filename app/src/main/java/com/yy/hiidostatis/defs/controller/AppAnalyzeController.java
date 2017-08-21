package com.yy.hiidostatis.defs.controller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.umeng.analytics.onlineconfig.C1837a;
import com.yy.hiidostatis.defs.interf.IConfigAPI;
import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.log.C1923L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppAnalyzeController {
    private static final String PREF_KEY_APP_ANALYZE_REPORT_DATE = "PREF_KEY_APP_ANALYZE_REPORT_DATE";
    private IConfigAPI mConfigAPI;
    private IStatisAPI statisAPI;

    class C18852 implements Comparator<Map<String, Object>> {
        C18852() {
        }

        public int compare(Map<String, Object> map, Map<String, Object> map2) {
            long parseLong;
            long parseLong2;
            if (map.containsKey(C1837a.f3790a) && map2.containsKey(C1837a.f3790a)) {
                parseLong = Long.parseLong(map2.get(C1837a.f3790a) + "");
                parseLong2 = Long.parseLong(map.get(C1837a.f3790a) + "");
                if (parseLong - parseLong2 > 0) {
                    return 1;
                }
                if (parseLong - parseLong2 < 0) {
                    return -1;
                }
            }
            if (!map.containsKey("lastUpdateTime") || !map2.containsKey("lastUpdateTime")) {
                return 0;
            }
            parseLong = Long.parseLong(map2.get("lastUpdateTime") + "");
            parseLong2 = Long.parseLong(map.get("lastUpdateTime") + "");
            return parseLong - parseLong2 <= 0 ? parseLong - parseLong2 < 0 ? -1 : 0 : 1;
        }
    }

    public AppAnalyzeController(IStatisAPI iStatisAPI, IConfigAPI iConfigAPI) {
        this.statisAPI = iStatisAPI;
        this.mConfigAPI = iConfigAPI;
    }

    private String getAppList(Context context, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Map<String, Object>> allApps = getAllApps(context);
        if (allApps != null) {
            sort(allApps);
            for (Map map : allApps) {
                if (str.equals(map.get(C1837a.f3790a))) {
                    stringBuffer.append(map.get("appname")).append("|");
                }
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
            C1923L.debug(AppAnalyzeController.class, "type=%s,applist length=%d,applist bypes length=%d", str, Integer.valueOf(stringBuffer.toString().length()), Integer.valueOf(stringBuffer.toString().getBytes().length));
            C1923L.debug(AppAnalyzeController.class, "applist=%s", stringBuffer.toString());
        }
        return stringBuffer.toString();
    }

    private void reportAppList(Context context, long j, String str) throws Throwable {
        this.statisAPI.reportAppList(j, str, getAppList(context, str));
    }

    private void sort(List<Map<String, Object>> list) {
        if (list != null) {
            try {
                Collections.sort(list, new C18852());
            } catch (Exception e) {
                C1923L.warn(AppAnalyzeController.class, "sort list error %s", e);
            }
        }
    }

    private void startAppAnalyzeReport(final Context context, final long j) {
        ThreadPool.getPool().execute(new Runnable() {
            public void run() {
                boolean z;
                boolean z2;
                Object obj = new Object();
                JSONObject appListConfig = AppAnalyzeController.this.mConfigAPI.getAppListConfig(context, true);
                if (appListConfig != null) {
                    try {
                        if (appListConfig.has("enable") && "1".equals(appListConfig.get("enable") + "")) {
                            z = true;
                            if (appListConfig != null) {
                                try {
                                    if (appListConfig.has("sysAppEnable") && "1".equals(appListConfig.get("sysAppEnable") + "")) {
                                        z2 = true;
                                        C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                                        if (z) {
                                            AppAnalyzeController.this.reportAppList(context, j, "2");
                                            if (z2) {
                                                AppAnalyzeController.this.reportAppList(context, j, "1");
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    JSONException jSONException = e;
                                    z2 = z;
                                    JSONException jSONException2 = jSONException;
                                    C1923L.error(AppAnalyzeController.class, "get json.enable exception: %s", obj);
                                    z = z2;
                                    z2 = false;
                                    C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                                    if (z) {
                                        try {
                                            AppAnalyzeController.this.reportAppList(context, j, "2");
                                            if (z2) {
                                                AppAnalyzeController.this.reportAppList(context, j, "1");
                                            }
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                    }
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                            z2 = false;
                            C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                            if (z) {
                                AppAnalyzeController.this.reportAppList(context, j, "2");
                                if (z2) {
                                    AppAnalyzeController.this.reportAppList(context, j, "1");
                                }
                            }
                        }
                    } catch (JSONException e2) {
                        obj = e2;
                        z2 = false;
                        C1923L.error(AppAnalyzeController.class, "get json.enable exception: %s", obj);
                        z = z2;
                        z2 = false;
                        C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                        if (z) {
                            try {
                                AppAnalyzeController.this.reportAppList(context, j, "2");
                                if (z2) {
                                    AppAnalyzeController.this.reportAppList(context, j, "1");
                                }
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                z = false;
                if (appListConfig != null) {
                    z2 = true;
                    C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                    if (z) {
                        try {
                            AppAnalyzeController.this.reportAppList(context, j, "2");
                            if (z2) {
                                AppAnalyzeController.this.reportAppList(context, j, "1");
                            }
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }

                    }
                }
                z2 = false;
                C1923L.debug(AppAnalyzeController.class, "AppAnalyze enable is %b，sysAppEnable is %b", Boolean.valueOf(z), Boolean.valueOf(z2));
                if (z) {
                    try {
                        AppAnalyzeController.this.reportAppList(context, j, "2");
                        if (z2) {
                            AppAnalyzeController.this.reportAppList(context, j, "1");
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }

                }
            }
        });
    }

    public List<Map<String, Object>> getAllApps(Context context) {
        List<Map<String, Object>> arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        List installedPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            Map hashMap = new HashMap();
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
            if ((packageInfo.applicationInfo.flags & 1) > 0) {
                hashMap.put(C1837a.f3790a, "1");
            } else {
                hashMap.put(C1837a.f3790a, "2");
            }
            hashMap.put("appname", packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
            hashMap.put("appid", packageInfo.applicationInfo.packageName);
            try {
                String str = packageInfo.applicationInfo.publicSourceDir;
                hashMap.put("dir", str);
                hashMap.put("lastUpdateTime", Long.valueOf(new File(str).lastModified()));
            } catch (Exception e) {
                try {
                    hashMap.put("lastUpdateTime", Integer.valueOf(0));
                    C1923L.warn(AppAnalyzeController.class, "exception on get updatetime info: %s", e);
                } catch (Exception e2) {
                    C1923L.warn(AppAnalyzeController.class, "exception on get All Apps info: %s", e2);
                }
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public void reportAppAnalyze(Context context, long j) {
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (DefaultPreference.getPreference().getPrefString(context, PREF_KEY_APP_ANALYZE_REPORT_DATE, "").equals(format)) {
            C1923L.debug(AppAnalyzeController.class, "AppAnalyze is reported today[%s]，so not report again!", format);
            return;
        }
        startAppAnalyzeReport(context, j);
        DefaultPreference.getPreference().setPrefString(context, PREF_KEY_APP_ANALYZE_REPORT_DATE, format);
    }
}
