package exceptions;

/**
 * Indicates the username is already taken
 */
public class AlreadyTakenException extends Exception {
    public AlreadyTakenException(String message) {super(message);}
}
