package phonebook.algorithm.search;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class BinarySearch implements Search {
    @Override
    public long execute(PhoneDirectory directory, String[] names) {
//        final Function<String, Optional<Entry>> search = name -> {
//            final int index = Arrays.binarySearch(directory.getEntries(),
//                                                  new Entry("0 " + name),
//                                                  Comparator.comparing(Entry::getName));
//
//            return (index >= 0) ? Optional.of(directory.getEntry(index)) : Optional.empty();
//        };

        final Function<String, Optional<Entry>> search = name -> binarySearch(directory, name);

        return findEntries(search, names);
    }

    private Optional<Entry> binarySearch(PhoneDirectory directory, String name) {
        int left = 0;
        int right = directory.size();

        while (left <= right) {
            int mid = left + (right - left) / 2; // the index of the middle element

            int cmp = name.compareTo(directory.getEntry(mid).getName());
            if      (cmp < 0) { right = mid - 1; }                             // go left
            else if (cmp > 0) { left = mid + 1;  }                             // go right
            else              { return Optional.of(directory.getEntry(mid)); } // if (cmp == 0) the element is found
        }
        return Optional.empty(); // the element is not found
    }
}
