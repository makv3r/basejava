package com.thunder.webapp;

import java.io.File;


public class MainShowDir {
    public static void main(String[] args) {

        File dir = new File(System.getProperty("user.dir"));

        if (dir.exists() && dir.isDirectory()) {
            showDir(dir, 0);
        }

    }

    public static void showDir(File dir, int level) {
        File[] filesArray = dir.listFiles();

        if (filesArray != null) {
            for (File file : filesArray) {
                for (int i = 0; i < level; i++) {
                    System.out.print(".");
                }
                if (file.isDirectory()) {
                    System.out.println("[" + file.getName() + "]");
                    showDir(file, level + 1);
                } else if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
