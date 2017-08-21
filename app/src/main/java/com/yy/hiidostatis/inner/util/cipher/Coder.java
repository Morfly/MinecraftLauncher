package com.yy.hiidostatis.inner.util.cipher;

import android.support.v4.view.InputDeviceCompat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public final class Coder {
    private static final String CHARSET = "UTF-8";
    private static final String KEY_DES = "DES";
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA";

    public static byte[] StringToBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bArr != null) {
            for (byte b : bArr) {
                stringBuilder.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
        }
        return stringBuilder.toString();
    }

    public static String bytesToString(byte[] bArr) throws UnsupportedEncodingException {
        return new String(bArr, "UTF-8");
    }

    public static String decryptBASE64(String str) throws UnsupportedEncodingException {
        return bytesToString(Base64Util.decode(str));
    }

    public static String decryptDES(String str, String str2) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        return str == null ? null : bytesToString(decryptDES(Base64Util.decode(str), StringToBytes(str2)));
    }

    public static byte[] decryptDES(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();
        Key generateSecret = SecretKeyFactory.getInstance(KEY_DES).generateSecret(new DESKeySpec(bArr2));
        Cipher instance = Cipher.getInstance(KEY_DES);
        instance.init(2, generateSecret, secureRandom);
        return instance.doFinal(bArr);
    }

    public static String encryptBASE64(String str) throws UnsupportedEncodingException {
        return Base64Util.encode(StringToBytes(str));
    }

    public static String encryptDES(String str, String str2) throws UnsupportedEncodingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        return Base64Util.encode(encryptDES(StringToBytes(str), StringToBytes(str2)));
    }

    public static byte[] encryptDES(byte[] bArr, byte[] bArr2) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();
        Key generateSecret = SecretKeyFactory.getInstance(KEY_DES).generateSecret(new DESKeySpec(bArr2));
        Cipher instance = Cipher.getInstance(KEY_DES);
        instance.init(1, generateSecret, secureRandom);
        return instance.doFinal(bArr);
    }

    public static String encryptMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return bytesToHexString(MessageDigest.getInstance("MD5").digest(StringToBytes(str)));
    }

    public static String encryptSHA(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest instance = MessageDigest.getInstance(KEY_SHA);
        instance.update(StringToBytes(str));
        return bytesToHexString(instance.digest());
    }

    public static byte[] hexStringToBytes(String str) {
        char[] toCharArray = str.toCharArray();
        int length = toCharArray.length / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int digit = (Character.digit(toCharArray[i * 2], 16) << 4) | Character.digit(toCharArray[(i * 2) + 1], 16);
            if (digit > 127) {
                digit += InputDeviceCompat.SOURCE_ANY;
            }
            bArr[i] = (byte) digit;
        }
        return bArr;
    }
}
