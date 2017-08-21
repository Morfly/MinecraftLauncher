package com.mcbox.pesdk.archive.geo;

import com.mcbox.pesdk.archive.util.Vector3f;

public class CuboidRegion {
    public int height;
    public int length;
    public int width;
    public int f3575x;
    public int f3576y;
    public int f3577z;

    public CuboidRegion(int i, int i2, int i3, int i4, int i5, int i6) {
        this.f3575x = i;
        this.f3576y = i2;
        this.f3577z = i3;
        this.width = i4;
        this.height = i5;
        this.length = i6;
    }

    public CuboidRegion(CuboidRegion cuboidRegion) {
        this(cuboidRegion.f3575x, cuboidRegion.f3576y, cuboidRegion.f3577z, cuboidRegion.width, cuboidRegion.height, cuboidRegion.length);
    }

    public CuboidRegion(Vector3f vector3f, Vector3f vector3f2) {
        this((int) vector3f.getX(), (int) vector3f.getY(), (int) vector3f.getZ(), (int) vector3f2.getX(), (int) vector3f2.getY(), (int) vector3f2.getZ());
    }

    public static CuboidRegion fromPoints(Vector3f vector3f, Vector3f vector3f2) {
        int x = (int) (vector3f.getX() < vector3f2.getX() ? vector3f.getX() : vector3f2.getX());
        int y = (int) (vector3f.getY() < vector3f2.getY() ? vector3f.getY() : vector3f2.getY());
        int z = (int) (vector3f.getZ() < vector3f2.getZ() ? vector3f.getZ() : vector3f2.getZ());
        return new CuboidRegion(x, y, z, (((int) (vector3f.getX() >= vector3f2.getX() ? vector3f.getX() : vector3f2.getX())) - x) + 1, (((int) (vector3f.getY() >= vector3f2.getY() ? vector3f.getY() : vector3f2.getY())) - y) + 1, (((int) (vector3f.getZ() >= vector3f2.getZ() ? vector3f.getZ() : vector3f2.getZ())) - z) + 1);
    }

    public boolean contains(CuboidRegion cuboidRegion) {
        return cuboidRegion.f3575x >= this.f3575x && cuboidRegion.f3576y >= this.f3576y && cuboidRegion.f3577z >= this.f3577z && cuboidRegion.f3575x + cuboidRegion.width <= this.f3575x + this.width && cuboidRegion.f3576y + cuboidRegion.height <= this.f3576y + this.height && cuboidRegion.f3577z + cuboidRegion.length <= this.f3577z + this.length;
    }

    public CuboidRegion createIntersection(CuboidRegion cuboidRegion) {
        int i = this.f3575x > cuboidRegion.f3575x ? this.f3575x : cuboidRegion.f3575x;
        int i2 = this.f3576y > cuboidRegion.f3576y ? this.f3576y : cuboidRegion.f3576y;
        int i3 = this.f3577z > cuboidRegion.f3577z ? this.f3577z : cuboidRegion.f3577z;
        int i4 = this.f3575x + this.width;
        int i5 = this.f3576y + this.height;
        int i6 = this.f3577z + this.length;
        int i7 = cuboidRegion.f3575x + cuboidRegion.width;
        int i8 = cuboidRegion.height + cuboidRegion.f3576y;
        int i9 = cuboidRegion.f3577z + cuboidRegion.length;
        if (i4 >= i7) {
            i4 = i7;
        }
        if (i5 >= i8) {
            i5 = i8;
        }
        return new CuboidRegion(i, i2, i3, i4 - i, i5 - i2, (i6 < i9 ? i6 : i9) - i3);
    }

    public int getBlockCount() {
        return (this.width * this.height) * this.length;
    }

    public Vector3f getPosition() {
        return new Vector3f((float) this.f3575x, (float) this.f3576y, (float) this.f3577z);
    }

    public Vector3f getSize() {
        return new Vector3f((float) this.width, (float) this.height, (float) this.length);
    }

    public boolean isValid() {
        return this.width >= 0 && this.height >= 0 && this.length >= 0;
    }
}
