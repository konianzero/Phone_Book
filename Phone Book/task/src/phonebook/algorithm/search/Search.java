package phonebook.algorithm.search;

import phonebook.model.PhoneDirectory;

public interface Search {
    long execute(PhoneDirectory directory, String[] names);
}
