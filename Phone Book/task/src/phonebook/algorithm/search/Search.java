package phonebook.algorithm.search;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public interface Search {
    long search(PhoneDirectory directory, String[] names);

    // Generic Type for HashTable class
    default <T> long findEntries(Function<String, Optional<T>> search, String[] names) {
        return Arrays.stream(names)
                     .map(search)
                     .filter(Optional::isPresent)
                     .count();
    }
}
