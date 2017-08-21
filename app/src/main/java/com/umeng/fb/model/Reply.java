package com.umeng.fb.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fb.C2325b;
import fb.C2350z;

public abstract class Reply implements Comparable<Reply> {
    private static final String f3907a = Reply.class.getName();
    private static final String f3908j = "content";
    private static final String f3909k = "reply_id";
    private static final String f3910l = "appkey";
    private static final String f3911m = "user_id";
    private static final String f3912n = "feedback_id";
    private static final String f3913o = "type";
    private static final String f3914p = "datetime";
    private static final String f3915q = "status";
    protected String f3916b;
    protected String f3917c;
    protected String f3918d;
    protected String f3919e;
    protected String f3920f;
    protected TYPE f3921g;
    protected Date f3922h;
    protected STATUS f3923i;
    public TYPE g;

    public enum STATUS {
        SENDING("sending"),
        NOT_SENT("not_sent"),
        SENT("sent");
        
        private final String f3926a;

        private STATUS(String str) {
            this.f3926a = str;
        }

        public static STATUS get(String str) {
            if (SENDING.toString().equals(str)) {
                return SENDING;
            }
            if (NOT_SENT.toString().equals(str)) {
                return NOT_SENT;
            }
            if (SENT.toString().equals(str)) {
                return SENT;
            }
            throw new RuntimeException(new StringBuilder(String.valueOf(str)).append("Cannot convert ").append(str).append(" to enum ").append(STATUS.class.getName()).toString());
        }

        public String toString() {
            return this.f3926a;
        }
    }

    public enum TYPE {
        NEW_FEEDBACK("new_feedback"),
        DEV_REPLY("dev_reply"),
        USER_REPLY("user_reply");
        
        private final String f3928a;

        private TYPE(String str) {
            this.f3928a = str;
        }

        public static TYPE get(String str) {
            if (NEW_FEEDBACK.toString().equals(str)) {
                return NEW_FEEDBACK;
            }
            if (DEV_REPLY.toString().equals(str)) {
                return DEV_REPLY;
            }
            if (USER_REPLY.toString().equals(str)) {
                return USER_REPLY;
            }
            throw new RuntimeException(new StringBuilder(String.valueOf(str)).append("Cannot convert ").append(str).append(" to enum ").append(TYPE.class.getName()).toString());
        }

        public String toString() {
            return this.f3928a;
        }
    }

    Reply(String str, String str2, String str3, String str4, TYPE type) {
        this.f3916b = str;
        this.f3917c = C2350z.m10408a();
        this.f3918d = str2;
        this.f3919e = str3;
        this.f3920f = str4;
        this.f3921g = type;
        this.f3922h = new Date();
        this.f3923i = STATUS.NOT_SENT;
    }

    Reply(JSONObject jSONObject) throws JSONException {
        this.f3916b = jSONObject.optString(f3908j, "");
        this.f3917c = jSONObject.optString(f3909k, "");
        this.f3918d = jSONObject.optString("appkey", "");
        this.f3919e = jSONObject.optString("user_id", "");
        this.f3920f = jSONObject.optString(f3912n, "");
        try {
            this.f3921g = TYPE.get(jSONObject.getString("type"));
            try {
                this.f3922h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jSONObject.getString(f3914p));
            } catch (ParseException e) {
                try {
                    this.f3922h = new SimpleDateFormat().parse(jSONObject.getString(f3914p));
                } catch (ParseException e2) {
                    e2.printStackTrace();
                    C2325b.m10287b(f3907a, "Reply(JSONObject json): error parsing datetime from json " + jSONObject.optString(f3914p, "") + ", using current Date instead.");
                    this.f3922h = new Date();
                }
            }
            this.f3923i = STATUS.get(jSONObject.optString("status", STATUS.NOT_SENT.toString()));
        } catch (Exception e3) {
            throw new JSONException(e3.getMessage());
        }
    }

    public int compareTo(Reply reply) {
        return this.f3922h.compareTo(reply.f3922h);
    }

    public String getContent() {
        return this.f3916b;
    }

    public Date getDatetime() {
        return this.f3922h;
    }

    public STATUS getStatus() {
        return this.f3923i;
    }

    public JSONObject toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(f3908j, this.f3916b);
            jSONObject.put(f3909k, this.f3917c);
            jSONObject.put("appkey", this.f3918d);
            jSONObject.put("user_id", this.f3919e);
            jSONObject.put(f3912n, this.f3920f);
            jSONObject.put("type", this.f3921g);
            jSONObject.put(f3914p, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(this.f3922h));
            jSONObject.put("status", this.f3923i.toString());
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
