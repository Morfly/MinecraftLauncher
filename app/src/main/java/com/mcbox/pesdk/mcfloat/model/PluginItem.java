package com.mcbox.pesdk.mcfloat.model;

public class PluginItem {
    public String iconName;
    public int iconSubindex;
    public int id;
    public String name;
    public String texture;
    public int type;

    public PluginItem(int i, String str, String str2, int i2) {
        this(i, str, str2, i2, null, 0);
    }

    public PluginItem(int i, String str, String str2, int i2, String str3, int i3) {
        this.id = i;
        this.iconName = str;
        this.name = str2;
        this.iconSubindex = i2;
        this.texture = str3;
        this.type = i3;
    }
}
