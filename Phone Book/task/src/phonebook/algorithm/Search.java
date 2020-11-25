package phonebook.algorithm;

import phonebook.model.PhoneDirectory;

public interface Search {
    void execute(PhoneDirectory directory, String[] names);
}
