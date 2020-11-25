package phonebook.algorithm;

import phonebook.model.Entry;
import phonebook.model.PhoneDirectory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class LinearSearch extends Statistic implements Search {

    @Override
    public void execute(PhoneDirectory directory, String[] names) {
        final Function<String, Optional<Entry>> search = name -> {
            for (Entry e: directory) {
                if (Objects.equals(name, e.getName())) {
                    return Optional.of(e);
                }
            }
            return Optional.empty();
        };

        allEntries = names.length;

        startTime();
        entriesFound = Arrays.stream(names)
                             .map(search)
                             .filter(Optional::isPresent)
                             .count();
        stopTime();
    }

    public String statistic() {
        return getStatistic();
    }
}
