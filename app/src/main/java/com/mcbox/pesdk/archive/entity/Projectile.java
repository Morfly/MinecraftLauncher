package com.mcbox.pesdk.archive.entity;

public class Projectile extends Entity {
    private boolean inGround = false;
    private byte inTile = (byte) 0;
    private byte shake = (byte) 0;
    private short xTile = (short) 0;
    private short yTile = (short) 0;
    private short zTile = (short) 0;

    public byte getInBlock() {
        return this.inTile;
    }

    public byte getShake() {
        return this.shake;
    }

    public short getXTile() {
        return this.xTile;
    }

    public short getYTile() {
        return this.yTile;
    }

    public short getZTile() {
        return this.zTile;
    }

    public boolean isInGround() {
        return this.inGround;
    }

    public void setInBlock(byte b) {
        this.inTile = b;
    }

    public void setInGround(boolean z) {
        this.inGround = z;
    }

    public void setShake(byte b) {
        this.shake = b;
    }

    public void setXTile(short s) {
        this.xTile = s;
    }

    public void setYTile(short s) {
        this.yTile = s;
    }

    public void setZTile(short s) {
        this.zTile = s;
    }
}
