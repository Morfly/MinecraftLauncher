package com.mcbox.pesdk.mcfloat.model;

public class ArmorItem {
    public int data = 0;
    public int[] enchantData = null;
    public int id = 0;

    public void reset() {
        this.id = 0;
        this.data = 0;
        this.enchantData = null;
    }
}
