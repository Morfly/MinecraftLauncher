package com.mcbox.p019a.p020a;

import android.util.Log;

import com.mcbox.pesdk.launcher.LauncherConstants;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class C1677c {
    private C1677c() {
    }

    private static Object[] m5696a(Object obj, ArrayList<File> arrayList, File file, ArrayList<IOException> arrayList2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (Object[]) C1675a.m5689b(obj, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(obj, new Object[]{arrayList, file, arrayList2});
    }

    public static void m5697b(ClassLoader classLoader, List<File> list, File file) {
        Object obj = null;
        try {
            obj = C1675a.m5688b(classLoader, "pathList").get(classLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ArrayList arrayList = new ArrayList();
        try {
            C1675a.m5690b(obj, "dexElements", C1677c.m5696a(obj, new ArrayList(list), file, arrayList));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Log.w("MultiDex", "Exception in makeDexElement", (IOException) it.next());
            }
            try {
                Field a = C1675a.m5688b(classLoader, "dexElementsSuppressedExceptions");
                IOException[] iOExceptionArr = (IOException[]) a.get(classLoader);
                if (iOExceptionArr == null) {
                    obj = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
                } else {
                    Object obj2 = new IOException[(arrayList.size() + iOExceptionArr.length)];
                    arrayList.toArray((Object[]) obj2);
                    System.arraycopy(iOExceptionArr, 0, obj2, arrayList.size(), iOExceptionArr.length);
                    obj = obj2;
                }
                a.set(classLoader, obj);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(LauncherConstants.LOG_TAG, "[MultiDex] no dexElementsSuppressedExceptions parameter for v19!");
            }
        }
    }
}
