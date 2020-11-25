package phonebook.view;

import phonebook.controller.Controller;

public class ConsoleView {
    private static final String START = "Start searching...";

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println(START);
        controller.onStart();
    }

    public void print(String s) {
        System.out.println(s);
    }
}
