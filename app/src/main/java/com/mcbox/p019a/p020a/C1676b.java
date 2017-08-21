package com.mcbox.p019a.p020a;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

final class C1676b {
    private C1676b() {
    }

    private static Object[] m5693a(Object obj, ArrayList<File> arrayList, File file) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (Object[]) C1675a.m5689b(obj, "makeDexElements", ArrayList.class, File.class).invoke(obj, new Object[]{arrayList, file});
    }

    public static void m5694b(ClassLoader classLoader, List<File> list, File file)  {
        Object obj = null;
        try {
            obj = C1675a.m5688b(classLoader, "pathList").get(classLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            C1675a.m5690b(obj, "dexElements", C1676b.m5693a(obj, new ArrayList(list), file));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
