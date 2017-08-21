package com.yy.hiidostatis.inner;

import android.content.Context;

import com.yy.hiidostatis.inner.util.ProcessUtil;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.Enumeration;
import java.util.Hashtable;

public class GeneralProxy {
    private static Hashtable<String, GeneralConfigTool> configToolContainer = new Hashtable();
    private static Hashtable<String, GeneralStatisTool> statisToolContainer = new Hashtable();

    public static synchronized void exit(Context context, boolean z) {
        synchronized (GeneralProxy.class) {
            try {
                Enumeration elements = statisToolContainer.elements();
                while (elements.hasMoreElements()) {
                    GeneralStatisTool generalStatisTool = (GeneralStatisTool) elements.nextElement();
                    if (z) {
                        generalStatisTool.getTaskManager().flush(context);
                    } else {
                        generalStatisTool.getTaskManager().enableSend(z);
                        generalStatisTool.getTaskManager().storePendingCommands(context, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    private static void flush(Context context, GeneralStatisTool generalStatisTool) {
        if (!ProcessUtil.isBackground(context)) {
            generalStatisTool.getTaskManager().flush(context);
        }
    }

    public static synchronized void flushCache(Context context) {
        synchronized (GeneralProxy.class) {
            try {
                Enumeration elements = statisToolContainer.elements();
                while (elements.hasMoreElements()) {
                    flush(context, (GeneralStatisTool) elements.nextElement());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public static synchronized void flushCacheForce(Context context) {
        synchronized (GeneralProxy.class) {
            try {
                Enumeration elements = statisToolContainer.elements();
                while (elements.hasMoreElements()) {
                    ((GeneralStatisTool) elements.nextElement()).getTaskManager().flush(context);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public static GeneralConfigTool getGeneralConfigInstance(Context context, AbstractConfig abstractConfig) {
        GeneralConfigTool generalConfigTool = (GeneralConfigTool) configToolContainer.get(abstractConfig.getConfigKey());
        if (generalConfigTool != null) {
            return generalConfigTool;
        }
        generalConfigTool = new GeneralConfigTool(context, abstractConfig);
        configToolContainer.put(abstractConfig.getConfigKey(), generalConfigTool);
        C1923L.infoOn(GeneralProxy.class, "new GeneralConfigTool && configKey:%s", abstractConfig.getConfigKey());
        return generalConfigTool;
    }

    public static GeneralStatisTool getGeneralStatisInstance(Context context, AbstractConfig abstractConfig) {
        GeneralStatisTool generalStatisTool = (GeneralStatisTool) statisToolContainer.get(abstractConfig.getConfigKey());
        if (generalStatisTool != null) {
            return generalStatisTool;
        }
        generalStatisTool = new GeneralStatisTool(context, abstractConfig);
        statisToolContainer.put(abstractConfig.getConfigKey(), generalStatisTool);
        flush(context, generalStatisTool);
        C1923L.infoOn(GeneralProxy.class, "new GeneralStatisTool && configKey:%s", abstractConfig.getConfigKey());
        return generalStatisTool;
    }
}
