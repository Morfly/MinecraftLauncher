package com.mcbox.pesdk.archive.material.icon;

import android.graphics.Bitmap;

public class MaterialIcon {
    public Bitmap bitmap;
    public short damage;
    public int typeId;

    public MaterialIcon(int i, short s, Bitmap bitmap) {
        this.typeId = i;
        this.damage = s;
        this.bitmap = bitmap;
    }
}
