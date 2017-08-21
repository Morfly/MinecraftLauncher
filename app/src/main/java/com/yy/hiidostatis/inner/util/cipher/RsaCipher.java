package com.yy.hiidostatis.inner.util.cipher;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class RsaCipher {
    private static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";
    private PrivateKey privateKey;
    private int private_m_flen = 16;
    private int private_m_tail = 5;
    private PublicKey publicKey;
    private int public_m_flen = 16;
    private int public_m_tail = 5;
    private final ThreadLocal<Cipher> rsaCipher = new C19151();

    class C19151 extends ThreadLocal<Cipher> {
        C19151() {
        }

        protected Cipher initialValue() {
            try {
                return Cipher.getInstance(RsaCipher.RSA_PADDING);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    private byte[] decrypt(byte[] bArr, int i, int i2, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = (Cipher) this.rsaCipher.get();
        cipher.init(2, key);
        return cipher.doFinal(bArr, i, i2);
    }

    private byte[] encrypt(byte[] bArr, int i, int i2, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = (Cipher) this.rsaCipher.get();
        cipher.init(1, key);
        return cipher.doFinal(bArr, i, i2);
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        List arrayList = new ArrayList(512);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        while (true) {
            int read = dataInputStream.read();
            if (read == -1) {
                break;
            }
            arrayList.add(Byte.valueOf((byte) read));
        }
        byte[] bArr = new byte[arrayList.size()];
        for (int read = 0; read < bArr.length; read++) {
            bArr[read] = ((Byte) arrayList.get(read)).byteValue();
        }
        return bArr;
    }

    public byte[] decrypt(byte[] bArr, int i) throws Exception {
        Object obj = new byte[((bArr.length / this.private_m_flen) * this.private_m_tail)];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i4 < bArr.length) {
            try {
                byte[] decrypt = decrypt(bArr, i4, this.private_m_flen, this.privateKey);
                if (decrypt == null) {
                    throw new Exception("Wrong rsa block ,decrypt result is null.");
                } else if (decrypt.length != this.private_m_tail) {
                    throw new Exception(String.format("Wrong rsa block, expect tail length [ %d ], get tail length [ %d ]", new Object[]{Integer.valueOf(this.private_m_tail), Integer.valueOf(decrypt.length)}));
                } else {
                    int length;
                    System.arraycopy(decrypt, 0, obj, i3, decrypt.length);
                    if (bArr.length - i4 > this.private_m_flen) {
                        length = decrypt.length + i2;
                    } else if (i % this.private_m_tail != 0) {
                        byte b = decrypt[this.private_m_tail - 1];
                        length = this.private_m_tail - b;
                        while (length < this.private_m_tail && decrypt[length] == (byte) 0) {
                            length++;
                        }
                        length = length == this.private_m_tail + -1 ? (decrypt.length - b) + i2 : decrypt.length + i2;
                    } else {
                        length = decrypt.length + i2;
                    }
                    i4 = this.private_m_flen + i4;
                    i3 = this.private_m_tail + i3;
                    i2 = length;
                }
            } catch (Throwable e) {
                throw new Exception(e);
            }
        }
        Object obj2 = new byte[i2];
        System.arraycopy(obj, 0, obj2, 0, i2);
        return (byte[]) obj2;
    }

    public byte[] decryptTlogAesKey(String str) throws Exception {
        int decimalCharLength = TextUtils.getDecimalCharLength(str);
        byte[] hex2Bytes = TextUtils.hex2Bytes(str, 8);
        if (hex2Bytes == null || hex2Bytes.length == 0) {
            return null;
        }
        hex2Bytes = decrypt(hex2Bytes, decimalCharLength);
        if (hex2Bytes.length == decimalCharLength) {
            return hex2Bytes;
        }
        throw new Exception(String.format("Head length [ %d ] != decrypt length [ %d ]", new Object[]{Integer.valueOf(decimalCharLength), Integer.valueOf(hex2Bytes.length)}));
    }

    public byte[] encrypt(byte[] bArr) throws Exception {
        int i = 0;
        int length = bArr.length;
        int i2 = length % this.public_m_tail;
        if (i2 != 0) {
            length = (length + this.public_m_tail) - i2;
        }
        int length2 = bArr.length;
        if (i2 != 0) {
            Object obj = new byte[length];
            System.arraycopy(bArr, 0, obj, 0, bArr.length);
            bArr = (byte[]) obj;
        }
        Object obj2 = new byte[((length / this.public_m_tail) * this.public_m_flen)];
        length = 0;
        i2 = 0;
        while (i2 < bArr.length) {
            try {
                length = length2 - i2;
                if (length < this.public_m_tail) {
                    break;
                }
                byte[] encrypt = encrypt(bArr, i2, this.public_m_tail, this.publicKey);
                System.arraycopy(encrypt, 0, obj2, i, encrypt.length);
                i2 += this.public_m_tail;
                i += this.public_m_flen;
            } catch (Throwable e) {
                throw new Exception(e);
            }
        }
        if (length > 0 && i2 < bArr.length) {
            bArr[(this.public_m_tail + i2) - 1] = (byte) (this.public_m_tail - length);
            byte[] encrypt2 = encrypt(bArr, i2, this.public_m_tail, this.publicKey);
            System.arraycopy(encrypt2, 0, obj2, i, encrypt2.length);
        }
        return (byte[]) obj2;
    }

    public String encryptTlogAesKey(byte[] bArr) throws Exception {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] encrypt = encrypt(bArr);
        StringBuilder stringBuilder = new StringBuilder((encrypt.length * 2) + 8);
        stringBuilder.append(TextUtils.length2DecimalChar(bArr.length));
        stringBuilder.append(TextUtils.bytes2hex(encrypt));
        return stringBuilder.toString();
    }

    public void loadPrivateKey(File file) throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                loadPrivateKey((InputStream) fileInputStream);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    public void loadPrivateKey(InputStream inputStream) throws Exception {
        try {
            this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(readAllBytes(inputStream)));
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public void loadPublicKey(InputStream inputStream) throws Exception {
        try {
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(readAllBytes(inputStream)));
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public void loadPublicKey(String str) throws Exception {
        loadPublicKey(new ByteArrayInputStream(TextUtils.decodeBase64(str)));
    }
}
