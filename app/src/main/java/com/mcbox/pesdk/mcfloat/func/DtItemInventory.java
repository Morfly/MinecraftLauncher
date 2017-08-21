package com.mcbox.pesdk.mcfloat.func;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DtItemInventory {
    public static Vector DTItemInventory_AddArray;
    private static Lock DTItemInventory_Lock = new ReentrantLock();
    private static int addItemCnt = 0;
    public static int nDTItemMax = 600;

    public static class DtItem {
        int addCount;
        int addDmg;
        int addId;
        int flag;
        int type;

        public DtItem() {
            this.type = 0;
            this.addId = 0;
            this.addCount = 0;
            this.addDmg = 0;
            this.flag = 0;
        }

        public DtItem(int i, int i2, int i3, int i4, int i5) {
            this.type = i;
            this.addId = i2;
            this.addCount = i3;
            this.addDmg = i4;
            this.flag = i5;
        }
    }

    static {
        DTItemInventory_AddArray = null;
        DTItemInventory_AddArray = new Vector(nDTItemMax);
    }

    public static boolean addItemNode(int i, int i2, int i3, int i4, int i5) {
        lock();
        DTItemInventory_AddArray.size();
        DtItem dtItem = (DtItem) DTItemInventory_AddArray.get(addItemCnt);
        if (dtItem == null || dtItem.flag != 0) {
            System.err.println("mydebug, err: get localDTItem fail -- addItemCnt:" + addItemCnt);
            unlock();
            return false;
        }
        dtItem.type = i;
        dtItem.addId = i2;
        dtItem.addCount = i3;
        dtItem.addDmg = i4;
        dtItem.flag = i5;
        incAddItemCnt();
        unlock();
        return true;
    }

    public static boolean delItemNode(int i) {
        DTItemInventory_AddArray.remove(i);
        return true;
    }

    public static int getAddItemCnt() {
        return addItemCnt;
    }

    public static void incAddItemCnt() {
        addItemCnt++;
    }

    public static void init() {
        DTItemInventory_AddArray.clear();
        for (int i = 0; i < nDTItemMax; i++) {
            DTItemInventory_AddArray.add(new DtItem(0, 0, 0, 0, 0));
        }
    }

    public static void lock() {
        DTItemInventory_Lock.lock();
    }

    public static void setAddItemCnt(int i) {
        addItemCnt = i;
    }

    public static void unlock() {
        DTItemInventory_Lock.unlock();
    }
}
