import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < this.size(); i++) {
            this.storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < this.size(); i++) {
            if (this.storage[i].uuid.equals(r.uuid)) return;
        }
        this.storage[this.size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (this.storage[i].uuid.equals(uuid)) {
                return this.storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (this.storage[i].uuid.equals(uuid)) {
                for (int j = i; j < this.size(); j++) {
                    this.storage[j] = this.storage[j + 1];
                }
                break;
            }
        }
    }

    //save, get, delete, size, clear, getAll

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] tmp = new Resume[this.size()];
        for (int i = 0; i < this.size(); i++) {
            tmp[i] = this.storage[i];
        }
        return tmp;
        //return Arrays.copyOfRange(storage, 0, this.size());
    }

    int size() {
        for (int i = 0; i < this.storage.length; i++) {
            if (this.storage[i] == null) return i;
        }
        return 10000;
    }
}
