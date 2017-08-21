package com.mcbox.pesdk.archive.entity;

public class Sheep extends Animal {
    private byte color = (byte) 0;
    private boolean sheared = false;

    public byte getColor() {
        return this.color;
    }

    public int getMaxHealth() {
        return 8;
    }

    public boolean isSheared() {
        return this.sheared;
    }

    public void setColor(byte b) {
        this.color = b;
    }

    public void setSheared(boolean z) {
        this.sheared = z;
    }
}
