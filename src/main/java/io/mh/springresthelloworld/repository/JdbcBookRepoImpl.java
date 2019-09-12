package io.mh.springresthelloworld.repository;

import io.mh.springresthelloworld.model.JdbcBook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author mhlukhov on 9/12/2019
 */
@Repository
public class JdbcBookRepoImpl implements JdbcBookRepository<JdbcBook, Long, Integer> {

    // Spring Boot will create and configure DataSource and JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer count() {
        return jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM books", Integer.class);
    }

    @Override
    public Integer save(JdbcBook book) {
        return jdbcTemplate.update(
                "INSERT INTO books (name, price) VALUES (?,?)",
                book.getName(), book.getPrice()
        );
    }

    @Override
    public Integer update(JdbcBook book) {
        return jdbcTemplate.update(
                "UPDATE books SET price = ? WHERE id = ?",
                book.getPrice(), book.getId()
        );
    }

    @Override
    public Integer deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    @Override
    public List<JdbcBook> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM books",
                (rs, rowNum) -> new JdbcBook(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price")
                ));
    }

    // jdbcTemplate.queryForObject, populates a single object
    @Override
    public List<JdbcBook> findByNameAndPrice(String name, BigDecimal price) {
        return jdbcTemplate.query(
                "select * from books where name like ? and price <= ?",
                new Object[]{"%" + name + "%", price},
                (rs, rowNum) ->
                        new JdbcBook(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price")
                        )
        );
    }

    @Override
    public Optional<JdbcBook> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM books WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new JdbcBook(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price")
                        ))
        );
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select name from books where id = ?",
                new Object[]{id},
                String.class
        );
    }
}
