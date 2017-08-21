package com.mcbox.p019a.p020a;

import android.support.v4.media.session.PlaybackStateCompat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class C1683i {
    private static final int f3558a = 22;
    private static final int f3559b = 101010256;
    private static final int f3560c = 16384;

    C1683i() {
    }

    static long m5716a(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            long a = C1683i.m5717a(randomAccessFile, C1683i.m5718a(randomAccessFile));
            return a;
        } finally {
            randomAccessFile.close();
        }
    }

    static long m5717a(RandomAccessFile randomAccessFile, C1684j c1684j) throws IOException {
        CRC32 crc32 = new CRC32();
        long j = c1684j.f3562b;
        randomAccessFile.seek(c1684j.f3561a);
        byte[] bArr = new byte[16384];
        int read = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j));
        while (read != -1) {
            crc32.update(bArr, 0, read);
            j -= (long) read;
            if (j == 0) {
                break;
            }
            read = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j));
        }
        return crc32.getValue();
    }

    static C1684j m5718a(RandomAccessFile randomAccessFile) throws IOException {
        long j = 0;
        long length = randomAccessFile.length() - 22;
        if (length < 0) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long j2 = length - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        if (j2 >= 0) {
            j = j2;
        }
        int reverseBytes = Integer.reverseBytes(f3559b);
        j2 = length;
        do {
            randomAccessFile.seek(j2);
            if (randomAccessFile.readInt() == reverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                C1684j c1684j = new C1684j();
                c1684j.f3562b = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                c1684j.f3561a = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                return c1684j;
            }
            j2--;
        } while (j2 >= j);
        throw new ZipException("End Of Central Directory signature not found");
    }
}
