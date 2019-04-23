package com.thunder.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        String resource1 = "resource1";
        String resource2 = "resource2";

        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread1: locked resource1");

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                synchronized (resource2) {
                    System.out.println("Thread1: locked resource2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread2: locked resource2");

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                synchronized (resource1) {
                    System.out.println("Thread2: locked resource1");
                }
            }

        });

        thread1.start();
        thread2.start();
    }
}
