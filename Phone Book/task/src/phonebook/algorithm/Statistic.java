package phonebook.algorithm;

import phonebook.algorithm.search.Search;
import phonebook.algorithm.sort.Sort;
import phonebook.model.PhoneDirectory;

import java.util.Objects;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

public class Statistic {
    private Search search;
    private Search other;
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
        if (Objects.nonNull(sort)) {
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            startTime();

            Future<?> future = executorService.submit(() -> sort.sort(directory));

            try {
                future.get(100_000, TimeUnit.MILLISECONDS);
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
        }
        return isSorted;
    }

    private void startTime() {
        startTime = currentTimeMillis();
    }

    private long stopTime() {
        return currentTimeMillis() - startTime;
    }

    public String getStatistic() {
        StringBuilder stat = new StringBuilder();
        stat.append(String.format("Found %d / %d entries. ", entriesFound, allEntries))
            .append(String.format("Time taken: %1$TM min. %1$TS sec. %1$TL ms.", sortTime + searchTime));

        if (Objects.nonNull(sort)) {
            stat.append(String.format("\nSorting time: %1$TM min. %1$TS sec. %1$TL ms.", sortTime));
            if (!isSorted) {
                stat.append(" - STOPPED, moved to linear search");
            }
            stat.append(String.format("\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.", searchTime));
        }
        return stat.toString();
    }
}
