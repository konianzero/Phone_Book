package phonebook.algorithm.hash;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @see <a href="https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html">CS Princeton - LinearProbingHashST.java</a
 */
public class HashTable<K, V> implements SymbolTable {
    private int size;
    private int n;
    private K[] keys;
    private V[] values;

    private void init(int size) {
        this.size = size;
        keys = (K[]) new Object[size];
        values = (V[]) new Object[size];

    }

    @Override
    public long search(PhoneDirectory directory, String[] names) {

        final Function<String, Optional<V>> search = this::get;

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
        return put((K) e.getName(), (V) e.getPhone());
    }

    private boolean put(K key, V value) {
        if (n >= size) rehash();

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % size) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return true;
            }
        }
        keys[i] = key;
        values[i] = value;
        n++;
        return true;
    }


    private Optional<V> get(String s) {
        return Optional.ofNullable(get((K) s));
    }

    private V get(K key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % size) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }

    private void rehash() {
        K[] oldKey = keys;
        V[] oldValue = values;

        size *= 2;
        keys = (K[]) new Object[size];
        values = (V[]) new Object[size];

        IntStream.range(0, size)
                 .forEach(i -> put(oldKey[i], oldValue[i]));
    }
}
