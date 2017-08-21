package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.defs.interf.IAct;

public enum Act implements IAct {
    MBSDK_INSTALL {
        public String toString() {
            return "mbsdkinstall";
        }
    },
    MBSDK_RUN {
        public String toString() {
            return "mbsdkrun";
        }
    },
    MBSDK_ACTION {
        public String toString() {
            return "mbsdkaction";
        }
    },
    MBSDK_ERROR {
        public String toString() {
            return "mbsdkerror";
        }
    },
    MBSDK_DO {
        public String toString() {
            return "mbsdkdo";
        }
    },
    MBSDK_DO5 {
        public String toString() {
            return "mbsdkdo5";
        }
    },
    MBSDK_LOGIN {
        public String toString() {
            return "mbsdklogin";
        }
    },
    MBSDK_REPORT {
        public String toString() {
            return "mbsdkreport";
        }
    },
    MBSDK_CRASH {
        public String toString() {
            return "mbsdkcrash";
        }
    },
    MBSDK_SUCCESS {
        public String toString() {
            return "mbsdksuccess";
        }
    },
    MBSDK_FAILURE {
        public String toString() {
            return "mbsdkfailure";
        }
    },
    MBSDK_APPLIST {
        public String toString() {
            return "mbsdkapplist";
        }
    },
    MBSDK_SDKLIST {
        public String toString() {
            return "mbsdksdklist";
        }
    },
    MBSDK_SDKDEVICE {
        public String toString() {
            return "mbsdkdevice";
        }
    },
    MBSDK_LANUCH {
        public String toString() {
            return "mbsdklaunch";
        }
    },
    MBSDK_PAGE {
        public String toString() {
            return "mbsdkpage";
        }
    },
    MBSDK_EVENT {
        public String toString() {
            return "mbsdkevent";
        }
    },
    MBSDK_PUSH {
        public String toString() {
            return "mbsdkpush";
        }
    },
    MBSDK_FBACK {
        public String toString() {
            return "mbsdkfback";
        }
    },
    MBSDK_CCLIST {
        public String toString() {
            return "mbsdkcclist";
        }
    };

    public String toString() {
        return super.toString().replaceAll("_", "").toLowerCase();
    }
}
