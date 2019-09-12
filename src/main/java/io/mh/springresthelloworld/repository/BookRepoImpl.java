package io.mh.springresthelloworld.repository;

import io.mh.springresthelloworld.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author mhlukhov on 9/12/2019
 */
@Repository
public class BookRepoImpl implements JdbcBookRepository<Book, Long, Book> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Book count() {
        return null;
    }

    @Override
    public Book save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO custom_books (name, author, price) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getPrice()
        );

        return jdbcTemplate.queryForObject("SELECT * FROM custom_books WHERE name LIKE ?", new Object[]{"%" + book.getName() + "%"},
                (rs, rowNum) -> new Book(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getBigDecimal("price")
                ));
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book deleteById(Long aLong) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM custom_books",
                (rs, rowNum) -> new Book(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getBigDecimal("price")
                ));
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        return Optional.empty();
    }
}
