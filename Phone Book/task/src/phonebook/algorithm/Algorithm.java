package phonebook.algorithm;

import phonebook.model.PhoneDirectory;

public interface Algorithm {
    long execute(PhoneDirectory directory, String[] names);
}
