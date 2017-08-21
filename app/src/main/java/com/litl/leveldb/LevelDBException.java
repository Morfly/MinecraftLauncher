package com.litl.leveldb;

public class LevelDBException extends RuntimeException {
    private static final long serialVersionUID = 2903013251786326801L;

    public LevelDBException(String str) {
        super(str);
    }

    public LevelDBException(String str, Throwable th) {
        super(str, th);
    }
}
