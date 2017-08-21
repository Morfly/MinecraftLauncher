package com.mcbox.pesdk.archive.entity;

public class PigZombie extends Monster {
    private short anger = (short) 0;

    public short getAnger() {
        return this.anger;
    }

    public int getMaxHealth() {
        return 20;
    }

    public void setAnger(short s) {
        this.anger = s;
    }
}
