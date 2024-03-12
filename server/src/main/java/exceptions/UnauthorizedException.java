package exceptions;

/**
 * Indicates the login is unauthorized
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {super(message);}
}
