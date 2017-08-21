package com.yy.hiidostatis.inner.util.cipher;

import java.security.MessageDigest;

public class TextUtils {
    private static final ThreadLocal<MessageDigest> MD5_DIGEST = new C19161();
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static final class C19161 extends ThreadLocal<MessageDigest> {
        C19161() {
        }

        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String bytes2hex(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[(bArr.length * 2)];
        int i2 = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            int i3 = i2 + 1;
            cArr[i2] = hexDigits[(b >>> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = hexDigits[b & 15];
            i++;
        }
        return new String(cArr);
    }

    public static byte[] decodeBase64(String str) {
        return Base64Util.decode(str);
    }

    public static String encodeBase64(byte[] bArr) {
        return Base64Util.encode(bArr);
    }

    public static int getDecimalCharLength(String str) {
        int i = 0;
        if (str.length() < 8) {
            throw new IllegalArgumentException("Wrong hex size : " + str.length() + ", at least 8 bytes");
        }
        int i2 = 0;
        while (i < 8) {
            i2 = ((i2 * 10) + str.charAt(i)) - 48;
            i++;
        }
        return i2;
    }

    public static byte[] hex2Bytes(String str, int i) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("Wrong hex size : " + str.length());
        } else if (str.length() <= i) {
            throw new IllegalArgumentException("Wrong hex size : " + str.length() + " , expect larger than " + i);
        } else {
            byte[] bArr = new byte[((str.length() - i) / 2)];
            int i2 = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                char charAt2 = str.charAt(i + 1);
                bArr[i2] = (byte) ((charAt2 < 'a' ? charAt2 - 48 : (charAt2 + 10) - 97) | ((charAt < 'a' ? charAt - 48 : (charAt + 10) - 97) << 4));
                i2++;
                i += 2;
            }
            return bArr;
        }
    }

    public static byte[] hex2bytes(String str) {
        return hex2Bytes(str, 0);
    }

    public static String length2DecimalChar(int i) {
        return String.format("%08d", new Object[]{Integer.valueOf(i)});
    }

    public static byte[] md5byte(byte[] bArr) {
        MessageDigest messageDigest = (MessageDigest) MD5_DIGEST.get();
        messageDigest.reset();
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    public static String removeLN(String str) {
        return str.replace("\n", "");
    }

    public static String wrapDecimalLength(String str, int i) {
        String format = String.format("%08d", new Object[]{Integer.valueOf(i)});
        return String.format("%s%s", new Object[]{format, str});
    }
}
