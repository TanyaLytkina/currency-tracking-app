databaseChangeLog:
  - changeSet:
      id: 1
      author: arbuzzzikk
      changes:
        - sql:
            sql: |
              CREATE TABLE currency (
                id UUID PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                base_currency VARCHAR(3) NOT NULL,
                price_change_range VARCHAR(20),
                description TEXT
              );
