package com.helper;

public class CompletedOrderLock {
    private final static CompletedOrderLock INSTANCE = new CompletedOrderLock();
    private boolean isLocked = false;

    private CompletedOrderLock() {
    }

    public static CompletedOrderLock getInstance() {
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
