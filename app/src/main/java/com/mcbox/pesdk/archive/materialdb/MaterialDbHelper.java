package com.mcbox.pesdk.archive.materialdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaterialDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "material.db";
    public static final int VERSION = 11;
    private static MaterialDbHelper helper = null;

    private MaterialDbHelper(Context context) {
        super(context, context.getFilesDir().getAbsolutePath() + "/" + "material.db", null, 11);
    }

    public static synchronized MaterialDbHelper getInstance(Context context) {
        MaterialDbHelper materialDbHelper;
        synchronized (MaterialDbHelper.class) {
            if (helper == null) {
                helper = new MaterialDbHelper(context);
            }
            materialDbHelper = helper;
        }
        return materialDbHelper;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
