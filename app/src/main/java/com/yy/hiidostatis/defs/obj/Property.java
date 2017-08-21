package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;

public class Property implements Serializable {
    private static final String DIVIDE_PROPERTY = ",";
    private static final int MAX_EVENT_FIELD_BYTES = 256;
    private static final int MAX_SIZE = 20;
    private static final long serialVersionUID = -6839046473425691433L;
    private LinkedHashMap<String, PropertyPair> mParams = new LinkedHashMap(20);

    private boolean isOverSize() {
        return this.mParams.size() >= 20;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.mParams = (LinkedHashMap) objectInputStream.readObject();
        if (this.mParams == null) {
            this.mParams = new LinkedHashMap();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.mParams);
    }

    public void clear() {
        this.mParams.clear();
    }

    public boolean containsKey(String str) {
        return this.mParams.containsKey(str);
    }

    public String getConnectedPropertys() {
        LinkedHashMap linkedHashMap = this.mParams;
        if (linkedHashMap == null || linkedHashMap.size() == 0) {
            return null;
        }
        if (linkedHashMap.size() == 1) {
            return Util.replaceEncode(((PropertyPair) linkedHashMap.values().iterator().next()).getConnectedPair(), DIVIDE_PROPERTY);
        }
        StringBuilder stringBuilder = new StringBuilder();
        Collection<PropertyPair> pair = linkedHashMap.values();
        for (PropertyPair connectedPair : pair) {
            stringBuilder.append(Util.replaceEncode(connectedPair.getConnectedPair(), DIVIDE_PROPERTY));
            stringBuilder.append(DIVIDE_PROPERTY);
        }
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    public void putDouble(String str, double d) {
        if (isOverSize()) {
            C1923L.warn(this, "Property max size is %d,now is %d,so get up this inParam:key=[%s],value=[%s]", Integer.valueOf(20), Integer.valueOf(size()), str + "", d + "");
        } else if (Util.empty(str)) {
            C1923L.error(this, "key is not allow null.", new Object[0]);
        } else if (str.getBytes().length > 256) {
            C1923L.warn(this, "key[%s] bytes[%d] must under %d bytes", str, Integer.valueOf(str.getBytes().length), Integer.valueOf(256));
        } else {
            this.mParams.put(str, new PropertyPair(str, d));
        }
    }

    public void putString(String str, String str2) {
        if (isOverSize()) {
            C1923L.warn(this, "Property max size is %d,now is %d,so get up this inParam:key=[%s],value=[%s]", Integer.valueOf(20), Integer.valueOf(size()), str + "", str2 + "");
        } else if (Util.empty(str)) {
            C1923L.error(this, "key is not allow null.", new Object[0]);
        } else if (str.getBytes().length > 256) {
            C1923L.warn(this, "key[%s] bytes[%d] must under %d bytes", str, Integer.valueOf(str.getBytes().length), Integer.valueOf(256));
        } else if (Util.empty(str2) || str2.getBytes().length <= 256) {
            this.mParams.put(str, new PropertyPair(str, str2));
        } else {
            C1923L.warn(this, "value[%s] bytes[%d] must under %d bytes", str2, Integer.valueOf(str2.getBytes().length), Integer.valueOf(256));
        }
    }

    public boolean removeProperty(String str) {
        return this.mParams.remove(str) != null;
    }

    public int size() {
        return this.mParams.size();
    }
}
