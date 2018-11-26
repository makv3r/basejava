package com.thunder.webapp;

import com.thunder.webapp.model.Resume;
import com.thunder.webapp.storage.ListStorage;
import com.thunder.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainArray {
    //private static final Storage STORAGE = new ArrayStorage();
    //private static final Storage STORAGE = new SortedArrayStorage();
    private static final Storage STORAGE = new ListStorage();
    //private static final Storage STORAGE = new MapUuidStorage();
    //private static final Storage STORAGE = new MapResumeStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | update uuid |  delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid);
                    STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid);
                    STORAGE.update(r);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(uuid));
                    break;
                case "clear":
                    STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        System.out.println("----------------------------");
        if (STORAGE.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : STORAGE.getAllSorted()) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}