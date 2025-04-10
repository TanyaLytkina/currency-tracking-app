package ru.arbuzzzikk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.arbuzzzikk.model.Currency;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
}