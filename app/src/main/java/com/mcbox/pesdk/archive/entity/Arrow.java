package com.mcbox.pesdk.archive.entity;

public class Arrow extends Projectile {
    private byte inData = (byte) 0;
    private boolean player = false;

    public byte getInData() {
        return this.inData;
    }

    public boolean isShotByPlayer() {
        return this.player;
    }

    public void setInData(byte b) {
        this.inData = b;
    }

    public void setShotByPlayer(boolean z) {
        this.player = z;
    }
}
