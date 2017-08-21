package com.yy.hiidostatis.defs.listener;

import com.yy.hiidostatis.api.StatisContent;
import com.yy.hiidostatis.defs.interf.IAct;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ActAdditionListenerController {
    private Map<IAct, ActListener> actMap = new HashMap();

    public ActListener add(ActListener actListener) {
        ActListener actListener2 = (ActListener) this.actMap.put(actListener.getAct(), actListener);
        C1923L.info(this, "add ActListener act[%s] new listener[%s],old listener[%s]", actListener.getAct(), actListener, actListener);
        return actListener2;
    }

    public StatisContent getActAddition(ActListener actListener) {
        StatisContent statisContent = null;
        if (actListener != null) {
            int i;
            if (actListener instanceof ActAdditionListener) {
                ActAdditionListener actAdditionListener = (ActAdditionListener) actListener;
                if (!(actAdditionListener.getAdditionMap() == null || actAdditionListener.getAdditionMap().isEmpty())) {
                    StatisContent statisContent2 = new StatisContent();
                    int size = actAdditionListener.getAdditionMap().size();
                    for (Entry entry : actAdditionListener.getAdditionMap().entrySet()) {
                        if (!(Util.empty((String) entry.getKey()) || Util.empty((String) entry.getValue()))) {
                            statisContent2.put((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    i = size;
                    statisContent = statisContent2;
                }
                i = 0;
            } else {
                if (actListener instanceof ActBakAdditionListener) {
                    int i2;
                    ActBakAdditionListener actBakAdditionListener = (ActBakAdditionListener) actListener;
                    StatisContent statisContent3 = new StatisContent();
                    if (Util.empty(actBakAdditionListener.getBak1())) {
                        i2 = 0;
                    } else {
                        statisContent3.put("bak1", actBakAdditionListener.getBak1());
                        i2 = 1;
                    }
                    if (!Util.empty(actBakAdditionListener.getBak2())) {
                        statisContent3.put("bak2", actBakAdditionListener.getBak2());
                        i2++;
                    }
                    if (Util.empty(actBakAdditionListener.getBak3())) {
                        i = i2;
                        statisContent = statisContent3;
                    } else {
                        statisContent3.put("bak3", actBakAdditionListener.getBak3());
                        i = i2 + 1;
                        statisContent = statisContent3;
                    }
                }
                i = 0;
            }
            C1923L.debug(this, "getActAddition act[%s], listener[%s], ActAddition size[%d]", actListener.getAct(), actListener, Integer.valueOf(i));
        }
        return statisContent;
    }

    public ActListener getListerner(IAct iAct) {
        return (ActListener) this.actMap.get(iAct);
    }

    public ActListener remove(ActListener actListener) {
        try {
            C1923L.info(this, "remove ActListener act[%s] listener[%s]", actListener.getAct(), actListener);
            return (ActListener) this.actMap.remove(actListener.getAct());
        } catch (Exception e) {
            C1923L.error(this, "error %s", e);
            return null;
        }
    }
}
