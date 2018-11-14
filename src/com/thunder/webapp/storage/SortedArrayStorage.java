package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.Arrays;

/**
 * SortedArray based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Stop It! Size will exceed limit.");
        } else if (getIndex(r.getUuid()) >= 0) { // !!!
            // binarySearch ->
            // Note that this guarantees that the return value will be >= 0 if and only if the key is found.
            System.out.println("Resume " + r.getUuid() + " already exist.");
        } else {
            storage[size] = r;
            size++;

            //Arrays.sort(storage,0,size);

            for (int i = size - 1; i > 0; i--) { // Sorting : O(size) worst case, O(1) best case
                if (storage[i].compareTo(storage[i - 1]) < 0) {
                    Resume tmp1 = storage[i];
                    storage[i] = storage[i - 1];
                    storage[i - 1] = tmp1;
                } else break;
            }
        }
    }

    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i < 0) {
            System.out.println("Resume " + r.getUuid() + " not found.");
        } else {
            storage[i] = r; // ссылка обновилась, сортировка не требуется
        }
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);

        if (i < 0) {
            System.out.println("Resume " + uuid + " not found.");
        } else { // O(size) - worst case, O(1) best case
            // Предполагается что массив отсортирован,
            // поэтому делается только сдвиг влево всех элементов после удаленного элемента.
            for (int j = i; j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
