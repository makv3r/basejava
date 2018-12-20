package com.thunder.webapp;

import java.io.File;


public class MainShowDir {
    public static void main(String[] args) {

        File dir = new File(System.getProperty("user.dir"));

        if (dir.exists() && dir.isDirectory()) {
            showDir(dir);
        }

    }

    public static void showDir(File dir) {
        File[] filesArray = dir.listFiles();

        if (filesArray != null) {
            for (File file : filesArray) {
                if (file.isDirectory()) {
                    System.out.println("[" + file.getName() + "]");
                    showDir(file);
                } else if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
