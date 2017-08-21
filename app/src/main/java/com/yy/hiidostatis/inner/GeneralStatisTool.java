package com.yy.hiidostatis.inner;

import android.content.Context;

import com.yy.hiidostatis.inner.implementation.CommonFiller;
import com.yy.hiidostatis.inner.implementation.ITaskManager;
import com.yy.hiidostatis.inner.implementation.TaskManagerNew;

public class GeneralStatisTool {
    private AbstractConfig mConfig;
    private Context mContext;
    private ITaskManager mTaskManager = new TaskManagerNew(this.mContext, this.mConfig);

    public GeneralStatisTool(Context context, AbstractConfig abstractConfig) {
        this.mConfig = abstractConfig;
        this.mContext = context;
    }

    public AbstractConfig getConfig() {
        return this.mConfig;
    }

    public ITaskManager getTaskManager() {
        return this.mTaskManager;
    }

    public void reportCustom(Context context, String str, BaseStatisContent baseStatisContent) {
        baseStatisContent.put(BaseStatisContent.ACT, str);
        this.mTaskManager.send(context, baseStatisContent.getContent());
    }

    public void reportCustom(Context context, String str, BaseStatisContent baseStatisContent, Long l) {
        baseStatisContent.put(BaseStatisContent.ACT, str);
        this.mTaskManager.send(context, baseStatisContent.getContent(), l);
    }

    public void reportCustom(Context context, String str, BaseStatisContent baseStatisContent, boolean z, boolean z2, boolean z3) throws Throwable {
        if (z || z2) {
            BaseStatisContent baseStatisContent2 = new BaseStatisContent();
            if (z) {
                CommonFiller.fillCommonNew(context, baseStatisContent2, str, this.mConfig.getSdkVer());
            }
            if (z2) {
                CommonFiller.fillConcreteInfoNew(context, baseStatisContent2);
            }
            baseStatisContent2.putContent(baseStatisContent, z3);
            baseStatisContent = baseStatisContent2;
        }
        reportCustom(context, str, baseStatisContent);
    }

    public void reportCustom(Context context, String str, BaseStatisContent baseStatisContent, boolean z, boolean z2, boolean z3, Long l) throws Throwable {
        if (z || z2) {
            BaseStatisContent baseStatisContent2 = new BaseStatisContent();
            if (z) {
                CommonFiller.fillCommonNew(context, baseStatisContent2, str, this.mConfig.getSdkVer());
            }
            if (z2) {
                CommonFiller.fillConcreteInfoNew(context, baseStatisContent2);
            }
            baseStatisContent2.putContent(baseStatisContent, z3);
            baseStatisContent = baseStatisContent2;
        }
        reportCustom(context, str, baseStatisContent, l);
    }
}
