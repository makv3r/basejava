package com.thunder.webapp;

import com.thunder.webapp.model.Resume;
import com.thunder.webapp.storage.SortedArrayStorage;
import com.thunder.webapp.storage.Storage;

public class MainTestSortedArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        /**  Моя проверка */
        for (int i = 10_000; i > 0; i--) {
            Resume r = new Resume();
            r.setUuid("uuid" + i);
            ARRAY_STORAGE.save(r);
        }

        for (int i = 10_000; i > 0; i--) {
            Resume r = new Resume();
            r.setUuid("uuid" + i);
            if (i % 2 == 0) ARRAY_STORAGE.delete(r.getUuid());
        }

        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        /********************/

        Resume r1 = new Resume();
        r1.setUuid("uuid3");
        Resume r2 = new Resume();
        r2.setUuid("uuid34");
        Resume r3 = new Resume();
        r3.setUuid("uuid265");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());

        Resume r4 = new Resume();
        r4.setUuid("uuid3");
        ARRAY_STORAGE.update(r4);
        r4.setUuid("NEW-uiud3"); // ТАДА ХАК!!
        // тут проблема, значение в ARRAY_STORAGE тоже поменялось,
        // но не было выполнена сортировка после, как быть не знаю)

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r2.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}

/**
 * OUTPUT:
 * Get All
 * uuid1
 * ******
 * uuid9995
 * uuid9997
 * uuid9999
 * Size: 5000
 * Size: 0
 * <p>
 * Get All
 * uuid265
 * uuid3
 * uuid34
 * Size: 3
 * Get r1: uuid34
 * Size: 3
 * Resume dummy not found.
 * Get dummy: null
 * <p>
 * Get All
 * uuid265
 * NEW-uiud3
 * uuid34
 * Resume uuid3 not found.
 * <p>
 * Get All
 * uuid265
 * NEW-uiud3
 * uuid34
 * <p>
 * Get All
 * Size: 0
 */