package com.mcbox.pesdk.archive.tileentity;

public class NetherReactorTileEntity extends TileEntity {
    private boolean hasFinished = false;
    private boolean isInitialized = false;
    private short progress = (short) 0;

    public short getProgress() {
        return this.progress;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public void setFinished(boolean z) {
        this.hasFinished = z;
    }

    public void setInitialized(boolean z) {
        this.isInitialized = z;
    }

    public void setProgress(short s) {
        this.progress = s;
    }
}
