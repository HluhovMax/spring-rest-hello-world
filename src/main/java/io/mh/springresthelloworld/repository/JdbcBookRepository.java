package io.mh.springresthelloworld.repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mhlukhov on 9/12/2019
 */
public interface JdbcBookRepository<T, ID, O> {

    O count();

    O save(T book);

    O update(T book);

    O deleteById(ID id);

    List<T> findAll();

    default List<T> findByNameAndPrice(String name, BigDecimal price) {
        return Collections.emptyList();
    }

    Optional<T> findById(ID id);

    default String getNameById(ID id) {
        return "";
    }
}
