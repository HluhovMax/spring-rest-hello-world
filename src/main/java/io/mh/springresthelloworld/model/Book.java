package io.mh.springresthelloworld.model;

import io.mh.springresthelloworld.exception.validator.Author;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author mhlukhov on 9/12/2019
 */
@Data
@Entity
@NoArgsConstructor
public class Book {

    @Id
    private Long id;
    @NotEmpty(message = "Please provide a name")
    private String name;
    @Author
    @NotEmpty(message = "Please provide a author")
    private String author;
    @NotNull(message = "Please provide a price")
    @DecimalMin("1.00")
    private BigDecimal price;

    public Book(String name, String author, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book(Long id, String name, String author, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }
}
