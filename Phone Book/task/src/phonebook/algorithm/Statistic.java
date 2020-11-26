package phonebook.algorithm;

import phonebook.algorithm.search.Search;
import phonebook.algorithm.sort.Sort;
import phonebook.model.PhoneDirectory;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

public class Statistic {
    protected Search search;
    protected Search other;
    protected Sort sort;

    protected long entriesFound;
    protected long allEntries;

    protected long startTime;
    private long sortTime;
    private long searchTime;

    private boolean isSorted = false;

    public Statistic() {
    }

    public Statistic addSearch(Search search) {
        this.search = search;
        return this;
    }

    public Statistic otherSearch(Search other) {
        this.other = other;
        return this;
    }

    public Statistic addSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public Statistic execute(PhoneDirectory directory, String[] names) {
        Search current;
        allEntries = names.length;

        if (sort(directory)) {
            current = other;
        } else {
            current = search;
        }

        startTime();
        entriesFound = current.execute(directory, names);
        searchTime = stopTime();
        return this;
    }

    private boolean sort(PhoneDirectory directory) {
        if (Optional.ofNullable(sort).isPresent()) {
            ExecutorService executor = Executors.newFixedThreadPool(4);
            startTime();

            Future<?> future =executor.submit(() -> sort.sort(directory));
            executor.shutdown();
            try {
                future.get(100_000, TimeUnit.MILLISECONDS);
                isSorted = true;
            } catch (TimeoutException e) {
                future.cancel(true);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            executor.shutdownNow();
            sortTime = stopTime();
        }
        return isSorted;
    }

    public void startTime() {
        startTime = currentTimeMillis();
    }

    public long stopTime() {
        return currentTimeMillis() - startTime;
    }

    public String getStatistic() {
        StringBuilder stat = new StringBuilder();
        stat.append(String.format("Found %d / %d entries. %s",
                entriesFound,
                allEntries,
                String.format("Time taken: %1$TM min. %1$TS sec. %1$TL ms.", sortTime + searchTime)));

        if (Objects.nonNull(sort)) {
            if (isSorted) {
                stat.append(String.format("\nSorting time: %1$TM min. %1$TS sec. %1$TL ms.", sortTime))
                    .append(String.format("\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.", searchTime));
            } else {
                stat.append(String.format("\nSorting time: %1$TM min. %1$TS sec. %1$TL ms. - STOPPED, moved to linear search", sortTime))
                    .append(String.format("\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.", searchTime));
            }
        }
        return stat.toString();
    }
}
