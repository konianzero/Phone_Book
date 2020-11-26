package phonebook.view;

import phonebook.controller.Controller;

public class ConsoleView {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        controller.onStart();
    }

    public void print(String s) {
        System.out.println(s);
    }
}
