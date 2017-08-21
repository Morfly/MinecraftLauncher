package com.mcbox.pesdk.archive.tileentity;

import com.mcbox.pesdk.archive.util.Vector3f;

import java.io.Serializable;

public class TileEntity implements Serializable {
    private String id = null;
    private int f3582x = 0;
    private int f3583y = 0;
    private int f3584z = 0;

    public double distanceSquaredTo(Vector3f vector3f) {
        return (Math.pow((double) (vector3f.f3586x - ((float) this.f3582x)), 2.0d) + Math.pow((double) (vector3f.f3587y - ((float) this.f3583y)), 2.0d)) + Math.pow((double) (vector3f.f3588z - ((float) this.f3584z)), 2.0d);
    }

    public String getId() {
        return this.id;
    }

    public int getX() {
        return this.f3582x;
    }

    public int getY() {
        return this.f3583y;
    }

    public int getZ() {
        return this.f3584z;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setX(int i) {
        this.f3582x = i;
    }

    public void setY(int i) {
        this.f3583y = i;
    }

    public void setZ(int i) {
        this.f3584z = i;
    }

    public String toString() {
        return this.id + ": X: " + this.f3582x + " Y: " + this.f3583y + " Z: " + this.f3584z;
    }
}
