package com.mcbox.pesdk.mcfloat.func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DtFile {
    public static String gamePackSupportPath = "";
    public static final String std_en_US_lang_md5 = "30938545A34785A261BD310CD67DCBEC";
    public static final String std_game_pack_md5 = "69A27F72BCD1501C3BA3DAFDE5FD1360";
    public static final String std_libminecraftpe_so_md5 = "85BA5D2D680F583A7A4CE28B11BD7F2";

    public static boolean DeleteFolder(String str) {
        File file = new File(str);
        return !file.exists() ? false : file.isFile() ? deleteFile(str) : deleteDirectory(str);
    }

    public static InputStream GetFileDataToInputStream(String str) {
        try {
            File file = new File(gamePackSupportPath + str);
            if (file != null) {
                return new FileInputStream(file);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void SetGamePackSupportPath(String str) {
        gamePackSupportPath = str;
    }

    public static boolean deleteDirectory(String str) {
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        File file = new File(str);
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
            } else {
                for (File absolutePath : listFiles) {
                    deleteDirectory(absolutePath.getAbsolutePath());
                }
                file.delete();
            }
        }
        return true;
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (!file.isFile() || !file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }

    public static String readFileWithEncode(String str, String str2) {
        String str3 = "";
        try {
            File file = new File(str);
            if (file.isFile() && file.exists()) {
                Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), str2);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        inputStreamReader.close();
                        return str3;
                    }
                    str3 = new StringBuilder(String.valueOf(str3)).append(readLine).toString() + "\r\n";
                }
            } else {
                System.out.println("File " + str + " not found!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Read file " + str + " error!" + e.getMessage());
            e.printStackTrace();
        }
        return str3;
    }

    public static String readSDFile(String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        byte[] bArr = new byte[fileInputStream.available()];
        fileInputStream.read(bArr);
        String str2 = new String(bArr, "UTF-8");
        fileInputStream.close();
        return str2;
    }

    public void copyFile(String str, String str2) {
        int i = 0;
        try {
            if (new File(str).exists()) {
                FileInputStream fileInputStream = new FileInputStream(str);
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                byte[] bArr = new byte[1444];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                    i += read;
                    System.out.println(i);
                    fileOutputStream.write(bArr, 0, read);
                }
            }
        } catch (Exception e) {
            System.out.println("Coping error!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void writeSDFile(String str, String str2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
        fileOutputStream.write(str2.getBytes());
        fileOutputStream.close();
    }
}
