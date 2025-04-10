package ru.arbuzzzikk.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.arbuzzzikk.model.Currency;

import java.util.List;
import java.util.UUID;
import ru.arbuzzzikk.repository.CurrencyRepository;


@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyRepository repository;

    @GetMapping
    public List<Currency> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Currency getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Currency create(@RequestBody Currency currency) {
        return repository.save(currency);
    }

    @PutMapping("/{id}")
    public Currency update(@PathVariable UUID id, @RequestBody Currency currency) {
        currency.setId(id);
        return repository.save(currency);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}