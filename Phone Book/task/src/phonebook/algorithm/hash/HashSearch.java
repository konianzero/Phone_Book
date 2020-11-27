package phonebook.algorithm.hash;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * {@link java.util.Hashtable}
 */
public class HashSearch implements SymbolTable {
    private Map<String, Long> table = new Hashtable<>();

    @Override
    public long search(PhoneDirectory directory, String[] names) {
        final Function<String, Optional<Long>> search = this::get;

        return findEntries(search, names);
    }

    @Override
    public void sort(PhoneDirectory directory) {
        for (Entry e: directory) {
            table.put(e.getName(), e.getPhone());
        }
    }

    private Optional<Long> get(String name) {
        return Optional.ofNullable(table.get(name));
    }
}
