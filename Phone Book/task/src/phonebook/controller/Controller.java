package phonebook.controller;

import phonebook.algorithm.search.BinarySearch;
import phonebook.algorithm.search.JumpSearch;
import phonebook.algorithm.search.LinearSearch;
import phonebook.algorithm.Statistic;
import phonebook.algorithm.sort.BobbleSort;
import phonebook.algorithm.sort.QuickSort;
import phonebook.model.PhoneDirectory;
import phonebook.view.ConsoleView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Controller {
    private static final String START = "Start searching (%s)...";

    private ConsoleView view;
    private PhoneDirectory directory;
    private String[] names;

    public Controller(String file) {
        try {
            names = Files.readAllLines(Path.of(file))
                         .toArray(String[]::new);
        } catch (IOException ioe) {
            System.err.printf("%s - %s%n", ioe.getClass().getSimpleName(), ioe.getMessage());
        }
    }

    public void setView(ConsoleView view) {
        this.view = view;
    }

    public void setModel(PhoneDirectory directory) {
        this.directory = directory;
    }

    public void onStart() {
        if (directory.isEmpty() && Objects.isNull(names)) { return; }

        linearSearch();
        jumpSearch();
        binarySearch();
    }

    private void linearSearch() {
        view.print(String.format(START, "linear search"));
        view.print(new Statistic()
                .addSearch(new LinearSearch())
                .execute(directory, names)
                .getStatistic()
        );
    }

    private void jumpSearch() {
        view.print("\n" + String.format(START, "bubble sort + jump search"));
        view.print(new Statistic()
                .addSort(new BobbleSort())
                .addSearch(new LinearSearch())
                .otherSearch(new JumpSearch())
                .execute(directory, names)
                .getStatistic()
        );
    }

    private void binarySearch() {
        view.print("\n" + String.format(START, "quick sort + binary search"));
        view.print(new Statistic()
                .addSort(new QuickSort())
                .addSearch(new LinearSearch())
                .otherSearch(new BinarySearch())
                .execute(directory, names)
                .getStatistic()
        );
    }
}
