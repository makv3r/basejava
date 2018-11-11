import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    int size() {
        return size;
    }

    void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    void save(Resume r) {
        if (size == storage.length) { // Проверку на переполнение
            System.out.println("Stop It! Size will exceed limit.");
        }
        else if (search(r.uuid) != -1) { // Проверку на повторение
            System.out.println("Resume already exist.");
        }
        else {
            storage[size] = r;
            size++;
        }
    }

    void update(Resume r, String updateTo) {
        int i = search(r.uuid);
        if (i == -1) {
            System.out.println("Resume not found.");
        }
        else {
            storage[i].uuid = updateTo;
        }
    }

    int search(String uuid) { // Избавляет от дублирования в коде ArrayStorage
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    Resume get(String uuid) {
        int i = search(uuid);

        if (i == -1) {
            System.out.println("Resume not found.");
            return null;
        }
        else {
            return storage[i];
        }
    }

    void delete(String uuid) {
        int i = search(uuid);

        if (i == -1) {
            System.out.println("Resume not found.");
        }
        else {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
