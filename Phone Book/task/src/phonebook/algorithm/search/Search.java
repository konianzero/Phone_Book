package phonebook.algorithm.search;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public interface Search {
    long execute(PhoneDirectory directory, String[] names);

    default long findEntries(Function<String, Optional<Entry>> search, String[] names) {
        return Arrays.stream(names)
                     .map(search)
                     .filter(Optional::isPresent)
                     .count();
    }
}
