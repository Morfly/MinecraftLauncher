package com.mcbox.pesdk.mcfloat.model;

import com.mcbox.pesdk.archive.entity.ChatColor;

public class BagItem {
    public int count;
    public int data;
    public int[] enchantData;
    public int id;
    public int index;
    public String name;

    public BagItem() {
        this.id = 0;
        this.data = 0;
        this.count = 0;
        this.enchantData = null;
    }

    public BagItem(int i) {
        this.id = 0;
        this.data = 0;
        this.count = 0;
        this.enchantData = null;
        this.id = i;
    }

    public BagItem(int i, int i2, int i3) {
        this(i, i2, i3, 0);
    }

    public BagItem(int i, int i2, int i3, int i4) {
        this.id = 0;
        this.data = 0;
        this.count = 0;
        this.enchantData = null;
        this.id = i;
        this.data = i2;
        this.count = i3;
        this.index = i4;
    }

    public int getCount() {
        return this.count;
    }

    public int getData() {
        return this.data;
    }

    public int[] getEnchantData() {
        return this.enchantData;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getShowName() {
        try {
            if (this.name == null) {
                return null;
            }
            int lastIndexOf = this.name.lastIndexOf(ChatColor.BEGIN);
            if (lastIndexOf > -1) {
                return this.name.substring(lastIndexOf + 2, this.name.length());
            }
            return this.name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reset() {
        this.id = 0;
        this.data = 0;
        this.count = 0;
        this.enchantData = null;
        this.name = null;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void setData(int i) {
        this.data = i;
    }

    public void setEnchantData(int[] iArr) {
        this.enchantData = iArr;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String toString() {
        return "BagItem [id=" + this.id + ", data=" + this.data + ", count=" + this.count + "]";
    }
}
