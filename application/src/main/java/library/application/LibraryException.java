package library.application;

/**
 * Thrown when the library refuses an operation. The message says why, in the words of Chapter 0.
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}
