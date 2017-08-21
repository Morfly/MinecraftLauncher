package com.mcbox.pesdk.archive;


public enum DyeColor {
    WHITE(221, 221, 221),
    ORANGE(219, 125, 62),
    MAGENTA(179, 80, 188),
    LIGHT_BLUE(107, 138, 201),
    YELLOW(177, 166, 39),
    LIME(65, 174, 56),
    PINK(208, 132, 153),
    GRAY(64, 64, 64),
    LIGHT_GREY(154, 161, 161),
    CYAN(46, 110, 137),
    PURPLE(126, 61, 181),
    BLUE(46, 56, 141),
    BROWN(79, 50, 31),
    GREEN(53, 70, 27),
    RED(150, 52, 48),
    BLACK(25, 22, 22);
    
    public int color;

    private DyeColor(int i) {
        this.color = i;
    }

    private DyeColor(int i, int i2, int i3) {
        this(i/*, i2, ((ViewCompat.MEASURED_STATE_MASK | (i << 16)) | (i2 << 8)) | i3*/);
    }

    public byte getDyeData() {
        return (byte) (15 - ordinal());
    }

    public byte getWoolData() {
        return (byte) ordinal();
    }
}
