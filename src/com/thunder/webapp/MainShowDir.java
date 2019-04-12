package com.thunder.webapp;

import java.io.File;


public class MainShowDir {
    public static void main(String[] args) {

        File dir = new File(System.getProperty("user.dir"));

        if (dir.exists() && dir.isDirectory()) {
            showDir(dir, "");
        }

    }

    public static void showDirOld(File dir, int index) {
        File[] filesArray = dir.listFiles();

        if (filesArray != null) {
            for (File file : filesArray) {
                for (int i = 0; i < index; i++) {
                    System.out.println(".");
                }
                if (file.isDirectory()) {
                    System.out.println("[" + file.getName() + "]");
                    showDirOld(file, index + 1);
                } else if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        }
    }

    public static void showDir(File dir, String indent) {
        File[] filesArray = dir.listFiles();

        if (filesArray != null) {
            for (File file : filesArray) {
                if (file.isDirectory()) {
                    System.out.println(indent + "[" + file.getName() + "]");
                    showDir(file, indent + "\t");
                } else if (file.isFile()) {
                    System.out.println(indent + file.getName());
                }
            }
        }
    }
}
