package com.mcbox.p019a.p020a;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

import dalvik.system.DexFile;

final class C1679e {
    private C1679e() {
    }

    public static void m5702b(ClassLoader classLoader, List<File> list) throws NoSuchFieldException, IllegalAccessException, IOException {
        int size = list.size();
        Field a = C1675a.m5688b(classLoader, "path");
        StringBuilder stringBuilder = new StringBuilder((String) a.get(classLoader));
        Object[] objArr = new String[size];
        Object[] objArr2 = new File[size];
        Object[] objArr3 = new ZipFile[size];
        Object[] objArr4 = new DexFile[size];
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            File file = (File) listIterator.next();
            String absolutePath = file.getAbsolutePath();
            stringBuilder.append(':').append(absolutePath);
            int previousIndex = listIterator.previousIndex();
            objArr[previousIndex] = absolutePath;
            objArr2[previousIndex] = file;
            objArr3[previousIndex] = new ZipFile(file);
            objArr4[previousIndex] = DexFile.loadDex(absolutePath, absolutePath + ".dex", 0);
        }
        a.set(classLoader, stringBuilder.toString());
        C1675a.m5690b((Object) classLoader, "mPaths", objArr);
        C1675a.m5690b((Object) classLoader, "mFiles", objArr2);
        C1675a.m5690b((Object) classLoader, "mZips", objArr3);
        C1675a.m5690b((Object) classLoader, "mDexs", objArr4);
    }
}
