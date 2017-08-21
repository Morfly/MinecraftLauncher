package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EventElementInfo extends ParamableElem implements Elem {
    public static final transient int CTYPE_CLICK_TIMES_REPORT = 1;
    public static final transient int CTYPE_CUSTOM_REPORT = 0;
    public static final transient int DEFAULT_CVALUE = 0;
    private static final long serialVersionUID = 7740962417443813455L;
    String cid;
    int ctype;
    String cvalue;
    private Property property = new Property();

    public EventElementInfo(String str, int i) {
        this.cid = str;
        this.ctype = 1;
        this.cvalue = Integer.toString(i);
    }

    public EventElementInfo(String str, String str2) {
        this.cid = str;
        this.ctype = 0;
        this.cvalue = str2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.cid = (String) objectInputStream.readObject();
        this.ctype = objectInputStream.readInt();
        this.cvalue = objectInputStream.readUTF();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.cid);
        objectOutputStream.writeInt(this.ctype);
        objectOutputStream.writeUTF(this.cvalue);
    }

    public String getCustomValue() {
        return this.cvalue;
    }

    public String getEventId() {
        return this.cid;
    }

    public int getEventType() {
        return this.ctype;
    }

    public Property getProperty() {
        return this.property;
    }

    public String getStringRep() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Util.replaceEncode(this.cid, Elem.DIVIDER));
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(this.ctype);
        stringBuilder.append(Elem.DIVIDER);
        stringBuilder.append(Util.replaceEncode(this.cvalue, Elem.DIVIDER));
        stringBuilder.append(Elem.DIVIDER);
        String connectedParams = getConnectedParams();
        if (!Util.empty(connectedParams)) {
            stringBuilder.append(Util.replaceEncode(connectedParams, Elem.DIVIDER));
        }
        stringBuilder.append(Elem.DIVIDER);
        connectedParams = this.property == null ? null : this.property.getConnectedPropertys();
        if (!Util.empty(connectedParams)) {
            stringBuilder.append(Util.replaceEncode(connectedParams, Elem.DIVIDER));
        }
        return stringBuilder.toString();
    }

    void setCid(String str) {
        this.cid = str;
    }

    void setCtype(int i) {
        this.ctype = i;
    }

    public void setCustomValue(String str) {
        if (this.ctype == 1) {
            try {
                Long.parseLong(str);
            } catch (NumberFormatException e) {
                C1923L.error(this, "Input arg error %s for %s", str, e);
            }
        }
        this.cvalue = str;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("eventId=");
        stringBuilder.append(this.cid);
        stringBuilder.append(", event type=");
        stringBuilder.append(this.ctype == 0 ? "Custom" : "Times");
        stringBuilder.append(", value=");
        stringBuilder.append(this.cvalue);
        return stringBuilder.toString();
    }
}
