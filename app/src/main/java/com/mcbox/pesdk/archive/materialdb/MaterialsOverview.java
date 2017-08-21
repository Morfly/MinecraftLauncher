package com.mcbox.pesdk.archive.materialdb;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;

import com.mcbox.pesdk.archive.material.Material;
import com.mcbox.pesdk.archive.material.MaterialKey;
import com.mcbox.pesdk.archive.material.MaterialType;
import com.mcbox.pesdk.archive.material.icon.MaterialIcon;
import com.mcbox.pesdk.util.LauncherMcVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MaterialsOverview {
    private static final Object LOCK = "";
    private static MaterialsOverview instance = null;
    private Context context;
    private ExecutorService executorService;
    private LauncherMcVersion gameVersion;
    private Map<MaterialKey, MaterialIcon> icons = new HashMap();
    private boolean isMaterialLoaded = false;
    private String language;
    private Map<MaterialKey, Material> materialMap = new HashMap();
    private SparseArray<List<Material>> materialTypeDataMaps = new SparseArray();
    private SparseArray<MaterialType> materialTypeMaps = new SparseArray();

    class C16941 implements Runnable {
        C16941() {
        }

        public void run() {
            synchronized (MaterialsOverview.LOCK) {
                if (MaterialsOverview.this.checkMaterialDb()) {
                    MaterialsOverview.this.loadFromDb();
                    MaterialsOverview.this.isMaterialLoaded = true;
                }
            }
        }
    }

    private MaterialsOverview(Context context) {
        this.context = context.getApplicationContext();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean checkMaterialDb() {
        /*
        r11 = this;
        r6 = 11;
        r0 = 1;
        r3 = 0;
        r1 = 0;
        r2 = "material.db";
        r5 = new java.io.File;
        r4 = r11.context;
        r4 = r4.getFilesDir();
        r5.<init>(r4, r2);
        r4 = r5.exists();
        if (r4 == 0) goto L_0x0027;
    L_0x0018:
        r4 = r11.getCachedDbVersion();
        if (r4 != r6) goto L_0x001f;
    L_0x001e:
        return r0;
    L_0x001f:
        r4 = r5.delete();
        if (r4 != 0) goto L_0x0027;
    L_0x0025:
        r0 = r1;
        goto L_0x001e;
    L_0x0027:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4 = r4.append(r2);
        r6 = ".tmp";
        r4 = r4.append(r6);
        r4 = r4.toString();
        r6 = new java.io.File;
        r7 = r11.context;
        r7 = r7.getFilesDir();
        r6.<init>(r7, r4);
        r4 = r11.context;	 Catch:{ Exception -> 0x00bf, all -> 0x00a1 }
        r4 = r4.getAssets();	 Catch:{ Exception -> 0x00bf, all -> 0x00a1 }
        r4 = r4.open(r2);	 Catch:{ Exception -> 0x00bf, all -> 0x00a1 }
        r2 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00c5, all -> 0x00b6 }
        r2.<init>(r6);	 Catch:{ Exception -> 0x00c5, all -> 0x00b6 }
        r7 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r7 = new byte[r7];	 Catch:{ Exception -> 0x0063, all -> 0x00b8 }
    L_0x0058:
        r8 = r4.read(r7);	 Catch:{ Exception -> 0x0063, all -> 0x00b8 }
        if (r8 <= 0) goto L_0x007e;
    L_0x005e:
        r9 = 0;
        r2.write(r7, r9, r8);	 Catch:{ Exception -> 0x0063, all -> 0x00b8 }
        goto L_0x0058;
    L_0x0063:
        r0 = move-exception;
        r3 = r4;
        r10 = r1;
        r1 = r0;
        r0 = r10;
    L_0x0068:
        r1.printStackTrace();	 Catch:{ all -> 0x00bb }
        if (r3 == 0) goto L_0x0070;
    L_0x006d:
        r3.close();	 Catch:{ IOException -> 0x0079 }
    L_0x0070:
        if (r2 == 0) goto L_0x001e;
    L_0x0072:
        r2.flush();	 Catch:{ IOException -> 0x0079 }
        r2.close();	 Catch:{ IOException -> 0x0079 }
        goto L_0x001e;
    L_0x0079:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001e;
    L_0x007e:
        r2.close();	 Catch:{ Exception -> 0x0063, all -> 0x00b8 }
        r2 = 0;
        r5 = r6.renameTo(r5);	 Catch:{ Exception -> 0x00c5, all -> 0x00b6 }
        if (r5 == 0) goto L_0x00d0;
    L_0x0088:
        r1 = 11;
        r11.saveCachedDbVersion(r1);	 Catch:{ Exception -> 0x00cc, all -> 0x00b6 }
    L_0x008d:
        if (r4 == 0) goto L_0x0092;
    L_0x008f:
        r4.close();	 Catch:{ IOException -> 0x009b }
    L_0x0092:
        if (r3 == 0) goto L_0x001e;
    L_0x0094:
        r2.flush();	 Catch:{ IOException -> 0x009b }
        r2.close();	 Catch:{ IOException -> 0x009b }
        goto L_0x001e;
    L_0x009b:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001e;
    L_0x00a1:
        r0 = move-exception;
        r4 = r3;
    L_0x00a3:
        if (r4 == 0) goto L_0x00a8;
    L_0x00a5:
        r4.close();	 Catch:{ IOException -> 0x00b1 }
    L_0x00a8:
        if (r3 == 0) goto L_0x00b0;
    L_0x00aa:
        r3.flush();	 Catch:{ IOException -> 0x00b1 }
        r3.close();	 Catch:{ IOException -> 0x00b1 }
    L_0x00b0:
        throw r0;
    L_0x00b1:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00b0;
    L_0x00b6:
        r0 = move-exception;
        goto L_0x00a3;
    L_0x00b8:
        r0 = move-exception;
        r3 = r2;
        goto L_0x00a3;
    L_0x00bb:
        r0 = move-exception;
        r4 = r3;
        r3 = r2;
        goto L_0x00a3;
    L_0x00bf:
        r0 = move-exception;
        r2 = r3;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x0068;
    L_0x00c5:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        r10 = r0;
        r0 = r1;
        r1 = r10;
        goto L_0x0068;
    L_0x00cc:
        r1 = move-exception;
        r2 = r3;
        r3 = r4;
        goto L_0x0068;
    L_0x00d0:
        r0 = r1;
        goto L_0x008d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mcbox.pesdk.archive.materialdb.MaterialsOverview.checkMaterialDb():boolean");
    }

    private void clearAllMaps() {
        this.materialTypeDataMaps.clear();
        this.materialTypeMaps.clear();
        this.materialMap.clear();
        this.icons.clear();
    }

    private int getCachedDbVersion() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getInt("items_db_version", 0);
    }

    public static synchronized MaterialsOverview getInstance(Context context) {
        MaterialsOverview materialsOverview;
        synchronized (MaterialsOverview.class) {
            if (instance == null) {
                instance = new MaterialsOverview(context);
            }
            materialsOverview = instance;
        }
        return materialsOverview;
    }

    private void loadFromDb() {
        clearAllMaps();
        MaterialDbDao materialDbDao = new MaterialDbDao(this.context);
        Cursor queryAllTypeData = materialDbDao.queryAllTypeData(this.gameVersion, this.language);
        if (queryAllTypeData.moveToFirst()) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                int i3 = queryAllTypeData.getInt(queryAllTypeData.getColumnIndex("type_id"));
                String string = queryAllTypeData.getString(queryAllTypeData.getColumnIndex(materialDbDao.getLanguageName(this.language)));
                if (TextUtils.isEmpty(string)) {
                    string = queryAllTypeData.getString(queryAllTypeData.getColumnIndex((String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(MaterialDbConfig.DEFAULT_LANGUAGE)));
                }
                this.materialTypeMaps.put(i2, new MaterialType(Integer.valueOf(i2), string));
                List arrayList = new ArrayList();
                Cursor queryItemsData = materialDbDao.queryItemsData(i3, this.gameVersion, this.language);
                queryItemsData.moveToFirst();
                do {
                    int i4 = queryItemsData.getInt(queryItemsData.getColumnIndex("game_id"));
                    int i5 = queryItemsData.getInt(queryItemsData.getColumnIndex("game_sub_id"));
                    byte[] blob = queryItemsData.getBlob(queryItemsData.getColumnIndex("icon_img"));
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    string = queryItemsData.getString(queryItemsData.getColumnIndex(materialDbDao.getLanguageName(this.language)));
                    if (TextUtils.isEmpty(string)) {
                        string = queryItemsData.getString(queryItemsData.getColumnIndex((String) MaterialDbConfig.LANGUAGE_CODE_COLUMN.get(MaterialDbConfig.DEFAULT_LANGUAGE)));
                    }
                    MaterialKey materialKey = new MaterialKey((short) i4, (short) i5);
                    Material material = new Material((short) i4, string, (short) i5, i5 != 0);
                    this.materialMap.put(materialKey, material);
                    this.icons.put(materialKey, new MaterialIcon(i3, (short) i5, decodeByteArray));
                    arrayList.add(material);
                } while (queryItemsData.moveToNext());
                this.materialTypeDataMaps.put(i2, arrayList);
                queryItemsData.close();
                if (!queryAllTypeData.moveToNext()) {
                    break;
                }
                i = i2;
            }
        }
        queryAllTypeData.close();
        materialDbDao.finalize();
    }

    private boolean saveCachedDbVersion(int i) {
        return PreferenceManager.getDefaultSharedPreferences(this.context).edit().putInt("items_db_version", i).commit();
    }

    public SparseArray<MaterialType> getAllMaterialTypes() {
        SparseArray<MaterialType> sparseArray;
        synchronized (LOCK) {
            if (this.materialTypeMaps.size() == 0) {
                loadAllMaterialsWithIcon();
            }
            sparseArray = this.materialTypeMaps;
        }
        return sparseArray;
    }

    public SparseArray<List<Material>> getAllMaterialTypesData() {
        SparseArray<List<Material>> sparseArray;
        synchronized (LOCK) {
            if (this.materialTypeDataMaps.size() == 0) {
                loadAllMaterialsWithIcon();
            }
            sparseArray = this.materialTypeDataMaps;
        }
        return sparseArray;
    }

    public Map<MaterialKey, Material> getAllMaterials() {
        Map<MaterialKey, Material> map;
        synchronized (LOCK) {
            if (this.materialMap.size() == 0) {
                loadAllMaterialsWithIcon();
            }
            map = this.materialMap;
        }
        return map;
    }

    public Map<MaterialKey, MaterialIcon> getAllMaterialsIcon() {
        if (this.icons.size() == 0) {
            loadAllMaterialsWithIcon();
        }
        return this.icons;
    }

    public Material getMaterialByItemId(short s) {
        MaterialDbDao materialDbDao = new MaterialDbDao(this.context);
        Cursor queryItem = materialDbDao.queryItem(s, this.gameVersion);
        Material material = null;
        if (queryItem.moveToFirst()) {
            Material material2;
            do {
                int i = queryItem.getInt(queryItem.getColumnIndex("game_id"));
                int i2 = queryItem.getInt(queryItem.getColumnIndex("game_sub_id"));
                material2 = new Material((short) i, "", (short) i2, i2 != 0);
            } while (queryItem.moveToNext());
            material = material2;
        }
        queryItem.close();
        materialDbDao.finalize();
        return material;
    }

    public void init(LauncherMcVersion launcherMcVersion, String str) {
        synchronized (LOCK) {
            if (this.gameVersion == null || TextUtils.isEmpty(str) || this.gameVersion.getMinor() != launcherMcVersion.getMinor() || !this.language.equals(str)) {
                this.gameVersion = launcherMcVersion;
                this.language = str;
                loadAllMaterialsWithIcon();
                return;
            }
        }
    }

    public boolean isMaterialLoaded() {
        return this.isMaterialLoaded;
    }

    public void loadAllMaterialsWithIcon() {
        this.executorService.execute(new C16941());
    }
}
