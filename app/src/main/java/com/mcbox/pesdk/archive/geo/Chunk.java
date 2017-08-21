package com.mcbox.pesdk.archive.geo;

public class Chunk {
    public static final int HEIGHT = 128;
    public static final int LENGTH = 16;
    public static final int WIDTH = 16;
    public byte[] blockLight;
    public byte[] blocks;
    public byte[] dirtyTable;
    private boolean hasFilledDirtyTable = false;
    public byte[] metaData;
    public boolean needsSaving = false;
    public byte[] skyLight;
    public final int f3573x;
    public final int f3574z;

    public static final class Key {
        private int f3571x;
        private int f3572z;

        public Key(int i, int i2) {
            this.f3571x = i;
            this.f3572z = i2;
        }

        public Key(Key key) {
            this(key.f3571x, key.f3572z);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return key.getX() == this.f3571x && key.getZ() == this.f3572z;
        }

        public int getX() {
            return this.f3571x;
        }

        public int getZ() {
            return this.f3572z;
        }

        public int hashCode() {
            return ((this.f3571x + 31) * 31) + this.f3572z;
        }

        public void setX(int i) {
            this.f3571x = i;
        }

        public void setZ(int i) {
            this.f3572z = i;
        }
    }

    public Chunk(int i, int i2) {
        this.f3573x = i;
        this.f3574z = i2;
        this.blocks = new byte[32768];
        this.metaData = new byte[16384];
        this.blockLight = new byte[16384];
        this.skyLight = new byte[16384];
        this.dirtyTable = new byte[256];
    }

    private static int getOffset(int i, int i2, int i3) {
        return (((i * 128) * 16) + (i3 * 128)) + i2;
    }

    public int countDiamonds() {
        int i = 0;
        int i2 = 0;
        while (i < this.blocks.length) {
            if (this.blocks[i] == (byte) 56) {
                i2++;
            }
            i++;
        }
        return i2;
    }

    public boolean dirtyTableIsReallyGross() {
        for (byte b : this.dirtyTable) {
            if (b != (byte) 0) {
                return true;
            }
        }
        return false;
    }

    public int getBlockData(int i, int i2, int i3) {
        if (i >= 16 || i2 >= 128 || i3 >= 16 || i < 0 || i2 < 0 || i3 < 0) {
            return 0;
        }
        int offset = getOffset(i, i2, i3);
        byte b = this.metaData[offset >> 1];
        return offset % 2 == 1 ? (b >> 4) & 15 : b & 15;
    }

    public int getBlockTypeId(int i, int i2, int i3) {
        if (i >= 16 || i2 >= 128 || i3 >= 16 || i < 0 || i2 < 0 || i3 < 0) {
            return 0;
        }
        byte b = this.blocks[getOffset(i, i2, i3)];
        return b < (byte) 0 ? b + 256 : b;
    }

    public int getHighestBlockYAt(int i, int i2) {
        for (int i3 = 127; i3 >= 0; i3--) {
            if (getBlockTypeId(i, i3, i2) != 0) {
                return i3;
            }
        }
        return 0;
    }

    public void loadFromByteArray(byte[] bArr) {
        System.arraycopy(bArr, 0, this.blocks, 0, this.blocks.length);
        int length = this.blocks.length + 0;
        System.arraycopy(bArr, length, this.metaData, 0, this.metaData.length);
        length += this.metaData.length;
        System.arraycopy(bArr, length, this.skyLight, 0, this.skyLight.length);
        length += this.skyLight.length;
        System.arraycopy(bArr, length, this.blockLight, 0, this.blockLight.length);
        System.arraycopy(bArr, length + this.blockLight.length, this.dirtyTable, 0, this.dirtyTable.length);
    }

    public byte[] saveToByteArray() {
        Object obj = new byte[(((((this.blocks.length + this.metaData.length) + this.skyLight.length) + this.blockLight.length) + this.dirtyTable.length) + 3)];
        System.arraycopy(this.blocks, 0, obj, 0, this.blocks.length);
        int length = this.blocks.length + 0;
        System.arraycopy(this.metaData, 0, obj, length, this.metaData.length);
        length += this.metaData.length;
        System.arraycopy(this.skyLight, 0, obj, length, this.skyLight.length);
        length += this.skyLight.length;
        System.arraycopy(this.blockLight, 0, obj, length, this.blockLight.length);
        System.arraycopy(this.dirtyTable, 0, obj, length + this.blockLight.length, this.dirtyTable.length);
        return (byte[]) obj;
    }

    public void setBlockData(int i, int i2, int i3, int i4) {
        if (i < 16 && i2 < 128 && i3 < 16 && i >= 0 && i2 >= 0 && i3 >= 0) {
            setBlockDataNoDirty(i, i2, i3, i4);
            setDirtyTable(i, i2, i3);
            setNeedsSaving(true);
        }
    }

    public void setBlockDataNoDirty(int i, int i2, int i3, int i4) {
        int offset = getOffset(i, i2, i3);
        byte b = this.metaData[offset >> 1];
        if (offset % 2 == 1) {
            this.metaData[offset >> 1] = (byte) ((b & 15) | (i4 << 4));
            return;
        }
        this.metaData[offset >> 1] = (byte) ((b & 240) | (i4 & 15));
    }

    public void setBlockTypeId(int i, int i2, int i3, int i4) {
        if (i < 16 && i2 < 128 && i3 < 16 && i >= 0 && i2 >= 0 && i3 >= 0) {
            setBlockTypeIdNoDirty(i, i2, i3, i4);
            setDirtyTable(i, i2, i3);
            setNeedsSaving(true);
        }
    }

    public void setBlockTypeIdNoDirty(int i, int i2, int i3, int i4) {
        this.blocks[getOffset(i, i2, i3)] = (byte) i4;
    }

    public void setDirtyTable(int i, int i2, int i3) {
        if (!this.hasFilledDirtyTable) {
            for (int i4 = 0; i4 < 256; i4++) {
                this.dirtyTable[i4] = (byte) -1;
            }
            this.hasFilledDirtyTable = true;
        }
    }

    public void setNeedsSaving(boolean z) {
        this.needsSaving = z;
    }
}
