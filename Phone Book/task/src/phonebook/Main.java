package phonebook;

import phonebook.controller.Controller;
import phonebook.model.PhoneDirectory;
import phonebook.view.ConsoleView;

public class Main {
    private static final String PATH_TO = "/home/nik/RawData/Hyperskill/PhoneBook/";
    private static final String DIRECTORY = PATH_TO + "directory.txt";
    private static final String NAMES = PATH_TO + "find.txt";

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        Controller controller = new Controller(NAMES);
        PhoneDirectory directory = new PhoneDirectory(DIRECTORY);

        view.setController(controller);
        controller.setView(view);
        controller.setModel(directory);

        view.start();
    }
}
