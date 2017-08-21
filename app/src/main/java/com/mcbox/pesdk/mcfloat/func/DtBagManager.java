package com.mcbox.pesdk.mcfloat.func;

import com.mcbox.pesdk.mcfloat.model.BagItem;

import java.util.ArrayList;
import java.util.Iterator;

public class DtBagManager {
    private static final boolean DEBUG = false;
    public static ArrayList<BagItem> dtArmorBagList = new ArrayList(4);
    public static final int dtArmorBagList_CntMax = 4;
    public static ArrayList<BagItem> dtPlayerBagList = new ArrayList(45);
    public static final int dtPlayerBagList_CntMax = 45;
    private static long lastSyncTimeMillis = 0;

    static {
        int i = 0;
        for (int i2 = 0; i2 < 45; i2++) {
            dtPlayerBagList.add(new BagItem());
        }
        while (i < 4) {
            dtArmorBagList.add(new BagItem());
            i++;
        }
    }

    public static void clearSavedItems() {
        Iterator it = dtPlayerBagList.iterator();
        while (it.hasNext()) {
            ((BagItem) it.next()).reset();
        }
        it = dtArmorBagList.iterator();
        while (it.hasNext()) {
            ((BagItem) it.next()).reset();
        }
    }

    public static int getArmorSlotId(int i) {
        if (i >= 0 && i < 4) {
            BagItem bagItem = (BagItem) dtArmorBagList.get(i);
            if (bagItem != null) {
                return bagItem.getId();
            }
        }
        return 0;
    }

    public static BagItem getPlagerBagSlot(int i) {
        return (i < 0 || i >= 45) ? null : (BagItem) dtPlayerBagList.get(i);
    }

    public static void init() {
        clearSavedItems();
    }

    public static void saveArmorSlotId(int i, int i2) {
        if (i >= 0 && i < 4 && i2 > 0) {
            BagItem bagItem = (BagItem) dtArmorBagList.get(i);
            if (bagItem == null) {
                dtArmorBagList.set(i, new BagItem(i2));
                return;
            }
            bagItem.reset();
            bagItem.setId(i2);
        }
    }

    public static void saveArmorSlotId(int i, int i2, int[] iArr) {
        if (i >= 0 && i < 4 && i2 > 0) {
            BagItem bagItem = (BagItem) dtArmorBagList.get(i);
            if (bagItem == null) {
                dtArmorBagList.set(i, new BagItem(i2));
                return;
            }
            bagItem.reset();
            bagItem.setId(i2);
            bagItem.setEnchantData(iArr);
        }
    }

    public static void savePlayerBagSlot(int i, int i2, int i3, int i4) {
        if (i >= 0 && i < 45 && i2 > 0) {
            BagItem bagItem = (BagItem) dtPlayerBagList.get(i);
            if (bagItem == null) {
                dtArmorBagList.set(i, new BagItem(i2, i3, i4));
                return;
            }
            bagItem.reset();
            bagItem.setId(i2);
            bagItem.setData(i3);
            bagItem.setCount(i4);
        }
    }

    public static void savePlayerBagSlot(int i, int i2, int i3, int i4, int[] iArr) {
        if (i >= 0 && i < 45 && i2 > 0) {
            BagItem bagItem = (BagItem) dtPlayerBagList.get(i);
            if (bagItem == null) {
                dtArmorBagList.set(i, new BagItem(i2, i3, i4));
                return;
            }
            bagItem.reset();
            bagItem.setId(i2);
            bagItem.setData(i3);
            bagItem.setCount(i4);
            bagItem.setEnchantData(iArr);
        }
    }
}
