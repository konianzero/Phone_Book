package phonebook.algorithm.sort;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

public class BobbleSort implements Sort {

    public void sort(PhoneDirectory directory) {
        sort(directory.getEntries());
    }

    private void sort(Entry[] a) {
        for (int out = a.length - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {

                if (greater(a[in], a[in + 1])) {
                    swap(a, in, in + 1);
                }
            }
        }
    }
}
