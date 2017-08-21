package com.mcbox.pesdk.mcfloat.func;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;

import com.mcbox.pesdk.archive.materialdb.MaterialDbConfig;
import com.mcbox.pesdk.archive.materialdb.MaterialDbDao;
import com.mcbox.pesdk.mcfloat.model.PotionItem;
import com.mcbox.pesdk.util.LanguageProperties;
import com.umeng.analytics.onlineconfig.C1837a;
import com.yy.hiidostatis.inner.BaseStatisContent;

import java.util.ArrayList;
import java.util.List;

public class DtPotion {
    public static SparseArray<PotionItem> enchantItems = new SparseArray();
    public static List<PotionItem> gainList = new ArrayList();
    public static List<PotionItem> negativeList = new ArrayList();

    private static Drawable byteArray2Drawable(Resources resources, byte[] bArr) {
        return (bArr == null || bArr.length <= 0) ? null : new BitmapDrawable(resources, BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
    }

    public static void loadDtPotionList(Context context) {
        gainList.clear();
        negativeList.clear();
        String systemLanguage = LanguageProperties.getSystemLanguage(context);
        MaterialDbDao materialDbDao = new MaterialDbDao(context);
        Cursor queryAllPotionData = materialDbDao.queryAllPotionData(systemLanguage);
        queryAllPotionData.moveToFirst();
        do {
            String string = queryAllPotionData.getString(queryAllPotionData.getColumnIndex(materialDbDao.getLanguageName(systemLanguage)));
            if (TextUtils.isEmpty(string)) {
                string = queryAllPotionData.getString(queryAllPotionData.getColumnIndex((String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(MaterialDbConfig.DEFAULT_LANGUAGE)));
            }
            int i = queryAllPotionData.getInt(queryAllPotionData.getColumnIndex("id"));
            byte[] blob = queryAllPotionData.getBlob(queryAllPotionData.getColumnIndex("drw"));
            String string2 = queryAllPotionData.getString(queryAllPotionData.getColumnIndex(BaseStatisContent.KEY));
            int i2 = queryAllPotionData.getInt(queryAllPotionData.getColumnIndex("level"));
            int i3 = queryAllPotionData.getInt(queryAllPotionData.getColumnIndex(C1837a.f3790a));
            if (string2 != null) {
                PotionItem potionItem = new PotionItem();
                potionItem.id = i;
                potionItem.key = string2;
                potionItem.drw = byteArray2Drawable(context.getResources(), blob);
                potionItem.level = i2;
                potionItem.name = string;
                potionItem.type = i3;
                if (i3 == 0) {
                    gainList.add(potionItem);
                } else if (i3 == 1) {
                    negativeList.add(potionItem);
                }
                enchantItems.put(potionItem.id, potionItem);
            }
        } while (queryAllPotionData.moveToNext());
        queryAllPotionData.close();
        materialDbDao.finalize();
    }
}
