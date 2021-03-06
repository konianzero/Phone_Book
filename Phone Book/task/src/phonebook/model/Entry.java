package phonebook.model;

public class Entry implements Comparable<Entry> {
    private final Long phone;
    private final String name;

    public Entry(final String line) {
        String[] tokens = line.split(" ", 2);
        phone = Long.valueOf(tokens[0]);
        name = tokens[1];
    }

    public Entry(Entry e) {
        phone = e.getPhone();
        name = e.getName();
    }

    public String getName() {
        return name;
    }

    public Long getPhone() {
        return phone;
    }

    /**
     * Compare by name only.
     * @param other the other Entry
     * @return the value 0 if the other name is equal to this name;
     *         a value less than 0 if this name is lexicographically less than the other name;
     *         and a value greater than 0 if this name is lexicographically greater than the other name.
     */
    @Override
    public int compareTo(Entry other) {
        return name.compareTo(other.getName());
    }
}
