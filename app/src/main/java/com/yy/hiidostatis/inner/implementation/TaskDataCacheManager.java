package com.yy.hiidostatis.inner.implementation;

import android.content.Context;

import com.yy.hiidostatis.inner.util.ProcessUtil;
import com.yy.hiidostatis.inner.util.ThreadPool;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReentrantLock;

public class TaskDataCacheManager {
    private static final int MAX_LOAD_ERROR = 5;
    private TaskDataSet errorCacheDataSet = new TaskDataSet();
    private boolean isErrorReset = true;
    private int loadError = 0;
    private ReentrantLock lock = new ReentrantLock();
    private String mCacheFileName;
    private String newFileName = null;

    public TaskDataCacheManager(String str) {
        this.mCacheFileName = str;
    }

    private RawDataSerializer getStorage(Context context) {
        if (this.newFileName == null) {
            this.newFileName = ProcessUtil.getFileNameBindProcess(context, this.mCacheFileName);
        }
        return new RawDataSerializer(this.newFileName);
    }

    private TaskDataSet loadStoredData(final Context context) throws Throwable {
        Exception e;
        RawDataSerializer rawDataSerializer;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        long currentTimeMillis = System.currentTimeMillis();
        ObjectInputStream objectInputStream2 = null;
        RawDataSerializer storage;
        try {
            storage = getStorage(context);
            try {
                TaskDataSet taskDataSet;
                if (storage.openForRead(context)) {
                    if (storage.haveData()) {
                        InputStream inputStream = storage.getInputStream();
                        if (inputStream == null) {
                            throw new RuntimeException("Unexpected occasion : have data but failed to get InputStream.");
                        }
                        objectInputStream2 = new ObjectInputStream(inputStream);
                        try {
                            taskDataSet = (TaskDataSet) objectInputStream2.readObject();
                            this.loadError = 0;
                            String str = "loadStoredData dataSet size = %d";
                            Object[] objArr = new Object[1];
                            objArr[0] = Integer.valueOf(taskDataSet == null ? 0 : taskDataSet.size());
                            C1923L.brief(str, objArr);
                            if (objectInputStream2 != null) {
                                try {
                                    objectInputStream2.close();
                                } catch (Exception e2) {
                                }
                            }
                            if (storage != null) {
                                storage.close();
                            }
                            C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        } catch (Exception e3) {
                            e = e3;
                            objectInputStream = objectInputStream2;
                            rawDataSerializer = storage;
                            try {
                                this.loadError++;
                                C1923L.warn(this, "loadError[%d] over the MAX_LOAD_ERROR[%d] . drop all the file and reset", Integer.valueOf(this.loadError), Integer.valueOf(5));
                                rawDataSerializer.close();
                                rawDataSerializer.openForWrite(context);
                                rawDataSerializer.dropAll();
                                rawDataSerializer.close();
                                ThreadPool.getPool().execute(new Runnable() {
                                    public void run() {
                                        TaskDataCacheManager.this.storePendingCommands(context);
                                    }
                                });
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
                            this.loadError = 0;
                            throw e;
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
                        } catch (Exception e6) {
                        }
                    }
                    if (storage != null) {
                        storage.close();
                    }
                    C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                } else if (storage.isExistFile(context)) {
                    throw new RuntimeException("Failed to open storage for read.");
                } else {
                    C1923L.brief("file is not exist.", new Object[0]);
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
                    C1923L.brief("loadStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                }
                return taskDataSet;
            } catch (Exception e8) {
                e = e8;
                rawDataSerializer = storage;
                this.loadError++;
                if (this.isErrorReset && this.loadError >= 5) {
                    C1923L.warn(this, "loadError[%d] over the MAX_LOAD_ERROR[%d] . drop all the file and reset", Integer.valueOf(this.loadError), Integer.valueOf(5));
                    rawDataSerializer.close();
                    rawDataSerializer.openForWrite(context);
                    rawDataSerializer.dropAll();
                    rawDataSerializer.close();
                    //ThreadPool.getPool().execute(/* anonymous class already generated */);
                    this.loadError = 0;
                }
                throw e;
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
        } catch (Exception e9) {
            e = e9;
            rawDataSerializer = null;
            this.loadError++;
            C1923L.warn(this, "loadError[%d] over the MAX_LOAD_ERROR[%d] . drop all the file and reset", Integer.valueOf(this.loadError), Integer.valueOf(5));
            rawDataSerializer.close();
            rawDataSerializer.openForWrite(context);
            rawDataSerializer.dropAll();
            rawDataSerializer.close();
            //ThreadPool.getPool().execute(/* anonymous class already generated */);
            this.loadError = 0;
            throw e;
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

    private void saveStoredData(Context context, TaskDataSet taskDataSet) throws Throwable {
        RawDataSerializer storage;
        Throwable th;
        ObjectOutputStream objectOutputStream = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            storage = getStorage(context);
            try {
                if (storage.openForWrite(context)) {
                    storage.dropAll();
                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(storage.getOutputStream());
                    try {
                        objectOutputStream2.writeObject(taskDataSet);
                        objectOutputStream2.flush();
                        String str = "saveStoredData dataSet size = %d";
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(taskDataSet == null ? 0 : taskDataSet.size());
                        C1923L.brief(str, objArr);
                        if (objectOutputStream2 != null) {
                            try {
                                objectOutputStream2.close();
                            } catch (Exception e) {
                            }
                        }
                        if (storage != null) {
                            storage.close();
                        }
                        C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        objectOutputStream = objectOutputStream2;
                        if (objectOutputStream != null) {
                            try {
                                objectOutputStream.close();
                            } catch (Exception e2) {
                            }
                        }
                        if (storage != null) {
                            storage.close();
                        }
                        C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        throw th;
                    }
                }
                throw new RuntimeException("Failed to open storage for write.");
            } catch (Throwable th3) {
                th = th3;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (storage != null) {
                    storage.close();
                }
                C1923L.brief("saveStoredData elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                throw th;
            }
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

    public TaskData getFirst(Context context) {
        TaskData taskData = new TaskData();
        Exception e;
        String str = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            if (this.errorCacheDataSet.isEmpty()) {
                TaskDataSet loadStoredData = loadStoredData(context);
                if (loadStoredData == null || loadStoredData.size() <= 0) {
                    C1923L.brief("no more store data.", new Object[0]);
                    taskData = null;
                } else {
                    taskData = loadStoredData.getFirst();
                    try {
                        String str2 = "getFirst data : %s";
                        Object[] objArr = new Object[1];
                        if (taskData != null) {
                            str = taskData.getDataId();
                        }
                        objArr[0] = str;
                        C1923L.brief(str2, objArr);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            C1923L.error(this, "Failed to getFirst data .Exception:%s", e);
                            this.lock.unlock();
                            C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                            return taskData;
                        } catch (Throwable th) {
                            this.lock.unlock();
                            C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        }
                    }
                }
                this.lock.unlock();
                C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            } else {
                C1923L.warn(this, "getFirst from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.errorCacheDataSet.size()));
                taskData = this.errorCacheDataSet.getFirst();
                this.lock.unlock();
                C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            }
        } catch (Exception e3) {
            Exception exception = e3;
            taskData = null;
            e = exception;
            C1923L.error(this, "Failed to getFirst data .Exception:%s", e);
            this.lock.unlock();
            C1923L.brief("getFirst elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            return taskData;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return taskData;
    }

    public TaskData getLast(Context context) {
        TaskData taskData = new TaskData();
        Exception e;
        String str = null;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            if (this.errorCacheDataSet.isEmpty()) {
                TaskDataSet loadStoredData = loadStoredData(context);
                if (loadStoredData == null || loadStoredData.size() <= 0) {
                    C1923L.brief("no more store data.", new Object[0]);
                    taskData = null;
                } else {
                    taskData = loadStoredData.getLast();
                    try {
                        String str2 = "getLast data : %s";
                        Object[] objArr = new Object[1];
                        if (taskData != null) {
                            str = taskData.getDataId();
                        }
                        objArr[0] = str;
                        C1923L.brief(str2, objArr);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            C1923L.error(this, "Failed to getLast data .Exception:%s", e);
                            this.lock.unlock();
                            C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                            return taskData;
                        } catch (Throwable th) {
                            this.lock.unlock();
                            C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        }
                    }
                }
                this.lock.unlock();
                C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            } else {
                C1923L.warn(this, "getLast from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.errorCacheDataSet.size()));
                taskData = this.errorCacheDataSet.getLast();
                this.lock.unlock();
                C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            }
        } catch (Exception e3) {
            Exception exception = e3;
            taskData = null;
            e = exception;
            C1923L.error(this, "Failed to getLast data .Exception:%s", e);
            this.lock.unlock();
            C1923L.brief("getLast elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            return taskData;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return taskData;
    }

    public TaskData getRandom(Context context) {
        TaskData taskData = new TaskData();
        Exception e;
        String str = null;
        this.lock.lock();
        try {
            if (this.errorCacheDataSet.isEmpty()) {
                TaskDataSet loadStoredData = loadStoredData(context);
                if (loadStoredData == null || loadStoredData.size() <= 0) {
                    C1923L.brief("no more store data.", new Object[0]);
                    taskData = null;
                } else {
                    taskData = loadStoredData.getRandom();
                    try {
                        String str2 = "getRandom data : %s";
                        Object[] objArr = new Object[1];
                        if (taskData != null) {
                            str = taskData.getDataId();
                        }
                        objArr[0] = str;
                        C1923L.brief(str2, objArr);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            C1923L.error(this, "Failed to getRandom data .Exception:%s", e);
                            return taskData;
                        } finally {
                            this.lock.unlock();
                        }
                    }
                }
                this.lock.unlock();
            } else {
                C1923L.warn(this, "getRandom from  memory cache. memory cache dataset size = %d", Integer.valueOf(this.errorCacheDataSet.size()));
                taskData = this.errorCacheDataSet.getRandom();
                this.lock.unlock();
            }
        } catch (Exception e3) {
            Exception exception = e3;
            taskData = null;
            e = exception;
            C1923L.error(this, "Failed to getRandom data .Exception:%s", e);
            return taskData;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return taskData;
    }

    public void remove(Context context, TaskData taskData) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            if (!this.errorCacheDataSet.isEmpty()) {
                C1923L.warn(this, "remove from  memory cache [%b]. memory cache dataset size = %d", Boolean.valueOf(this.errorCacheDataSet.remove(taskData)), Integer.valueOf(this.errorCacheDataSet.size()));
            }
            TaskDataSet loadStoredData = loadStoredData(context);
            if (loadStoredData == null || loadStoredData.size() <= 0) {
                C1923L.brief("no more store data.", new Object[0]);
            } else {
                boolean remove = loadStoredData.remove(taskData);
                String str = "remove data : %s [%b]";
                Object[] objArr = new Object[2];
                objArr[0] = taskData == null ? null : taskData.getDataId();
                objArr[1] = Boolean.valueOf(remove);
                C1923L.brief(str, objArr);
                saveStoredData(context, loadStoredData);
            }
            this.lock.unlock();
            C1923L.brief("remove elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        } catch (Exception e) {
            C1923L.error(this, "Failed to remove data .Exception:%s", e);
            this.lock.unlock();
            C1923L.brief("remove elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        } catch (Throwable th) {
            this.lock.unlock();
            C1923L.brief("remove elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
    }

    public boolean save(Context context, TaskData taskData) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            TaskDataSet loadStoredData = loadStoredData(context);
            if (loadStoredData == null) {
                loadStoredData = new TaskDataSet();
            }
            loadStoredData.saveOrUpdate(taskData);
            C1923L.brief("save data : %s", taskData.getDataId());
            saveStoredData(context, loadStoredData);
            if (!this.errorCacheDataSet.isEmpty()) {
                C1923L.warn(this, "save file successful,remove from  memory cache [%b]. memory cache dataset size = %d", Boolean.valueOf(this.errorCacheDataSet.remove(taskData)), Integer.valueOf(this.errorCacheDataSet.size()));
            }
            this.lock.unlock();
            C1923L.brief("save elapsed time :%d ms", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            return true;
        } catch (Exception e) {
            C1923L.error(this, "Failed to save data : %s Exception:%s", taskData.getDataId(), e);
            try {
                this.errorCacheDataSet.saveOrUpdate(taskData);
                C1923L.warn(this, "save file failure,save data : %s to memory cache. memory cache dataset size = %d", taskData.getDataId(), Integer.valueOf(this.errorCacheDataSet.size()));
            } catch (Exception e2) {
            }
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
        int i = 0;
        this.lock.lock();
        try {
            if (!this.errorCacheDataSet.isEmpty()) {
                int size = this.errorCacheDataSet.size();
                TaskDataSet loadStoredData = loadStoredData(context);
                if (loadStoredData != null) {
                    i = loadStoredData.size();
                }
                if (loadStoredData != null) {
                    while (true) {
                        TaskData removeFirst = this.errorCacheDataSet.removeFirst();
                        if (removeFirst == null) {
                            break;
                        }
                        loadStoredData.saveOrUpdate(removeFirst);
                    }
                } else {
                    loadStoredData = this.errorCacheDataSet;
                }
                saveStoredData(context, loadStoredData);
                this.errorCacheDataSet.clear();
                C1923L.warn(this, "flush memory cache to file . Before:memory cache dataset size = %d,file dataset size = %d；After:memory cache dataset size = %d,file dataset size = %d", Integer.valueOf(size), Integer.valueOf(i), Integer.valueOf(this.errorCacheDataSet.size()), Integer.valueOf(loadStoredData.size()));
                this.lock.unlock();
            }
        } catch (Exception e) {
            C1923L.error(this, "Failed to storePendingCommands .Exception:%s", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }
}
