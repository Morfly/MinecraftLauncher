package com.mcbox.pesdk.archive.tileentity;

public class ChestTileEntity extends ContainerTileEntity {
    private int pairx = -65535;
    private int pairz = -65535;

    public int getPairX() {
        return this.pairx;
    }

    public int getPairZ() {
        return this.pairz;
    }

    public void setPairX(int i) {
        this.pairx = i;
    }

    public void setPairZ(int i) {
        this.pairz = i;
    }
}
