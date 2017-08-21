package com.mcbox.pesdk.mcfloat.func;

import android.graphics.Bitmap;
import android.os.Handler;

import com.mcbox.pesdk.mcfloat.model.PluginItem;

import java.util.List;

public interface DtPlugin {
    void addPluginComposed(int i, String[] strArr, int[] iArr);

    Bitmap getDrwId(Integer num);

    Object[] getPluginComposed(int i);

    List<PluginItem> getPluginList();

    void loadPluginDrw(Handler handler);
}
