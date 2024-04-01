package ui;

public interface UI {
    public String eval(String input);
    public String help();
    public enum State {
        LOGGED_OUT,
        LOGGED_IN
    }
}
