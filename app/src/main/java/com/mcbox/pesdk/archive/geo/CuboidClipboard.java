package com.mcbox.pesdk.archive.geo;

import com.mcbox.pesdk.archive.util.Vector3f;

public class CuboidClipboard implements AreaBlockAccess, SizeLimitedArea {
    public static final int AIR = 0;
    public byte[] blocks;
    protected int height;
    protected int length;
    public byte[] metaData;
    protected int width;

    public CuboidClipboard(Vector3f vector3f) {
        this(vector3f, new byte[((int) ((vector3f.getX() * vector3f.getY()) * vector3f.getZ()))], new byte[((int) ((vector3f.getX() * vector3f.getY()) * vector3f.getZ()))]);
    }

    public CuboidClipboard(Vector3f vector3f, byte[] bArr, byte[] bArr2) {
        this.width = (int) vector3f.getX();
        this.height = (int) vector3f.getY();
        this.length = (int) vector3f.getZ();
        this.blocks = bArr;
        this.metaData = bArr2;
    }

    public void copy(AreaBlockAccess areaBlockAccess, Vector3f vector3f) {
        int blockX = vector3f.getBlockX();
        int blockY = vector3f.getBlockY();
        int blockZ = vector3f.getBlockZ();
        for (int i = 0; i < this.width; i++) {
            for (int i2 = 0; i2 < this.length; i2++) {
                for (int i3 = 0; i3 < this.height; i3++) {
                    int blockTypeId = areaBlockAccess.getBlockTypeId(blockX + i, blockY + i3, blockZ + i2);
                    int blockData = areaBlockAccess.getBlockData(blockX + i, blockY + i3, blockZ + i2);
                    setBlockTypeId(i, i3, i2, blockTypeId);
                    setBlockData(i, i3, i2, blockData);
                }
            }
        }
    }

    public int getBlockData(int i, int i2, int i3) {
        return this.metaData[getOffset(i, i2, i3)];
    }

    public int getBlockTypeId(int i, int i2, int i3) {
        return this.blocks[getOffset(i, i2, i3)];
    }

    public int getHeight() {
        return this.height;
    }

    public int getLength() {
        return this.length;
    }

    public int getOffset(int i, int i2, int i3) {
        return (((this.width * i2) * this.length) + (this.width * i3)) + i;
    }

    public int getWidth() {
        return this.width;
    }

    public void place(AreaBlockAccess areaBlockAccess, Vector3f vector3f, boolean z) {
        int x = (int) vector3f.getX();
        int y = (int) vector3f.getY();
        int z2 = (int) vector3f.getZ();
        for (int i = 0; i < this.width; i++) {
            for (int i2 = 0; i2 < this.length; i2++) {
                for (int i3 = 0; i3 < this.height; i3++) {
                    int blockTypeId = getBlockTypeId(i, i3, i2);
                    if (!z || blockTypeId != 0) {
                        int blockData = getBlockData(i, i3, i2);
                        areaBlockAccess.setBlockTypeId(x + i, y + i3, z2 + i2, blockTypeId);
                        areaBlockAccess.setBlockData(x + i, y + i3, z2 + i2, blockData);
                    }
                }
            }
        }
    }

    public void setBlockData(int i, int i2, int i3, int i4) {
        this.metaData[getOffset(i, i2, i3)] = (byte) i4;
    }

    public void setBlockTypeId(int i, int i2, int i3, int i4) {
        this.blocks[getOffset(i, i2, i3)] = (byte) i4;
    }
}
