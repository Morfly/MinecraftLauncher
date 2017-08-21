package com.yy.hiidostatis.inner.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCipher {
    private static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
    private static final int BLOCK_SIZE = 16;
    private final ThreadLocal<Cipher> Cipher_AES_CBC_NOPADDING = new C19141();
    private final byte[] m_key;

    final class C19141 extends ThreadLocal<Cipher> {
        C19141() {
        }

        protected Cipher initialValue() {
            try {
                return Cipher.getInstance(AesCipher.AES_CBC_NOPADDING);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AesCipher(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Key is null");
        }
        this.m_key = TextUtils.md5byte(bArr);
    }

    private IvParameterSpec createIv() {
        Object obj = new byte[this.m_key.length];
        System.arraycopy(this.m_key, 0, obj, 0, this.m_key.length);
        return new IvParameterSpec((byte[]) obj);
    }

    private byte[] fillBlock(byte[] bArr) {
        int length = bArr.length % 16;
        if (length == 0) {
            return bArr;
        }
        Object obj = new byte[(length == 0 ? bArr.length : (bArr.length + 16) - length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        return (byte[]) obj;
    }

    public byte[] decrypt(byte[] bArr) throws Exception {
        try {
            Cipher cipher = (Cipher) Cipher_AES_CBC_NOPADDING.get();
            cipher.init(2, new SecretKeySpec(this.m_key, "AES"), createIv());
            return cipher.doFinal(bArr);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public byte[] decryptTlogBase64(String str) throws Exception {
        int decimalCharLength = TextUtils.getDecimalCharLength(str);
        byte[] decode = Base64Util.decode(str.substring(8));
        if (decode == null || decode.length == 0) {
            return decode;
        }
        byte[] decrypt = decrypt(decode);
        if (decimalCharLength > decrypt.length) {
            throw new Exception(String.format("Expect data length [ %d ] ,but get [ %d ].", new Object[]{Integer.valueOf(decimalCharLength), Integer.valueOf(decrypt.length)}));
        }
        Object obj = new byte[decimalCharLength];
        System.arraycopy(decrypt, 0, obj, 0, decimalCharLength);
        return (byte[]) obj;
    }

    public byte[] decryptTlogHex(String str) throws Exception {
        int decimalCharLength = TextUtils.getDecimalCharLength(str);
        byte[] hex2Bytes = TextUtils.hex2Bytes(str, 8);
        if (hex2Bytes == null || hex2Bytes.length == 0) {
            return hex2Bytes;
        }
        byte[] decrypt = decrypt(hex2Bytes);
        if (decimalCharLength > decrypt.length) {
            throw new Exception(String.format("Expect data length [ %d ] ,but get [ %d ].", new Object[]{Integer.valueOf(decimalCharLength), Integer.valueOf(decrypt.length)}));
        }
        Object obj = new byte[decimalCharLength];
        System.arraycopy(decrypt, 0, obj, 0, decimalCharLength);
        return (byte[]) obj;
    }

    public byte[] encrypt(byte[] bArr) throws Exception {
        try {
            Cipher cipher = (Cipher) Cipher_AES_CBC_NOPADDING.get();
            cipher.init(1, new SecretKeySpec(this.m_key, "AES"), createIv());
            return cipher.doFinal(fillBlock(bArr));
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public String encryptTlogBytes(byte[] bArr) throws Exception {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] encrypt = encrypt(bArr);
        if (encrypt == null) {
            throw new Exception("EncryptData is null");
        }
        StringBuilder stringBuilder = new StringBuilder(bArr.length + (encrypt.length * 2));
        stringBuilder.append(TextUtils.length2DecimalChar(bArr.length));
        stringBuilder.append(TextUtils.bytes2hex(encrypt));
        return stringBuilder.toString();
    }

    public String encryptTlogBytesBase64(byte[] bArr) throws Exception {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] encrypt = encrypt(bArr);
        if (encrypt == null) {
            throw new Exception("EncryptData is null");
        }
        StringBuilder stringBuilder = new StringBuilder(bArr.length + (encrypt.length * 2));
        stringBuilder.append(TextUtils.length2DecimalChar(bArr.length));
        stringBuilder.append(Base64Util.encode(encrypt));
        return stringBuilder.toString();
    }
}
