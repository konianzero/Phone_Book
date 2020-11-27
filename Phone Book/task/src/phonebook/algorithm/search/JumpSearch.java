package phonebook.algorithm.search;

import phonebook.model.PhoneDirectory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class JumpSearch implements Search {
    @Override
    public long search(PhoneDirectory directory, String[] names) {
        final Function<String, Optional<Long>> search = name -> jumpSearch(directory, name);

        return findEntries(search, names);
    }

    private Optional<Long> jumpSearch(PhoneDirectory directory, String name) {
        int curRight = 0; // right border of the current block


//         if (directory.isEmpty()) {                                         // if array is empty, the element is not found
//            return Optional.empty();
//        }

        if (Objects.equals(directory.getEntry(curRight).getName(), name)) {   // check the first element
            return Optional.of(directory.getEntry(curRight).getPhone());
        }

        int jumpLength = (int) Math.sqrt(directory.size());                   // calculating the jump length over array elements
        int prevRight = 0;                                                    // right border of the previous block

        while (curRight < directory.size() - 1) {                             // finding a block where the element may be present
            curRight = Math.min(directory.size() - 1, curRight + jumpLength); // calculating the right border of the following block

            if (directory.getEntry(curRight).getName().compareTo(name) >= 0) {// found a block that may contain the target element
                break;
            }

            prevRight = curRight;                                             // update the previous right block border
        }

        // if the last block is reached and it cannot contain the target value => not found
        if (curRight == directory.size() - 1 && name.compareTo(directory.getEntry(curRight).getName()) > 0) {
            return Optional.empty();
        }

        // Doing linear search in the found block
        return backwardSearch(directory, name, prevRight, curRight);
    }

    private Optional<Long> backwardSearch(PhoneDirectory directory, String name, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {

            int cmp = directory.getEntry(i).getName().compareTo(name);
            if (cmp == 0)  {
                return Optional.of(directory.getEntry(i).getPhone());
            } else if (cmp < 0) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
