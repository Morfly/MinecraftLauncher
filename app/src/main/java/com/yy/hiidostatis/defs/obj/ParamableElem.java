package com.yy.hiidostatis.defs.obj;

import android.text.TextUtils;

import com.yy.hiidostatis.inner.util.Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParamableElem implements Serializable {
    private static final String DIVIDE_PARAM = ";";
    private static final String DIVIDE_PARAM_TMP = "@@$$@@";
    private static final long serialVersionUID = 6761787877387462101L;
    private ArrayList<String> mParams = new ArrayList();

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.mParams = (ArrayList) objectInputStream.readObject();
        if (this.mParams == null) {
            this.mParams = new ArrayList();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.mParams);
    }

    public ParamableElem addParam(String str) {
        this.mParams.add(Util.asEmptyOnNull(str));
        return this;
    }

    public ParamableElem addParams(List<String> list) {
        this.mParams.addAll(list);
        return this;
    }

    public ParamableElem clearParams() {
        this.mParams.clear();
        return this;
    }

    public String getConnectedParams() {
        List<String> collection = this.mParams;
        if (Util.empty(collection)) {
            return null;
        }
        if (collection.size() == 1) {
            return Util.replaceEncode(collection.get(0), DIVIDE_PARAM);
        }
        return Util.replaceEncode(TextUtils.join(DIVIDE_PARAM_TMP, collection.toArray(new String[collection.size()])), DIVIDE_PARAM).replace(DIVIDE_PARAM_TMP, DIVIDE_PARAM);
    }

    public ArrayList<String> getParams() {
        return new ArrayList(this.mParams);
    }

    boolean isSameParams(String... strArr) {
        if (this.mParams.isEmpty()) {
            return strArr.length == 0;
        } else if (this.mParams.size() != strArr.length) {
            return false;
        } else {
            for (int i = 0; i < strArr.length; i++) {
                if (!strArr[i].equals((String) this.mParams.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
