databaseChangeLog:
  - changeSet:
      id: 1
      author: arbuzzzikk
      changes:
        - sql:
            sql: |
              CREATE TABLE currency (
                id UUID PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                base_currency VARCHAR(3) NOT NULL,
                price_change_range VARCHAR(50) NOT NULL,
                description TEXT
              );
