package phonebook.algorithm.sort;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

public class BobbleSort implements Sort {

    public void sort(PhoneDirectory directory) {
        for (int out = directory.size() - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {

                if (directory.getEntry(in).getName().compareTo(directory.getEntry(in + 1).getName()) > 0) {
                    swap(directory, in, in + 1);
                }
            }
        }
    }

    private void swap(PhoneDirectory directory, int prev, int next) {
        Entry temp = directory.getEntry(prev);
        directory.setEntry(prev, directory.getEntry(next));
        directory.setEntry(next, temp);
    }
}
