package com.mcbox.pesdk.archive.util;

import java.io.Serializable;

public class Vector3f implements Serializable {
    public float f3586x;
    public float f3587y;
    public float f3588z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float f, float f2, float f3) {
        this.f3586x = f;
        this.f3587y = f2;
        this.f3588z = f3;
    }

    public double distSquared(Vector3f vector3f) {
        return (Math.pow(((double) vector3f.f3586x) - ((double) this.f3586x), 2.0d) + Math.pow(((double) vector3f.f3587y) - ((double) this.f3587y), 2.0d)) + Math.pow(((double) vector3f.f3588z) - ((double) this.f3588z), 2.0d);
    }

    public int getBlockX() {
        return (int) this.f3586x;
    }

    public int getBlockY() {
        return (int) this.f3587y;
    }

    public int getBlockZ() {
        return (int) this.f3588z;
    }

    public float getX() {
        return this.f3586x;
    }

    public float getY() {
        return this.f3587y;
    }

    public float getZ() {
        return this.f3588z;
    }

    public Vector3f setX(float f) {
        this.f3586x = f;
        return this;
    }

    public Vector3f setY(float f) {
        this.f3587y = f;
        return this;
    }

    public Vector3f setZ(float f) {
        this.f3588z = f;
        return this;
    }
}
