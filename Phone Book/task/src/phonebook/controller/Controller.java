package phonebook.controller;

import phonebook.algorithm.LinearSearch;
import phonebook.algorithm.Search;
import phonebook.model.PhoneDirectory;
import phonebook.view.ConsoleView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Controller {
    private ConsoleView view;
    private PhoneDirectory directory;
    private String[] names;

    public Controller(String file) {
        try {
//            names = Pattern.compile("\\R").split(Files.readString(Path.of(file)));
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
        LinearSearch search = new LinearSearch();
        if (directory.notEmpty() && Objects.nonNull(names)) {
            search.execute(directory, names);
        }
        view.print(search.statistic());
    }
}
