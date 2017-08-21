package com.mcbox.pesdk.archive.entity;

public class FallingBlock extends Entity {
    private byte blockData;
    private int blockId = 12;
    private int time;

    public byte getBlockData() {
        return this.blockData;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getTime() {
        return this.time;
    }

    public void setBlockData(byte b) {
        this.blockData = b;
    }

    public void setBlockId(int i) {
        this.blockId = i;
    }

    public void setTime(int i) {
        this.time = i;
    }
}
