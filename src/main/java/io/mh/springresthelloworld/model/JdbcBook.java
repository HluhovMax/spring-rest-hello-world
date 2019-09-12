package io.mh.springresthelloworld.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mhlukhov on 9/12/2019
 */
@Data
@AllArgsConstructor
public class JdbcBook {

    private Long id;
    private String name;
    private BigDecimal price;

    public JdbcBook(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
