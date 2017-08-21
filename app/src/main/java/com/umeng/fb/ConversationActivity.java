package com.umeng.fb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;

import java.text.SimpleDateFormat;
import java.util.List;

import fb.C2340p;
import fb.C2345u;
import fb.C2346v;
import fb.C2347w;
import fb.C2348x;

public class ConversationActivity extends Activity {
    private static final String f3877e = ConversationActivity.class.getName();
    RelativeLayout f3878a;
    int f3879b;
    int f3880c;
    EditText f3881d;
    private FeedbackAgent f3882f;
    private Conversation f3883g;
    private C1870a f3884h;
    private ListView f3885i;
    private int f3886j;

    class C18631 implements OnClickListener {
        final /* synthetic */ ConversationActivity f3864a;

        class C18621 {
            final /* synthetic */ C18631 f3863a;

            C18621(C18631 c18631) {
                this.f3863a = c18631;
            }

            public void m5953a(Activity activity) {
                activity.overridePendingTransition(C2345u.m10381b(this.f3863a.f3864a), C2345u.m10382c(this.f3863a.f3864a));
            }
        }

        C18631(ConversationActivity conversationActivity) {
            this.f3864a = conversationActivity;
        }

        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(this.f3864a, ContactActivity.class);
            this.f3864a.startActivity(intent);
            if (VERSION.SDK_INT > 4) {
                new C18621(this).m5953a(this.f3864a);
            }
        }
    }

    class C18642 implements OnClickListener {
        final /* synthetic */ ConversationActivity f3865a;

        C18642(ConversationActivity conversationActivity) {
            this.f3865a = conversationActivity;
        }

        public void onClick(View view) {
            this.f3865a.finish();
        }
    }

    class C18653 implements OnClickListener {
        final /* synthetic */ ConversationActivity f3866a;

        C18653(ConversationActivity conversationActivity) {
            this.f3866a = conversationActivity;
        }

        public void onClick(View view) {
            String trim = this.f3866a.f3881d.getEditableText().toString().trim();
            if (!C2340p.m10364d(trim)) {
                this.f3866a.f3881d.getEditableText().clear();
                this.f3866a.f3883g.addUserReply(trim);
                this.f3866a.m5963a();
                InputMethodManager inputMethodManager = (InputMethodManager) this.f3866a.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(this.f3866a.f3881d.getWindowToken(), 0);
                }
            }
        }
    }

    class C18664 implements OnTouchListener {
        final /* synthetic */ ConversationActivity f3867a;

        C18664(ConversationActivity conversationActivity) {
            this.f3867a = conversationActivity;
        }

        private void m5955a(MotionEvent motionEvent) {
            int historySize = motionEvent.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                if (this.f3867a.f3885i.getFirstVisiblePosition() == 0) {
                    int historicalY = (int) (((double) ((((int) motionEvent.getHistoricalY(i)) - this.f3867a.f3886j) - this.f3867a.f3879b)) / 1.7d);
                    this.f3867a.f3878a.setVisibility(View.VISIBLE);
                    this.f3867a.f3878a.setPadding(this.f3867a.f3878a.getPaddingLeft(), historicalY, this.f3867a.f3878a.getPaddingRight(), this.f3867a.f3878a.getPaddingBottom());
                }
            }
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.f3867a.f3885i.getAdapter().getCount() >= 2) {
                switch (motionEvent.getAction()) {
                    case 0:
                        this.f3867a.f3886j = (int) motionEvent.getY();
                        break;
                    case 1:
                        if (this.f3867a.f3885i.getFirstVisiblePosition() == 0) {
                            if (this.f3867a.f3878a.getBottom() < this.f3867a.f3879b + 20 && this.f3867a.f3878a.getTop() <= 0) {
                                this.f3867a.f3885i.setSelection(1);
                                this.f3867a.f3878a.setVisibility(View.GONE);
                                this.f3867a.f3878a.setPadding(this.f3867a.f3878a.getPaddingLeft(), -this.f3867a.f3879b, this.f3867a.f3878a.getPaddingRight(), this.f3867a.f3878a.getPaddingBottom());
                                break;
                            }
                            this.f3867a.f3878a.setVisibility(View.VISIBLE);
                            this.f3867a.f3878a.setPadding(this.f3867a.f3878a.getPaddingLeft(), this.f3867a.f3880c, this.f3867a.f3878a.getPaddingRight(), this.f3867a.f3878a.getPaddingBottom());
                            break;
                        }
                        break;
                    case 2:
                        m5955a(motionEvent);
                        break;
                    default:
                        break;
                }
            }
            return false;
        }
    }

    class C18675 implements OnScrollListener {
        final /* synthetic */ ConversationActivity f3868a;
        private int f3869b;

        C18675(ConversationActivity conversationActivity) {
            this.f3868a = conversationActivity;
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (this.f3869b != 2) {
            }
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            this.f3869b = i;
        }
    }

    class C18686 implements SyncListener {
        final /* synthetic */ ConversationActivity f3870a;

        C18686(ConversationActivity conversationActivity) {
            this.f3870a = conversationActivity;
        }

        public void onReceiveDevReply(List<DevReply> list) {
        }

        public void onSendUserReply(List<Reply> list) {
            this.f3870a.f3884h.notifyDataSetChanged();
        }
    }

    class C1870a extends BaseAdapter {
        Context f3874a;
        LayoutInflater f3875b = LayoutInflater.from(this.f3874a);
        final /* synthetic */ ConversationActivity f3876c;

        class C1869a {
            TextView f3871a;
            TextView f3872b;
            final /* synthetic */ C1870a f3873c;

            C1869a(C1870a c1870a) {
                this.f3873c = c1870a;
            }
        }

        public C1870a(ConversationActivity conversationActivity, Context context) {
            this.f3876c = conversationActivity;
            this.f3874a = context;
        }

        public int getCount() {
            List replyList = this.f3876c.f3883g.getReplyList();
            return replyList == null ? 0 : replyList.size();
        }

        public Object getItem(int i) {
            return this.f3876c.f3883g.getReplyList().get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            C1869a c1869a;
            if (view == null) {
                view = this.f3875b.inflate(C2348x.m10401c(this.f3874a), null);
                c1869a = new C1869a(this);
                c1869a.f3871a = (TextView) view.findViewById(C2347w.m10392f(this.f3874a));
                c1869a.f3872b = (TextView) view.findViewById(C2347w.m10388b(this.f3874a));
                view.setTag(c1869a);
            } else {
                c1869a = (C1869a) view.getTag();
            }
            Reply reply = (Reply) this.f3876c.f3883g.getReplyList().get(i);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            if (reply instanceof DevReply) {
                layoutParams.addRule(9);
                c1869a.f3872b.setLayoutParams(layoutParams);
                c1869a.f3872b.setBackgroundResource(C2346v.m10385b(this.f3874a));
            } else {
                layoutParams.addRule(11);
                c1869a.f3872b.setLayoutParams(layoutParams);
                c1869a.f3872b.setBackgroundResource(C2346v.m10384a(this.f3874a));
            }
            c1869a.f3871a.setText(SimpleDateFormat.getDateTimeInstance().format(reply.getDatetime()));
            c1869a.f3872b.setText(reply.getContent());
            return view;
        }
    }

    private void m5957a(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int i = layoutParams.height;
        view.measure(childMeasureSpec, i > 0 ? MeasureSpec.makeMeasureSpec(i, MeasureSpec.EXACTLY) : MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void m5960b() {
        this.f3878a = (RelativeLayout) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(C2348x.m10403e(this), this.f3885i, false);
        this.f3885i.addHeaderView(this.f3878a);
        m5957a(this.f3878a);
        this.f3879b = this.f3878a.getMeasuredHeight();
        this.f3880c = this.f3878a.getPaddingTop();
        this.f3878a.setPadding(this.f3878a.getPaddingLeft(), -this.f3879b, this.f3878a.getPaddingRight(), this.f3878a.getPaddingBottom());
        this.f3878a.setVisibility(View.GONE);
        this.f3885i.setOnTouchListener(new C18664(this));
        this.f3885i.setOnScrollListener(new C18675(this));
    }

    void m5963a() {
        this.f3883g.sync(new C18686(this));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C2348x.m10400b(this));
        try {
            this.f3882f = new FeedbackAgent(this);
            this.f3883g = this.f3882f.getDefaultConversation();
            this.f3885i = (ListView) findViewById(C2347w.m10387a(this));
            m5960b();
            this.f3884h = new C1870a(this, this);
            this.f3885i.setAdapter(this.f3884h);
            m5963a();
            View findViewById = findViewById(C2347w.m10389c(this));
            findViewById.setOnClickListener(new C18631(this));
            if (this.f3882f.getUserInfoLastUpdateAt() > 0) {
                findViewById.setVisibility(View.GONE);
            }
            findViewById(C2347w.m10390d(this)).setOnClickListener(new C18642(this));
            this.f3881d = (EditText) findViewById(C2347w.m10388b(this));
            findViewById(C2347w.m10391e(this)).setOnClickListener(new C18653(this));
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }
}
