package com.mcbox.pesdk.archive.util;

import android.os.Environment;
import android.util.Log;

import com.mcbox.pesdk.archive.entity.Options;
import com.yy.hiidostatis.defs.obj.Elem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OptionsUtil {
    public static final String OPTIONS_FILE_PATH = "games/com.mojang/minecraftpe/options.txt";
    private static OptionsUtil instance = null;
    public static final String mcPackageName = "/mnt/sdcard/games/com.mojang/minecraftpe";

    private OptionsUtil() {
    }

    private void buildOptions(String str, Options options) {
        int i = 0;
        if (str != null && str.trim().length() != 0) {
            Field[] declaredFields = options.getClass().getDeclaredFields();
            String[] split = str.split(Elem.DIVIDER);
            String str2 = split[0];
            String str3 = split.length > 1 ? split[1] : null;
            int length = declaredFields.length;
            while (i < length) {
                Field field = declaredFields[i];
                if (str3 == null || !str2.equals(field.getName())) {
                    i++;
                } else {
                    try {
                        Class type = field.getType();
                        Method declaredMethod = options.getClass().getDeclaredMethod("set" + str2.substring(0, 1).toUpperCase() + str2.substring(1), new Class[]{type});
                        if (type.getSimpleName().equalsIgnoreCase("Integer")) {
                            declaredMethod.invoke(options, new Object[]{Integer.valueOf(Integer.parseInt(str3))});
                        }
                        if (type.getSimpleName().equalsIgnoreCase("String")) {
                            declaredMethod.invoke(options, new Object[]{str3});
                        }
                        if (type.getSimpleName().equalsIgnoreCase("Float")) {
                            declaredMethod.invoke(options, new Object[]{Float.valueOf(Float.parseFloat(str3))});
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        }
    }

    private Map<String, Object> conventObjectToMap(Options options) {
        Map<String, Object> hashMap = new HashMap();
        for (Method method : options.getClass().getDeclaredMethods()) {
            String name = method.getName();
            if (name.startsWith("get")) {
                name = name.substring(3);
                name = name.substring(0, 1).toLowerCase() + name.substring(1);
                try {
                    Object invoke = method.invoke(options, new Object[0]);
                    if (invoke != null) {
                        hashMap.put(name, invoke);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return hashMap;
    }

    public static OptionsUtil getInstance() {
        if (instance == null) {
            instance = new OptionsUtil();
        }
        return instance;
    }

    private File getOptionsFile() {
        return new File(Environment.getExternalStorageDirectory(), OPTIONS_FILE_PATH);
    }

    private Map<String, String> getRawOptions() throws Throwable {
        BufferedReader bufferedReader;
        Exception e;
        Throwable th;
        Map linkedHashMap = new LinkedHashMap();
        File optionsFile = getOptionsFile();
        if (optionsFile.exists()) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(optionsFile)));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        String[] split = readLine.split(Elem.DIVIDER);
                        linkedHashMap.put(split[0], split.length > 1 ? split[1] : null);
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Exception e4) {
                e = e4;
                bufferedReader = null;
                try {
                    e.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    return linkedHashMap;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e322) {
                            e322.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        }
        return linkedHashMap;
    }

    public static void main(String[] strArr) {
    }

    public static void setAutoLoadLevel(boolean z) throws Throwable {
        String str = z ? "1" : "0";
        Options options = getInstance().getOptions();
        if (options != null && !str.equals(options.getDev_autoloadlevel())) {
            options.setDev_autoloadlevel(str);
            getInstance().writeOptions(options);
        }
    }

    public Options getOptions() throws Throwable {
        Exception e;
        Throwable th;
        Options options = new Options();
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), OPTIONS_FILE_PATH);
            if (file.exists()) {
                bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            break;
                        }
                        buildOptions(readLine, options);
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                bufferedReader2.close();
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } else if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        } catch (Exception e4) {
            e = e4;
            bufferedReader2 = bufferedReader;
            try {
                e.printStackTrace();
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                return options;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th;
        }
        return options;
    }

    public void writeOptions(Options options) throws Throwable {
        writeOptions(options, null);
    }

    public void writeOptions(Options options, Map<String, String> map) throws Throwable {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        if (options != null) {
            Map rawOptions = getRawOptions();
            Map conventObjectToMap = conventObjectToMap(options);
            for (Object str : rawOptions.keySet()) {
                Object obj = conventObjectToMap.get(str);
                if (obj != null) {
                    rawOptions.put(str, obj.toString());
                }
            }
            if (map != null && map.size() > 0) {
                rawOptions.putAll(map);
            }
            File optionsFile = getOptionsFile();
            if (optionsFile.exists()) {
                BufferedWriter bufferedWriter = null;
                BufferedWriter bufferedWriter2;
                try {
                    bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(optionsFile)));
                    try {
                        for (Object str2 : rawOptions.keySet()) {
                            String str3 = (String) rawOptions.get(str2);
                            bufferedWriter2.write(str2 + (str3 != null ? Elem.DIVIDER + str3 : ""));
                            bufferedWriter2.newLine();
                        }
                        bufferedWriter2.flush();
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                                return;
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (FileNotFoundException e4) {
                        e2 = e4;
                        bufferedWriter = bufferedWriter2;
                    } catch (IOException e5) {
                        IOException e3 = e5;
                    }
                } catch (FileNotFoundException e6) {
                    e2 = e6;
                    try {
                        e2.printStackTrace();
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                                return;
                            } catch (IOException e32) {
                                e32.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedWriter2 = bufferedWriter;
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e8) {

                    bufferedWriter2 = null;
                    try {
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                                return;
                            } catch (IOException e322) {
                                e322.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        if (bufferedWriter2 != null) {
                            bufferedWriter2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    bufferedWriter2 = null;
                    if (bufferedWriter2 != null) {
                        bufferedWriter2.close();
                    }
                    throw th;
                }
            }
            Log.e("OptionsUtil", "The options file does not exists! optFile=" + optionsFile.getAbsolutePath());
        }
    }
}
