package com.umeng.fb;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fb.C2340p;
import fb.C2345u;
import fb.C2347w;
import fb.C2348x;
import fb.C2349y;

public class ContactActivity extends Activity {
    private static final String f3857a = "plain";
    private ImageView f3858b;
    private ImageView f3859c;
    private EditText f3860d;
    private FeedbackAgent f3861e;
    private TextView f3862f;

    class C18591 implements OnClickListener {
        final /* synthetic */ ContactActivity f3854a;

        C18591(ContactActivity contactActivity) {
            this.f3854a = contactActivity;
        }

        public void onClick(View view) {
            this.f3854a.m5952a();
        }
    }

    class C18602 implements OnClickListener {
        final /* synthetic */ ContactActivity f3855a;

        C18602(ContactActivity contactActivity) {
            this.f3855a = contactActivity;
        }

        public void onClick(View view) {
            try {
                UserInfo userInfo = this.f3855a.f3861e.getUserInfo();
                UserInfo userInfo2 = userInfo == null ? new UserInfo() : userInfo;
                Map contact = userInfo2.getContact();
                if (contact == null) {
                    contact = new HashMap();
                }
                contact.put(ContactActivity.f3857a, this.f3855a.f3860d.getEditableText().toString());
                userInfo2.setContact(contact);
                this.f3855a.f3861e.setUserInfo(userInfo2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.f3855a.m5952a();
        }
    }

    class C18613 {
        final /* synthetic */ ContactActivity f3856a;

        C18613(ContactActivity contactActivity) {
            this.f3856a = contactActivity;
        }

        public void m5949a(Activity activity) {
            activity.overridePendingTransition(C2345u.m10380a(this.f3856a), C2345u.m10383d(this.f3856a));
        }
    }

    void m5952a() {
        finish();
        if (VERSION.SDK_INT > 4) {
            new C18613(this).m5949a(this);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C2348x.m10399a(this));
        this.f3861e = new FeedbackAgent(this);
        this.f3858b = (ImageView) findViewById(C2347w.m10390d(this));
        this.f3859c = (ImageView) findViewById(C2347w.m10395i(this));
        this.f3860d = (EditText) findViewById(C2347w.m10396j(this));
        this.f3862f = (TextView) findViewById(C2347w.m10397k(this));
        try {
            String str = (String) this.f3861e.getUserInfo().getContact().get(f3857a);
            this.f3860d.setText(str);
            long userInfoLastUpdateAt = this.f3861e.getUserInfoLastUpdateAt();
            if (userInfoLastUpdateAt > 0) {
                Date date = new Date(userInfoLastUpdateAt);
                this.f3862f.setText(new StringBuilder(String.valueOf(getResources().getString(C2349y.m10404a(this)))).append(SimpleDateFormat.getDateTimeInstance().format(date)).toString());
                this.f3862f.setVisibility(View.VISIBLE);
            } else {
                this.f3862f.setVisibility(View.GONE);
            }
            if (C2340p.m10364d(str)) {
                this.f3860d.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.toggleSoftInput(2, 0);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.f3858b.setOnClickListener(new C18591(this));
        this.f3859c.setOnClickListener(new C18602(this));
    }
}
