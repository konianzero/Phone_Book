package phonebook.algorithm.search;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class JumpSearch implements Search {
    @Override
    public long execute(PhoneDirectory directory, String[] names) {
        final Function<String, Optional<Entry>> search = name -> jumpSearch(directory, name);

        return findEntries(search, names);
    }

    public Optional<Entry> jumpSearch(PhoneDirectory directory, String name) {
        int currentRight = 0; // right border of the current block

        // If array is empty, the element is not found
//        if (directory.isEmpty()) {
//            return Optional.empty();
//        }

        // Check the first element
        if (Objects.equals(directory.getEntry(currentRight).getName(), name)) {
            return Optional.of(directory.getEntry(currentRight));
        }

        // Calculating the jump length over array elements
        int jumpLength = (int) Math.sqrt(directory.size());
        int prevRight = 0; // right border of the previous block

        // Finding a block where the element may be present
        while (currentRight < directory.size() - 1) {
            // Calculating the right border of the following block
            currentRight = Math.min(directory.size() - 1, currentRight + jumpLength);

            if (directory.getEntry(currentRight).getName().compareTo(name) >= 0) {
                break; // Found a block that may contain the target element
            }

            prevRight = currentRight; // update the previous right block border
        }

        // If the last block is reached and it cannot contain the target value => not found
        if (currentRight == directory.size() - 1 && name.compareTo(directory.getEntry(currentRight).getName()) > 0) {
            return Optional.empty();
        }

        // Doing linear search in the found block
        return backwardSearch(directory, name, prevRight, currentRight);
    }

    public Optional<Entry> backwardSearch(PhoneDirectory directory, String name, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            int cmp = directory.getEntry(i).getName().compareTo(name);
            if (cmp == 0)  {
                return Optional.of(directory.getEntry(i));
            } else if (cmp < 0) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
