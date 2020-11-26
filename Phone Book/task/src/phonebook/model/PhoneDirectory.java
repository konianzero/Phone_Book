package phonebook.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PhoneDirectory implements Iterable<Entry>{
    private Entry[] entries;

    public PhoneDirectory(String file) {
        try {
            entries = Files.readAllLines(Path.of(file))
                           .stream()
                           .map(Entry::new)
                           .toArray(Entry[]::new);
        } catch (IOException ioe) {
            System.err.printf("%s - %s%n", ioe.getClass().getSimpleName(), ioe.getMessage());
        }
    }

    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<>() {
            private int i = 0;
            @Override
            public boolean hasNext()  { return i < entries.length; }
            @Override
            public void remove()      { throw new UnsupportedOperationException();  }
            @Override
            public Entry next() {
                if (!hasNext())       { throw new NoSuchElementException();         }
                Entry e = entries[i];
                i++;
                return e;
            }
        };
    }

    public boolean isEmpty() {
        return Objects.isNull(entries);
    }

    public int size() {
        return entries.length;
    }

    public Entry getEntry(int i) {
        return entries[i];
    }

    public void setEntry(int i, Entry e) {
        entries[i] = e;
    }
}
