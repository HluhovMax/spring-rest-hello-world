package io.mh.springresthelloworld.exception;

import java.util.Set;

/**
 * @author mhlukhov on 9/12/2019
 */
public class BookUnSupportedFieldPatchException extends RuntimeException {

    public BookUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow");
    }
}
