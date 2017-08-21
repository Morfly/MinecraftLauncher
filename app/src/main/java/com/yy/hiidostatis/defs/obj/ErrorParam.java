package com.yy.hiidostatis.defs.obj;

import java.util.List;

public class ErrorParam extends ParamableElem {
    private static final long serialVersionUID = -4313338615427788235L;

    public ErrorParam addParam(String str) {
        super.addParam(str);
        return this;
    }

    public ErrorParam addParams(List<String> list) {
        super.addParams(list);
        return this;
    }

    public ErrorParam clearParams() {
        super.clearParams();
        return this;
    }

    public String toString() {
        return "ErrorParam: " + super.toString();
    }
}
