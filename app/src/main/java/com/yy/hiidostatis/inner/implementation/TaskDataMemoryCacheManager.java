package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

import com.yy.hiidostatis.inner.util.ProcessUtil;
import com.yy.hiidostatis.inner.util.log.C1923L;
import com.yy.hiidostatis.track.Report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TaskDataMemoryCacheManager {
    private boolean isSync = false;
    private ReentrantLock lock = new ReentrantLock();
    private String mCacheFileName;
    private TaskDataSet memoryCacheDataSet = new TaskDataSet();
    private String newFileName = null;
    private Map<String, String> newFileNameMap = new HashMap();

    public TaskDataMemoryCacheManager(String str) {
        this.mCacheFileName = str + "_m";
        C1923L.brief("mCacheFileName = %s", this.mCacheFileName);
    }

    private String getFileNameBindProcess(Context context, String str) {
        String str2 = (String) this.newFileNameMap.get(str);
        if (str2 != null) {
            return str2;
        }
        str2 = ProcessUtil.getFileNameBindProcess(context, str);
        this.newFileNameMap.put(str, str2);
        return str2;
    }

    private RawDataSerializer getStorage(Context context) {
        if (this.newFileName == null) {
            this.newFileName = ProcessUtil.getFileNameBindProcess(context, this.mCacheFileName);
        }
        return new RawDataSerializer(this.newFileName);
    }

    private TaskDataSet loadStoredData(Context context) throws Throwable {
        Exception e;
        RawDataSerializer rawDataSerializer;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        long currentTimeMillis = System.currentTimeMillis();
        ObjectInputStream objectInputStream2 = null;
        RawDataSerializer storage;
        TaskDataSet taskDataSet;
        String str;
        Object[] objArr;
        try {
            storage = getStorage(context);
            try {
                if (storage.openForRead(context)) {
                    if (storage.haveData()) {
                        InputStream inputStream = storage.getInputStream();
                        if (inputStream == null) {
                            throw new RuntimeException("Unexpected occasion : have data but failed to get InputStream.");
                        }
                        C1923L.brief("Input stream length is %d for %s", Integer.valueOf(inputStream.available()), this.mCacheFileName);
                        objectInputStream2 = new ObjectInputStream(inputStream);
                        try {
                            taskDataSet = (TaskDataSet) objectInputStream2.readObject();
                            String str2 = "loadStoredData dataSet size = %d";
                            Object[] objArr2 = new Object[1];
                            objArr2[0] = Integer.valueOf(taskDataSet == null ? 0 : taskDataSet.size());
                            C1923L.brief(str2, objArr2);
                            if (objectInputStream2 != null) {
                                try {
                                    objectInputStream2.close();
                                } catch (Exception e2) {
                                }
                            }
                            if (storage != null) {
                                storage.close();
                            }
                            str = "loadStoredData elapsed time :%d ms";
                            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                        } catch (Exception e3) {
                            e = e3;
                            objectInputStream = objectInputStream2;
                            rawDataSerializer = storage;
                            try {
                                C1923L.warn(this, "loadError = [%s]. drop all the file and reset", e);
                                if (rawDataSerializer != null) {
                                    rawDataSerializer.close();
                                    rawDataSerializer.openForWrite(context);
                                    rawDataSerializer.dropAll();
                                    rawDataSerializer.close();
                                }
                                Report.onError(String.format("loadError = [%s]. drop all the file and reset", new Object[]{e}));
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            } catch (Throwable th2) {
                                th = th2;
                                storage = rawDataSerializer;
                                if (objectInputStream != null) {
                                    try {
                                        objectInputStream.close();
                                    } catch (Exception e5) {
                                    }
                                }
                                if (storage != null) {
                                    storage.close();
                                }
                                C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                                throw th;
                            }
                            taskDataSet = new TaskDataSet();
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (Exception e6) {
                                }
                            }
                            if (rawDataSerializer != null) {
                                rawDataSerializer.close();
                            }
                            str = "loadStoredData elapsed time :%d ms";
                            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                            C1923L.brief(str, objArr);
                            return taskDataSet;
                        } catch (Throwable th3) {
                            th = th3;
                            objectInputStream = objectInputStream2;
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                            if (storage != null) {
                                storage.close();
                            }
                            C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                            throw th;
                        }
                    }
                    C1923L.brief("have no data.", new Object[0]);
                    taskDataSet = new TaskDataSet();
                    if (null != null) {
                        try {
                            objectInputStream2.close();
                        } catch (Exception e7) {
                        }
                    }
                    if (storage != null) {
                        storage.close();
                    }
                    str = "loadStoredData elapsed time :%d ms";
                    objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                    C1923L.brief(str, objArr);
                    return taskDataSet;
                } else if (storage.isExistFile(context)) {
                    throw new RuntimeException("Failed to open storage for read.");
                } else {
                    C1923L.brief("file is not exist.", new Object[0]);
                    taskDataSet = new TaskDataSet();
                    if (null != null) {
                        try {
                            objectInputStream2.close();
                        } catch (Exception e8) {
                        }
                    }
                    if (storage != null) {
                        storage.close();
                    }
                    str = "loadStoredData elapsed time :%d ms";
                    objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                    C1923L.brief(str, objArr);
                    return taskDataSet;
                }
            } catch (Exception e9) {
                String e4 = String.valueOf(e9);
                rawDataSerializer = storage;
                C1923L.warn(this, "loadError = [%s]. drop all the file and reset", e4);
                if (rawDataSerializer != null) {
                    rawDataSerializer.close();
                    rawDataSerializer.openForWrite(context);
                    rawDataSerializer.dropAll();
                    rawDataSerializer.close();
                }
                Report.onError(String.format("loadError = [%s]. drop all the file and reset", new Object[]{e4}));
                taskDataSet = new TaskDataSet();
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (rawDataSerializer != null) {
                    rawDataSerializer.close();
                }
                str = "loadStoredData elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                C1923L.brief(str, objArr);
                return taskDataSet;
            } catch (Throwable th4) {
                th = th4;
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (storage != null) {
                    storage.close();
                }
                C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                throw th;
            }
        } catch (Exception e10) {
            String e4 = e10.getMessage();
            rawDataSerializer = null;
            C1923L.warn(this, "loadError = [%s]. drop all the file and reset", e4);
            if (rawDataSerializer != null) {
                rawDataSerializer.close();
                rawDataSerializer.openForWrite(context);
                rawDataSerializer.dropAll();
                rawDataSerializer.close();
            }
            Report.onError(String.format("loadError = [%s]. drop all the file and reset", new Object[]{e4}));
            taskDataSet = new TaskDataSet();
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (rawDataSerializer != null) {
                rawDataSerializer.close();
            }
            str = "loadStoredData elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            C1923L.brief(str, objArr);
            return taskDataSet;
        } catch (Throwable th5) {
            th = th5;
            storage = null;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (storage != null) {
                storage.close();
            }
            C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.yy.hiidostatis.inner.implementation.TaskDataSet loadStoredDataByLine(android.content.Context r15) {
        /*
        r14 = this;
        r2 = 0;
        r12 = 1;
        r5 = 0;
        r6 = java.lang.System.currentTimeMillis();
        r0 = new com.yy.hiidostatis.inner.implementation.TaskDataSet;
        r0.<init>();
        r1 = 0;
        r3 = 0;
        r4 = new java.io.File;	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r8 = r15.getFilesDir();	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r9 = r14.mCacheFileName;	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r9 = r14.getFileNameBindProcess(r15, r9);	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r4.<init>(r8, r9);	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r8 = r4.exists();	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        if (r8 != 0) goto L_0x0049;
    L_0x0023:
        r4 = "file is not exist.";
        r8 = 0;
        r8 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r4, r8);	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        if (r2 == 0) goto L_0x0030;
    L_0x002d:
        r3.close();	 Catch:{ Exception -> 0x0126 }
    L_0x0030:
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r1.close();	 Catch:{ Exception -> 0x0126 }
    L_0x0035:
        r1 = "loadStoredData elapsed time :%d ms";
        r2 = new java.lang.Object[r12];
        r8 = java.lang.System.currentTimeMillis();
        r6 = r8 - r6;
        r3 = java.lang.Long.valueOf(r6);
        r2[r5] = r3;
    L_0x0045:
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r1, r2);
        return r0;
    L_0x0049:
        r3 = new java.io.FileReader;	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0136, all -> 0x0103 }
        r4 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x013a, all -> 0x012d }
        r4.<init>(r3);	 Catch:{ Exception -> 0x013a, all -> 0x012d }
    L_0x0053:
        r2 = r4.readLine();	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        if (r2 == 0) goto L_0x00d0;
    L_0x0059:
        r1 = "[|]";
        r1 = r2.split(r1);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8 = new com.yy.hiidostatis.inner.implementation.TaskData;	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.<init>();	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = 0;
        r9 = r1[r9];	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.setDataId(r9);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = 1;
        r9 = r1[r9];	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.setContent(r9);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = 2;
        r9 = r1[r9];	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r10 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.setTime(r10);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = 3;
        r9 = r1[r9];	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = java.lang.Integer.parseInt(r9);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.setTryTimes(r9);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r9 = r1.length;	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r10 = 5;
        if (r9 < r10) goto L_0x008e;
    L_0x0088:
        r9 = 4;
        r1 = r1[r9];	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        r8.setVerifyMd5(r1);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
    L_0x008e:
        r0.saveOrUpdate(r8);	 Catch:{ Exception -> 0x0092, all -> 0x0130 }
        goto L_0x0053;
    L_0x0092:
        r1 = move-exception;
        r8 = "data read exception ,give up :%s.\n %s";
        r9 = 2;
        r9 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        r10 = 0;
        r9[r10] = r2;	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        r2 = 1;
        r9[r2] = r1;	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        com.yy.hiidostatis.inner.util.log.C1923L.warn(r14, r8, r9);	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        goto L_0x0053;
    L_0x00a2:
        r1 = move-exception;
        r2 = r3;
        r3 = r4;
    L_0x00a5:
        r4 = "loadError = [%s]. loadStoredDataByLine error";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x0132 }
        r9 = 0;
        r8[r9] = r1;	 Catch:{ all -> 0x0132 }
        r1 = java.lang.String.format(r4, r8);	 Catch:{ all -> 0x0132 }
        com.yy.hiidostatis.track.Report.onError(r1);	 Catch:{ all -> 0x0132 }
        if (r2 == 0) goto L_0x00b9;
    L_0x00b6:
        r2.close();	 Catch:{ Exception -> 0x0129 }
    L_0x00b9:
        if (r3 == 0) goto L_0x00be;
    L_0x00bb:
        r3.close();	 Catch:{ Exception -> 0x0129 }
    L_0x00be:
        r1 = "loadStoredData elapsed time :%d ms";
        r2 = new java.lang.Object[r12];
        r8 = java.lang.System.currentTimeMillis();
        r6 = r8 - r6;
        r3 = java.lang.Long.valueOf(r6);
        r2[r5] = r3;
        goto L_0x0045;
    L_0x00d0:
        r2 = "loadStoredData dataSet size = %d";
        r1 = 1;
        r8 = new java.lang.Object[r1];	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        r9 = 0;
        if (r0 != 0) goto L_0x00fe;
    L_0x00d8:
        r1 = r5;
    L_0x00d9:
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        r8[r9] = r1;	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r2, r8);	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        if (r3 == 0) goto L_0x00e7;
    L_0x00e4:
        r3.close();	 Catch:{ Exception -> 0x0124 }
    L_0x00e7:
        if (r4 == 0) goto L_0x00ec;
    L_0x00e9:
        r4.close();	 Catch:{ Exception -> 0x0124 }
    L_0x00ec:
        r1 = "loadStoredData elapsed time :%d ms";
        r2 = new java.lang.Object[r12];
        r8 = java.lang.System.currentTimeMillis();
        r6 = r8 - r6;
        r3 = java.lang.Long.valueOf(r6);
        r2[r5] = r3;
        goto L_0x0045;
    L_0x00fe:
        r1 = r0.size();	 Catch:{ Exception -> 0x00a2, all -> 0x0130 }
        goto L_0x00d9;
    L_0x0103:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
    L_0x0106:
        if (r3 == 0) goto L_0x010b;
    L_0x0108:
        r3.close();	 Catch:{ Exception -> 0x012b }
    L_0x010b:
        if (r4 == 0) goto L_0x0110;
    L_0x010d:
        r4.close();	 Catch:{ Exception -> 0x012b }
    L_0x0110:
        r1 = "loadStoredData elapsed time :%d ms";
        r2 = new java.lang.Object[r12];
        r8 = java.lang.System.currentTimeMillis();
        r6 = r8 - r6;
        r3 = java.lang.Long.valueOf(r6);
        r2[r5] = r3;
        com.yy.hiidostatis.inner.util.log.C1923L.brief(r1, r2);
        throw r0;
    L_0x0124:
        r1 = move-exception;
        goto L_0x00ec;
    L_0x0126:
        r1 = move-exception;
        goto L_0x0035;
    L_0x0129:
        r1 = move-exception;
        goto L_0x00be;
    L_0x012b:
        r1 = move-exception;
        goto L_0x0110;
    L_0x012d:
        r0 = move-exception;
        r4 = r2;
        goto L_0x0106;
    L_0x0130:
        r0 = move-exception;
        goto L_0x0106;
    L_0x0132:
        r0 = move-exception;
        r4 = r3;
        r3 = r2;
        goto L_0x0106;
    L_0x0136:
        r1 = move-exception;
        r3 = r2;
        goto L_0x00a5;
    L_0x013a:
        r1 = move-exception;
        r13 = r3;
        r3 = r2;
        r2 = r13;
        goto L_0x00a5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yy.hiidostatis.inner.implementation.TaskDataMemoryCacheManager.loadStoredDataByLine(android.content.Context):com.yy.hiidostatis.inner.implementation.TaskDataSet");
    }

    private void saveStoredData(Context context, TaskDataSet taskDataSet) throws Throwable {
        String str;
        Object[] objArr;
        Exception e;
        Throwable th;
        ObjectOutputStream objectOutputStream = null;
        long currentTimeMillis = System.currentTimeMillis();
        RawDataSerializer storage;
        try {
            storage = getStorage(context);
            try {
                if (storage.openForWrite(context)) {
                    storage.dropAll();
                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(storage.getOutputStream());
                    try {
                        objectOutputStream2.writeObject(taskDataSet);
                        objectOutputStream2.flush();
                        String str2 = "saveStoredData dataSet size = %d";
                        Object[] objArr2 = new Object[1];
                        objArr2[0] = Integer.valueOf(taskDataSet == null ? 0 : taskDataSet.size());
                        C1923L.brief(str2, objArr2);
                        if (objectOutputStream2 != null) {
                            try {
                                objectOutputStream2.close();
                            } catch (Exception e2) {
                            }
                        }
                        if (storage != null) {
                            storage.close();
                        }
                        str = "saveStoredData elapsed time :%d ms";
                        objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                    } catch (Exception e3) {
                        e = e3;
                        objectOutputStream = objectOutputStream2;
                        try {
                            C1923L.error(this, "saveStoredData exception:%s", e);
                            if (objectOutputStream != null) {
                                try {
                                    objectOutputStream.close();
                                } catch (Exception e4) {
                                }
                            }
                            if (storage != null) {
                                storage.close();
                            }
                            str = "saveStoredData elapsed time :%d ms";
                            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                            C1923L.brief(str, objArr);
                        } catch (Throwable th2) {
                            th = th2;
                            if (objectOutputStream != null) {
                                try {
                                    objectOutputStream.close();
                                } catch (Exception e5) {
                                }
                            }
                            if (storage != null) {
                                storage.close();
                            }
                            C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectOutputStream = objectOutputStream2;
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (storage != null) {
                            storage.close();
                        }
                        C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        throw th;
                    }
                    C1923L.brief(str, objArr);
                }
                throw new RuntimeException("Failed to open storage for write.");
            } catch (Exception e6) {
                e = e6;
                C1923L.error(this, "saveStoredData exception:%s", e);
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (storage != null) {
                    storage.close();
                }
                str = "saveStoredData elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                C1923L.brief(str, objArr);
            }
        } catch (Exception e7) {
            e = e7;
            storage = null;
            C1923L.error(this, "saveStoredData exception:%s", e);
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (storage != null) {
                storage.close();
            }
            str = "saveStoredData elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            C1923L.brief(str, objArr);
        } catch (Throwable th4) {
            th = th4;
            storage = null;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (storage != null) {
                storage.close();
            }
            C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            throw th;
        }
    }

    private void saveStoredDataByLine(Context context, TaskDataSet taskDataSet) throws Throwable {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;
        Exception e;
        FileWriter fileWriter2;
        String str;
        Object[] objArr;
        Throwable th;
        BufferedWriter bufferedWriter2 = null;
        long currentTimeMillis = System.currentTimeMillis();
        if (taskDataSet == null || taskDataSet.size() == 0) {
            C1923L.brief("saveStoredData dataSet size = 0 , return", new Object[0]);
            return;
        }
        try {
            File file = new File(context.getFilesDir(), getFileNameBindProcess(context, this.mCacheFileName));
            if (!file.exists()) {
                C1923L.brief("file is not exist. create it", new Object[0]);
                file.createNewFile();
            }
            fileWriter = new FileWriter(file);
            try {
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (Exception e2) {
                e = e2;
                fileWriter2 = fileWriter;
                try {
                    C1923L.error(this, "saveStoredData exception:%s", e);
                    if (fileWriter2 != null) {
                        try {
                            fileWriter2.close();
                        } catch (Exception e3) {
                            str = "saveStoredData elapsed time :%d ms";
                            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                            C1923L.brief(str, objArr);
                        }
                    }
                    if (bufferedWriter2 != null) {
                        bufferedWriter2.close();
                    }
                    str = "saveStoredData elapsed time :%d ms";
                    objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                    C1923L.brief(str, objArr);
                } catch (Throwable th2) {
                    th = th2;
                    fileWriter = fileWriter2;
                    bufferedWriter = bufferedWriter2;
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (Exception e4) {
                            C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                            throw th;
                        }
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
                if (fileWriter != null) {
                    fileWriter.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                throw th;
            }
            try {
                Iterator it = taskDataSet.iterator();
                int i = 0;
                while (it.hasNext()) {
                    TaskData taskData = (TaskData) it.next();
                    bufferedWriter.append(taskData.getDataId()).append("|");
                    bufferedWriter.append(taskData.getContent()).append("|");
                    bufferedWriter.append(taskData.getTime() + "").append("|");
                    bufferedWriter.append(taskData.getTryTimes() + "").append("|");
                    CharSequence verifyMd5 = taskData.getVerifyMd5();
                    if (verifyMd5 == null) {
                        verifyMd5 = "";
                    }
                    bufferedWriter.append(verifyMd5);
                    bufferedWriter.newLine();
                    int i2 = i + 1;
                    if (i % 100 == 0) {
                        bufferedWriter.flush();
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
                bufferedWriter.flush();
                String str2 = "saveStoredData dataSet size = %d";
                Object[] objArr2 = new Object[1];
                objArr2[0] = Integer.valueOf(taskDataSet == null ? 0 : taskDataSet.size());
                C1923L.brief(str2, objArr2);
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (Exception e5) {
                    }
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                str = "saveStoredData elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            } catch (Exception e6) {
                e = e6;
                bufferedWriter2 = bufferedWriter;
                fileWriter2 = fileWriter;
                C1923L.error(this, "saveStoredData exception:%s", e);
                if (fileWriter2 != null) {
                    fileWriter2.close();
                }
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                str = "saveStoredData elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
                C1923L.brief(str, objArr);
            } catch (Throwable th4) {
                th = th4;
                if (fileWriter != null) {
                    fileWriter.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                throw th;
            }
        } catch (Exception e7) {
            e = e7;
            fileWriter2 = null;
            C1923L.error(this, "saveStoredData exception:%s", e);
            if (fileWriter2 != null) {
                fileWriter2.close();
            }
            if (bufferedWriter2 != null) {
                bufferedWriter2.close();
            }
            str = "saveStoredData elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            C1923L.brief(str, objArr);
        } catch (Throwable th5) {
            th = th5;
            bufferedWriter = null;
            fileWriter = null;
            if (fileWriter != null) {
                fileWriter.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            throw th;
        }
        C1923L.brief(str, objArr);
    }

    private void syncFromFile(Context context) {
        if (!this.isSync) {
            TaskDataSet loadStoredDataByLine = loadStoredDataByLine(context);
            String str = "syncFromFile dataset size = %d";
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(loadStoredDataByLine == null ? 0 : loadStoredDataByLine.size());
            C1923L.debug(this, str, objArr);
            if (loadStoredDataByLine != null) {
                int i = 0;
                int i2 = 0;
                while (true) {
                    TaskData removeFirst = loadStoredDataByLine.removeFirst();
                    if (removeFirst == null) {
                        break;
                    } else if (removeFirst.verifyMd5()) {
                        this.memoryCacheDataSet.saveOrUpdate(removeFirst);
                        i2++;
                    } else {
                        i++;
                        C1923L.warn(this, "data verify failure ,give up .data=[%s]", removeFirst.getContent());
                    }
                }
                C1923L.debug(this, "syncFromFile. succ size = [%d],fail size = [%d]", Integer.valueOf(i2), Integer.valueOf(i));
            }
            this.isSync = true;
        }
    }

    private void syncToFile(Context context, TaskDataSet taskDataSet) throws Throwable {
        saveStoredDataByLine(context, taskDataSet);
    }

    public TaskData getFirst(Context context) {
        String str = "";
        Object[] objArr = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        TaskData taskData = null;
        try {
            syncFromFile(context);
            if (this.memoryCacheDataSet.isEmpty()) {
                this.lock.unlock();
                str = "getFirst elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            } else {
                C1923L.debug(this, "getFirst from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.memoryCacheDataSet.size()));
                taskData = this.memoryCacheDataSet.getFirst();
                this.lock.unlock();
                str = "getFirst elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            }
        } catch (Exception e) {
            C1923L.error(this, "Failed to getFirst data .Exception:%s", e);
            this.lock.unlock();
            str = "getFirst elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        C1923L.brief(str, objArr);
        return taskData;
    }

    public TaskData getLast(Context context) {
        String str = "";
        Object[] objArr = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        TaskData taskData = null;
        try {
            syncFromFile(context);
            if (this.memoryCacheDataSet.isEmpty()) {
                this.lock.unlock();
                str = "getLast elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            } else {
                C1923L.debug(this, "getLast from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.memoryCacheDataSet.size()));
                taskData = this.memoryCacheDataSet.getLast();
                this.lock.unlock();
                str = "getLast elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            }
        } catch (Exception e) {
            C1923L.error(this, "Failed to getLast data .Exception:%s", e);
            this.lock.unlock();
            str = "getLast elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        C1923L.brief(str, objArr);
        return taskData;
    }

    public TaskData getRandom(Context context) {
        String str = "";
        Object[] objArr = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        TaskData taskData = null;
        try {
            syncFromFile(context);
            if (this.memoryCacheDataSet.isEmpty()) {
                this.lock.unlock();
                str = "getRandom elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            } else {
                C1923L.debug(this, "getRandom from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.memoryCacheDataSet.size()));
                taskData = this.memoryCacheDataSet.getRandom();
                this.lock.unlock();
                str = "getRandom elapsed time :%d ms";
                objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
            }
        } catch (Exception e) {
            C1923L.error(this, "Failed to getRandom data .Exception:%s", e);
            this.lock.unlock();
            str = "getRandom elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("getRandom elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        C1923L.brief(str, objArr);
        return taskData;
    }

    public void remove(Context context, TaskData taskData) {
        String str = "";
        Object[] objArr = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            syncFromFile(context);
            if (!this.memoryCacheDataSet.isEmpty()) {
                C1923L.debug(this, "remove from  memory cache [%b]. memory cache dataset size = %d", Boolean.valueOf(this.memoryCacheDataSet.remove(taskData)), Integer.valueOf(this.memoryCacheDataSet.size()));
            }
            syncToFile(context, this.memoryCacheDataSet);
            this.lock.unlock();
            str = "remove elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
        } catch (Exception e) {
            C1923L.error(this, "Failed to remove data .Exception:%s", e);
            this.lock.unlock();
            str = "remove elapsed time :%d ms";
            objArr = new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)};
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("remove elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        C1923L.brief(str, objArr);
    }

    public boolean save(Context context, TaskData taskData) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            syncFromFile(context);
            this.memoryCacheDataSet.saveOrUpdate(taskData);
            C1923L.debug(this, "save data : %s to memory cache. memory cache dataset size = %d", taskData.getDataId(), Integer.valueOf(this.memoryCacheDataSet.size()));
            syncToFile(context, this.memoryCacheDataSet);
            this.lock.unlock();
            C1923L.brief("save elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            return true;
        } catch (Exception e) {
            C1923L.error(this, "Failed to save data : %s Exception:%s", taskData.getDataId(), e);
            this.lock.unlock();
            C1923L.brief("save elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            return false;
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("save elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        return false;
    }

    public void storePendingCommands(Context context) {
    }
}
