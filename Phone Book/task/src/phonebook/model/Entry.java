package phonebook.model;

public class Entry {
    private final String phone;
    private final String name;

    public Entry(final String line) {
        String[] tokens = line.split(" ", 2);
        phone = tokens[0];
        name = tokens[1];
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
