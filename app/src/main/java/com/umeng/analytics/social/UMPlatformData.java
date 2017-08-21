package com.umeng.analytics.social;

import android.text.TextUtils;

import com.umeng.analytics.C1823a;

import java.util.Locale;

public class UMPlatformData {
    private UMedia f3815a;
    private String f3816b = "";
    private String f3817c = "";
    private String f3818d;
    private GENDER f3819e;

    public enum GENDER {
        MALE(0) {
            public String toString() {
                return String.format(Locale.US, "Male:%d", new Object[]{Integer.valueOf(this.value)});
            }
        },
        FEMALE(1) {
            public String toString() {
                return String.format(Locale.US, "Female:%d", new Object[]{Integer.valueOf(this.value)});
            }
        };
        
        public int value;

        private GENDER(int i) {
            this.value = i;
        }
    }

    public enum UMedia {
        SINA_WEIBO {
            public String toString() {
                return "sina";
            }
        },
        TENCENT_WEIBO {
            public String toString() {
                return "tencent";
            }
        },
        TENCENT_QZONE {
            public String toString() {
                return "qzone";
            }
        },
        TENCENT_QQ {
            public String toString() {
                return "qq";
            }
        },
        WEIXIN_FRIENDS {
            public String toString() {
                return "wxsesion";
            }
        },
        WEIXIN_CIRCLE {
            public String toString() {
                return "wxtimeline";
            }
        },
        RENREN {
            public String toString() {
                return "renren";
            }
        },
        DOUBAN {
            public String toString() {
                return "douban";
            }
        };
    }

    public UMPlatformData(UMedia uMedia, String str) {
        if (uMedia == null || TextUtils.isEmpty(str)) {
            C1854b.m5922b(C1823a.f3725e, "parameter is not valid");
            return;
        }
        this.f3815a = uMedia;
        this.f3816b = str;
    }

    public GENDER getGender() {
        return this.f3819e;
    }

    public UMedia getMeida() {
        return this.f3815a;
    }

    public String getName() {
        return this.f3818d;
    }

    public String getUsid() {
        return this.f3816b;
    }

    public String getWeiboId() {
        return this.f3817c;
    }

    public boolean isValid() {
        return (this.f3815a == null || TextUtils.isEmpty(this.f3816b)) ? false : true;
    }

    public void setGender(GENDER gender) {
        this.f3819e = gender;
    }

    public void setName(String str) {
        this.f3818d = str;
    }

    public void setWeiboId(String str) {
        this.f3817c = str;
    }

    public String toString() {
        return "UMPlatformData [meida=" + this.f3815a + ", usid=" + this.f3816b + ", weiboId=" + this.f3817c + ", name=" + this.f3818d + ", gender=" + this.f3819e + "]";
    }
}
