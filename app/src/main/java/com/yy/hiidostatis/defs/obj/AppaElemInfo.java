package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AppaElemInfo extends ParamableElem implements Elem {
    private static final long serialVersionUID = 5075819899173282579L;
    long dtime;
    long ftime;
    long ltime;
    long stime;

    public AppaElemInfo(long j, long j2, long j3, long j4) {
        this.stime = j;
        this.ftime = j2;
        this.ltime = j3;
        this.dtime = j4;
    }

    public AppaElemInfo() {

    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this.stime = objectInputStream.readLong();
        this.ftime = objectInputStream.readLong();
        this.ltime = objectInputStream.readLong();
        this.dtime = objectInputStream.readLong();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeLong(this.stime);
        objectOutputStream.writeLong(this.ftime);
        objectOutputStream.writeLong(this.ltime);
        objectOutputStream.writeLong(this.dtime);
    }

    public AppaElemInfo copy() {
        AppaElemInfo appaElemInfo = new AppaElemInfo();
        appaElemInfo.dtime = this.dtime;
        appaElemInfo.ftime = this.ftime;
        appaElemInfo.ltime = this.ltime;
        appaElemInfo.stime = this.stime;
        appaElemInfo.addParams(new ArrayList(getParams()));
        return appaElemInfo;
    }

    public long getDelayedTime() {
        return this.dtime;
    }

    public long getFtime() {
        return this.ftime;
    }

    public long getLingerTime() {
        return this.ltime;
    }

    public long getStime() {
        return this.stime;
    }

    public String getStringRep() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.stime);
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(this.ftime);
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

    public void setDtime(long j) {
        this.dtime = j;
    }

    public void setFtime(long j) {
        this.ftime = j;
    }

    public void setLingerTime(long j) {
        this.ltime = j;
    }

    public void setStime(long j) {
        this.stime = j;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("stime=");
        stringBuilder.append(this.stime);
        stringBuilder.append(" ftime(millis)=");
        stringBuilder.append(this.ftime);
        stringBuilder.append(" ltime(millis)=");
        stringBuilder.append(this.ltime);
        stringBuilder.append(" dtime(millis)=");
        stringBuilder.append(this.dtime);
        return stringBuilder.toString();
    }
}
