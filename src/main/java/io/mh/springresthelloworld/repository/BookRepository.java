package io.mh.springresthelloworld.repository;

import io.mh.springresthelloworld.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mhlukhov on 9/12/2019
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
