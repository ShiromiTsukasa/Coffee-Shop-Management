package com.UserAsClient.Controller;

public class Lock {
    private final static Lock INSTANCE = new Lock();
    private boolean isLocked = false;

    private Lock() {
    }

    public static Lock getInstance() {
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
