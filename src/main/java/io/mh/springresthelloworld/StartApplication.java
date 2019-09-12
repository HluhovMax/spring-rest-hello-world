package io.mh.springresthelloworld;

import io.mh.springresthelloworld.model.Book;
import io.mh.springresthelloworld.model.JdbcBook;
import io.mh.springresthelloworld.repository.JdbcBookRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author mhlukhov on 9/12/2019
 */
@Slf4j
@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //    @Qualifier("jdbcBookRepoImpl")
    @Qualifier("namedParameterJdbcBookRepository")
    @Autowired
    JdbcBookRepository<JdbcBook, Long, Integer> bookRepository;

    @Qualifier("bookRepoImpl")
    @Autowired
    JdbcBookRepository<Book, Long, Book> bookRepoImpl;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("StartApplication...");
        runJDBC();
    }

    @SneakyThrows
    void runJDBC() {

        log.info("Creating tables for testing...");

        jdbcTemplate.execute("DROP TABLE IF EXISTS books");
        jdbcTemplate.execute("CREATE TABLE books(" +
                "id SERIAL, name VARCHAR(255), price NUMERIC(15, 2))");
        jdbcTemplate.execute("DROP TABLE IF EXISTS custom_books");
        jdbcTemplate.execute("CREATE TABLE custom_books(" +
                "id SERIAL, name VARCHAR(255),author VARCHAR(255), price NUMERIC(15, 2))");

        bookRepoImpl.save(new Book("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41")));
        bookRepoImpl.save(new Book("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69")));
        bookRepoImpl.save(new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99")));

        List<JdbcBook> books = Arrays.asList(
                new JdbcBook("Thinking in Java", new BigDecimal("46.32")),
                new JdbcBook("Mkyong in Java", new BigDecimal("1.99")),
                new JdbcBook("Getting Clojure", new BigDecimal("37.3")),
                new JdbcBook("Head First Android Development", new BigDecimal("41.19"))
        );

        log.info("[SAVE]");
        books.forEach(book -> {
            log.info("Saving...{}", book.getName());
            bookRepository.save(book);
        });

        // count
        log.info("[COUNT] Total books: {}", bookRepository.count());

        // find all
        log.info("[FIND_ALL] {}", bookRepository.findAll());

        // find by id
        log.info("[FIND_BY_ID] :2L");
        JdbcBook book = bookRepository.findById(2L).orElseThrow(IllegalArgumentException::new);
        log.info("{}", book);

        // find by name (like) and price
        log.info("[FIND_BY_NAME_AND_PRICE] : like '%Java%' and price <= 10");
        log.info("{}", bookRepository.findByNameAndPrice("Java", new BigDecimal(10)));

        // get name (string) by id
        log.info("[GET_NAME_BY_ID] :1L = {}", bookRepository.getNameById(1L));

        // update
        log.info("[UPDATE] :2L :99.99");
        book.setPrice(new BigDecimal("99.99"));
        log.info("rows affected: {}", bookRepository.update(book));

        // delete
        log.info("[DELETE] :3L");
        log.info("rows affected: {}", bookRepository.deleteById(3L));

        // find all
        log.info("[FIND_ALL] {}", bookRepository.findAll());

    }
}
