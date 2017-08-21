package com.mcbox.pesdk.archive.tileentity;

import java.util.Arrays;

public class SignTileEntity extends TileEntity {
    public static final int NUM_LINES = 4;
    private String[] lines = new String[]{"", "", "", ""};

    public String getLine(int i) {
        return this.lines[i];
    }

    public String[] getLines() {
        return this.lines;
    }

    public void setLine(int i, String str) {
        this.lines[i] = str;
    }

    public String toString() {
        return super.toString() + Arrays.asList(this.lines).toString();
    }
}
