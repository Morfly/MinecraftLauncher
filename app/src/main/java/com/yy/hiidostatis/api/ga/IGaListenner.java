package com.yy.hiidostatis.api.ga;

public interface IGaListenner {
    String getGaClientId();

    void reportErrorToGa(String str);

    void reportEventToGa(String str, String str2, long j);
}
