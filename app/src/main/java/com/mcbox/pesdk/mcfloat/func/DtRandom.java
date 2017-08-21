package com.mcbox.pesdk.mcfloat.func;

import java.util.Random;

public class DtRandom {
    private static char[] ch = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1'};
    private static Random random = new Random();

    public static String createRandomString(int i) {
        return null;
    }

    public static void main(String[] strArr) {
        System.out.println(createRandomString(14));
        System.out.println("重复率:" + rateOfRepeat(10000));
    }

    public static double rateOfRepeat(int i) {
        int i2;
        String[] strArr = new String[i];
        for (i2 = 0; i2 < i; i2++) {
            strArr[i2] = createRandomString(10);
        }
        if (0 >= i) {
            return (double) (0 / i);
        }
        i2 = 0;
        for (int i3 = 1; i3 < i - 1; i3++) {
            if (strArr[0].equals(strArr[i3])) {
                i2++;
            }
        }
        return (double) i2;
    }
}
