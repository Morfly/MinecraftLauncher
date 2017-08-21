package com.mcbox.pesdk.archive.materialdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mcbox.pesdk.util.LauncherMcVersion;

public class MaterialDbDao {
    private SQLiteDatabase db = null;

    public MaterialDbDao(Context context) {
        this.db = MaterialDbHelper.getInstance(context).getReadableDatabase();
    }

    private String getItemTableName(LauncherMcVersion launcherMcVersion) {
        String str = "";
        if (launcherMcVersion.getMajor().intValue() >= 0) {
            str = (launcherMcVersion.getMinor().intValue() == 14 && launcherMcVersion.getPatch().intValue() == 99) ? (String) MaterialDbConfig.VERSION_TAB_MAP.get(15) : (launcherMcVersion.getMinor().intValue() == 15 && launcherMcVersion.getBeta().intValue() == 0) ? (String) MaterialDbConfig.VERSION_TAB_MAP.get(14) : (String) MaterialDbConfig.VERSION_TAB_MAP.get(launcherMcVersion.getMinor().intValue());
        }
        return (str == null || str.length() == 0) ? (String) MaterialDbConfig.VERSION_TAB_MAP.valueAt(0) : str;
    }

    private String getLanFiled(String str) {
        String str2 = (String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(MaterialDbConfig.DEFAULT_LANGUAGE);
        return !str.equalsIgnoreCase(str2) ? str2 + ", " + str : str2;
    }

    private String getTypeTableName(LauncherMcVersion launcherMcVersion) {
        String str = (String) MaterialDbConfig.VERSION_TYPE_MAP.valueAt(0);
        if (launcherMcVersion.getMajor().intValue() >= 0) {
            str = (launcherMcVersion.getMinor().intValue() == 14 && launcherMcVersion.getPatch().intValue() == 99) ? (String) MaterialDbConfig.VERSION_TYPE_MAP.get(15) : (launcherMcVersion.getMinor().intValue() == 15 && launcherMcVersion.getBeta().intValue() == 0) ? (String) MaterialDbConfig.VERSION_TYPE_MAP.get(14) : (String) MaterialDbConfig.VERSION_TYPE_MAP.get(launcherMcVersion.getMinor().intValue());
        }
        return (str == null || str.length() == 0) ? (String) MaterialDbConfig.VERSION_TYPE_MAP.valueAt(0) : str;
    }

    public void finalize() {
        if (this.db.isOpen()) {
            this.db.close();
        }
    }

    public String getLanguageName(String str) {
        return MaterialDbConfig.LANGUAGE_CODE_COLUMN.containsKey(str) ? (String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(str) : (String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(MaterialDbConfig.DEFAULT_LANGUAGE);
    }

    public Cursor queryAllPotionData(String str) {
        return this.db.rawQuery("SELECT id, " + getLanFiled(getLanguageName(str)) + ", drw, key, level, type" + " FROM potion_items", null);
    }

    public Cursor queryAllTypeData(LauncherMcVersion launcherMcVersion, String str) {
        String typeTableName = getTypeTableName(launcherMcVersion);
        return this.db.rawQuery("SELECT type_id, " + getLanFiled(getLanguageName(str)) + " FROM " + typeTableName + " JOIN type USING (type_id)" + " WHERE " + typeTableName + ".type_id=type.type_id", null);
    }

    public Cursor queryEquipData(String str) {
        return this.db.rawQuery("SELECT item_id, drwId, id FROM equipment WHERE type='" + str + "'", null);
    }

    public Cursor queryItem(short s, LauncherMcVersion launcherMcVersion) {
        return this.db.rawQuery("SELECT * FROM " + getItemTableName(launcherMcVersion) + " WHERE game_id=" + s, null);
    }

    public Cursor queryItemsData(int i, LauncherMcVersion launcherMcVersion, String str) {
        String itemTableName = getItemTableName(launcherMcVersion);
        return this.db.rawQuery("SELECT game_id, game_sub_id, item_id, icon_img, " + getLanFiled(getLanguageName(str)) + " FROM " + itemTableName + " JOIN items ON " + itemTableName + ".item_id=items.items_id" + " WHERE type_id1=" + i + " OR type_id2=" + i + " OR type_id3=" + i, null);
    }
}
