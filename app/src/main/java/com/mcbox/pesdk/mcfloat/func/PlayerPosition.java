package com.mcbox.pesdk.mcfloat.func;

public class PlayerPosition {
    private float curX;
    private float curY;
    private float curZ;
    private float destX;
    private float destY;
    private float destZ;
    private boolean updateFlag;

    public float getCurX() {
        return this.curX;
    }

    public float getCurY() {
        return this.curY;
    }

    public float getCurZ() {
        return this.curZ;
    }

    public float getDestX() {
        return this.destX;
    }

    public float getDestY() {
        return this.destY;
    }

    public float getDestZ() {
        return this.destZ;
    }

    public boolean getUpdateFlag() {
        return this.updateFlag;
    }

    public void setCurX(float f) {
        this.curX = f;
    }

    public void setCurY(float f) {
        this.curY = f;
    }

    public void setCurZ(float f) {
        this.curZ = f;
    }

    public void setDestX(float f) {
        this.destX = f;
    }

    public void setDestY(float f) {
        this.destY = f;
    }

    public void setDestZ(float f) {
        this.destZ = f;
    }

    public void setUpdateFlag(boolean z) {
        this.updateFlag = z;
    }
}
