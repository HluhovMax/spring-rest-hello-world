package io.mh.springresthelloworld.controller;

import io.mh.springresthelloworld.exception.BookNotFoundException;
import io.mh.springresthelloworld.exception.BookUnSupportedFieldPatchException;
import io.mh.springresthelloworld.model.Book;
import io.mh.springresthelloworld.model.JdbcBook;
import io.mh.springresthelloworld.repository.JdbcBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * @author mhlukhov on 9/12/2019
 */
@Validated
@RestController
public class BookController {

    @Qualifier("bookRepoImpl")
    @Autowired
    private JdbcBookRepository<Book, Long, Book> bookRepoImpl;

    @Qualifier("jdbcBookRepoImpl")
    @Autowired
    private JdbcBookRepository<JdbcBook, Long, Integer> jdbcBookRepoImpl;

    // Find
    @GetMapping("/books")
    List<Book> findAll() {
        return bookRepoImpl.findAll();
    }

    @GetMapping("/j/books")
    List<JdbcBook> findAllJBooks() {
        return jdbcBookRepoImpl.findAll();
    }

    // Save
    // return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    Book newBook(@Valid @RequestBody Book newBook) {
        return bookRepoImpl.save(newBook);
    }

    // Find
    @GetMapping("/books/{id}")
    Book findOne(@PathVariable @Min(1) Long id) {//jsr 303 annotations
        return bookRepoImpl.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        return bookRepoImpl.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());

                    return bookRepoImpl.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepoImpl.save(newBook);
                });
    }

    // Update author only
    @PatchMapping("/books/{id}")
    Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return bookRepoImpl.findById(id)
                .map(x -> {

                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);

                        return bookRepoImpl.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });
    }

    // Delete
    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepoImpl.deleteById(id);
    }
}
