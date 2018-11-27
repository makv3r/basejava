package com.thunder.webapp;

import com.thunder.webapp.model.Resume;
import com.thunder.webapp.storage.MapResumeStorage;
import com.thunder.webapp.storage.Storage;


public class MainTestStorage {
    //private static final Storage STORAGE = new ArrayStorage();
    //private static final Storage STORAGE = new SortedArrayStorage();
    //private static final Storage STORAGE = new ListStorage();
    //private static final Storage STORAGE = new MapUuidStorage();
    private static final Storage STORAGE = new MapResumeStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("uuid1", "Гарри Поттер");
        Resume r2 = new Resume("uuid2", "Гермиона Грейнджер");
        Resume r3 = new Resume("uuid3", "Рон Уизли");
        Resume r4 = new Resume("uuid3", "Том Реддл");

/*
        Resume r1 = new Resume("Гарри Поттер");
        Resume r2 = new Resume("Гермиона Грейнджер");
        Resume r3 = new Resume("Рон Уизли");
        Resume r4 = new Resume("Том Реддл");
*/
        try {
            for (int i = 0; i <= 10_000; i++) {
                STORAGE.save(new Resume("uuid" + i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Size: " + STORAGE.size());
        STORAGE.clear();
        System.out.println("Size: " + STORAGE.size());


        STORAGE.save(r1);
        STORAGE.save(r2);
        STORAGE.save(r3);

        try {
            STORAGE.update(r4); // Warning
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Get r3: " + STORAGE.get(r3.getUuid()));
        System.out.println("Size: " + STORAGE.size());

        try {
            System.out.println("Get dummy: " + STORAGE.get("dummy"));
        } catch (Exception e) {
            System.out.println(e);
        }


        printAll();
        try {
            STORAGE.delete(r1.getUuid());
        } catch (Exception e) {
            System.out.println(e);
        }
        printAll();

        try {
            STORAGE.delete("uuid5");
        } catch (Exception e) {
            System.out.println(e);
        }

        STORAGE.clear();
        printAll();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
