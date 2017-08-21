package com.yy.hiidostatis.api.ga;

public final class GaListenerController {
    private static IGaListenner mGaListenner;

    public static IGaListenner getListerner() {
        return mGaListenner;
    }

    public static void setListerner(IGaListenner iGaListenner) {
        mGaListenner = iGaListenner;
    }
}
