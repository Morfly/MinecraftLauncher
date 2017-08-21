package com.yy.hiidostatis.api;

import com.yy.hiidostatis.inner.BaseStatisContent;

import java.util.TreeMap;

public class StatisContent extends BaseStatisContent {
    public StatisContent copy() {
        StatisContent statisContent = new StatisContent();
        statisContent.raw = new TreeMap(COMPARATOR);
        statisContent.raw.putAll(this.raw);
        return statisContent;
    }

    public void putContent(StatisContent statisContent, boolean z) {
        super.putContent(statisContent, z);
    }
}
