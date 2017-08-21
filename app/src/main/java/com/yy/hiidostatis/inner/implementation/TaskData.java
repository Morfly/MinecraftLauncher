package com.yy.hiidostatis.inner.implementation;

import com.yy.hiidostatis.inner.util.cipher.Coder;

import java.io.Serializable;
import java.util.UUID;

public class TaskData implements Serializable {
    private static final String MAGIC_MD5 = "hd!@#$%";
    private static final long serialVersionUID = -3875880932357283826L;
    private String content;
    private String dataId;
    private long order = this.time;
    private long time = System.currentTimeMillis();
    private int tryTimes;
    private String verifyMd5;

    public String createDataId() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            return this.content + this.time;
        }
    }

    public String createVerifyMd5() {
        try {
            return Coder.encryptMD5(this.content + MAGIC_MD5);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    public String getContent() {
        return this.content;
    }

    public String getDataId() {
        return this.dataId;
    }

    public long getOrder() {
        return this.order;
    }

    public long getTime() {
        return this.time;
    }

    public int getTryTimes() {
        return this.tryTimes;
    }

    public String getVerifyMd5() {
        return this.verifyMd5;
    }

    public int hashCode() {
        return (getDataId() + "").hashCode();
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setDataId(String str) {
        this.dataId = str;
    }

    public void setOrder(long j) {
        this.order = j;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public void setTryTimes(int i) {
        this.tryTimes = i;
    }

    public void setVerifyMd5(String str) {
        this.verifyMd5 = str;
    }

    public boolean verifyMd5() {
        return (getVerifyMd5() == null || getVerifyMd5().trim().length() == 0) ? true : getVerifyMd5().equals(createVerifyMd5());
    }
}
