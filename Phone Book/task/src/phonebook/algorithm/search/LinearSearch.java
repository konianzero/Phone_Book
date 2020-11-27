package phonebook.algorithm.search;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class LinearSearch implements Search {

    @Override
    public long search(PhoneDirectory directory, String[] names) {
        final Function<String, Optional<Long>> search = name -> {
            for (Entry e: directory) {
                if (Objects.equals(name, e.getName())) {
                    return Optional.of(e.getPhone());
                }
            }
            return Optional.empty();
        };

        return findEntries(search, names);
    }
}
