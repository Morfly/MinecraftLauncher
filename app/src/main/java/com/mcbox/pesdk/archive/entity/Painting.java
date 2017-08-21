package com.mcbox.pesdk.archive.entity;

import com.mcbox.pesdk.archive.util.Vector3f;

public class Painting extends Entity {
    private String artType = "Alban";
    private Vector3f blockCoordinates = new Vector3f(0.0f, 0.0f, 0.0f);
    private byte direction = (byte) 0;

    public String getArt() {
        return this.artType;
    }

    public Vector3f getBlockCoordinates() {
        return this.blockCoordinates;
    }

    public byte getDirection() {
        return this.direction;
    }

    public void setArt(String str) {
        this.artType = str;
    }

    public void setDirection(byte b) {
        this.direction = b;
    }
}
