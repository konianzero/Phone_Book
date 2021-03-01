package phonebook.controller;

import phonebook.algorithm.hash.EntriesHashTable;
import phonebook.algorithm.hash.HashSearch;
import phonebook.algorithm.hash.HashTable;
import phonebook.algorithm.hash.SymbolTable;
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

import static java.util.Objects.isNull;

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
        if (directory.isEmpty() && isNull(names)) { return; }

        linearSearch();
        jumpSearch();
        binarySearch();
        hashTableSearch();
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
                        .addSearch(new JumpSearch())
                        .otherSearch(new LinearSearch())
                        .execute(directory, names)
                        .getStatistic()
        );
    }

    private void binarySearch() {
        view.print("\n" + String.format(START, "quick sort + binary search"));
        view.print(new Statistic()
                        .addSort(new QuickSort())
                        .addSearch(new BinarySearch())
                        .execute(directory, names)
                        .getStatistic()
        );
    }

    private void hashTableSearch() {
        view.print("\n" + String.format(START, "hash table"));
        SymbolTable entriesHashTable = new EntriesHashTable();
        view.print(new Statistic()
                        .addSort(entriesHashTable)
                        .addSearch(entriesHashTable)
                        .execute(directory, names)
                        .getStatistic("\nCreating time: %1$TM min. %1$TS sec. %1$TL ms.",
                                          "\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.")
        );

        view.print("\n" + String.format(START, "hash table without TableEntry class"));
        SymbolTable hashTable = new HashTable<String, Long>();
        view.print(new Statistic()
                        .addSort(hashTable)
                        .addSearch(hashTable)
                        .execute(directory, names)
                        .getStatistic("\nCreating time: %1$TM min. %1$TS sec. %1$TL ms.",
                                          "\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.")
        );

        view.print("\n" + String.format(START, "hash table with java.util.Hashtable"));
        SymbolTable hashSearch = new HashSearch();
        view.print(new Statistic()
                        .addSort(hashSearch)
                        .addSearch(hashSearch)
                        .execute(directory, names)
                        .getStatistic("\nCreating time: %1$TM min. %1$TS sec. %1$TL ms.",
                                          "\nSearching time: %1$TM min. %1$TS sec. %1$TL ms.")
        );
    }
}
