package com.yy.hiidostatis.api;

import com.yy.hiidostatis.inner.util.log.BaseDefaultStatisLogWriter;

public class DefaultStatisLogWriter extends BaseDefaultStatisLogWriter implements StatisLogWriter {
    public DefaultStatisLogWriter(String str, int i, boolean z) {
        super(str, i, z);
    }

    public DefaultStatisLogWriter(String str, boolean z) {
        super(str, z);
    }
}
