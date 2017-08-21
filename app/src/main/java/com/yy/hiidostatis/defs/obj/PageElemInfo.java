package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PageElemInfo extends ParamableElem implements Elem {
    private static final long serialVersionUID = -5734456734934257499L;
    long dtime;
    long ltime;
    String npage;
    String page;
    long stime;

    public PageElemInfo(String str, String str2, long j, long j2, long j3) {
        this.page = str;
        this.npage = str2;
        this.ltime = j;
        this.dtime = j2;
        this.stime = j3;
    }

    public PageElemInfo() {

    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.page = (String) objectInputStream.readObject();
        this.npage = (String) objectInputStream.readObject();
        this.ltime = objectInputStream.readLong();
        this.dtime = objectInputStream.readLong();
        this.stime = objectInputStream.readLong();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.page);
        objectOutputStream.writeObject(this.npage);
        objectOutputStream.writeLong(this.ltime);
        objectOutputStream.writeLong(this.dtime);
        objectOutputStream.writeLong(this.stime);
    }

    public PageElemInfo copy() {
        PageElemInfo pageElemInfo = new PageElemInfo();
        pageElemInfo.dtime = this.dtime;
        pageElemInfo.ltime = this.ltime;
        pageElemInfo.stime = this.stime;
        pageElemInfo.page = this.page;
        pageElemInfo.npage = this.npage;
        pageElemInfo.addParams(new ArrayList(getParams()));
        return pageElemInfo;
    }

    public long getDelayedTime() {
        return this.dtime;
    }

    public String getDestinationPage() {
        return this.npage;
    }

    public long getLingerTime() {
        return this.ltime;
    }

    public String getPage() {
        return this.page;
    }

    public long getStime() {
        return this.stime;
    }

    public String getStringRep() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Util.replaceEncode(this.page, Elem.DIVIDER));
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(Util.replaceEncode(this.npage, Elem.DIVIDER));
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(this.stime);
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(this.ltime);
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(this.dtime);
        stringBuilder.append(Elem.DIVIDER);
        String connectedParams = getConnectedParams();
        if (!Util.empty(connectedParams)) {
            stringBuilder.append(Util.replaceEncode(connectedParams, Elem.DIVIDER));
        }
        return stringBuilder.toString();
    }

    public boolean isValid() {
        return (Util.empty(this.page) || Util.empty(this.npage)) ? false : true;
    }

    public void setDestinationPage(String str) {
        this.npage = str;
    }

    public void setDtime(long j) {
        this.dtime = j;
    }

    public void setLtime(long j) {
        this.ltime = j;
    }

    public void setPage(String str) {
        this.page = str;
    }

    public void setStime(long j) {
        this.stime = j;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" page=");
        stringBuilder.append(this.page);
        stringBuilder.append(", dest page=");
        stringBuilder.append(this.npage);
        stringBuilder.append(", stime=");
        stringBuilder.append(this.stime);
        stringBuilder.append(", lingertime=");
        stringBuilder.append(this.ltime);
        stringBuilder.append(", dtime=");
        stringBuilder.append(this.dtime);
        return stringBuilder.toString();
    }
}
