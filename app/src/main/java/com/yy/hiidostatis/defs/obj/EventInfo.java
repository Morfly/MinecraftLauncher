package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.Iterator;

public class EventInfo extends Info<EventElementInfo> {
    private static final long serialVersionUID = -2909020670205500872L;

    private long parseAsInt(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            C1923L.error(this, "Failed to parse %s as number", str);
            return 1;
        }
    }

    public int getRealElemCount() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int parseAsInt;
            EventElementInfo eventElementInfo = (EventElementInfo) it.next();
            if (eventElementInfo.ctype == 1) {
                parseAsInt = (int) (parseAsInt(eventElementInfo.cvalue) + ((long) i));
            } else {
                parseAsInt = i + 1;
            }
            i = parseAsInt;
        }
        return i;
    }
}
