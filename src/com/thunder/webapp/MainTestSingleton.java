package com.thunder.webapp;

import com.thunder.webapp.model.SectionType;

public class MainTestSingleton {
    private static MainTestSingleton instance;

    public static MainTestSingleton getInstance() {
        if (instance == null) {
            instance = new MainTestSingleton();
        }
        return instance;
    }

    private MainTestSingleton() {
    }

    public static void main(String[] args) {
        MainTestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}

