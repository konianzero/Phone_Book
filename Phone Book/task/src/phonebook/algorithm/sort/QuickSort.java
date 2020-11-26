package phonebook.algorithm.sort;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @see <a href="https://algs4.cs.princeton.edu/23quicksort/Quick.java">CS Princeton - Quick.java</a>
 */
public class QuickSort implements Sort {
    @Override
    public void sort(PhoneDirectory directory) {
        Entry[] entries = directory.getEntries();

        Collections.shuffle(Arrays.asList(entries));
        sort(entries, 0, entries.length - 1);

        // OR
        // Arrays.sort(directory.getEntries(), Comparator.comparing(Entry::getName));
    }

    private void sort(Entry[] a, int lo, int hi) {
        if (hi <= lo) return;
        int pivotIndex = partition(a, lo, hi);
        sort(a, lo, pivotIndex - 1);
        sort(a, pivotIndex + 1, hi);
    }

    private int partition(Entry[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Entry pivot = a[lo];

        while (true) {
            // Scan i from left to right so long as (a[i] < a[lo])
            while (less(a[++i], pivot)) {
                if (i == hi) { break; }
            }
            // Scan j from right to left so long as (a[j] > a[lo])
            while (less(pivot, a[--j])) {
                if (j == lo) break;
            }
            // check if pointers cross
            if (i >= j) break;
            // Exchange a[i] with a[j]
            swap(a, i, j);
        }
        // When pointers cross, exchange a[lo] with a[j]
        swap(a, lo, j);

        return j; // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
    }
}
