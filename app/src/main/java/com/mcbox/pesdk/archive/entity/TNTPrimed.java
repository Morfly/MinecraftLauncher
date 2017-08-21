package com.mcbox.pesdk.archive.entity;

public class TNTPrimed extends Entity {
    private byte fuse = (byte) 0;

    public byte getFuseTicks() {
        return this.fuse;
    }

    public void setFuseTicks(byte b) {
        this.fuse = b;
    }
}
