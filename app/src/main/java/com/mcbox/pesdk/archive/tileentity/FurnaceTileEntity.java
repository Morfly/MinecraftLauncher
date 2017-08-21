package com.mcbox.pesdk.archive.tileentity;

public class FurnaceTileEntity extends ContainerTileEntity {
    private short burnTime = (short) 0;
    private short cookTime = (short) 0;

    public short getBurnTime() {
        return this.burnTime;
    }

    public int getContainerSize() {
        return 3;
    }

    public short getCookTime() {
        return this.cookTime;
    }

    public void setBurnTime(short s) {
        this.burnTime = s;
    }

    public void setCookTime(short s) {
        this.cookTime = s;
    }
}
