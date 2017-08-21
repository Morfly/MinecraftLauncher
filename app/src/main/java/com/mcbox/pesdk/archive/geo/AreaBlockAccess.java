package com.mcbox.pesdk.archive.geo;

public interface AreaBlockAccess {
    int getBlockData(int i, int i2, int i3);

    int getBlockTypeId(int i, int i2, int i3);

    void setBlockData(int i, int i2, int i3, int i4);

    void setBlockTypeId(int i, int i2, int i3, int i4);
}
