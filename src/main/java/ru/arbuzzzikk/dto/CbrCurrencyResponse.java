package ru.arbuzzzikk.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CbrCurrencyResponse {
    private Map<String, CbrCurrency> Valute;

    @Data
    public static class CbrCurrency {
        private String CharCode;
        private String Name;
        private double Value;
        private double Previous;
    }
}