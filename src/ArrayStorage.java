import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    public final static int MAX_SIZE = 10_000;
    Resume[] storage = new Resume[MAX_SIZE];
    private int size = 0;

    int size() {
        return size;
    }

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (size == MAX_SIZE) { // Проверку на переполнение
            System.out.println("Stop It! Size will exceed limit.");
        } else if (getIndex(r.getUuid()) != -1) { // Проверку на повторение
            System.out.println("Resume already exist.");
        } else {
            storage[size] = r;
            size++;
        }
    }

    void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i == -1) {
            System.out.println("Resume not found.");
        } else {
            storage[i] = r;
        }
    }

    void delete(String uuid) {
        int i = getIndex(uuid);

        if (i == -1) {
            System.out.println("Resume not found.");
        } else {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    Resume get(String uuid) {
        int i = getIndex(uuid);

        if (i == -1) {
            System.out.println("Resume not found.");
            return null;
        } else {
            return storage[i];
        }
    }

    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    private int getIndex(String uuid) { // Избавляет от дублирования в коде ArrayStorage
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
