package phonebook.algorithm;

import phonebook.algorithm.search.Search;
import phonebook.algorithm.sort.Sort;
import phonebook.model.PhoneDirectory;

import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Statistic {
    private static final long SORT_TIMEOUT = 10000L;

    private Search baseSearch;
    private Search otherSearch;
    private Sort sort;

    private long entriesFound;
    private long allEntries;

    private long startTime;
    private long sortTime;
    private long searchTime;

    private boolean isSorted = false;

    public Statistic() {
    }

    public Statistic addSearch(Search search) {
        this.baseSearch = search;
        return this;
    }

    public Statistic otherSearch(Search other) {
        this.otherSearch = other;
        return this;
    }

    public Statistic addSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public Statistic execute(PhoneDirectory directory, String[] names) {
        if (sort(directory)) {
            search(baseSearch, directory, names);
        } else if (nonNull(otherSearch)) {
            search(otherSearch, directory, names);
        } else {
            search(baseSearch, directory, names);
        }
        return this;
    }

    private void search(Search current, PhoneDirectory directory, String[] names) {
        allEntries = names.length;

        startTime();
        entriesFound = current.search(directory, names);
        searchTime = stopTime();
    }

    private boolean sort(PhoneDirectory directory) {
        if (isNull(sort)) { return false; }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        startTime();

        Future<?> future = executorService.submit(() -> sort.sort(directory));

        try {
            future.get(SORT_TIMEOUT, TimeUnit.MILLISECONDS);
            isSorted = true;
        } catch (TimeoutException te) {
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
        }
        sortTime = stopTime();

        return isSorted;
    }

    private void startTime() {
        startTime = currentTimeMillis();
    }

    private long stopTime() {
        return currentTimeMillis() - startTime;
    }

    /**
     * Return full statistics as string.
     * @param s users string format for time statistic of sorting and searching
     * @return full statistics string.
     */
    public String getStatistic(String...s) {
        StringBuilder stat = new StringBuilder();
        stat.append(String.format("Found %d / %d entries. ", entriesFound, allEntries))
            .append(String.format("Time taken: %1$TM min. %1$TS sec. %1$TL ms.", sortTime + searchTime));

        if (nonNull(sort)) {
            if (s.length == 2) {
                stat.append(String.format(s[0], sortTime));
                stat.append(String.format(s[1], searchTime));
            } else {
                stat.append(String.format("\nSorting time: %1$TM min. %1$TS sec. %1$TL ms.", sortTime));
                if (nonNull(otherSearch) && !isSorted) {
                    stat.append(" - STOPPED, moved to linear search");
                }
                stat.append(String.format("\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.", searchTime));
            }
        }
        return stat.toString();
    }
}
