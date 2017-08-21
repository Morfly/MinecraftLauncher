package com.mcbox.pesdk.archive.io.region;

import android.support.v4.media.session.PlaybackStateCompat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;

public class RegionFile {
    public static final String ANVIL_EXTENSION = ".mca";
    static final int CHUNK_HEADER_SIZE = 5;
    public static final String MCREGION_EXTENSION = ".mcr";
    private static final int SECTOR_BYTES = 4096;
    private static final int SECTOR_INTS = 1024;
    private static final int VERSION_DEFLATE = 2;
    private static final int VERSION_GZIP = 1;
    private static final byte[] emptySector = new byte[4096];
    private final int[] chunkTimestamps = new int[1024];
    private RandomAccessFile file;
    private final File fileName;
    private long lastModified = 0;
    private final int[] offsets = new int[1024];
    private ArrayList<Boolean> sectorFree;
    private int sizeDelta;

    class ChunkBuffer extends ByteArrayOutputStream {
        private int f3580x;
        private int f3581z;

        public ChunkBuffer(int i, int i2) {
            super(8096);
            this.f3580x = i;
            this.f3581z = i2;
        }

        public void close() {
            RegionFile.this.write(this.f3580x, this.f3581z, this.buf, this.count);
        }
    }

    public RegionFile(File file) {
        this.fileName = file;
        debugln("REGION LOAD " + this.fileName);
        this.sizeDelta = 0;
        try {
            int i;
            if (file.exists()) {
                this.lastModified = file.lastModified();
            }
            this.file = new RandomAccessFile(file, "rw");
            if (this.file.length() < PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
                for (i = 0; i < 1024; i++) {
                    this.file.writeInt(0);
                }
                for (i = 0; i < 1024; i++) {
                    this.file.writeInt(0);
                }
                this.sizeDelta += 8192;
            }
            if ((this.file.length() & 4095) != 0) {
                for (i = 0; ((long) i) < (this.file.length() & 4095); i++) {
                    this.file.write(0);
                }
            }
            int length = ((int) this.file.length()) / 4096;
            this.sectorFree = new ArrayList(length);
            for (i = 0; i < length; i++) {
                this.sectorFree.add(Boolean.valueOf(true));
            }
            this.sectorFree.set(0, Boolean.valueOf(false));
            this.file.seek(0);
            for (length = 0; length < 1024; length++) {
                int reverseBytes = Integer.reverseBytes(this.file.readInt());
                this.offsets[length] = reverseBytes;
                if (reverseBytes != 0 && (reverseBytes >> 8) + (reverseBytes & 255) <= this.sectorFree.size()) {
                    for (i = 0; i < (reverseBytes & 255); i++) {
                        this.sectorFree.set((reverseBytes >> 8) + i, Boolean.valueOf(false));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void debug(String str) {
    }

    private void debug(String str, int i, int i2, int i3, String str2) {
        debug("REGION " + str + " " + this.fileName.getName() + "[" + i + "," + i2 + "] " + i3 + "B = " + str2);
    }

    private void debug(String str, int i, int i2, String str2) {
        debug("REGION " + str + " " + this.fileName.getName() + "[" + i + "," + i2 + "] = " + str2);
    }

    private void debugln(String str) {
        debug(str + "\n");
    }

    private void debugln(String str, int i, int i2, String str2) {
        debug(str, i, i2, str2 + "\n");
    }

    private int getOffset(int i, int i2) {
        return this.offsets[(i2 * 32) + i];
    }

    private boolean outOfBounds(int i, int i2) {
        return i < 0 || i >= 32 || i2 < 0 || i2 >= 32;
    }

    private void setOffset(int i, int i2, int i3) throws IOException {
        this.offsets[(i2 * 32) + i] = i3;
        this.file.seek((long) (((i2 * 32) + i) * 4));
        this.file.writeInt(Integer.reverseBytes(i3));
    }

    private void write(int i, byte[] bArr, int i2) throws IOException {
        debugln(" " + i);
        this.file.seek((long) (i * 4096));
        this.file.writeInt(Integer.reverseBytes(i2 + 1));
        this.file.write(bArr, 0, i2);
    }

    public void close() throws IOException {
        this.file.close();
    }

    public byte[] getChunkData(int i, int i2) {
        if (outOfBounds(i, i2)) {
            debugln("READ", i, i2, "out of bounds");
            return null;
        }
        try {
            int offset = getOffset(i, i2);
            if (offset == 0) {
                return null;
            }
            int i3 = offset >> 8;
            offset &= 255;
            if (i3 + offset > this.sectorFree.size()) {
                debugln("READ", i, i2, "invalid sector");
                return null;
            }
            this.file.seek((long) (i3 * 4096));
            debugln("READ", i, i2, "location = " + Integer.toString(i3 * 4096, 16));
            i3 = Integer.reverseBytes(this.file.readInt());
            if (i3 > offset * 4096) {
                debugln("READ", i, i2, "invalid length: " + i3 + " > 4096 * " + offset);
                return null;
            }
            byte[] bArr = new byte[(i3 - 1)];
            this.file.read(bArr);
            return bArr;
        } catch (IOException e) {
            debugln("READ", i, i2, "exception");
            return null;
        }
    }

    public DataOutputStream getChunkDataOutputStream(int i, int i2) {
        return outOfBounds(i, i2) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(i, i2)));
    }

    public synchronized int getSizeDelta() {
        int i;
        i = this.sizeDelta;
        this.sizeDelta = 0;
        return i;
    }

    public boolean hasChunk(int i, int i2) {
        return getOffset(i, i2) != 0;
    }

    public long lastModified() {
        return this.lastModified;
    }

    public void write(int i, int i2, byte[] bArr, int i3) {
        int i4 = 0;
        try {
            int offset = getOffset(i, i2);
            int i5 = offset >> 8;
            int i6 = offset & 255;
            int i7 = ((i3 + 5) / 4096) + 1;
            if (i7 < 256) {
                if (i5 == 0 || i6 != i7) {
                    for (offset = 0; offset < i6; offset++) {
                        this.sectorFree.set(i5 + offset, Boolean.valueOf(true));
                    }
                    offset = this.sectorFree.indexOf(Boolean.valueOf(true));
                    if (offset != -1) {
                        int i8 = offset;
                        i6 = 0;
                        int i9 = offset;
                        while (i8 < this.sectorFree.size()) {
                            if (i6 != 0) {
                                if (((Boolean) this.sectorFree.get(i8)).booleanValue()) {
                                    offset = i6 + 1;
                                    i6 = i9;
                                } else {
                                    offset = 0;
                                    i6 = i9;
                                }
                            } else if (((Boolean) this.sectorFree.get(i8)).booleanValue()) {
                                offset = 1;
                                i6 = i8;
                            } else {
                                offset = i6;
                                i6 = i9;
                            }
                            if (offset >= i7) {
                                i5 = i6;
                                break;
                            }
                            i8++;
                            i9 = i6;
                            i6 = offset;
                        }
                        offset = i6;
                        i5 = i9;
                    } else {
                        i5 = offset;
                        offset = 0;
                    }
                    if (offset >= i7) {
                        debug("SAVE", i, i2, i3, "reuse");
                        setOffset(i, i2, (i5 << 8) | i7);
                        while (i4 < i7) {
                            this.sectorFree.set(i5 + i4, Boolean.valueOf(false));
                            i4++;
                        }
                        write(i5, bArr, i3);
                        return;
                    }
                    debug("SAVE", i, i2, i3, "grow");
                    this.file.seek(this.file.length());
                    i6 = this.sectorFree.size();
                    for (offset = 0; offset < i7; offset++) {
                        this.file.write(emptySector);
                        this.sectorFree.add(Boolean.valueOf(false));
                    }
                    this.sizeDelta += i7 * 4096;
                    write(i6, bArr, i3);
                    setOffset(i, i2, (i6 << 8) | i7);
                    return;
                }
                debug("SAVE", i, i2, i3, "rewrite");
                write(i5, bArr, i3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
