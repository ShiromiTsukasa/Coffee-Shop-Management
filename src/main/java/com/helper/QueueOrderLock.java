package com.helper;

public class QueueOrderLock {
    private final static QueueOrderLock INSTANCE = new QueueOrderLock();
    private boolean isLocked = false;

    private QueueOrderLock() {
    }

    public static QueueOrderLock getInstance() {
        return INSTANCE;
    }

    public boolean acquire() {
        if (isLocked) {
            return false;
        }
        isLocked = true;
        return true;
    }

    public void release() {
        isLocked = false;
    }
}
