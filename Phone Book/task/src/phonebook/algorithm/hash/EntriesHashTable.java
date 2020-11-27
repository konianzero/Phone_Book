package phonebook.algorithm.hash;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.isNull;

public class EntriesHashTable implements SymbolTable {
    private int size;
    private TableEntry[] table;

    private static class TableEntry {
        private final int key;
        private final Long value;

        public TableEntry(int key, Long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Long getValue() {
            return value;
        }
    }

    private void init(int size) {
        this.size = size;
        table = new TableEntry[size];

    }

    @Override
    public long search(PhoneDirectory directory, String[] names) {

        final Function<String, Optional<Long>> search = this::get;

        return findEntries(search, names);
    }

    @Override
    public void sort(PhoneDirectory directory) {
        init(directory.size() * 2);

        for (Entry e: directory) {
            put(e);
        }
    }

    private boolean put(Entry e) {
        return put(getPositiveHash(e.getName()), e.getPhone());
    }

    private boolean put(int key, Long value) {
        int idx = findKey(key);

        if (idx == -1) {
            rehash();
            idx = findKey(key);
        }

        table[idx] = new TableEntry(key, value);
        return true;
    }

    private Optional<Long> get(String key) {
        return Optional.ofNullable(get(getPositiveHash(key)));
    }

    private Long get(int key) {
        int idx = findKey(key);

        if (idx == -1 || table[idx] == null) {
            return null;
        }

        return table[idx].getValue();
    }

    private int findKey(int key) {
        int hash = key % size;

        while (!(isNull(table[hash]) || table[hash].getKey() == key)) {
            hash = (hash + 1) % size;

            if (hash ==  key % size) {
                return -1;
            }
        }

        return hash;
    }

    private int getPositiveHash(String name) {
        return name.hashCode() & 0x7fffffff;
    }

    private void rehash() {
        TableEntry[] old = table;

        size *= 2;
        table = new TableEntry[size];

        Arrays.stream(old).forEach(e -> put(e.getKey(), e.getValue()));
    }
}
