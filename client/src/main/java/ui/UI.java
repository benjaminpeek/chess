package ui;

public interface UI {
    String eval(String input);
    String help();
    enum State {
        LOGGED_OUT,
        LOGGED_IN
    }
}
