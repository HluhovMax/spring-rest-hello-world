package io.mh.springresthelloworld.exception.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author mhlukhov on 9/12/2019
 */
public class AuthorValidator implements ConstraintValidator<Author, String> {

    List<String> authors = Arrays.asList("Santideva", "Marie Kondo", "Martin Fowler", "mkyong");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return authors.contains(s);
    }
}
