package io.mh.springresthelloworld.exception;

/**
 * @author mhlukhov on 9/12/2019
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book id not found : " + id);
    }
}
