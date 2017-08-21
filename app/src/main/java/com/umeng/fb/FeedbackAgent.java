package com.umeng.fb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.Store;
import com.umeng.fb.model.UserInfo;

import java.util.List;
import java.util.Locale;

import fb.C2325b;
import fb.C2346v;
import fb.C2349y;

public class FeedbackAgent {
    private static final String f3888a = FeedbackAgent.class.getName();
    private Context f3889b;
    private Store f3890c = Store.getInstance(this.f3889b);

    class C18711 implements SyncListener {
        final /* synthetic */ FeedbackAgent f3887a;

        C18711(FeedbackAgent feedbackAgent) {
            this.f3887a = feedbackAgent;
        }

        public void onReceiveDevReply(List<DevReply> list) {
            if (list != null && list.size() >= 1) {
                String str = "";
                if (list.size() == 1) {
                    String string = this.f3887a.f3889b.getResources().getString(C2349y.m10406c(this.f3887a.f3889b));
                    str = String.format(Locale.US, string, new Object[]{((DevReply) list.get(0)).getContent()});
                } else {
                    str = this.f3887a.f3889b.getResources().getString(C2349y.m10407d(this.f3887a.f3889b));
                    str = String.format(Locale.US, str, new Object[]{Integer.valueOf(list.size())});
                }
                try {
                    this.f3887a.m5966a(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onSendUserReply(List<Reply> list) {
        }
    }

    public FeedbackAgent(Context context) {
        this.f3889b = context;
    }

    private void m5966a(String str) {
        NotificationManager notificationManager = (NotificationManager) this.f3889b.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence string = this.f3889b.getString(C2349y.m10405b(this.f3889b));
        Intent intent = new Intent(this.f3889b, ConversationActivity.class);
        intent.setFlags(131072);
        notificationManager.notify(0, new Builder(this.f3889b).setSmallIcon(C2346v.m10386c(this.f3889b)).setContentTitle(string).setTicker(string).setContentText(str).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(this.f3889b, 0, intent, 0)).build());
    }

    public List<String> getAllConversationIds() {
        return this.f3890c.getAllConversationIds();
    }

    public Conversation getConversationById(String str) {
        return this.f3890c.getConversationById(str);
    }

    public Conversation getDefaultConversation() {
        List allConversationIds = getAllConversationIds();
        if (allConversationIds == null || allConversationIds.size() < 1) {
            C2325b.m10289c(f3888a, "getDefaultConversation: No conversation saved locally. Create a new one.");
            return new Conversation(this.f3889b);
        }
        C2325b.m10289c(f3888a, "getDefaultConversation: There are " + allConversationIds.size() + " saved locally, use the first one by default.");
        return getConversationById((String) allConversationIds.get(0));
    }

    public UserInfo getUserInfo() {
        return this.f3890c.getUserInfo();
    }

    public long getUserInfoLastUpdateAt() {
        return this.f3890c.getUserInfoLastUpdateAt();
    }

    public void setDebug(boolean z) {
        C2325b.f6237a = z;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.f3890c.saveUserInfo(userInfo);
    }

    public void startFeedbackActivity() {
        try {
            Intent intent = new Intent();
            intent.setClass(this.f3889b, ConversationActivity.class);
            this.f3889b.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sync() {
        getDefaultConversation().sync(new C18711(this));
    }
}
