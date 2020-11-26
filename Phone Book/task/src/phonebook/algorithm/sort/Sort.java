package phonebook.algorithm.sort;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

public interface Sort {
    void sort(PhoneDirectory directory);

    default  boolean less(Entry v, Entry w) {
        return v.compareTo(w) < 0;
    }

    default boolean greater(Entry v, Entry w) {
        return v.compareTo(w) > 0;
    }

    default void swap(Entry[] a, int i, int j) {
        Entry swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
