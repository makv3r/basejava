package com.thunder.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        String resource1 = "resource1";
        String resource2 = "resource2";

        deadLock(resource1, resource2);
        deadLock(resource2, resource1);
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }
}
