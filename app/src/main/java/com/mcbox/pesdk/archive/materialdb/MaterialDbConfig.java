package com.mcbox.pesdk.archive.materialdb;

import android.util.SparseArray;

import com.mcbox.pesdk.archive.entity.Options;

import java.util.HashMap;
import java.util.Map;

public class MaterialDbConfig {
    public static final String DATABASE_NAME = "material.db";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final Map<String, String> LANGUAGE_CODE_COLUMN = new C16933();
    public static final int VERSION = 11;
    public static final SparseArray<String> VERSION_TAB_MAP = new C16922();
    public static final SparseArray<String> VERSION_TYPE_MAP = new C16911();

    static final class C16911 extends SparseArray<String> {
        C16911() {
            put(10, "mcversion_type_v10");
            put(11, "mcversion_type_v11");
            put(12, "mcversion_type_v12");
            put(13, "mcversion_type_v13");
            put(14, "mcversion_type_v14");
            put(15, "mcversion_type_v15");
        }
    }

    static final class C16922 extends SparseArray<String> {
        C16922() {
            put(10, "mc_v10");
            put(11, "mc_v11");
            put(12, "mc_v12");
            put(13, "mc_v13");
            put(14, "mc_v14");
            put(15, "mc_v15");
        }
    }

    static final class C16933 extends HashMap<String, String> {
        private static final long serialVersionUID = 1;

        C16933() {
            put(MaterialDbConfig.DEFAULT_LANGUAGE, "english");
            put("es", "spanish");
            put("ru", "russia");
            put("pt", "portuguese");
            put("ko", "korean");
            put("zh_TW", "chinese_traditional");
            put(Options.GAME_LANGUAGE_CH, "chinese_simple");
            put("th", "thai");
            put("ja", "japanese");
            put("fr", "french");
            put("tr", "turkish");
            put("in", "indonisian");
            put("vi", "vietnamese");
        }
    }
}
