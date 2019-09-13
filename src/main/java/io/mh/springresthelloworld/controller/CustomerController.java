package io.mh.springresthelloworld.controller;

import io.mh.springresthelloworld.model.Customer;
import io.mh.springresthelloworld.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mhlukhov on 9/13/2019
 */
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository repository;

    @GetMapping
    public List<Customer> findAll() {
        return repository.findAll();
    }
}
