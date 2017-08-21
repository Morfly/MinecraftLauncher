package com.umeng.fb.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.umeng.analytics.onlineconfig.C1837a;
import com.umeng.fb.model.Reply.STATUS;
import com.umeng.fb.model.Reply.TYPE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fb.C2324a;
import fb.C2342r;
import fb.C2350z;

public class Conversation {
    private static final String f3899b = Conversation.class.getName();
    private static ExecutorService f3900d = Executors.newSingleThreadExecutor();
    List<Reply> f3901a = new ArrayList();
    private Context f3902c;
    private String f3903e;
    private String f3904f;
    private String f3905g;
    private Map<String, Reply> f3906h;

    public interface SyncListener {
        void onReceiveDevReply(List<DevReply> list);

        void onSendUserReply(List<Reply> list);
    }

    class MessageWrapper {
        List<Reply> f3893a;
        List<DevReply> f3894b;

        MessageWrapper() {
        }
    }

    class SyncHandler extends Handler {
        static final int f3895b = 1;
        static final int f3896c = 2;
        SyncListener f3897a;
        final /* synthetic */ Conversation f3898d;

        public SyncHandler(Conversation conversation, SyncListener syncListener) {
            this.f3898d = conversation;
            this.f3897a = syncListener;
        }

        public void handleMessage(Message message) {
            Object obj = 1;
            Reply reply;
            if (message.what == 2) {
                reply = (Reply) message.obj;
                if (message.arg1 != 1) {
                    obj = null;
                }
                if (obj != null) {
                    reply.f3923i = STATUS.SENT;
                }
            } else if (message.what == 1) {
                MessageWrapper messageWrapper = (MessageWrapper) message.obj;
                List list = messageWrapper.f3894b;
                List list2 = messageWrapper.f3893a;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        reply = (Reply) it.next();
                        if (this.f3898d.f3906h.containsKey(reply.f3917c)) {
                            it.remove();
                        } else {
                            this.f3898d.f3906h.put(reply.f3917c, reply);
                        }
                    }
                }
                this.f3898d.m5973b();
                if (this.f3897a != null) {
                    this.f3897a.onReceiveDevReply(list);
                    this.f3897a.onSendUserReply(list2);
                }
            }
        }
    }

    public Conversation(Context context) {
        this.f3902c = context;
        this.f3905g = C2324a.m10275o(this.f3902c);
        this.f3903e = C2350z.m10409a(this.f3902c);
        this.f3904f = C2324a.m10267g(this.f3902c);
        this.f3906h = new ConcurrentHashMap();
    }

    Conversation(String str, JSONArray jSONArray, Context context) throws JSONException {
        this.f3902c = context;
        this.f3905g = C2324a.m10275o(this.f3902c);
        this.f3903e = str;
        this.f3904f = C2324a.m10267g(this.f3902c);
        this.f3906h = new HashMap();
        if (jSONArray != null && jSONArray.length() >= 1) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString(C1837a.f3790a);
                Reply reply = null;
                if (TYPE.NEW_FEEDBACK.toString().equals(string)) {
                    reply = new UserTitleReply(jSONObject);
                } else if (TYPE.USER_REPLY.toString().equals(string)) {
                    reply = new UserReply(jSONObject);
                } else if (TYPE.DEV_REPLY.toString().equals(string)) {
                    reply = new DevReply(jSONObject);
                }
                if (reply == null) {
                    throw new JSONException("Failed to create Conversation using given JSONArray: " + jSONArray + " at element " + i + ": " + jSONObject);
                }
                if (!this.f3906h.containsKey(reply.f3917c)) {
                    this.f3906h.put(reply.f3917c, reply);
                }
            }
            m5973b();
        }
    }

    private void m5973b() {
        Store.getInstance(this.f3902c).saveCoversation(this);
    }

    JSONArray m5978a() {
        JSONArray jSONArray = new JSONArray();
        for (Entry value : this.f3906h.entrySet()) {
            jSONArray.put(((Reply) value.getValue()).toJson());
        }
        return jSONArray;
    }

    public void addUserReply(String str) {
        Reply userTitleReply = this.f3906h.size() < 1 ? new UserTitleReply(str, this.f3905g, this.f3904f, this.f3903e) : new UserReply(str, this.f3905g, this.f3904f, this.f3903e);
        if (!this.f3906h.containsKey(userTitleReply.f3917c)) {
            this.f3906h.put(userTitleReply.f3917c, userTitleReply);
        }
        m5973b();
    }

    public String getId() {
        return this.f3903e;
    }

    public synchronized List<Reply> getReplyList() {
        this.f3901a.clear();
        this.f3901a.addAll(this.f3906h.values());
        Collections.sort(this.f3901a);
        return this.f3901a;
    }

    public void sync(SyncListener syncListener) {
        final Handler syncHandler = new SyncHandler(this, syncListener);
        f3900d.execute(new Runnable() {
            /* synthetic */ Conversation f3891a;

            public void run() {
                List<Reply> arrayList = new ArrayList();
                List arrayList2 = new ArrayList();
                Date date = null;
                String str = "";
                for (Entry value : this.f3891a.f3906h.entrySet()) {
                    Reply reply = (Reply) value.getValue();
                    String r2 = "";
                    if ((reply instanceof UserReply) || (reply instanceof UserTitleReply)) {
                        if (reply.f3923i == STATUS.NOT_SENT) {
                            arrayList.add(reply);
                        }
                    } else if ((reply instanceof DevReply) && (r2 == null || r2.compareTo(String.valueOf(reply.getDatetime())) < 0)) {
                        date = reply.getDatetime();
                        str = reply.f3917c;
                    }
                }
                arrayList2.add(this.f3891a.f3903e);
                for (Reply reply2 : arrayList) {
                    boolean a = new C2342r(this.f3891a.f3902c).m10375a(reply2);
                    if (a) {
                        Message obtain = Message.obtain();
                        obtain.what = 2;
                        obtain.obj = reply2;
                        obtain.arg1 = a ? 1 : 0;
                        syncHandler.sendMessage(obtain);
                    }
                }
                List a2 = new C2342r(this.f3891a.f3902c).m10373a(arrayList2, str, this.f3891a.f3905g);
                Message obtain2 = Message.obtain();
                obtain2.what = 1;
                MessageWrapper messageWrapper = new MessageWrapper();
                messageWrapper.f3894b = a2;
                messageWrapper.f3893a = arrayList;
                obtain2.obj = messageWrapper;
                syncHandler.sendMessage(obtain2);
            }
        });
    }
}
